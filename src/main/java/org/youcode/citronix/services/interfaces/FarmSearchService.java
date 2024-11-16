package org.youcode.citronix.services.interfaces;

import org.youcode.citronix.services.dto.FarmSearchDTO;

import java.util.List;

public interface FarmSearchService {
    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);
}
