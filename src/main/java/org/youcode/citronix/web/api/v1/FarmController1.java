package org.youcode.citronix.web.api.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.services.interfaces.FarmService;
import org.youcode.citronix.services.interfaces.FarmService2;
import org.youcode.citronix.web.vm.FarmVm.FarmResponseVM;
import org.youcode.citronix.web.vm.FarmVm.FarmVM;
import org.youcode.citronix.web.vm.mapper.FarmMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/farms")
public class FarmController1 {

    private final FarmService farmServiceImpl;
    private final FarmService2 farmServiceImpl2;
    private final FarmMapper farmMapper;

    public FarmController1(@Qualifier("farmServiceImpl") FarmService farmServiceImpl, @Qualifier("farmServiceImpl2") FarmService2 farmServiceImpl2, FarmMapper farmMapper) {
        this.farmServiceImpl = farmServiceImpl;
        this.farmServiceImpl2 = farmServiceImpl2;
        this.farmMapper = farmMapper;
    }

    @PostMapping("/impl1")
    public ResponseEntity<FarmResponseVM> saveFarmWithImpl1(@Valid @RequestBody FarmVM farmVM) {
        Farm farm = farmMapper.toEntity(farmVM);
        Farm savedFarm = farmServiceImpl.save(farm);
        return ResponseEntity.ok(farmMapper.toResponseVM(savedFarm));
    }

    @PostMapping("/impl2")
    public ResponseEntity<FarmResponseVM> saveFarmWithImpl2(@Valid @RequestBody FarmVM farmVM) {
        Farm farm = farmMapper.toEntity(farmVM);
        Farm savedFarm = farmServiceImpl2.saveFarmWithFields(farm);
        return ResponseEntity.ok(farmMapper.toResponseVM(savedFarm));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseVM> getFarmById(@PathVariable UUID id) {
        Optional<Farm> farm = farmServiceImpl.getFarmById(id);
        return farm.map(farmMapper::toResponseVM).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/max-area")
    public ResponseEntity<List<FarmResponseVM>> findByMaximumArea(
            @RequestParam @Positive(message = "Maximum area must be positive.") double maxArea) {
        List<Farm> farms = farmServiceImpl2.findByMaximumArea(maxArea);
        List<FarmResponseVM> response = farms.stream().map(farmMapper::toResponseVM).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/max-field-area")
    public ResponseEntity<List<FarmResponseVM>> findByMaximumFieldArea(
            @RequestParam @Positive(message = "Maximum area must be positive.") double area) {
        List<Farm> farms = farmServiceImpl2.findByTotalAreaOfFieldsLessThan4000(area);
        List<FarmResponseVM> response = farms.stream().map(farmMapper::toResponseVM).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
