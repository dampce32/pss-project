package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:发票类型
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_InvoiceType")
public class InvoiceType extends BaseModel {

	// Fields
	private static final long serialVersionUID = 4320560020202765487L;
	/**
	 * 发票类型Id
	 */
	private String invoiceTypeId;
	/**
	 * 发票类型名称
	 */
	private String invoiceTypeName;

	// Constructors

	/** default constructor */
	public InvoiceType() {
	}

	/** full constructor */
	public InvoiceType(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	// Property accessors
	@Id
	@Column(name = "invoiceTypeId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getInvoiceTypeId() {
		return this.invoiceTypeId;
	}

	public void setInvoiceTypeId(String invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}

	@Column(name = "invoiceTypeName", length = 50)
	public String getInvoiceTypeName() {
		return this.invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

}