package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Field;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    // Find all fields associated with a specific farm
    List<Field> findByFarmId(UUID farmId);

    // Count the number of fields associated with a specific farm
    long countByFarmId(UUID farmId);
}
