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
 * 
 * @Description: 收款
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Entity
@Table(name="T_Receipt")
public class Receipt extends BaseModel {

	private static final long serialVersionUID = 8818536861511077752L;
	
	/**
	 * 付款单Id
	 */
	private String receiptId;
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
	 * 收款单编号
	 */
	private String receiptCode;
	/**
	 * 收款方式
	 */
	private String receiptway;
	/**
	 * 收款日期
	 */
	private Date receiptDate;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 收款单状态
	 */
	private Integer status;
	
	/**
	 * 应收金额
	 */
	private Double amount;
	
	/**
	 * 本收款单实收金额
	 */
	private Double receiptAmount;
	/**
	 * 本付款单优惠金额
	 */
	private Double discountAmount;
	
	private Set<ReceiptDetail> receiptDetails = new HashSet<ReceiptDetail>(0);

	@Id
	@Column(name = "receiptId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
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

	public String getReceiptCode() {
		return receiptCode;
	}

	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
	}

	public String getReceiptway() {
		return receiptway;
	}

	public void setReceiptway(String receiptway) {
		this.receiptway = receiptway;
	}

	@Temporal(TemporalType.DATE)
	@Column(length = 10)
	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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

	public Double getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(Double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "receipt")
	public Set<ReceiptDetail> getReceiptDetails() {
		return receiptDetails;
	}

	public void setReceiptDetails(Set<ReceiptDetail> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
