// File: org/youcode/citronix/web/api/v1/FieldController.java

package org.youcode.citronix.web.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.exceptions.ResourceNotFoundException;
import org.youcode.citronix.services.interfaces.FieldService;
import org.youcode.citronix.web.vm.FieldVm.FieldVM;
import org.youcode.citronix.web.vm.FieldVm.FieldResponseVM;
import org.youcode.citronix.web.vm.mapper.FieldMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(FieldService fieldService, FieldMapper fieldMapper) {
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new field", description = "Creates a new field and associates it with a farm.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Field created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FieldResponseVM> save(@RequestBody @Valid FieldVM fieldVM) {
        Field field = fieldMapper.toEntity(fieldVM);
        Field savedField = fieldService.createField(fieldVM.getFarmId(), field);
        FieldResponseVM response = fieldMapper.toResponse(savedField);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{field_uuid}")
    @Operation(summary = "Update a field", description = "Updates the details of an existing field by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field updated successfully"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FieldResponseVM> update(@PathVariable UUID field_uuid, @RequestBody @Valid FieldVM fieldVM) {
        Field updatedField = fieldService.updateField(field_uuid, fieldMapper.toEntity(fieldVM));
        FieldResponseVM response = fieldMapper.toResponse(updatedField);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{field_uuid}")
    @Operation(summary = "Delete a field", description = "Deletes a field by its unique UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(@PathVariable UUID field_uuid) {
        fieldService.deleteField(field_uuid);
        return new ResponseEntity<>("Field deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/find/{field_uuid}")
    @Operation(summary = "Find a field by ID", description = "Retrieves the details of a field by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field found successfully"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FieldResponseVM> findById(@PathVariable UUID field_uuid) {
        Field field = fieldService.getFieldById(field_uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + field_uuid));
        FieldResponseVM response = fieldMapper.toResponse(field);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/all")
    @Operation(summary = "Retrieve all fields", description = "Retrieves a paginated list of all fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fields retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<FieldResponseVM>> findAll(Pageable pageable) {
        Page<Field> fields = fieldService.findAll(pageable);
        Page<FieldResponseVM> response = fields.map(fieldMapper::toResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
