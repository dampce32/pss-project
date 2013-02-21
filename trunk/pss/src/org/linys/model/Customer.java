package org.linys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:客户
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Customer")
public class Customer extends BaseModel {

	private static final long serialVersionUID = 3776663314613637676L;
	/**
	 * 客户Id
	 */
	@Id
	@Column(name = "customerId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String customerId;
	
	/**
	 * 客户编号
	 */
	@Column(name = "customerCode", nullable = false, length = 50)
	private String customerCode;
	
	/**
	 * 客户名称
	 */
	@Column(name = "customerName", nullable = false, length = 100)
	private String customerName;
	
	/**
	 * 联系人
	 */
	private String contacter;
	
	/**
	 * 联系电话
	 */
	@Column(name = "phone", length = 50)
	private String phone;
	/**
	 * 传真
	 */
	@Column(name = "fax", length = 50)
	private String fax;
	/**
	 * 地址
	 */
	@Column(name = "addr", length = 1000)
	private String addr;
	
	/**
	 * 客户状态  0:无效   1:有效
	 */
	@Column(updatable=false)
	private Integer status;
	/**
	 * 价格等级
	 * 批发价格--wholesalePrice
	 * VIP价格--vipPrice
	 * 会员价格--memberPrice
	 * 零售价格--salePrice
	 */
	@Column(name = "priceLevel", length = 50)
	private String priceLevel;
	
	public String getPriceLevel() {
		return priceLevel;
	}

	public void setPriceLevel(String priceLevel) {
		this.priceLevel = priceLevel;
	}

	/**
	 * 备注
	 */
	@Column(length=1000)
	private String note;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customerTypeID")
	private CustomerType customerType;
	
	/**
	 * 客户订单
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Sale> sales = new HashSet<Sale>(0);
	
	/**
	 * 客户出库单
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Deliver> delivers = new HashSet<Deliver>(0);
	
	/***
	 * 客户退货单
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<DeliverReject> deliverRejects = new HashSet<DeliverReject>(0);

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	public Set<Sale> getSales() {
		return this.sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}

	public Set<Deliver> getDelivers() {
		return this.delivers;
	}

	public void setDelivers(Set<Deliver> delivers) {
		this.delivers = delivers;
	}

	public Set<DeliverReject> getDeliverRejects() {
		return this.deliverRejects;
	}

	public void setDeliverRejects(Set<DeliverReject> deliverRejects) {
		this.deliverRejects = deliverRejects;
	}

	@Transient
	public void setCustomerTypeID(String customerTypeID){
		if(customerType==null){
			customerType = new CustomerType();
		}
		customerType.setCustomerTypeID(customerTypeID);
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}