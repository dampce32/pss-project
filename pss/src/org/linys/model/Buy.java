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
 * @Description:采购单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-12
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Buy")
public class Buy extends BaseModel {

	// Fields

	private static final long serialVersionUID = 3865707116069500967L;
	/**
	 * 采购单Id
	 */
	private String buyId;
	/**
	 * 发票类型
	 */
	private InvoiceType invoiceType;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 供应商
	 */
	private Supplier supplier;
	/**
	 * 银行
	 */
	private Bank bank;
	/**
	 * 采购单编号
	 */
	private String buyCode;
	/**
	 * 原始单号
	 */
	private String sourceCode;
	/**
	 * 采购日期
	 */
	private Date buyDate;
	/**
	 * 收货日期
	 */
	private Date receiveDate;
	/**
	 * 其他费用，这里是运费
	 */
	private Double otherAmount;
	/**
	 * 应付费用
	 */
	private Double amount;
	/**
	 * 已付定金
	 */
	private Double payAmount;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 采购单状态
	 */
	private Integer status;
	/**
	 * 采购单明细
	 */
	private Set<BuyDetail> buyDetails = new HashSet<BuyDetail>(0);

	// Constructors

	/** default constructor */
	public Buy() {
	}

	/** minimal constructor */
	public Buy(String buyCode, Date buyDate) {
		this.buyCode = buyCode;
		this.buyDate = buyDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "buyId", unique = true, nullable = false, length = 32)
	public String getBuyId() {
		return this.buyId;
	}

	public void setBuyId(String buyId) {
		this.buyId = buyId;
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
	@JoinColumn(name = "employeeId")
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	@Column(name = "buyCode", nullable = false, length = 50)
	public String getBuyCode() {
		return this.buyCode;
	}

	public void setBuyCode(String buyCode) {
		this.buyCode = buyCode;
	}

	@Column(name = "sourceCode", length = 50)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "buyDate", nullable = false, length = 10)
	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	@Column(name = "otherAmount", precision = 53, scale = 0)
	public Double getOtherAmount() {
		return this.otherAmount;
	}

	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	@Column(name = "amount", precision = 53, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "payAmount", precision = 53, scale = 0)
	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "buy")
	public Set<BuyDetail> getBuyDetails() {
		return this.buyDetails;
	}

	public void setBuyDetails(Set<BuyDetail> buyDetails) {
		this.buyDetails = buyDetails;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "receiveDate", length = 10)
	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

}