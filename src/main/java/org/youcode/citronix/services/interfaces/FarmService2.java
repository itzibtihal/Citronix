package org.youcode.citronix.services.interfaces;

import org.youcode.citronix.domain.Farm;

import java.util.List;

public interface FarmService2 {
    List<Farm> findByMaximumArea(double maxArea);

    Farm saveFarmWithFields(Farm farm);

    List<Farm> findByTotalAreaOfFieldsLessThan4000(double area);
}
