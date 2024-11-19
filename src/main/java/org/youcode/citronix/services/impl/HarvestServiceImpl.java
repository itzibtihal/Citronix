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

    @Override
    public Harvest createHarvest(UUID fieldId, @NotEmpty List<HarvestDetail> harvestDetails, Season season, double totalQuantity) {

        if (harvestRepository.existsByFieldIdAndSeason(fieldId, season)) {
            throw new IllegalArgumentException("A harvest already exists for this field and season.");
        }

        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        validateHarvestDetails(harvestDetails, season, field);

        double calculatedTotalQuantity = harvestDetails.stream()
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();

        Harvest harvest = Harvest.builder()
                .field(field)
                .season(season)
                .totalQuantity(calculatedTotalQuantity)
                .harvestDate(LocalDate.now())
                .build();

        harvestDetails.forEach(hd -> hd.setHarvest(harvest));

        harvest.setHarvestDetails(harvestDetails);

        harvestRepository.save(harvest);

        return harvest;
    }

    private void validateHarvestDetails(List<HarvestDetail> harvestDetails, Season season, Field field) {
        for (HarvestDetail detail : harvestDetails) {

            if (!field.getTrees().contains(detail.getTree())) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " does not belong to the field " + field.getId() + ".");
            }

            if (detail.getTree().getPlantingDate().plusYears(20).isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " is older than 20 years and cannot be harvested.");
            }

            int plantingMonth = detail.getTree().getPlantingDate().getMonthValue();
            if (plantingMonth < 3 || plantingMonth > 5) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " was not planted during the valid period (March to May).");
            }

            if (harvestDetailRepository.existsByTreeIdAndHarvestSeason(detail.getTree().getId(), season)) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " is already included in another harvest for the same season.");
            }
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
