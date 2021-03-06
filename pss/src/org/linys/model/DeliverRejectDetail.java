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
 * @Description:客户退货单明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_DeliverRejectDetail")
public class DeliverRejectDetail extends BaseModel {

	// Fields
	private static final long serialVersionUID = 6052728842247365710L;
	/**
	 * 客户退货单明细Id
	 */
	private String deliverRejectDetailId;
	/**
	 * 销售退货单
	 */
	private DeliverReject deliverReject;
	/**
	 * 商品
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
	public DeliverRejectDetail() {
	}

	/** minimal constructor */
	public DeliverRejectDetail(String deliverRejectDetailId) {
		this.deliverRejectDetailId = deliverRejectDetailId;
	}

	// Property accessors
	@Id
	@Column(name = "deliverRejectDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getDeliverRejectDetailId() {
		return this.deliverRejectDetailId;
	}

	public void setDeliverRejectDetailId(String deliverRejectDetailId) {
		this.deliverRejectDetailId = deliverRejectDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverRejectId")
	public DeliverReject getDeliverReject() {
		return this.deliverReject;
	}

	public void setDeliverReject(DeliverReject deliverReject) {
		this.deliverReject = deliverReject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "qty", precision = 12, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "price", precision = 12, scale = 0)
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

	@Transient
	public Double getAmount(){
		Double amount = qty*price;
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(amount));
	}
}