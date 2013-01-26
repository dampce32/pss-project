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
 * @Description:收入
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Income")
public class Income extends BaseModel {

	// Fields

	private static final long serialVersionUID = 1491270738426506920L;
	/**
	 * 收入Id
	 */
	private String incomeId;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 收入账号
	 */
	private Bank bank;
	/**
	 * 收入名称
	 */
	private String incomeName;
	/**
	 * 收入日期
	 */
	private Date incomeDate;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 收入金额
	 */
	private Double amount;
	/**
	 * 状态
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public Income() {
	}

	// Property accessors
	@Id
	@Column(name = "incomeId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getIncomeId() {
		return this.incomeId;
	}

	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
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
	@JoinColumn(name = "bankId", nullable = false)
	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "incomeName", length = 50)
	public String getIncomeName() {
		return this.incomeName;
	}

	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "incomeDate", nullable = false, length = 10)
	public Date getIncomeDate() {
		return this.incomeDate;
	}

	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "amount", nullable = false, precision = 22, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}