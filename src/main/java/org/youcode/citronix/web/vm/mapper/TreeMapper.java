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

    @Mapping(target = "fieldId", source = "field.id")
    TreeVM treeToTreeVM(Tree tree);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    Tree treeVMToTree(TreeVM treeVM);

    @Mapping(target = "age", source = "age")
    @Mapping(target = "productivity", source = "productivity")
    @Mapping(target = "fieldId", source = "field.id")
    TreeResponseVM treeToTreeResponseVM(Tree tree);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    Tree treeResponseVMToTree(TreeResponseVM treeResponseVM);

    default UUID mapFieldId(Tree tree) {
        return tree.getField() != null ? tree.getField().getId() : null;
    }

    Iterable<TreeResponseVM> treesToTreeResponseVMs(Iterable<Tree> trees);
}
