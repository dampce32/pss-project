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
 * 
 * @Description: 收款明细
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Entity
@Table(name="T_ReceiptDetail")
public class ReceiptDetail extends BaseModel {

	private static final long serialVersionUID = 3252782651857569358L;

	/**
	 * 付款明细Id
	 */
	private String receiptDetailId;
	/**
	 * 付款单
	 */
	private Receipt receipt;
	/**
	 * 销售单
	 */
	private Sale sale;
	/**
	 * 出库单
	 */
	private Deliver deliver;
	/**
	 * 退货单
	 */
	private DeliverReject deliverReject;
	/**
	 * 预收单
	 */
	private PreReceipt preReceipt;
	
	/**
	 * 收款类型
	 */
	private String receiptKind;
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
	 * 已收金额
	 */
	private Double receiptedAmount;
	/**
	 * 本次优惠金额
	 */
	private Double discountAmount;
	/**
	 * 本次实付
	 */
	private Double receiptAmount;
	
	@Id
	@Column(name = "receiptDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getReceiptDetailId() {
		return receiptDetailId;
	}
	public void setReceiptDetailId(String receiptDetailId) {
		this.receiptDetailId = receiptDetailId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiptId")
	public Receipt getReceipt() {
		return receipt;
	}
	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saleId")
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverId")
	public Deliver getDeliver() {
		return deliver;
	}
	public void setDeliver(Deliver deliver) {
		this.deliver = deliver;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverRejectId")
	public DeliverReject getDeliverReject() {
		return deliverReject;
	}
	public void setDeliverReject(DeliverReject deliverReject) {
		this.deliverReject = deliverReject;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preReceiptId")
	public PreReceipt getPreReceipt() {
		return preReceipt;
	}
	public void setPreReceipt(PreReceipt preReceipt) {
		this.preReceipt = preReceipt;
	}
	public String getReceiptKind() {
		return receiptKind;
	}
	public void setReceiptKind(String receiptKind) {
		this.receiptKind = receiptKind;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getSourceDate() {
		return sourceDate;
	}
	public void setSourceDate(String sourceDate) {
		this.sourceDate = sourceDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getReceiptedAmount() {
		return receiptedAmount;
	}
	public void setReceiptedAmount(Double receiptedAmount) {
		this.receiptedAmount = receiptedAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getReceiptAmount() {
		return receiptAmount;
	}
	public void setReceiptAmount(Double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	@Transient
	public Double getNeedReceiptAmount() {
		return amount-receiptedAmount;
	}
}
