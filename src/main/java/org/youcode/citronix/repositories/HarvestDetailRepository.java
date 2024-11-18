package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.youcode.citronix.domain.HarvestDetail;
import org.youcode.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {

    List<HarvestDetail> findByHarvestId(UUID harvestId);
    HarvestDetail findByTreeIdAndHarvestId(UUID treeId, UUID harvestId);
    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd WHERE hd.tree.id = :treeId AND hd.harvest.season = :season")
    boolean existsByTreeIdAndHarvestSeason(@Param("treeId") UUID treeId, @Param("season") Season season);


}
