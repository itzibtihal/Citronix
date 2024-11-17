package org.youcode.citronix.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youcode.citronix.domain.Field;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldService {
    Field createField(UUID farmId, Field field);
    List<Field> getFieldsByFarm(UUID farmId);
    Field updateField(UUID fieldId, Field updatedField);
    void deleteField(UUID fieldId);
    Page<Field> findAll(Pageable pageable);
    Optional<Field> getFieldById(UUID fieldId);
}
