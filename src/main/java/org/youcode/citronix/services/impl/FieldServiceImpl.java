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
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + farmId));
        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= 10) {
            throw new IllegalArgumentException("Farm cannot have more than 10 fields.");
        }

        if (field.getArea() > (farm.getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }


        double totalFieldArea = fieldRepository.findByFarmId(farmId).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + field.getArea() >= farm.getArea()) {
            throw new IllegalArgumentException("Total area of fields must be strictly less than the farm's total area.");
        }

        field.setMaxTrees((int) (field.getArea() / 100.0));

        field.setFarm(farm);


        return fieldRepository.save(field);
    }

    @Override
    public Field updateField(UUID fieldId, Field updatedField) {
        Field existingField = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldId));


        existingField.setArea(updatedField.getArea());

        if (updatedField.getArea() > (existingField.getFarm().getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }


        double totalFieldArea = fieldRepository.findByFarmId(existingField.getFarm().getId()).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + updatedField.getArea() >= existingField.getFarm().getArea()) {
            throw new IllegalArgumentException("Total area of fields must be strictly less than the farm's total area.");
        }


        existingField.setMaxTrees((int) (updatedField.getArea() / 100.0));

        return fieldRepository.save(existingField);
    }


    @Override
    public List<Field> getFieldsByFarm(UUID farmId) {
        return fieldRepository.findByFarmId(farmId);
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
