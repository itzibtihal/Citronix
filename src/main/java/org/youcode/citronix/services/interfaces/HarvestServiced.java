package org.youcode.citronix.services.interfaces;

import jakarta.validation.constraints.NotEmpty;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetail;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestServiced {

    Harvest createHarvest(UUID fieldId, @NotEmpty List<HarvestDetail> harvestDetails, Season season, double totalQuantity);


    Harvest getHarvestById(UUID harvestId);

    void deleteHarvest(UUID harvestId);
}
