package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.web.vm.SaleVm.SaleResponseVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    /**
     * Convert Sale entity to SaleResponseVM.
     */
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "harvestId", source = "harvest.id")
    SaleResponseVM saleToSaleResponseVM(Sale sale);

    /**
     * Convert a list of Sale entities to a list of SaleResponseVMs.
     */
    List<SaleResponseVM> salesToSaleResponseVMs(List<Sale> sales);
}
