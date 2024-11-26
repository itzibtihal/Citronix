package org.youcode.citronix.web.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.services.interfaces.HarvestService;
import org.youcode.citronix.web.vm.HarvestVm.HarvestVM;
import org.youcode.citronix.web.vm.HarvestVm.HarvestResponseVM;
import org.youcode.citronix.web.vm.mapper.HarvestMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;

    public HarvestController(HarvestService harvestService, HarvestMapper harvestMapper) {
        this.harvestService = harvestService;
        this.harvestMapper = harvestMapper;
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new harvest", description = "Creates a new harvest and associates it with a field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Harvest created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HarvestResponseVM> save(@RequestBody @Valid HarvestVM harvestVM) {

        Harvest savedHarvest = harvestService.createHarvest(
                harvestVM.getFieldId(),
                harvestVM.getSeason()
        );


        HarvestResponseVM response = harvestMapper.harvestToHarvestResponseVM(savedHarvest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @GetMapping("/all")
//    @Operation(summary = "Get all harvests", description = "Retrieves a list of all harvests.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Harvests retrieved successfully"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    public ResponseEntity<List<HarvestResponseVM>> getAllHarvests() {
//        List<Harvest> harvests = harvestService.getAllHarvests();
//        List<HarvestResponseVM> responses = harvestMapper.harvestsToHarvestResponseVMs(harvests);
//        return new ResponseEntity<>(responses, HttpStatus.OK);
//    }

    @GetMapping("/all")
    @Operation(summary = "Get all harvests (paginated)", description = "Retrieves a paginated list of all harvests.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Harvests retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<HarvestResponseVM>> getAllHarvests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Harvest> harvestsPage = harvestService.getAllHarvests(pageable);

        Page<HarvestResponseVM> responses = harvestsPage.map(harvestMapper::harvestToHarvestResponseVM);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @GetMapping("/find/{harvestId}")
    @Operation(summary = "Get a specific harvest by ID", description = "Retrieves the details of a harvest by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Harvest retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Harvest not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HarvestResponseVM> getHarvestById(@PathVariable UUID harvestId) {
        Harvest harvest = harvestService.getHarvestById(harvestId);
        HarvestResponseVM response = harvestMapper.harvestToHarvestResponseVM(harvest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{harvestId}")
    @Operation(summary = "Delete a harvest", description = "Deletes a harvest by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Harvest deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Harvest not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteHarvest(@PathVariable UUID harvestId) {
        harvestService.deleteHarvest(harvestId);
        return new ResponseEntity<>("Harvest deleted successfully.", HttpStatus.OK);
    }
}
