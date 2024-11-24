package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetail;
import org.youcode.citronix.web.vm.HarvestVm.HarvestResponseVM;
import org.youcode.citronix.web.vm.HarvestVm.HarvestVM;
import org.youcode.citronix.web.vm.HarvestVm.HarvestDetailResponseVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    HarvestMapper INSTANCE = Mappers.getMapper(HarvestMapper.class);

    @Mapping(target = "harvestDetails", source = "harvestDetails")
    @Mapping(target = "fieldId", expression = "java(harvest.getHarvestDetails().isEmpty() ? null : harvest.getHarvestDetails().get(0).getTree().getField().getId())")
    HarvestResponseVM harvestToHarvestResponseVM(Harvest harvest);


    List<HarvestResponseVM> harvestsToHarvestResponseVMs(List<Harvest> harvests);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvestDate", ignore = true)
    // @Mapping(target = "field", ignore = true)  Field is resolved in the service layer
    @Mapping(target = "harvestDetails", ignore = true)
    Harvest harvestVMToHarvest(HarvestVM harvestVM);


    @Mapping(target = "treeId", source = "tree.id")
    HarvestDetailResponseVM harvestDetailToHarvestDetailResponseVM(HarvestDetail harvestDetail);


    List<HarvestDetailResponseVM> harvestDetailsToHarvestDetailResponseVMs(List<HarvestDetail> harvestDetails);
}
