package org.youcode.citronix.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestService {
    Harvest createHarvest(UUID fieldId, Season season);

    void validateTree(Tree tree, Season season);

    Page<Harvest> getAllHarvests(Pageable pageable);

    Harvest getHarvestById(UUID harvestId);

    void deleteHarvest(UUID harvestId);
}
