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
 * @Description:供应商
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Supplier")
public class Supplier extends BaseModel {

	// Fields

	private static final long serialVersionUID = 2383968276751762402L;
	/**
	 * 供应商Id
	 */
	private String supplierId;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 供应商编号
	 */
	private String supplierCode;
	/**
	 * 收货单
	 */
	private Set<Receive> receives = new HashSet<Receive>(0);

	// Constructors

	/** default constructor */
	public Supplier() {
	}

	/** minimal constructor */
	public Supplier(String supplierId) {
		this.supplierId = supplierId;
	}

	/** full constructor */
	public Supplier(String supplierId, String supplierName,
			String supplierCode, Set<Receive> receives) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.supplierCode = supplierCode;
		this.receives = receives;
	}

	// Property accessors
	@Id
	@Column(name = "supplierId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "supplierName", length = 100)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplierCode", length = 50)
	public String getSupplierCode() {
		return this.supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplier")
	public Set<Receive> getReceives() {
		return this.receives;
	}

	public void setReceives(Set<Receive> receives) {
		this.receives = receives;
	}

}