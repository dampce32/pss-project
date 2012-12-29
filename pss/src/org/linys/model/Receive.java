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
	 * 发票类型
	 */
	private InvoiceType invoiceType;
	/**
	 * 审核状态
	 */
	private Integer shzt;
	/**
	 * 备注
	 */
	private String note;
	private Set<Pay> paies = new HashSet<Pay>(0);
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

	@Column(name = "deliverCode", length = 50)
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

	@Column(name = "shzt")
	public Integer getShzt() {
		return this.shzt;
	}

	public void setShzt(Integer shzt) {
		this.shzt = shzt;
	}

	@Column(name = "note", length = 50)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "receive")
	public Set<Pay> getPaies() {
		return this.paies;
	}

	public void setPaies(Set<Pay> paies) {
		this.paies = paies;
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

}