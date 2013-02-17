package org.linys.model;

import java.util.HashSet;
import java.util.Set;

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
	 * 联系人
	 */
	private String contact;
	/**
	 * 地址
	 */
	private String addr;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * E-mail
	 */
	private String email;
	/**
	 * 备注1
	 */
	private String note1;
	/**
	 * 备注2
	 */
	private String note2;
	/**
	 * 备注3
	 */
	private String note3;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
	public Set<Receive> getReceives() {
		return this.receives;
	}

	public void setReceives(Set<Receive> receives) {
		this.receives = receives;
	}
	@Column(name = "contact", length = 50)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column(name = "addr", length = 100)
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column(name = "phone", length = 20)
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "fax", length = 20)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "note1", length = 100)
	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}
	@Column(name = "note2", length = 100)
	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}
	@Column(name = "note3", length = 100)
	public String getNote3() {
		return note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

}