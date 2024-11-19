package org.youcode.citronix.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.repositories.FieldRepository;
import org.youcode.citronix.repositories.FarmRepository;
import org.youcode.citronix.services.impl.FieldServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    private FieldRepository fieldRepository;
    private FarmRepository farmRepository;
    private FieldServiceImpl fieldService;

    @BeforeEach
    void setUp() {
        fieldRepository = mock(FieldRepository.class);
        farmRepository = mock(FarmRepository.class);
        fieldService = new FieldServiceImpl(fieldRepository, farmRepository);
    }

    @Test
    void createField_Success() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field field = new Field();
        field.setArea(400);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarmId(farmId)).thenReturn(2L);
        when(fieldRepository.findByFarmId(farmId)).thenReturn(Arrays.asList());
        when(fieldRepository.save(field)).thenReturn(field);

        Field createdField = fieldService.createField(farmId, field);

        assertNotNull(createdField);
        assertEquals(4, createdField.getMaxTrees());
        verify(fieldRepository).save(field);
    }

    @Test
    void createField_FarmNotFound_ShouldThrowException() {
        UUID farmId = UUID.randomUUID();
        Field field = new Field();

        when(farmRepository.findById(farmId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.createField(farmId, field)
        );

        assertEquals("Farm not found with ID: " + farmId, exception.getMessage());
    }

    @Test
    void createField_FieldAreaExceedsFarmArea_ShouldThrowException() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field field = new Field();
        field.setArea(600);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.createField(farmId, field)
        );

        assertEquals("Field area exceeds 50% of the farm's total area.", exception.getMessage());
    }

    @Test
    void createField_TotalFieldAreaExceedsFarmArea_ShouldThrowException() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field existingField = new Field();
        existingField.setArea(900);

        Field newField = new Field();
        newField.setArea(200);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByFarmId(farmId)).thenReturn(Arrays.asList(existingField));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.createField(farmId, newField)
        );

        assertEquals("Total area of fields must be strictly less than the farm's total area.", exception.getMessage());
    }

    @Test
    void createField_FieldAreaZero_ShouldThrowException() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field field = new Field();
        field.setArea(0);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.createField(farmId, field)
        );

        assertEquals("Field area must be greater than zero.", exception.getMessage());
    }

    @Test
    void createField_FieldAreaNegative_ShouldThrowException() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field field = new Field();
        field.setArea(-50);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.createField(farmId, field)
        );

        assertEquals("Field area cannot be negative.", exception.getMessage());
    }

    @Test
    void createField_FarmWithZeroArea_ShouldThrowException() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(0);

        Field field = new Field();
        field.setArea(100);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.createField(farmId, field)
        );

        assertEquals("Farm area must be greater than zero.", exception.getMessage());
    }

    @Test
    void createField_FieldMaxTreesCalculation_Success() {
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field field = new Field();
        field.setArea(250);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarmId(farmId)).thenReturn(0L);
        when(fieldRepository.findByFarmId(farmId)).thenReturn(Arrays.asList());
        when(fieldRepository.save(field)).thenReturn(field);

        Field createdField = fieldService.createField(farmId, field);

        assertEquals(2, createdField.getMaxTrees());
        verify(fieldRepository).save(field);
    }







    @Test
    void updateField_Success() {
        UUID fieldId = UUID.randomUUID();
        UUID farmId = UUID.randomUUID();
        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(1000);

        Field existingField = new Field();
        existingField.setId(fieldId);
        existingField.setArea(200);
        existingField.setFarm(farm);

        Field updatedField = new Field();
        updatedField.setArea(300);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));
        when(fieldRepository.findByFarmId(farmId)).thenReturn(Arrays.asList(existingField));
        when(fieldRepository.save(existingField)).thenReturn(existingField);

        Field result = fieldService.updateField(fieldId, updatedField);

        assertNotNull(result);
        assertEquals(3, result.getMaxTrees());
        verify(fieldRepository).save(existingField);
    }

    @Test
    void deleteField_Success() {
        UUID fieldId = UUID.randomUUID();
        when(fieldRepository.existsById(fieldId)).thenReturn(true);

        fieldService.deleteField(fieldId);

        verify(fieldRepository).deleteById(fieldId);
    }

    @Test
    void deleteField_FieldNotFound_ShouldThrowException() {
        UUID fieldId = UUID.randomUUID();
        when(fieldRepository.existsById(fieldId)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fieldService.deleteField(fieldId)
        );

        assertEquals("Field not found with ID: " + fieldId, exception.getMessage());
    }

    @Test
    void getFieldsByFarm_Success() {
        UUID farmId = UUID.randomUUID();
        List<Field> fields = Arrays.asList(new Field(), new Field());
        when(fieldRepository.findByFarmId(farmId)).thenReturn(fields);

        List<Field> result = fieldService.getFieldsByFarm(farmId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(fieldRepository).findByFarmId(farmId);
    }

    @Test
    void findAll_Success() {
        Pageable pageable = mock(Pageable.class);
        Page<Field> fieldPage = new PageImpl<>(Arrays.asList(new Field(), new Field()));
        when(fieldRepository.findAll(pageable)).thenReturn(fieldPage);

        Page<Field> result = fieldService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(fieldRepository).findAll(pageable);
    }

    @Test
    void getFieldById_Success() {
        UUID fieldId = UUID.randomUUID();
        Field field = new Field();
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        Optional<Field> result = fieldService.getFieldById(fieldId);

        assertTrue(result.isPresent());
        assertEquals(field, result.get());
        verify(fieldRepository).findById(fieldId);
    }

    @Test
    void getFieldById_FieldNotFound_ShouldReturnEmpty() {
        UUID fieldId = UUID.randomUUID();
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        Optional<Field> result = fieldService.getFieldById(fieldId);

        assertFalse(result.isPresent());
        verify(fieldRepository).findById(fieldId);
    }
}
