package org.youcode.citronix.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.services.dto.FarmSearchDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {
    Farm save(Farm farm);

    Page<Farm> findAll(Pageable pageable);

    Optional<Farm> getFarmById(UUID id);

    Farm updateFarm(UUID id, Farm updatedFarm);

    boolean deleteFarm(UUID id);

    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);
}
