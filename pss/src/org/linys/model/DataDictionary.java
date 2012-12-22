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
 * @Description: 数据字典
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-18
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_DataDictionary")
public class DataDictionary extends BaseModel {

	// Fields
	private static final long serialVersionUID = -8349598887172194695L;
	/**
	 * 数据字典Id
	 */
	private String dataDictionaryId;
	/**
	 * 数据字典类型
	 */
	private String dataDictionaryKind;
	/**
	 * 数据字典名称
	 */
	private String dataDictionaryName;
	private Set<Product> productsForUnitId = new HashSet<Product>(0);
	private Set<Product> productsForColorId = new HashSet<Product>(0);
	private Set<RejectDetail> rejectDetails = new HashSet<RejectDetail>(0);
	private Set<ReceiveDetail> receiveDetails = new HashSet<ReceiveDetail>(0);
	private Set<Product> productsForSizeId = new HashSet<Product>(0);

	// Constructors

	/** default constructor */
	public DataDictionary() {
	}

	/** minimal constructor */
	public DataDictionary(String dataDictionaryId) {
		this.dataDictionaryId = dataDictionaryId;
	}

	/** full constructor */
	public DataDictionary(String dataDictionaryId, String dataDictionaryKind,
			String dataDictionaryName, Set<Product> productsForUnitId,
			Set<Product> productsForColorId, Set<RejectDetail> rejectDetails,
			Set<ReceiveDetail> receiveDetails, Set<Product> productsForSizeId) {
		this.dataDictionaryId = dataDictionaryId;
		this.dataDictionaryKind = dataDictionaryKind;
		this.dataDictionaryName = dataDictionaryName;
		this.productsForUnitId = productsForUnitId;
		this.productsForColorId = productsForColorId;
		this.rejectDetails = rejectDetails;
		this.receiveDetails = receiveDetails;
		this.productsForSizeId = productsForSizeId;
	}

	// Property accessors
	@Id
	@Column(name = "dataDictionaryId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getDataDictionaryId() {
		return this.dataDictionaryId;
	}

	public void setDataDictionaryId(String dataDictionaryId) {
		this.dataDictionaryId = dataDictionaryId;
	}

	@Column(name = "dataDictionaryKind", length = 20)
	public String getDataDictionaryKind() {
		return this.dataDictionaryKind;
	}

	public void setDataDictionaryKind(String dataDictionaryKind) {
		this.dataDictionaryKind = dataDictionaryKind;
	}

	@Column(name = "dataDictionaryName", length = 50)
	public String getDataDictionaryName() {
		return this.dataDictionaryName;
	}

	public void setDataDictionaryName(String dataDictionaryName) {
		this.dataDictionaryName = dataDictionaryName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataDictionaryByUnitId")
	public Set<Product> getProductsForUnitId() {
		return this.productsForUnitId;
	}

	public void setProductsForUnitId(Set<Product> productsForUnitId) {
		this.productsForUnitId = productsForUnitId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataDictionaryByColorId")
	public Set<Product> getProductsForColorId() {
		return this.productsForColorId;
	}

	public void setProductsForColorId(Set<Product> productsForColorId) {
		this.productsForColorId = productsForColorId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataDictionary")
	public Set<RejectDetail> getRejectDetails() {
		return this.rejectDetails;
	}

	public void setRejectDetails(Set<RejectDetail> rejectDetails) {
		this.rejectDetails = rejectDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataDictionary")
	public Set<ReceiveDetail> getReceiveDetails() {
		return this.receiveDetails;
	}

	public void setReceiveDetails(Set<ReceiveDetail> receiveDetails) {
		this.receiveDetails = receiveDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataDictionaryBySizeId")
	public Set<Product> getProductsForSizeId() {
		return this.productsForSizeId;
	}

	public void setProductsForSizeId(Set<Product> productsForSizeId) {
		this.productsForSizeId = productsForSizeId;
	}

}