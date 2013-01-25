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
 * @Description:付款明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_PayDetail")
public class PayDetail extends BaseModel {

	// Fields

	private static final long serialVersionUID = 7919296917199662421L;
	/**
	 * 付款明细Id
	 */
	private String payDetailId;
	/**
	 * 付款单
	 */
	private Pay pay;
	/**
	 * 采购单
	 */
	private Buy buy;
	/**
	 * 入库单
	 */
	private Receive receive;
	/**
	 * 退货单
	 */
	private Reject reject;
	/**
	 * 预付单
	 */
	private Prepay prepay;
	
	/**
	 * 付款类型
	 */
	private String payKind;
	/**
	 * 对账单据编号
	 */
	private String sourceCode;
	/**
	 * 单据日期
	 */
	private String sourceDate;
	/**
	 * 应付金额
	 */
	private Double amount;
	/**
	 * 已付金额
	 */
	private Double payedAmount;
	/**
	 * 还需付金额
	 */
	private Double needPayAmount;
	/**
	 * 本次优惠金额
	 */
	private Double discountAmount;
	/**
	 * 本次实付
	 */
	private Double payAmount;

	// Constructors

	/** default constructor */
	public PayDetail() {
	}

	/** minimal constructor */
	public PayDetail(String payDetailId) {
		this.payDetailId = payDetailId;
	}

	// Property accessors
	@Id
	@Column(name = "payDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPayDetailId() {
		return this.payDetailId;
	}

	public void setPayDetailId(String payDetailId) {
		this.payDetailId = payDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payId")
	public Pay getPay() {
		return this.pay;
	}

	public void setPay(Pay pay) {
		this.pay = pay;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiveId")
	public Receive getReceive() {
		return this.receive;
	}

	public void setReceive(Receive receive) {
		this.receive = receive;
	}

	@Column(name = "payKind", length = 20)
	public String getPayKind() {
		return this.payKind;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	@Column(name = "amount", precision = 12, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "payedAmount", precision = 12, scale = 0)
	public Double getPayedAmount() {
		return this.payedAmount;
	}

	public void setPayedAmount(Double payedAmount) {
		this.payedAmount = payedAmount;
	}

	@Column(name = "discountAmount", precision = 12, scale = 0)
	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "payAmount", precision = 12, scale = 0)
	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	@Transient
	public Double getNeedPayAmount() {
		this.needPayAmount =  this.amount-this.payedAmount;
		return needPayAmount;
	}

	public void setNeedPayAmount(Double needPayAmount) {
		this.needPayAmount = needPayAmount;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyId")
	public Buy getBuy() {
		return buy;
	}

	public void setBuy(Buy buy) {
		this.buy = buy;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rejectId")
	public Reject getReject() {
		return reject;
	}

	public void setReject(Reject reject) {
		this.reject = reject;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prepayId")
	public Prepay getPrepay() {
		return prepay;
	}

	public void setPrepay(Prepay prepay) {
		this.prepay = prepay;
	}
	@Column(name = "sourceCode", length=50)
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	@Column(name = "sourceDate", length=20)
	public String getSourceDate() {
		return sourceDate;
	}

	public void setSourceDate(String sourceDate) {
		this.sourceDate = sourceDate;
	}

}