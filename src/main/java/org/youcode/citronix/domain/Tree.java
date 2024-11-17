package org.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @Transient
    public int getAge() {
        return plantingDate != null ? Period.between(plantingDate, LocalDate.now()).getYears() : 0;
    }

    @Transient
    public double getProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age >= 3 && age <= 10) {
            return 12.0;
        } else if (age > 10 && age <= 20) {
            return 20.0;
        } else {
            return 0.0;
        }
    }

    @Transient
    public boolean isPlantingSeason() {
        if (plantingDate == null) {
            return false;
        }
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }

}
