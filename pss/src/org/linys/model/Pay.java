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
 * @Description:付款单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Pay")
public class Pay extends BaseModel {

	private static final long serialVersionUID = 8175841086216147961L;
	// Fields
	/**
	 * 付款单Id
	 */
	private String payId;
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
	 * 付款单编号
	 */
	private String payCode;
	/**
	 * 付款方式
	 */
	private String payway;
	/**
	 * 付款日期
	 */
	private Date payDate;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 付款单状态
	 */
	private Integer status;
	/**
	 * 本付款单实付金额
	 */
	private Double payAmount;
	/**
	 * 本付款单优惠金额
	 */
	private Double discountAmount;
	/**
	 * 付款明细
	 */
	private Set<PayDetail> payDetails = new HashSet<PayDetail>(0);

	// Constructors

	/** default constructor */
	public Pay() {
	}

	/** minimal constructor */
	public Pay(String payId) {
		this.payId = payId;
	}

	// Property accessors
	@Id
	@Column(name = "payId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPayId() {
		return this.payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
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

	@Column(name = "payCode", length = 50)
	public String getPayCode() {
		return this.payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	@Column(name = "payway", length = 50)
	public String getPayway() {
		return this.payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "payDate", length = 10)
	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "note", length = 50)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pay")
	public Set<PayDetail> getPayDetails() {
		return this.payDetails;
	}

	public void setPayDetails(Set<PayDetail> payDetails) {
		this.payDetails = payDetails;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

}