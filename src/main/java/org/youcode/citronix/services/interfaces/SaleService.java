package org.youcode.citronix.services.interfaces;

import org.youcode.citronix.domain.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    Sale createSale(UUID harvestId, UUID clientId, double unitPrice, double quantity);

    List<Sale> getAllSales();

    Sale getSaleById(UUID saleId);

    Sale updateSale(UUID saleId, double unitPrice, double quantity);
}
