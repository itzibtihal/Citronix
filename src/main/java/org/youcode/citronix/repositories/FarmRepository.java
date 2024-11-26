package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Farm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {

    Optional<Farm> findByName(String name);
    List<Farm> findByLocation(String location);

    @Query("SELECT f FROM Farm f WHERE f.area >= :minArea")
    List<Farm> findByMinimumArea(double minArea);
}
