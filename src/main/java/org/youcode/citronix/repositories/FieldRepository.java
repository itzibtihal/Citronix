package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Field;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {

    List<Field> findByFarmId(UUID farmId);

    long countByFarmId(UUID farmId);

    @Query("SELECT f FROM Field f JOIN FETCH f.trees WHERE f.id = :fieldId")
    Optional<Field> findByIdWithTrees(@Param("fieldId") UUID fieldId);

}
