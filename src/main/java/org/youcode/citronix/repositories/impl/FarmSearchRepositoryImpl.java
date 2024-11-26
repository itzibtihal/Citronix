package org.youcode.citronix.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.repositories.FarmSearchRepository;
import org.youcode.citronix.services.dto.FarmSearchDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmSearchRepositoryImpl implements FarmSearchRepository {

    @PersistenceContext
    private EntityManager entityManager; // JPA

    @Override
    public List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO) {
        CriteriaBuilder cb  = entityManager.getCriteriaBuilder();
        CriteriaQuery<FarmSearchDTO> query = cb.createQuery(FarmSearchDTO.class); // select  FarmSearchDTO
        Root<Farm> farmRoot = query.from(Farm.class);// from Farm

        query.select(cb.construct(FarmSearchDTO.class,
                farmRoot.get("name"),
                farmRoot.get("location"),
                farmRoot.get("creationDate")
        ));

        List<Predicate> predicates = new ArrayList<>();// where

        if (StringUtils.hasText(searchDTO.getName())) {
            predicates.add(cb.equal(farmRoot.get("name"), searchDTO.getName()));
        }
        if (StringUtils.hasText(searchDTO.getLocation())) {
            predicates.add(cb.like(cb.lower(farmRoot.get("location")),
                    "%" + searchDTO.getLocation().toLowerCase() + "%"));
        }
        if (searchDTO.getCreationDate() != null) {
            predicates.add(cb.equal(farmRoot.get("creationDate"), searchDTO.getCreationDate()));
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(query).getResultList();
    }

    // example
//    SELECT name, location, creation_date
//    FROM Farm
//    WHERE name = 'Some Name'
//    AND LOWER(location) LIKE '%some location%'
//    AND creation_date = '2024-11-18';

}
