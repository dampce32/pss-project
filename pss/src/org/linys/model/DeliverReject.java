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
 * @Description:出库明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_DeliverReject")
public class DeliverReject extends BaseModel {

	// Fields
	private static final long serialVersionUID = -2977233618789517853L;
	/**
	 * 销售退货单Id
	 */
	private String deliverRejectId;
	/**
	 * 发票类型
	 */
	private InvoiceType invoiceType;
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
	 * 销售退货单编号
	 */
	private String deliverRejectCode;
	/**
	 * 客户订单号
	 */
	private String sourceCode;
	/**
	 * 退货日期
	 */
	private Date deliverRejectDate;
	/**
	 * 应退金额
	 */
	private Double amount;
	/**
	 * 实退金额
	 */
	private Double payedAmount;
	/**
	 * 审核状态
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 退货明细
	 */
	private Set<DeliverRejectDetail> deliverRejectDetails = new HashSet<DeliverRejectDetail>(0);

	// Constructors

	/** default constructor */
	public DeliverReject() {
	}

	/** minimal constructor */
	public DeliverReject(String deliverRejectId) {
		this.deliverRejectId = deliverRejectId;
	}

	// Property accessors
	@Id
	@Column(name = "deliverRejectId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getDeliverRejectId() {
		return this.deliverRejectId;
	}

	public void setDeliverRejectId(String deliverRejectId) {
		this.deliverRejectId = deliverRejectId;
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

	@Column(name = "deliverRejectCode", length = 50)
	public String getDeliverRejectCode() {
		return this.deliverRejectCode;
	}

	public void setDeliverRejectCode(String deliverRejectCode) {
		this.deliverRejectCode = deliverRejectCode;
	}

	@Column(name = "sourceCode", length = 50)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deliverRejectDate", length = 10)
	public Date getDeliverRejectDate() {
		return this.deliverRejectDate;
	}

	public void setDeliverRejectDate(Date deliverRejectDate) {
		this.deliverRejectDate = deliverRejectDate;
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
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deliverReject")
	public Set<DeliverRejectDetail> getDeliverRejectDetails() {
		return this.deliverRejectDetails;
	}

	public void setDeliverRejectDetails(
			Set<DeliverRejectDetail> deliverRejectDetails) {
		this.deliverRejectDetails = deliverRejectDetails;
	}

}