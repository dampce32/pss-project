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
 * @Description:组装明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_PackagingDetail")
public class PackagingDetail extends BaseModel {

	// Fields
	private static final long serialVersionUID = -3017931311344841644L;
	/**
	 * 商品组装明细Id
	 */
	private String packagingDetailId;
	/**
	 * 商品组装
	 */
	private Packaging packaging;
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
	 * 单价
	 */
	private Double price;
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

	// Constructors

	/** default constructor */
	public PackagingDetail() {
	}

	// Property accessors
	@Id
	@Column(name = "packagingDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPackagingDetailId() {
		return this.packagingDetailId;
	}

	public void setPackagingDetailId(String packagingDetailId) {
		this.packagingDetailId = packagingDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packagingId", nullable = false)
	public Packaging getPackaging() {
		return this.packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouseId", nullable = false)
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "qty", nullable = false, precision = 22, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "price", nullable = false, precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "note1", length = 50)
	public String getNote1() {
		return this.note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	@Column(name = "note2", length = 50)
	public String getNote2() {
		return this.note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	@Column(name = "note3", length = 50)
	public String getNote3() {
		return this.note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

}