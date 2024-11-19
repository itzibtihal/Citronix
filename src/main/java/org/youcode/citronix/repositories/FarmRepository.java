package org.youcode.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.youcode.citronix.domain.Farm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {

    Optional<Farm> findByName(String name);
    List<Farm> findByLocation(String location);

    @Query("SELECT f FROM Farm f WHERE f.area >= :minArea")
    List<Farm> findByMinimumArea(double minArea);

    @Query("SELECT f FROM Farm f WHERE f.area <= :maxArea")
    List<Farm> findByMaximumArea(double maxArea);

    @Query("SELECT f FROM Farm f WHERE (SELECT SUM(field.area) FROM Field field WHERE field.farm = f) < :area")
    List<Farm> findByTotalAreaOfFieldsLessThan(@Param("area") double area);
}
