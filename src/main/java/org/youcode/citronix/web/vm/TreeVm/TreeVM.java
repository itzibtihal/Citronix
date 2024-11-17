package org.youcode.citronix.web.vm.TreeVm;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeVM {

    @NotNull(message = "Field ID is required.")
    private UUID fieldId;

    @NotNull(message = "Planting date is required.")
    @PastOrPresent(message = "Planting date must be in the past or today.")
    private LocalDate plantingDate;

}
