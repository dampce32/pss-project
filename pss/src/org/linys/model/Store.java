package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * @Description: 库存
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Store")
public class Store extends BaseModel {

	// Fields

	private static final long serialVersionUID = -2142998724096543688L;
	/**
	 * 库存Id
	 */
	private String storeId;
	/**
	 * 仓库
	 */
	private Warehouse warehouse;
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
	public Store() {
	}

	/** minimal constructor */
	public Store(String storeId) {
		this.storeId = storeId;
	}


	// Property accessors
	@Id
	@Column(name = "storeId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouseId")
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "qty", precision = 53, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "amount", precision = 53, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}