package org.youcode.citronix.services.impl;

import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.repositories.FarmRepository;
import org.youcode.citronix.services.interfaces.FarmSearchService;
import org.youcode.citronix.services.interfaces.FarmService2;

import java.util.List;
import java.util.Optional;

@Service
public class FarmServiceImpl2 implements FarmService2 {

    private final FarmRepository farmRepository;

    public FarmServiceImpl2(FarmRepository farmRepository, FarmSearchService farmSearchService) {
        this.farmRepository = farmRepository;
    }


    @Override
    public List<Farm> findByMaximumArea(double maxArea) {
        if (maxArea <= 0) {
            throw new IllegalArgumentException("Maximum area must be greater than 0.");
        }
        return farmRepository.findByMaximumArea(maxArea);
    }


    @Override
    public List<Farm> findByTotalAreaOfFieldsLessThan4000(@Positive double area) {
        return farmRepository.findByTotalAreaOfFieldsLessThan(area);
    }



    @Override
    public Farm saveFarmWithFields(Farm farm) {

        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new RuntimeException("Farm already exists");
        }

        if (farm.getFields() == null || farm.getFields().isEmpty()) {
            throw new IllegalArgumentException("A farm must have at least one field.");
        }else {
            for (Field field : farm.getFields()){
                field.setFarm(farm);
            }
        }
        return farmRepository.save(farm);

    }












}
