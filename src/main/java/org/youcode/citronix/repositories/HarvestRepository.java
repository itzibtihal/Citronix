package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {

    @Query("SELECT COUNT(h) > 0 FROM Harvest h WHERE h.season = :season AND h.id IN " +
            "(SELECT hd.harvest.id FROM HarvestDetail hd WHERE hd.tree.field.id = :fieldId)")
    boolean existsByFieldIdAndSeason(@Param("fieldId") UUID fieldId, @Param("season") Season season);

    @Query("SELECT h FROM Harvest h WHERE h.season = :season")
    List<Harvest> findAllBySeason(@Param("season") Season season);
}
