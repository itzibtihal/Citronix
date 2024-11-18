package org.youcode.citronix.web.vm.SaleVm;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

@Data
public class SaleVM {

    @NotNull(message = "Harvest ID is required")
    private UUID harvestId;

    @NotNull(message = "Client ID is required")
    private UUID clientId;

    @Positive(message = "Unit price must be greater than zero")
    private double unitPrice;

    @DecimalMin(value = "0.1", message = "Quantity must be at least 0.1")
    private double quantity;
}
