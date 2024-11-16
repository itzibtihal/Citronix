package org.youcode.citronix.services.dto;

import lombok.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmSearchDTO {

    private String name;
    private String location;
    private LocalDate creationDate;

}
