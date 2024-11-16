package org.youcode.citronix.web.api.v1;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.services.dto.FarmSearchDTO;
import org.youcode.citronix.services.interfaces.FarmService;
import org.youcode.citronix.web.vm.FarmVm.FarmResponseVM;
import org.youcode.citronix.web.vm.FarmVm.FarmVM;
import org.youcode.citronix.web.vm.mapper.FarmMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<FarmResponseVM> save(@RequestBody @Valid FarmVM farmVM) {
        Farm farm = farmMapper.toEntity(farmVM);
        Farm savedFarm = farmService.save(farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(savedFarm);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.CREATED);
    }

    @PutMapping("/update/{farm_uuid}")
    public ResponseEntity<FarmResponseVM> update(@PathVariable UUID farm_uuid, @RequestBody @Valid FarmVM farmVM) {
        Farm farm = farmMapper.toEntity(farmVM);
        Farm updatedFarm = farmService.updateFarm(farm_uuid, farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(updatedFarm);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{farm_uuid}")
    public ResponseEntity<String> delete(@PathVariable UUID farm_uuid) {
        boolean isDeleted = farmService.deleteFarm(farm_uuid);
        if (isDeleted) {
            return new ResponseEntity<>("The farm was deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Farm not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{farm_uuid}")
    public ResponseEntity<FarmResponseVM> findById(@PathVariable UUID farm_uuid) {
        Farm farm = farmService.getFarmById(farm_uuid)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + farm_uuid));
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(farm);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FarmResponseVM>> findAll(Pageable pageable) {
        Page<Farm> farms = farmService.findAll(pageable);
        Page<FarmResponseVM> farmResponseVM = farms.map(farmMapper::toResponseVM);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FarmSearchDTO>> searchFarm(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String creationDate) {

        LocalDate creationDateParsed = null;
        if (StringUtils.hasText(creationDate)) {
            try {
                creationDateParsed = LocalDate.parse(creationDate);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        FarmSearchDTO searchDTO = new FarmSearchDTO(name, location, creationDateParsed);
        List<FarmSearchDTO> farms = farmService.findByCriteria(searchDTO);
        return ResponseEntity.ok(farms);
    }
}
