package org.youcode.citronix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Tree;
import java.util.UUID;


@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {

    long countByFieldId(UUID fieldId);

    Page<Tree> findByFieldId(UUID fieldId, Pageable pageable);

}
