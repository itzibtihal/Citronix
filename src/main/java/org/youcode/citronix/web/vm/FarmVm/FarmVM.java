package org.youcode.citronix.web.vm.FarmVm;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmVM {

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must not exceed 100 characters.")
    private String name;

    @NotBlank(message = "Location is required.")
    @Size(max = 150, message = "Location must not exceed 150 characters.")
    private String location;

    @Positive(message = "Total area must be positive.")
    @DecimalMin(value = "0.1", inclusive = true, message = "Total area must be at least 0.1 hectares.")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Total area must not exceed 1000 hectares.")
    private double area;

    @NotNull(message = "Creation date is required.")
    @PastOrPresent(message = "Creation date must be in the past or today.")
    private LocalDate creationDate;

}

