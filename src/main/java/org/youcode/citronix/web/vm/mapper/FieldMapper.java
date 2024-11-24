package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.web.vm.FieldVm.FieldResponseVM;
import org.youcode.citronix.web.vm.FieldVm.FieldVM;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    FieldMapper INSTANCE = Mappers.getMapper(FieldMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "maxTrees", ignore = true)
    Field toEntity(FieldVM fieldVM);

    @Mapping(source = "farm.id", target = "farmId")
    FieldResponseVM toResponse(Field field);


    default UUID mapFarmId(Field field) {
        return field.getFarm() != null ? field.getFarm().getId() : null;
    }
}
