package org.youcode.citronix.web.vm.HarvestVm;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HarvestVM {

    @NotNull(message = "Field ID is required.") // Ensures the fieldId is not null
    private UUID fieldId;

    @NotNull(message = "Season is required.") // Ensures the season is provided
    private Season season;

    @NotEmpty(message = "Harvest details cannot be empty.") // Ensures the harvestDetails list is not empty
    private List<@Valid HarvestDetailVM> harvestDetails; // Validates each item in the list as well
}
