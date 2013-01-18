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
 * @Description:退货单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Reject")
public class Reject extends BaseModel {

	// Fields

	private static final long serialVersionUID = 8577807063361654218L;
	/**
	 * 退货Id
	 */
	private String rejectId;
	/**
	 * 经办人
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
	 * 退货单号
	 */
	private String rejectCode;
	/**
	 * 采购订单号
	 */
	private String buyCode;
	/**
	 * 退货日期
	 */
	private Date rejectDate;
	/**
	 * 应收金额
	 */
	private Double amount;
	/**
	 * 实退金额
	 */
	private Double payAmount;
	/**
	 * 审核状态
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 银行
	 */
	private Bank bank;
	private Set<RejectDetail> rejectDetails = new HashSet<RejectDetail>(0);

	// Constructors

	/** default constructor */
	public Reject() {
	}

	/** minimal constructor */
	public Reject(String rejectId) {
		this.rejectId = rejectId;
	}

	// Property accessors
	@Id
	@Column(name = "rejectId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getRejectId() {
		return this.rejectId;
	}

	public void setRejectId(String rejectId) {
		this.rejectId = rejectId;
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

	@Column(name = "rejectCode", length = 50)
	public String getRejectCode() {
		return this.rejectCode;
	}

	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}

	@Column(name = "buyCode", length = 50)
	public String getBuyCode() {
		return this.buyCode;
	}

	public void setBuyCode(String buyCode) {
		this.buyCode = buyCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "rejectDate", length = 10)
	public Date getRejectDate() {
		return this.rejectDate;
	}

	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
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


	@Column(name = "note", length = 50)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reject")
	public Set<RejectDetail> getRejectDetails() {
		return this.rejectDetails;
	}

	public void setRejectDetails(Set<RejectDetail> rejectDetails) {
		this.rejectDetails = rejectDetails;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplierId")
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankId")
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}