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
 * 
 * @Description: 预收单
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Entity
@Table(name="T_PreReceipt")
public class PreReceipt extends BaseModel {

	private static final long serialVersionUID = 5911066749418568310L;
	
	/**
	 * ID
	 */
	private String preReceiptId;
	
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
	 * 预收单编号
	 */
	private String preReceiptCode;
	/**
	 * 预收款日期
	 */
	private Date preReceiptDate;
	/**
	 * 付款金额
	 */
	private Double amount;
	/**
	 * 对账金额
	 */
	private Double checkAmount;
	/**
	 * 余额
	 */
	private Double balanceAmount;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String note;
	
	@Id
	@Column(name = "prepayId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPreReceiptId() {
		return preReceiptId;
	}
	public void setPreReceiptId(String preReceiptId) {
		this.preReceiptId = preReceiptId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankId")
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public String getPreReceiptCode() {
		return preReceiptCode;
	}
	public void setPreReceiptCode(String preReceiptCode) {
		this.preReceiptCode = preReceiptCode;
	}
	@Temporal(TemporalType.DATE)
	@Column(length = 10)
	public Date getPreReceiptDate() {
		return preReceiptDate;
	}
	public void setPreReceiptDate(Date preReceiptDate) {
		this.preReceiptDate = preReceiptDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	@Column(length=1000)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	} 

}
