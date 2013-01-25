package org.linys.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:销售出库单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "t_deliver")
public class Deliver extends BaseModel {

	private static final long serialVersionUID = 6366814112348818446L;
	// Fields
	/**
	 * 销售出库单Id
	 */
	private String deliverId;
	/**
	 * 发票类型
	 */
	private InvoiceType invoiceType;
	/**
	 * 快递
	 */
	private Express express;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 客户
	 */
	private Customer customer;
	/**
	 * 仓库
	 */
	private Warehouse warehouse;
	/**
	 * 银行
	 */
	private Bank bank;
	/**
	 * 出库单号
	 */
	private String deliverCode;
	/**
	 * 原始单号
	 */
	private String sourceCode;
	/**
	 * 出库日期
	 */
	private Date deliverDate;
	/**
	 * 应收金额
	 */
	private Double amount;
	/**
	 * 优惠金额
	 */
	private Double discountAmount;
	/**
	 * 已收金额
	 */
	private Double receiptedAmount;
	
	/**
	 * 对账金额
	 */
	private Double checkAmount;
	
	/**
	 * 出库单状态
	 */
	private Integer status;
	/**
	 * 快递单号
	 */
	private String expressCode;
	/**
	 * 是否已收款
	 */
	private Integer isReceipt;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 出库明细
	 */
	private Set<DeliverDetail> deliverDetails = new HashSet<DeliverDetail>(0);

	// Constructors

	/** default constructor */
	public Deliver() {
	}

	/** minimal constructor */
	public Deliver(String deliverId) {
		this.deliverId = deliverId;
	}

	// Property accessors
	@Id
	@Column(name = "deliverId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getDeliverId() {
		return this.deliverId;
	}

	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoiceTypeId")
	public InvoiceType getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expressId")
	public Express getExpress() {
		return this.express;
	}

	public void setExpress(Express express) {
		this.express = express;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId")
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId")
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	@JoinColumn(name = "bankId")
	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "deliverCode", length = 50)
	public String getDeliverCode() {
		return this.deliverCode;
	}

	public void setDeliverCode(String deliverCode) {
		this.deliverCode = deliverCode;
	}

	@Column(name = "sourceCode", length = 50)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deliverDate", length = 10)
	public Date getDeliverDate() {
		return this.deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	@Column(name = "amount", precision = 12, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "discountAmount", precision = 12, scale = 0)
	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "receiptedAmount", precision = 12, scale = 0)
	public Double getReceiptedAmount() {
		return this.receiptedAmount;
	}

	public void setReceiptedAmount(Double receiptedAmount) {
		this.receiptedAmount = receiptedAmount;
	}

	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "expressCode", length = 50)
	public String getExpressCode() {
		return this.expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	@Column(name = "isReceipt")
	public Integer getIsReceipt() {
		return this.isReceipt;
	}

	public void setIsReceipt(Integer isReceipt) {
		this.isReceipt = isReceipt;
	}

	@Column(name = "note", length = 50)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deliver")
	public Set<DeliverDetail> getDeliverDetails() {
		return this.deliverDetails;
	}

	public void setDeliverDetails(Set<DeliverDetail> deliverDetails) {
		this.deliverDetails = deliverDetails;
	}

}