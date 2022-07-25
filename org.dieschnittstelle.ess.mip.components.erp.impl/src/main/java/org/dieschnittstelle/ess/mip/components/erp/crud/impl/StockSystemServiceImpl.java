package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystemService;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.transaction.Transactional;
import java.util.List;

@Alternative
@Priority(Interceptor.Priority.APPLICATION+10)
@ApplicationScoped
@Transactional
@Logged
public class StockSystemServiceImpl implements StockSystemService {
    @Inject
    private StockSystem stockSystem;
    @Inject
    private ProductCRUD productCRUD;

    @Override
    public void addToStock(long productId, long pointOfSaleId, int units) {
        IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
        stockSystem.addToStock(product, pointOfSaleId, units);
    }

    @Override
    public void removeFromStock(long productId, long pointOfSaleId, int units) {
        IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
        stockSystem.removeFromStock(product, pointOfSaleId, units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        List<IndividualisedProductItem> products = stockSystem.getProductsOnStock(pointOfSaleId);
        return products;
    }

    @Override
    public int getUnitsOnStock(long productId, long pointOfSaleId) {
        IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
        return stockSystem.getUnitsOnStock(product, pointOfSaleId);
    }

    @Override
    public List<Long> getPointsOfSale(long productId) {
        IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
        return stockSystem.getPointsOfSale(product);
    }

}
