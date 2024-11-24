package org.youcode.citronix.services.interfaces;

import org.youcode.citronix.domain.Tree;

import java.util.Optional;
import java.util.UUID;

public interface TreeService {

    Tree createTree(UUID fieldId, Tree tree);

    Tree updateTree(UUID treeId, Tree updatedTree);

    void deleteTree(UUID treeId);

    Optional<Tree> getTreeById(UUID treeId);

    Iterable<Tree> getTreesByField(UUID fieldId);
}
