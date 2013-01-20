package org.linys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:客户
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "t_customer")
public class Customer extends BaseModel {

	// Fields
	private static final long serialVersionUID = 3776663314613637676L;
	/**
	 * 客户Id
	 */
	private String customerId;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户订单
	 */
	private Set<Sale> sales = new HashSet<Sale>(0);
	/**
	 * 客户出库单
	 */
	private Set<Deliver> delivers = new HashSet<Deliver>(0);
	/***
	 * 客户退货单
	 */
	private Set<DeliverReject> deliverRejects = new HashSet<DeliverReject>(0);

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(String customerId, String customerCode, String customerName) {
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
	}

	/** full constructor */
	public Customer(String customerId, String customerCode,
			String customerName, Set<Sale> sales, Set<Deliver> delivers,
			Set<DeliverReject> deliverRejects) {
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.sales = sales;
		this.delivers = delivers;
		this.deliverRejects = deliverRejects;
	}

	// Property accessors
	@Id
	@Column(name = "customerId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customerCode", nullable = false, length = 50)
	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@Column(name = "customerName", nullable = false, length = 100)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Sale> getSales() {
		return this.sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Deliver> getDelivers() {
		return this.delivers;
	}

	public void setDelivers(Set<Deliver> delivers) {
		this.delivers = delivers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<DeliverReject> getDeliverRejects() {
		return this.deliverRejects;
	}

	public void setDeliverRejects(Set<DeliverReject> deliverRejects) {
		this.deliverRejects = deliverRejects;
	}

}