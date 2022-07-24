package org.dieschnittstelle.ess.jrs;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import java.util.AbstractList;
import java.util.List;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDServiceImpl.class);
	//@Context()
	private ServletContext servletContext;

	private GenericCRUDExecutor<AbstractProduct> readExecFromServletContext() {
		return (GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD");
	}

	public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		// read out the dataAccessor
		// this.productCRUD = (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
		this.servletContext = servletContext;
		logger.debug("read out the productCRUD from the servlet context: " + this.readExecFromServletContext());
	}

	@Override
	public AbstractProduct createProduct(
			AbstractProduct prod) {
		// TODO Auto-generated method stub
		return (AbstractProduct) this.readExecFromServletContext().createObject(prod);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		// TODO Auto-generated method stub
		return this.readExecFromServletContext().readAllObjects();
	}

	@Override
	public AbstractProduct updateProduct(long id,
			AbstractProduct update) {
		// TODO Auto-generated method stub
		AbstractProduct prod = (AbstractProduct) this.readExecFromServletContext().readObject(id);
		if (prod != null) {
			return this.readExecFromServletContext().updateObject(update);
		} else {
			throw new NotFoundException("Couldn't update. The prod with id " + id + " does not exist!");
		}
	}

	@Override
	public boolean deleteProduct(long id) {
		// TODO Auto-generated method stub
		return this.readExecFromServletContext().deleteObject(id);
	}

	@Override
	public AbstractProduct readProduct(long id) {
		// TODO Auto-generated method stub
		AbstractProduct prod = (AbstractProduct) this.readExecFromServletContext().readObject(id);
		if (prod != null) {
			return prod;
		} else {
			return null;
		}
	}
}
