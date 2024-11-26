package org.youcode.citronix.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youcode.citronix.domain.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    Sale createSale(UUID harvestId, UUID clientId, double unitPrice, double quantity);

    Page<Sale> getAllSales(Pageable pageable);

    Sale getSaleById(UUID saleId);

    Sale updateSale(UUID saleId, double unitPrice, double quantity);
}
