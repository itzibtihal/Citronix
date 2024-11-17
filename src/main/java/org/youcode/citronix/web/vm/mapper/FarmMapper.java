package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.services.dto.FarmSearchDTO;
import org.youcode.citronix.web.vm.FarmVm.FarmResponseVM;
import org.youcode.citronix.web.vm.FarmVm.FarmVM;
import org.youcode.citronix.web.vm.search.FarmSearchVM;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toEntity(FarmVM farmVM);
    FarmVM toVM(Farm farm);
    FarmResponseVM toResponseVM(Farm farm);
    List<FarmSearchVM> toSearchVM(List<FarmSearchDTO> searchDTO);
}
