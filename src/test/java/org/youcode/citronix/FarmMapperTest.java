package org.youcode.citronix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.web.vm.FarmVm.FarmResponseVM;
import org.youcode.citronix.web.vm.FarmVm.FarmVM;
import org.youcode.citronix.web.vm.mapper.FarmMapper;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FarmMapperTest {

    @Autowired
    private FarmMapper farmMapper;

    @Test
    public void testToEntity() {
        FarmVM farmVM = new FarmVM("ibtifarm", "string", 1000, LocalDate.now());
        Farm farm = farmMapper.toEntity(farmVM);
        assertEquals(farmVM.getArea(), farm.getArea());
    }

    @Test
    public void testToResponseVM() {
        Farm farm = new Farm(UUID.randomUUID(), "ibtifarm", "string", 1000, LocalDate.now());
        FarmResponseVM responseVM = farmMapper.toResponseVM(farm);
        assertEquals(farm.getArea(), responseVM.getArea());
    }
}

