package org.youcode.citronix.domain;


import jakarta.persistence.*;
import lombok.*;
import org.youcode.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    private LocalDate harvestDate;

    @Enumerated(EnumType.STRING)
    private Season season;

    private double totalQuantity;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

}
