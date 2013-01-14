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
 * @Description:收货明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-1
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_ReceiveDetail")
public class ReceiveDetail extends BaseModel {
	// Fields
	private static final long serialVersionUID = -8594815795599620621L;
	/**
	 * 收货明细Id
	 */
	private String receiveDetailId;
	/**
	 * 收货单
	 */
	private Receive receive;
	/**
	 * 收货商品
	 */
	private Product product;
	/**
	 * 商品颜色
	 */
	private DataDictionary color;
	/**
	 * 收货数量
	 */
	private Double qty;
	/**
	 * 收货单价
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
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 采购明细
	 */
	private BuyDetail buyDetail;

	// Constructors

	/** default constructor */
	public ReceiveDetail() {
	}

	/** minimal constructor */
	public ReceiveDetail(String receiveDetailId) {
		this.receiveDetailId = receiveDetailId;
	}

	// Property accessors
	@Id
	@Column(name = "receiveDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getReceiveDetailId() {
		return this.receiveDetailId;
	}

	public void setReceiveDetailId(String receiveDetailId) {
		this.receiveDetailId = receiveDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiveId",nullable=false)
	public Receive getReceive() {
		return this.receive;
	}

	public void setReceive(Receive receive) {
		this.receive = receive;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "qty", precision = 53, scale = 0,nullable=false)
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
	@Transient
	public Double getAmount() {
		this.amount = this.qty*this.price;
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(amount));
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyDetailId")
	public BuyDetail getBuyDetail() {
		return buyDetail;
	}

	public void setBuyDetail(BuyDetail buyDetail) {
		this.buyDetail = buyDetail;
	}

}