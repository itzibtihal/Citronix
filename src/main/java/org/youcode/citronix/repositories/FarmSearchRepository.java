package org.youcode.citronix.repositories;


import org.springframework.stereotype.Repository;
import org.youcode.citronix.services.dto.FarmSearchDTO;

import java.util.List;

@Repository
public interface FarmSearchRepository {

    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);

}