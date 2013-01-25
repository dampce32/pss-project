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
 * @Description:客户销售单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Sale")
public class Sale extends BaseModel {

	private static final long serialVersionUID = -4975793041146451113L;
	// Fields
	/**
	 * 销售单Id
	 */
	private String saleId;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 客户
	 */
	private Customer customer;
	/**
	 * 银行
	 */
	private Bank bank;
	/**
	 * 订单编号
	 */
	private String saleCode;
	/**
	 * 原始编号
	 */
	private String sourceCode;
	/**
	 * 订单日期
	 */
	private Date saleDate;
	/**
	 * 订单交期
	 */
	private Date deliverDate;
	/**
	 * 其他费用(运费)
	 */
	private Double otherAmount;
	/**
	 * 应收金额
	 */
	private Double amount;
	/**
	 * 预收定金
	 */
	private Double receiptedAmount;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 客户销售单状态   0:未审核   1:审核
	 */
	private Integer status;
	/**
	 * 销售单明细
	 */
	private Set<SaleDetail> saleDetails = new HashSet<SaleDetail>(0);

	// Constructors

	/** default constructor */
	public Sale() {
	}

	/** minimal constructor */
	public Sale(String saleId, String saleCode) {
		this.saleId = saleId;
		this.saleCode = saleCode;
	}

	// Property accessors
	@Id
	@Column(name = "saleId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getSaleId() {
		return this.saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
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
	@JoinColumn(name = "bankId")
	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "saleCode", nullable = false, length = 50)
	public String getSaleCode() {
		return this.saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	@Column(name = "sourceCode", length = 50)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "saleDate", length = 10)
	public Date getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deliverDate", length = 10)
	public Date getDeliverDate() {
		return this.deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	@Column(name = "otherAmount", precision = 12, scale = 0)
	public Double getOtherAmount() {
		return this.otherAmount;
	}

	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	@Column(name = "amount", precision = 12, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "note", length = 500)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sale")
	public Set<SaleDetail> getSaleDetails() {
		return this.saleDetails;
	}

	public void setSaleDetails(Set<SaleDetail> saleDetails) {
		this.saleDetails = saleDetails;
	}

	@Column(name = "receiptedAmount", precision = 12, scale = 0)
	public Double getReceiptedAmount() {
		return receiptedAmount;
	}

	public void setReceiptedAmount(Double receiptedAmount) {
		this.receiptedAmount = receiptedAmount;
	}

}