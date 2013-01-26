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
 * @Description:支出
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Expense")
public class Expense extends BaseModel {

	private static final long serialVersionUID = -6162865260407859072L;
	// Fields
	/**
	 * 费用支出Id
	 */
	private String expenseId;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 支付银行
	 */
	private Bank bank;
	/**
	 * 费用名称
	 */
	private String expenseName;
	/**
	 * 费用金额
	 */
	private Double amount;
	/**
	 * 支出日期
	 */
	private Date expenseDate;
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
	public Expense() {
	}

	// Property accessors
	@Id
	@Column(name = "expenseId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getExpenseId() {
		return this.expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
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

	@Column(name = "expenseName", length = 50)
	public String getExpenseName() {
		return this.expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	@Column(name = "amount", nullable = false, precision = 22, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expenseDate", nullable = false, length = 10)
	public Date getExpenseDate() {
		return this.expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
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

}