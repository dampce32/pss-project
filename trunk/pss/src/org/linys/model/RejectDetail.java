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

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:退货明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_RejectDetail")
public class RejectDetail extends BaseModel {

	// Fields
	private static final long serialVersionUID = 7384817427902934615L;
	/**
	 * 退货明细Id
	 */
	private String rejectDetailId;
	/**
	 * 退货单
	 */
	private Reject reject;
	/**
	 * 退货商品
	 */
	private Product product;
	/**
	 * 颜色
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

	// Constructors

	/** default constructor */
	public RejectDetail() {
	}

	/** minimal constructor */
	public RejectDetail(String rejectDetailId) {
		this.rejectDetailId = rejectDetailId;
	}

	// Property accessors
	@Id
	@Column(name = "rejectDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getRejectDetailId() {
		return this.rejectDetailId;
	}

	public void setRejectDetailId(String rejectDetailId) {
		this.rejectDetailId = rejectDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rejectId")
	public Reject getReject() {
		return this.reject;
	}

	public void setReject(Reject reject) {
		this.reject = reject;
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
	@JoinColumn(name = "colorId")
	public DataDictionary getColor() {
		return color;
	}

	public void setColor(DataDictionary color) {
		this.color = color;
	}

	public Double getAmount() {
		this.amount = this.qty*this.price;
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(amount));
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}