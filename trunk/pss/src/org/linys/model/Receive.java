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
 * @Description:采购收货单
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Receive")
public class Receive extends BaseModel {

	// Fields
	private static final long serialVersionUID = -2593429521835791387L;
	/**
	 * 收货id
	 */
	private String receiveId;
	/**
	 * 经手人
	 */
	private Employee employee;
	/**
	 * 仓库
	 */
	private Warehouse warehouse;
	/**
	 * 供应商
	 */
	private Supplier supplier;
	/**
	 * 银行
	 */
	private Bank bank;
	/**
	 * 进货单号
	 */
	private String receiveCode;
	/**
	 * 送货单号
	 */
	private String deliverCode;
	/**
	 * 收货日期
	 */
	private Date receiveDate;
	/**
	 * 其他费用：运费
	 */
	private Double otherAmount;
	/**
	 * 应付金额
	 */
	private Double amount;
	/**
	 * 优惠金额
	 */
	private Double discountAmount;
	/**
	 * 已付金额
	 */
	private Double payAmount;
	/**
	 * 对账金额
	 */
	private Double checkAmount;
	/**
	 * 发票类型
	 */
	private InvoiceType invoiceType;
	/**
	 * 审核状态
	 * 0--未审核
	 * 1--已审核
	 */
	private Integer status;
	/**
	 * 是否付款
	 */
	private Integer isPay;
	/**
	 * 备注
	 */
	private String note;
	private Set<ReceiveDetail> receiveDetails = new HashSet<ReceiveDetail>(0);

	// Constructors

	/** default constructor */
	public Receive() {
	}

	/** minimal constructor */
	public Receive(String receiveId) {
		this.receiveId = receiveId;
	}

	// Property accessors
	@Id
	@Column(name = "receiveId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getReceiveId() {
		return this.receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
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
	@JoinColumn(name = "warehouseId")
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplierId")
	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankId")
	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "receiveCode", length = 50)
	public String getReceiveCode() {
		return this.receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}

	@Column(name = "deliverCode", length = 50,unique = true)
	public String getDeliverCode() {
		return this.deliverCode;
	}

	public void setDeliverCode(String deliverCode) {
		this.deliverCode = deliverCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "receiveDate", length = 10)
	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Column(name = "amount", precision = 53, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "discountAmount", precision = 53, scale = 0)
	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "payAmount", precision = 53, scale = 0)
	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name = "note", length = 50)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "receive")
	public Set<ReceiveDetail> getReceiveDetails() {
		return this.receiveDetails;
	}

	public void setReceiveDetails(Set<ReceiveDetail> receiveDetails) {
		this.receiveDetails = receiveDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoiceTypeId")
	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}
	@Column(name = "isPay")
	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	@Column(name = "otherAmount", precision = 53, scale = 0)
	public Double getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "checkAmount", precision = 53, scale = 0)
	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}

}