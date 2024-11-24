package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Tree;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {

    long countByFieldId(UUID fieldId);

    Iterable<Tree> findByFieldId(UUID fieldId);

}
