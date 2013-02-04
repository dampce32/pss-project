package org.linys.model;

import java.text.DecimalFormat;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:商品组装
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Packaging")
public class Packaging extends BaseModel {

	private static final long serialVersionUID = 1468918954357363380L;
	// Fields
	/**
	 * 商品组装Id
	 */
	private String packagingId;
	/**
	 * 经办人
	 */
	private Employee employee;
	/**
	 * 组装仓库
	 */
	private Warehouse warehouse;
	/**
	 * 组装商品
	 */
	private Product product;
	/**
	 * 商品组装编号
	 */
	private String packagingCode;
	/**
	 * 组装日期
	 */
	private Date packagingDate;
	/**
	 * 组装数量
	 */
	private Integer qty;
	/**
	 * 组装成本价
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
	 * 组装明细
	 */
	private Set<PackagingDetail> packagingDetails = new HashSet<PackagingDetail>(0);

	// Constructors

	/** default constructor */
	public Packaging() {
	}

	// Property accessors
	@Id
	@Column(name = "packagingId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPackagingId() {
		return this.packagingId;
	}

	public void setPackagingId(String packagingId) {
		this.packagingId = packagingId;
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

	@Column(name = "packagingCode", nullable = false, length = 50)
	public String getPackagingCode() {
		return this.packagingCode;
	}

	public void setPackagingCode(String packagingCode) {
		this.packagingCode = packagingCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "packagingDate", nullable = false, length = 10)
	public Date getPackagingDate() {
		return this.packagingDate;
	}

	public void setPackagingDate(Date packagingDate) {
		this.packagingDate = packagingDate;
	}

	@Column
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "packaging")
	public Set<PackagingDetail> getPackagingDetails() {
		return this.packagingDetails;
	}

	public void setPackagingDetails(Set<PackagingDetail> packagingDetails) {
		this.packagingDetails = packagingDetails;
	}

	@Transient
	public Double getAmount(){
		if(price==null){
			return 0D;
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(price*qty));
	}
}