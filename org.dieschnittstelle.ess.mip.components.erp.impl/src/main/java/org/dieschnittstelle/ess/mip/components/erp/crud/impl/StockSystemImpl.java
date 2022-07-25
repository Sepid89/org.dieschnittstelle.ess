package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.PointOfSaleCRUD;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class StockSystemImpl implements StockSystem {
    @Inject
    private PointOfSaleCRUD posCRUD;
    @Inject
    private StockItemCRUD stockItemCRUD;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUD.readStockItem(product, pos);

        if (stockItem != null) {
            stockItem.setUnits(stockItem.getUnits() + units);
            stockItemCRUD.updateStockItem(stockItem);
        } else {
            stockItem = new StockItem(product, pos, units);
            stockItemCRUD.createStockItem(stockItem);
        }

    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUD.readStockItem(product, pos);

        if (stockItem != null) {
            stockItem.setUnits(stockItem.getUnits() - units);
            stockItemCRUD.updateStockItem(stockItem);
        }
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        List<StockItem> stockItems = stockItemCRUD.readStockItemsForPointOfSale(pos);
        if (stockItems != null) {
            return stockItems.stream().map(StockItem::getProduct).collect(Collectors.toList());
        } else {
            return new ArrayList<IndividualisedProductItem>();
        }
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        List<StockItem> stockItems = stockItemCRUD.readAllStockItems();
        List<IndividualisedProductItem> individualisedProductItems = stockItems.stream().map(StockItem::getProduct).collect(Collectors.toList());
        return individualisedProductItems;
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUD.readStockItem(product, pos);
        return stockItem.getUnits();
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        List<StockItem> stockItems = stockItemCRUD.readAllStockItems();
        int units = stockItems.stream().mapToInt(StockItem::getUnits).sum();
        if (units > 0) {
            return units;
        } else {
            return 0;
        }
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        List<StockItem> stockItems = stockItemCRUD.readStockItemsForProduct(product);
        return stockItems.stream().map(StockItem::getId).collect(Collectors.toList());
    }
}
