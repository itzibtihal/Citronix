package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetail;
import org.youcode.citronix.web.vm.HarvestVm.HarvestResponseVM;
import org.youcode.citronix.web.vm.HarvestVm.HarvestVM;
import org.youcode.citronix.web.vm.HarvestVm.HarvestDetailResponseVM;
import org.youcode.citronix.web.vm.HarvestVm.HarvestDetailVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    HarvestMapper INSTANCE = Mappers.getMapper(HarvestMapper.class);

    /**
     * Convert Harvest entity to HarvestResponseVM (ViewModel)
     */
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "harvestDetails", source = "harvestDetails")
    HarvestResponseVM harvestToHarvestResponseVM(Harvest harvest);

    List<HarvestResponseVM> harvestsToHarvestResponseVMs(List<Harvest> harvests);

    /**
     * Convert HarvestVM to Harvest entity
     */
    @Mapping(target = "field.id", source = "fieldId")
    @Mapping(target = "harvestDetails", source = "harvestDetails")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvestDate", ignore = true)
    Harvest harvestVMToHarvest(HarvestVM harvestVM);

    /**
     * Convert HarvestDetailVM to HarvestDetail entity
     */
    @Mapping(target = "tree.id", source = "treeId")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    HarvestDetail harvestDetailVMToHarvestDetail(HarvestDetailVM harvestDetailVM);

    List<HarvestDetail> harvestDetailVMsToHarvestDetails(List<HarvestDetailVM> harvestDetailVMs);

    /**
     * Convert HarvestDetail entity to HarvestDetailResponseVM (ViewModel)
     */
    @Mapping(target = "treeId", source = "tree.id")
    @Mapping(target = "quantity", source = "quantity")
    HarvestDetailResponseVM harvestDetailToHarvestDetailResponseVM(HarvestDetail harvestDetail);

    List<HarvestDetailResponseVM> harvestDetailsToHarvestDetailResponseVMs(List<HarvestDetail> harvestDetails);
}
