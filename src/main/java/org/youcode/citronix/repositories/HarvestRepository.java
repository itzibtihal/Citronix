package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {

    Optional<Harvest> findById(UUID harvestId);

    List<Harvest> findByFieldId(UUID fieldId);

    boolean existsByFieldIdAndSeason(UUID fieldId, Season season);
}
