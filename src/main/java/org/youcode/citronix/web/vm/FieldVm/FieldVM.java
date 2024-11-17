package org.youcode.citronix.web.vm.FieldVm;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldVM {

    @Positive(message = "Field area must be positive.")
    @DecimalMin(value = "0.1", message = "Field area must be at least 0.1 hectares.")
    private double area;

    @NotNull(message = "Farm ID is required.")
    private UUID farmId;
}