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
 * @Description: 商品拆分
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Split")
public class Split extends BaseModel {

	// Fields
	private static final long serialVersionUID = 5465745672921861911L;
	/**
	 * 商品拆分Id
	 */
	private String splitId;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 拆分仓库
	 */
	private Warehouse warehouse;
	/**
	 * 拆分商品
	 */
	private Product product;
	/**
	 * 商品拆分编号
	 */
	private String splitCode;
	/**
	 * 拆分日期
	 */
	private Date splitDate;
	/**
	 * 拆分数量
	 */
	private Double qty;
	/**
	 * 拆分成本价
	 */
	private Double price;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 拆分明细
	 */
	private Set<SplitDetail> splitDetails = new HashSet<SplitDetail>(0);

	// Constructors

	/** default constructor */
	public Split() {
	}


	// Property accessors
	@Id
	@Column(name = "splitId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getSplitId() {
		return this.splitId;
	}

	public void setSplitId(String splitId) {
		this.splitId = splitId;
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
	@JoinColumn(name = "warehouseId", nullable = false)
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "splitCode", nullable = false, length = 50)
	public String getSplitCode() {
		return this.splitCode;
	}

	public void setSplitCode(String splitCode) {
		this.splitCode = splitCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "splitDate", nullable = false, length = 10)
	public Date getSplitDate() {
		return this.splitDate;
	}

	public void setSplitDate(Date splitDate) {
		this.splitDate = splitDate;
	}

	@Column(name = "qty", nullable = false, precision = 22, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "price", nullable = false, precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "split")
	public Set<SplitDetail> getSplitDetails() {
		return this.splitDetails;
	}

	public void setSplitDetails(Set<SplitDetail> splitDetails) {
		this.splitDetails = splitDetails;
	}

}