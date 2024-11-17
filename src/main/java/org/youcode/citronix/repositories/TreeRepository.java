package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Tree;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {
    // Custom method to count trees in a specific field
    long countByFieldId(UUID fieldId);

    // Optionally, add custom queries as needed
    Iterable<Tree> findByFieldId(UUID fieldId);

}
