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
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.services.interfaces.SaleService;
import org.youcode.citronix.web.vm.SaleVm.SaleVM;
import org.youcode.citronix.web.vm.SaleVm.SaleResponseVM;
import org.youcode.citronix.web.vm.mapper.SaleMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SaleService saleService;
    private final SaleMapper saleMapper;

    public SaleController(SaleService saleService, SaleMapper saleMapper) {
        this.saleService = saleService;
        this.saleMapper = saleMapper;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new sale", description = "Records a new sale with details such as harvest, client, unit price, and quantity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SaleResponseVM> createSale(@RequestBody @Valid SaleVM saleVM) {
        Sale sale = saleService.createSale(saleVM.getHarvestId(), saleVM.getClientId(), saleVM.getUnitPrice(), saleVM.getQuantity());
        SaleResponseVM response = saleMapper.saleToSaleResponseVM(sale);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all sales (paginated)", description = "Retrieves a paginated list of all recorded sales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<SaleResponseVM>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Sale> salesPage = saleService.getAllSales(pageable);

        Page<SaleResponseVM> responses = salesPage.map(saleMapper::saleToSaleResponseVM);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @GetMapping("/{saleId}")
    @Operation(summary = "Get sale by ID", description = "Retrieves details of a specific sale by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SaleResponseVM> getSaleById(@PathVariable UUID saleId) {
        Sale sale = saleService.getSaleById(saleId);
        SaleResponseVM response = saleMapper.saleToSaleResponseVM(sale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update/{saleId}")
    @Operation(summary = "Update a sale", description = "Updates the details of an existing sale, ensuring sufficient harvest quantity is available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient quantity available"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SaleResponseVM> updateSale(@PathVariable UUID saleId, @RequestBody @Valid SaleVM saleVM) {
        Sale updatedSale = saleService.updateSale(saleId, saleVM.getUnitPrice(), saleVM.getQuantity());
        SaleResponseVM response = saleMapper.saleToSaleResponseVM(updatedSale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
