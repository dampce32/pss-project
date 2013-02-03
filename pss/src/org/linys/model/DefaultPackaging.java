package org.linys.model;

import java.text.DecimalFormat;

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
 * @Description:默认商品组装
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_DefaultPackaging")
public class DefaultPackaging extends BaseModel {

	private static final long serialVersionUID = -3021709806927290781L;
	// Fields
	/**
	 * 默认商品组装Id
	 */
	private String defaultPackagingId;
	/**
	 * 隶属商品
	 */
	private Product parentProduct;
	/**
	 * 商品
	 */
	private Product product;
	/**
	 * 数量
	 */
	private Double qty;
	/**
	 * 金额
	 */
	private Double amount;

	// Constructors

	/** default constructor */
	public DefaultPackaging() {
	}

	/** minimal constructor */
	public DefaultPackaging(String defaultPackagingId) {
		this.defaultPackagingId = defaultPackagingId;
	}

	// Property accessors
	@Id
	@Column(name = "defaultPackagingId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getDefaultPackagingId() {
		return this.defaultPackagingId;
	}

	public void setDefaultPackagingId(String defaultPackagingId) {
		this.defaultPackagingId = defaultPackagingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "qty", precision = 22, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentProductId")
	public Product getParentProduct() {
		return parentProduct;
	}
	
	public void setParentProduct(Product parentProduct) {
		this.parentProduct = parentProduct;
	}
	@Transient
	public Double getAmount() {
		this.amount = this.qty*this.product.getBuyingPrice();
		DecimalFormat df = new DecimalFormat("##########0.00");
		return Double.parseDouble(df.format(this.amount));
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}