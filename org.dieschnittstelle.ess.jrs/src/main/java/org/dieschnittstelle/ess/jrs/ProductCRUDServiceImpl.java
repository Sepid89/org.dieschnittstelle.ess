package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.util.AbstractList;
import java.util.List;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {

	@Context()
	private ServletContext servletContext;

	private GenericCRUDExecutor<AbstractProduct> readExecFromServletContext() {
		return (GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD");
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
		return this.readExecFromServletContext().updateObject(update);
	}

	@Override
	public boolean deleteProduct(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IndividualisedProductItem readProduct(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
