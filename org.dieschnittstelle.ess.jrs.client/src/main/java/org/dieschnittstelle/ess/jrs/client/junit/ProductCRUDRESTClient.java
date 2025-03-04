package org.dieschnittstelle.ess.jrs.client.junit;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import org.dieschnittstelle.ess.jrs.IProductCRUDService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.ClientBuilder;

public class ProductCRUDRESTClient {

	private IProductCRUDService serviceProxy;
	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDRESTClient.class);

	public ProductCRUDRESTClient() throws Exception {


		/*
		 * TODO: JRS2: create a client for the web service using ResteasyClientBuilder and ResteasyWebTarget
		 */
		serviceProxy = null;

		ResteasyClient rcb = (ResteasyClient) ClientBuilder.newBuilder().build();
		ResteasyWebTarget target = rcb.target("http://localhost:8080/api");
		serviceProxy = target.proxy(IProductCRUDService.class);
	}

	public AbstractProduct createProduct(AbstractProduct prod) {
		AbstractProduct created = serviceProxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	// TODO: activate this method for testing JRS3
	public AbstractProduct createCampaign(Campaign prod) {
		AbstractProduct created = serviceProxy.createProduct((Campaign) prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<?> readAllProducts() {
		return serviceProxy.readAllProducts();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return serviceProxy.updateProduct(update.getId(),(IndividualisedProductItem)update);
	}

	public boolean deleteProduct(long id) {
		return serviceProxy.deleteProduct(id);
	}

	public AbstractProduct readProduct(long id) {
		AbstractProduct abPr = serviceProxy.readProduct(id);
		return abPr;
	}

}
