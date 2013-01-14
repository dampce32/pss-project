package org.linys.model;

import java.text.DecimalFormat;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:采购单明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-12
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_BuyDetail")
public class BuyDetail extends BaseModel {

	// Fields

	private static final long serialVersionUID = 2980721466158618803L;
	/**
	 * 采购明细Id
	 */
	private String buyDetailId;
	/**
	 * 采购单
	 */
	private Buy buy;
	/**
	 * 采购商品
	 */
	private Product product;
	/**
	 * 商品颜色
	 */
	private DataDictionary color;
	/**
	 * 数量
	 */
	private Double qty;
	/**
	 * 单价
	 */
	private Double price;
	/**
	 * 金额
	 */
	private Double amount;
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
	 * 已收货数量
	 */
	private Double receiveQty;
	/**
	 * 是否已全部收货
	 */
	private Integer isReceiveAll;
	/**
	 * 收货明细
	 */
	private Set<ReceiveDetail> receiveDetails = new HashSet<ReceiveDetail>(0);
 
	// Constructors

	/** default constructor */
	public BuyDetail() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "buyDetailId", unique = true, nullable = false, length = 32)
	public String getBuyDetailId() {
		return this.buyDetailId;
	}

	public void setBuyDetailId(String buyDetailId) {
		this.buyDetailId = buyDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyId")
	public Buy getBuy() {
		return this.buy;
	}

	public void setBuy(Buy buy) {
		this.buy = buy;
	}

	@Column(name = "qty", precision = 53, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "price", precision = 53, scale = 0)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colorId")
	public DataDictionary getColor() {
		return color;
	}

	public void setColor(DataDictionary color) {
		this.color = color;
	}
	@Transient
	public Double getAmount() {
		this.amount = this.qty*this.price;
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(amount));
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Double receiveQty) {
		this.receiveQty = receiveQty;
	}

	public Integer getIsReceiveAll() {
		return isReceiveAll;
	}

	public void setIsReceiveAll(Integer isReceiveAll) {
		this.isReceiveAll = isReceiveAll;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "buyDetail")
	public Set<ReceiveDetail> getReceiveDetails() {
		return receiveDetails;
	}

	public void setReceiveDetails(Set<ReceiveDetail> receiveDetails) {
		this.receiveDetails = receiveDetails;
	}

}