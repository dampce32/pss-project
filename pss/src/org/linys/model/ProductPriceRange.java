package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @description 商品价格区间
 * @copyright 福州骏华信息有限公司 (c)2015
 * @createDate 2015-6-16
 * @author 以宋
 * @vesion 1.0
 */
@Entity
@Table(name = "T_ProductPriceRange")
public class ProductPriceRange extends BaseModel {

	// Fields

	private static final long serialVersionUID = 97067480229530114L;
	/**
	 * 商品价格区间Id
	 */
	private String productPriceRangeId;
	/**
	 * 商品
	 */
	private Product product;
	/**
	 * 价格等级
	 */
	private String priceLevel;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 数量下限
	 */
	private Double qtyBegin;
	/**
	 * 数量上限
	 */
	private Double qtyEnd;
	/**
	 * 数量
	 */
	private Double qty;
	
	

	// Constructors

	/** default constructor */
	public ProductPriceRange() {
	}

	// Property accessors
	@Id
	@Column(name = "productPriceRangeId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getProductPriceRangeId() {
		return productPriceRangeId;
	}

	public void setProductPriceRangeId(String productPriceRangeId) {
		this.productPriceRangeId = productPriceRangeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "priceLevel", nullable = false, length = 50)
	public String getPriceLevel() {
		return this.priceLevel;
	}

	public void setPriceLevel(String priceLevel) {
		this.priceLevel = priceLevel;
	}

	@Column(name = "qtyBegin", nullable = false, precision = 22, scale = 0)
	public Double getQtyBegin() {
		return qtyBegin;
	}

	public void setQtyBegin(Double qtyBegin) {
		this.qtyBegin = qtyBegin;
	}
	@Column(name = "qtyEnd", nullable = false, precision = 22, scale = 0)
	public Double getQtyEnd() {
		return qtyEnd;
	}

	public void setQtyEnd(Double qtyEnd) {
		this.qtyEnd = qtyEnd;
	}
	@Transient
	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}
	@Column(name = "price", nullable = false, precision = 22, scale = 0)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}