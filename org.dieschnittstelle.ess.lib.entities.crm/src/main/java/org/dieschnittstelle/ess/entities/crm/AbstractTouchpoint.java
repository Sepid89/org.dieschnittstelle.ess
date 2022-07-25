package org.dieschnittstelle.ess.entities.crm;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDEntity;
import org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler.KLASSNAME_PROPERTY;

/**
 * this is an abstraction over different touchpoints (with pos being the most
 * prominent one, others may be a service center, website, appsite, etc.)
 * 
 * @author kreutel
 * 
 */
// jpa annotations
@Entity
// inheritance
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="touchpointType", discriminatorType=DiscriminatorType.STRING)
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS);
//@Inheritance(strategy=InheritanceType.JOINED);
@SequenceGenerator(name = "touchpoint_sequence", sequenceName = "touchpoint_id_sequence")

// jrs/jackson annotations
// note that the value of the constant KLASSNAME_PROPERTY in this implementation is "@class". It
// specifies the name of the json property the value of which will be the classname of the respective
// concrete subclass of the abstract class, and thus allows to create correctly typed java objects
// based on the untyped json data.
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property=KLASSNAME_PROPERTY)
@JsonIgnoreProperties(ignoreUnknown = true)
// jsonb annotations
@JsonbTypeDeserializer(JsonbJsonTypeInfoHandler.class)
@JsonbTypeSerializer(JsonbJsonTypeInfoHandler.class)
@Schema(name="AbstractTouchpoint")
public abstract class AbstractTouchpoint implements Serializable, GenericCRUDEntity {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(AbstractTouchpoint.class);


	/**
	 * 
	 */
	private static final long serialVersionUID = 5207353251688141788L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "touchpoint_sequence")
	protected long id;

	/**
	 * the id of the PointOfSale from the erp data
	 */
	protected long erpPointOfSaleId;

	/**
	 * the name of the touchpoint
	 */
	protected String name;

	/*
	 * TODO JWS2: kommentieren Sie @XmlTransient aus
	 */
	@XmlTransient
	@ManyToMany
	@JsonbTransient
	private Collection<Customer> customers = new HashSet<Customer>();

	@XmlTransient
	@OneToMany(mappedBy="touchpoint")
	@JsonbTransient
	private Collection<CustomerTransaction> transactions;


	public AbstractTouchpoint() {
		logger.debug("<constructor>");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getErpPointOfSaleId() {
		return erpPointOfSaleId;
	}

	public void setErpPointOfSaleId(long erpPointOfSaleId) {
		this.erpPointOfSaleId = erpPointOfSaleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// here, this annotation must be set on accessor methods rather than on the attributes themselves
	@JsonIgnore
	@JsonbTransient
	public Collection<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(HashSet<Customer> customers) {
		this.customers = customers;
	}

	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}

	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof AbstractTouchpoint)) {
			return false;
		}

		return this.getId() == ((AbstractTouchpoint) obj).getId();
	}

	@JsonIgnore
	@JsonbTransient
	public Collection<CustomerTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Collection<CustomerTransaction> transactions) {
		this.transactions = transactions;
	}

}
