package org.youcode.citronix.services.interfaces;

import jakarta.validation.constraints.NotEmpty;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetail;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestService {

    /**
     * Create a new harvest, ensuring that only one harvest exists per season for a field.
     * @param fieldId The ID of the field for the harvest.
     * @param harvestDetails List of harvest details, including harvested quantity per tree.
     * @param season The season in which the harvest is made.
     * @param totalQuantity The total quantity harvested (in kg).
     * @return The created Harvest object.
     */
    Harvest createHarvest(UUID fieldId, @NotEmpty List<HarvestDetail> harvestDetails, Season season, double totalQuantity);

    /**
     * Get all harvests.
     * @return List of all Harvest objects.
     */
    List<Harvest> getAllHarvests();

    /**
     * Get a specific harvest by its ID.
     * @param harvestId The ID of the harvest.
     * @return The Harvest object.
     */
    Harvest getHarvestById(UUID harvestId);

    /**
     * Delete a harvest by its ID.
     * @param harvestId The ID of the harvest to delete.
     */
    void deleteHarvest(UUID harvestId);
}
