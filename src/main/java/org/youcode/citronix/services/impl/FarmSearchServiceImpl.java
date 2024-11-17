package org.youcode.citronix.services.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.repositories.FarmSearchRepository;
import org.youcode.citronix.services.dto.FarmSearchDTO;
import org.youcode.citronix.services.interfaces.FarmSearchService;

import java.util.List;

@Service
public class FarmSearchServiceImpl implements FarmSearchService {

    private final FarmSearchRepository farmSearchRepository;

    public FarmSearchServiceImpl(FarmSearchRepository farmSearchRepository) {
        this.farmSearchRepository = farmSearchRepository;
    }

    @Override
    public List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO) {
        return farmSearchRepository.findByCriteria(searchDTO);
    }
}
