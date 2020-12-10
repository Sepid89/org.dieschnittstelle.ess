package org.dieschnittstelle.ess.ejb.webapi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.CampaignTrackingImplSingleton;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.CustomerTrackingImpl;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.TouchpointAccess;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.TouchpointAccessImpl;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.crud.CustomerCRUD;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.crud.CustomerCRUDImpl;
import org.dieschnittstelle.ess.ejb.ejbmodule.crm.crud.CustomerTransactionCRUDImpl;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/*
 * Note that in order for the webapi to work correctly, the option
 * -Dresteasy.preferJacksonOverJsonB=true must be set when starting
 * jboss, otherwise JsonTypeInfo will not be considered, see https://docs.jboss.org/resteasy/docs/4.3.1.Final/userguide/html/JAX-RS_2.1_additions.html
 */
@ApplicationPath("/api")
public class RESTWebAPIRoot extends Application {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RESTWebAPIRoot.class);
	
	public RESTWebAPIRoot() {
		logger.info("<constructor>");
	}

	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet(Arrays.asList(
				TouchpointAccessImpl.class,
				CustomerCRUDImpl.class,
				CustomerTransactionCRUDImpl.class,
				CustomerTrackingImpl.class,
				CampaignTrackingImplSingleton.class));
	}
}
