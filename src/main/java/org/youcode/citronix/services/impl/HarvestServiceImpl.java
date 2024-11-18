package org.youcode.citronix.services.impl;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetail;
import org.youcode.citronix.domain.enums.Season;
import org.youcode.citronix.repositories.FieldRepository;
import org.youcode.citronix.repositories.HarvestDetailRepository;
import org.youcode.citronix.repositories.HarvestRepository;
import org.youcode.citronix.services.interfaces.HarvestService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;
    private final FieldRepository fieldRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository,
                              HarvestDetailRepository harvestDetailRepository,
                              FieldRepository fieldRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
        this.fieldRepository = fieldRepository;
    }

    /**
     * Create a new harvest, ensuring that only one harvest exists per season for a field.
     * @param fieldId The ID of the field for the harvest.
     * @param harvestDetails List of harvest details, including harvested quantity per tree.
     * @param season The season in which the harvest is made.
     * @param totalQuantity The total quantity harvested (in kg).
     * @return The created Harvest object.
     */

    @Override
    public Harvest createHarvest(UUID fieldId, @NotEmpty List<HarvestDetail> harvestDetails, Season season, double totalQuantity) {
        // Validate that there is only one harvest per season for a field
        if (harvestRepository.existsByFieldIdAndSeason(fieldId, season)) {
            throw new IllegalArgumentException("A harvest already exists for this field and season.");
        }

        // Retrieve the field by its ID
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        // Calculate the totalQuantity from harvestDetails
        double calculatedTotalQuantity = harvestDetails.stream()
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();

        // Create a new harvest object
        Harvest harvest = Harvest.builder()
                .field(field)
                .season(season)
                .totalQuantity(calculatedTotalQuantity) // Use calculated total quantity
                .harvestDate(LocalDate.now()) // Set the current date as the harvest date
                .build();

        // Set the Harvest reference for each HarvestDetail
        harvestDetails.forEach(hd -> hd.setHarvest(harvest));

        // Set the harvest details to the harvest object
        harvest.setHarvestDetails(harvestDetails);

        // Save the harvest, which will cascade to HarvestDetails due to CascadeType.ALL
        harvestRepository.save(harvest);

        return harvest;
    }

    /**
     * Get all harvests.
     * @return List of all Harvest objects.
     */
    @Override
    public List<Harvest> getAllHarvests() {
        return harvestRepository.findAll();
    }

    /**
     * Get a specific harvest by its ID.
     * @param harvestId The ID of the harvest.
     * @return The Harvest object.
     */
    @Override
    public Harvest getHarvestById(UUID harvestId) {
        return harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));
    }

    /**
     * Delete a harvest by its ID.
     * @param harvestId The ID of the harvest to delete.
     */
    @Override
    public void deleteHarvest(UUID harvestId) {
        // Check if the harvest exists before attempting to delete it
        if (!harvestRepository.existsById(harvestId)) {
            throw new IllegalArgumentException("Harvest not found");
        }

        // Delete the harvest
        harvestRepository.deleteById(harvestId);
    }
}
