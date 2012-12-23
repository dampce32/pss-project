package org.linys.model;

// default package

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

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:商品类别
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_ProductType")
public class ProductType extends BaseModel {

	// Fields
	private static final long serialVersionUID = 4463211279152456115L;
	/**
	 * 商品类别Id
	 */
	private String productTypeId;
	/**
	 * 父商品类型
	 */
	private ProductType parentProductType;
	/**
	 * 商品类别名称
	 */
	private String productTypeName;
	/**
	 * 商品类别编号
	 */
	private String productTypeCode;
	
	/**
	 * 是否为叶子节点
	 */
	private Boolean isLeaf;
	/**
	 * 子商品类别
	 */
	private Set<ProductType> childrenProductTypes = new HashSet<ProductType>(0);

	// Constructors

	/** default constructor */
	public ProductType() {
	}

	/** minimal constructor */
	public ProductType(String productTypeId) {
		this.productTypeId = productTypeId;
	}


	// Property accessors
	@Id
	@Column(name = "productTypeID", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentProductTypeID")
	public ProductType getParentProductType() {
		return this.parentProductType;
	}

	public void setParentProductType(ProductType parentProductType) {
		this.parentProductType = parentProductType;
	}

	@Column(name = "productTypeName", length = 50)
	public String getProductTypeName() {
		return this.productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	@Column(name = "productTypeCode", length = 50)
	public String getProductTypeCode() {
		return this.productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentProductType")
	public Set<ProductType> getChildrenProductTypes() {
		return this.childrenProductTypes;
	}

	public void setChildrenProductTypes(Set<ProductType> childrenProductTypes) {
		this.childrenProductTypes = childrenProductTypes;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}