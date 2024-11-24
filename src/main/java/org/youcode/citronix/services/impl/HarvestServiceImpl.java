package org.youcode.citronix.services.impl;

import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.*;
import org.youcode.citronix.domain.enums.Season;
import org.youcode.citronix.repositories.FieldRepository;
import org.youcode.citronix.repositories.HarvestDetailRepository;
import org.youcode.citronix.repositories.HarvestRepository;
import org.youcode.citronix.services.interfaces.HarvestService;


import java.time.LocalDate;
import java.util.ArrayList;
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


    @Override
    public Harvest createHarvest(UUID fieldId, Season season) {

        // Step 1: Validate that no harvest exists for the field and season
        if (harvestRepository.existsByFieldIdAndSeason(fieldId, season)) {
            throw new IllegalArgumentException("A harvest already exists for this field and season.");
        }

        // Step 2: Retrieve the field and its trees
        Field field = fieldRepository.findByIdWithTrees(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        // Step 3: Create an empty list to hold HarvestDetail entries
        List<HarvestDetail> harvestDetails = new ArrayList<>();
        AtomicDouble totalQuantity = new AtomicDouble(0);

        // Step 4: Iterate over the trees in the field
        field.getTrees().forEach(tree -> {
            validateTree(tree, season);

            double productivity = tree.getProductivity();
            if (productivity <= 0) {
                throw new IllegalArgumentException("Tree " + tree.getId() + " has non-positive productivity: " + productivity);
            }

            HarvestDetail detail = HarvestDetail.builder()
                    .tree(tree)
                    .quantity(productivity)
                    .build();

            harvestDetails.add(detail);
            totalQuantity.addAndGet(detail.getQuantity());
        });

        // Step 5: Create the Harvest object
        Harvest harvest = Harvest.builder()
                .harvestDate(LocalDate.now())
                .season(season)
                .totalQuantity(totalQuantity.get())
                .build();

        // Step 6: Link the HarvestDetail entries to the Harvest
        harvestDetails.forEach(detail -> detail.setHarvest(harvest));

        // Step 7: Save the Harvest
        harvest.setHarvestDetails(harvestDetails);
        return harvestRepository.save(harvest);
    }

    @Override
    public void validateTree(Tree tree, Season season) {
        if (tree.getPlantingDate() == null) {
            throw new IllegalArgumentException("Tree " + tree.getId() + " has no planting date set.");
        }

        if (tree.getPlantingDate().plusYears(20).isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Tree " + tree.getId() + " is older than 20 years and cannot be harvested.");
        }

        int plantingMonth = tree.getPlantingDate().getMonthValue();
        if (plantingMonth < 3 || plantingMonth > 5) {
            throw new IllegalArgumentException("Tree " + tree.getId() + " was not planted during the valid period (March to May).");
        }

        if (harvestDetailRepository.existsByTreeIdAndHarvestSeason(tree.getId(), season)) {
            throw new IllegalArgumentException("Tree " + tree.getId() + " is already included in another harvest for the same season.");
        }
    }


    @Override
    public List<Harvest> getAllHarvests() {
        return harvestRepository.findAll();
    }


    @Override
    public Harvest getHarvestById(UUID harvestId) {
        return harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));
    }


    @Override
    public void deleteHarvest(UUID harvestId) {
        if (!harvestRepository.existsById(harvestId)) {
            throw new IllegalArgumentException("Harvest not found");
        }
        harvestRepository.deleteById(harvestId);
    }
}
