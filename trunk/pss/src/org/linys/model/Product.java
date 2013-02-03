package org.linys.model;

import java.text.DecimalFormat;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:商品
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Product")
public class Product extends BaseModel {

	// Fields
	private static final long serialVersionUID = 5659596899462348102L;
	/**
	 * 商品id
	 */
	private String productId;
	/**
	 * 商品类别
	 */
	private ProductType productType;
	/**
	 * 商品颜色
	 */
	private DataDictionary color;
	/**
	 * 商品尺码
	 */
	private DataDictionary size;
	/**
	 * 商品单位
	 */
	private DataDictionary unit;
	/**
	 * 商品编号
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 库存数量
	 */
	private Double qtyStore;
	/**
	 * 库存金额
	 */
	private Double amountStore;
	/**
	 * 库存单价
	 */
	private Double priceStore;
	/**
	 * 预设进价
	 */
	private Double buyingPrice;
	/**
	 * 零售单价
	 */
	private Double salePrice;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 退货明细
	 */
	private Set<RejectDetail> rejectDetails = new HashSet<RejectDetail>(0);
	/**
	 * 收货明细
	 */
	private Set<ReceiveDetail> receiveDetails = new HashSet<ReceiveDetail>(0);
	/**
	 * 默认组装包装
	 */
	private Set<DefaultPackaging> defaultPackagings = new HashSet<DefaultPackaging>(0);

	// Constructors

	/** default constructor */
	public Product() {
	}

	/** minimal constructor */
	public Product(String productId) {
		this.productId = productId;
	}

	// Property accessors
	@Id
	@Column(name = "productId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productTypeID")
	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Column(name = "productCode", length = 50)
	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name = "productName", length = 100)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "qtyStore", precision = 53, scale = 0)
	public Double getQtyStore() {
		return this.qtyStore;
	}

	public void setQtyStore(Double qtyStore) {
		this.qtyStore = qtyStore;
	}

	@Column(name = "amountStore", precision = 53, scale = 0)
	public Double getAmountStore() {
		return this.amountStore;
	}

	public void setAmountStore(Double amountStore) {
		this.amountStore = amountStore;
	}

	@Column(name = "note", length = 50)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<RejectDetail> getRejectDetails() {
		return this.rejectDetails;
	}

	public void setRejectDetails(Set<RejectDetail> rejectDetails) {
		this.rejectDetails = rejectDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<ReceiveDetail> getReceiveDetails() {
		return this.receiveDetails;
	}

	public void setReceiveDetails(Set<ReceiveDetail> receiveDetails) {
		this.receiveDetails = receiveDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colorId")
	public DataDictionary getColor() {
		return color;
	}

	public void setColor(DataDictionary color) {
		this.color = color;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sizeId")
	public DataDictionary getSize() {
		return size;
	}

	public void setSize(DataDictionary size) {
		this.size = size;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitId")
	public DataDictionary getUnit() {
		return unit;
	}

	public void setUnit(DataDictionary unit) {
		this.unit = unit;
	}
	@Transient
	public Double getPriceStore() {
		this.priceStore =this.qtyStore==0?0:this.amountStore/this.qtyStore;
		DecimalFormat df = new DecimalFormat("##########0.00");
		return Double.parseDouble(df.format(this.priceStore));
	}

	public void setPriceStore(Double priceStore) {
		this.priceStore = priceStore;
	}
	@Column(name = "buyingPrice", precision = 53, scale = 0)
	public Double getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(Double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	
	@Column(name = "salePrice", precision = 53, scale = 0)
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentProduct")
	public Set<DefaultPackaging> getDefaultPackagings() {
		return defaultPackagings;
	}

	public void setDefaultPackagings(Set<DefaultPackaging> defaultPackagings) {
		this.defaultPackagings = defaultPackagings;
	}

}