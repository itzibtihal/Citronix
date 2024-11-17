package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.web.vm.TreeVm.TreeResponseVM;
import org.youcode.citronix.web.vm.TreeVm.TreeVM;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    TreeMapper INSTANCE = Mappers.getMapper(TreeMapper.class);

    // Convert Tree entity to TreeVM (ViewModel)
    @Mapping(target = "fieldId", source = "field.id")  // Map field ID explicitly
    TreeVM treeToTreeVM(Tree tree);

    // Convert TreeVM to Tree entity (for create/update)
    @Mapping(target = "id", ignore = true) // Ignore id when mapping from TreeVM to Tree for create
    @Mapping(target = "field", ignore = true) // Ignore field, will be set separately in service
    Tree treeVMToTree(TreeVM treeVM);

    // Convert Tree entity to TreeResponseVM (Response model)
    @Mapping(target = "age", source = "age")  // Use the calculated 'age' from the entity
    @Mapping(target = "productivity", source = "productivity")  // Use the calculated 'productivity' from the entity
    @Mapping(target = "fieldId", source = "field.id")  // Map field ID explicitly
    TreeResponseVM treeToTreeResponseVM(Tree tree);

    // Convert TreeResponseVM to Tree entity (used if you want to map back from response VM)
    @Mapping(target = "id", ignore = true)  // Ignore id when mapping from TreeResponseVM to Tree
    @Mapping(target = "field", ignore = true)  // Ignore field, will be set separately in service
    Tree treeResponseVMToTree(TreeResponseVM treeResponseVM);

    // Custom method directly used in the mapper
    default UUID mapFieldId(Tree tree) {
        return tree.getField() != null ? tree.getField().getId() : null;
    }

    // Convert a collection of Tree entities to a collection of TreeResponseVMs
    Iterable<TreeResponseVM> treesToTreeResponseVMs(Iterable<Tree> trees);
}
