package org.youcode.citronix.services.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Client;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.repositories.ClientRepository;
import org.youcode.citronix.repositories.HarvestRepository;
import org.youcode.citronix.repositories.SaleRepository;
import org.youcode.citronix.services.interfaces.SaleService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final ClientRepository clientRepository;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestRepository harvestRepository, ClientRepository clientRepository) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Sale createSale(UUID harvestId, UUID clientId, double unitPrice, double quantity) {
        // Step 1: Retrieve the harvest
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));

        // Step 2: Calculate the total quantity sold (sum_saled)
        double sumSaled = saleRepository.findTotalQuantitySoldByHarvestId(harvestId).orElse(0.0);

        // Step 3: Check if the requested quantity is available
        double quantityLeft = harvest.getTotalQuantity() - sumSaled;
        if (quantity > quantityLeft) {
            throw new IllegalArgumentException("Harvest only has " + quantityLeft + " kg available.");
        }

        // Step 4: Retrieve the client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Step 5: Calculate revenue and create the sale
        double revenue = unitPrice * quantity;

        Sale sale = Sale.builder()
                .harvest(harvest)
                .client(client)
                .unitPrice(unitPrice)
                .quantity(quantity)
                .revenue(revenue)
                .saleDate(LocalDate.now())
                .build();

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(UUID saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    }

    @Override
    public Sale updateSale(UUID saleId, double unitPrice, double quantity) {
        // Step 1: Retrieve the existing sale
        Sale existingSale = saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found"));

        // Step 2: Retrieve the associated harvest
        Harvest harvest = existingSale.getHarvest();

        // Step 3: Calculate sum_saled excluding the current sale's quantity
        double sumSaledExcludingCurrent = saleRepository.findTotalQuantitySoldByHarvestId(harvest.getId()).orElse(0.0) - existingSale.getQuantity();

        // Step 4: Check if the updated quantity is available
        double quantityLeft = harvest.getTotalQuantity() - sumSaledExcludingCurrent;
        if (quantity > quantityLeft) {
            throw new IllegalArgumentException("Harvest only has " + quantityLeft + " kg available for sale.");
        }

        // Step 5: Update sale details
        existingSale.setUnitPrice(unitPrice);
        existingSale.setQuantity(quantity);
        existingSale.setRevenue(unitPrice * quantity);

        // Step 6: Save and return the updated sale
        return saleRepository.save(existingSale);
    }

}
