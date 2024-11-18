package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.HarvestDetail;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {

    List<HarvestDetail> findByHarvestId(UUID harvestId);
    HarvestDetail findByTreeIdAndHarvestId(UUID treeId, UUID harvestId);

}
