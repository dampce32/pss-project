package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @Description: 客户类型entity
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Entity
@Table(name="t_CustomerType")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CustomerType extends BaseModel{

	private static final long serialVersionUID = -3712506982901176883L;
	
	@Id
	@Column(name = "dataDictionaryId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	/**
	 *  ID
	 */
	private String customerTypeID;
	
	/**
	 * 客户类型编号
	 */
	@Column(length=100)
	private String customerTypeCode;
	
	/**
	 * 客户类型名称
	 */
	@Column(length=500)
	private String customerTypeName;
	
	@Column(length=1000)
	private String note;

	public String getCustomerTypeID() {
		return customerTypeID;
	}

	public void setCustomerTypeID(String customerTypeID) {
		this.customerTypeID = customerTypeID;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

}
