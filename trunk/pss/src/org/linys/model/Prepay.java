package org.linys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:预付单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Prepay")
public class Prepay extends BaseModel {

	// Fields

	private static final long serialVersionUID = -1004662285251138227L;
	/**
	 * 预付单Id
	 */
	private String prepayId;
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
	 * 预付单编号
	 */
	private String prepayCode;
	/**
	 * 预付款日期
	 */
	private Date prepayDate;
	/**
	 * 付款金额
	 */
	private Double amount;
	/**
	 * 余额
	 */
	private Double balance;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public Prepay() {
	}

	/** minimal constructor */
	public Prepay(String prepayId) {
		this.prepayId = prepayId;
	}

	// Property accessors
	@Id
	@Column(name = "prepayId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPrepayId() {
		return this.prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
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

	@Column(name = "prepayCode", length = 50)
	public String getPrepayCode() {
		return this.prepayCode;
	}

	public void setPrepayCode(String prepayCode) {
		this.prepayCode = prepayCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "prepayDate", length = 10)
	public Date getPrepayDate() {
		return this.prepayDate;
	}

	public void setPrepayDate(Date prepayDate) {
		this.prepayDate = prepayDate;
	}

	@Column(name = "amount", precision = 12, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "note", length = 100)
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
	@Column(name = "balance", precision = 12, scale = 0)
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}