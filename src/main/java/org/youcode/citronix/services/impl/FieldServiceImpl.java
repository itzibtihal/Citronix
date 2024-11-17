package org.youcode.citronix.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.repositories.FieldRepository;
import org.youcode.citronix.repositories.FarmRepository;
import org.youcode.citronix.services.interfaces.FieldService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public Field createField(UUID farmId, Field field) {
        // Step 1: Validate that the farm exists
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + farmId));

        // Step 2: Check if the farm already has 10 fields
        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= 10) {
            throw new IllegalArgumentException("Farm cannot have more than 10 fields.");
        }

        // Step 3: Check if the field area exceeds 50% of the farm's area
        if (field.getArea() > (farm.getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }

        // Step 4: Calculate maxTrees based on the field's area
        field.setMaxTrees((int) (field.getArea() / 100.0));

        // Step 5: Associate the field with the farm
        field.setFarm(farm);

        // Step 6: Save the field
        return fieldRepository.save(field);
    }

    @Override
    public List<Field> getFieldsByFarm(UUID farmId) {
        return fieldRepository.findByFarmId(farmId);
    }

    @Override
    public Field updateField(UUID fieldId, Field updatedField) {
        Field existingField = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldId));

        // Update properties
        existingField.setArea(updatedField.getArea());

        // Check area limit and recalculate maxTrees
        if (updatedField.getArea() > (existingField.getFarm().getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }
        existingField.setMaxTrees((int) (updatedField.getArea() / 100.0));

        return fieldRepository.save(existingField);
    }

    @Override
    public void deleteField(UUID fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new IllegalArgumentException("Field not found with ID: " + fieldId);
        }
        fieldRepository.deleteById(fieldId);
    }

    @Override
    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }
    @Override
    public Optional<Field> getFieldById(UUID fieldId) {
        return fieldRepository.findById(fieldId);
    }

}
