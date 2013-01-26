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
 * @Description:银行账单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_BankStatements")
public class BankStatements extends BaseModel {

	private static final long serialVersionUID = 7603203916507358699L;
	// Fields
	/**
	 * 银行账单Id
	 */
	private String bankStatementsId;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 银行
	 */
	private Bank bank;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 交易日期
	 */
	private Date bankStatementsDate;
	/**
	 * 交易类型
	 * 
	 */
	private String bankStatementsKind;
	/**
	 * 状态
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public BankStatements() {
	}

	// Property accessors
	@Id
	@Column(name = "bankStatementsId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getBankStatementsId() {
		return this.bankStatementsId;
	}

	public void setBankStatementsId(String bankStatementsId) {
		this.bankStatementsId = bankStatementsId;
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

	@Column(name = "amount", nullable = false, precision = 22, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "bankStatementsDate", nullable = false, length = 10)
	public Date getBankStatementsDate() {
		return this.bankStatementsDate;
	}

	public void setBankStatementsDate(Date bankStatementsDate) {
		this.bankStatementsDate = bankStatementsDate;
	}

	@Column(name = "bankStatementsKind", nullable = false, length = 20)
	public String getBankStatementsKind() {
		return this.bankStatementsKind;
	}

	public void setBankStatementsKind(String bankStatementsKind) {
		this.bankStatementsKind = bankStatementsKind;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}