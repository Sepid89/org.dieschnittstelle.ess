package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Alternative
@Priority(Interceptor.Priority.APPLICATION+10)
@ApplicationScoped
@Transactional
@Logged
public class ProductCRUDImpl implements ProductCRUD {
    @Inject
    @EntityManagerProvider.ERPDataAccessor
    private EntityManager em;

    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDImpl.class);

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        logger.info("Hi createProduct");
        em.persist(prod);
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
//        IndividualisedProductItem item = new IndividualisedProductItem();
//        item.setName("product");
//        return Arrays.asList(item);
        Query readAllQuery = em.createQuery("SELECT p FROM AbstractProduct p"); // JPA Query
        List<AbstractProduct> abstractProducts = (List<AbstractProduct>) readAllQuery.getResultList();
        return abstractProducts;
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return em.merge(update);
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return em.find(AbstractProduct.class, productID);
    }

    @Override
    public boolean deleteProduct(long productID) {
        em.remove(em.find(AbstractProduct.class, productID));
        return true;
    }

    @Override
    public List<Campaign> getCampaignsForProduct(long productID) {
        return new ArrayList<>();
    }
}
