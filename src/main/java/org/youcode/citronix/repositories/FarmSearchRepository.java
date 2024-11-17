package org.youcode.citronix.repositories;


import org.youcode.citronix.services.dto.FarmSearchDTO;

import java.util.List;

public interface FarmSearchRepository {

    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);

}