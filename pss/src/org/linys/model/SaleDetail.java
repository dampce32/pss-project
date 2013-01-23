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
 * @Description:销售单明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "t_saledetail")
public class SaleDetail extends BaseModel {

	// Fields
	private static final long serialVersionUID = 3211586732929390097L;
	/**
	 * 客户订单明细Id
	 */
	private String saleDetailId;
	/**
	 * 商品
	 */
	private Product product;
	/**
	 * 客户订单
	 */
	private Sale sale;
	/**
	 * 颜色
	 */
	private DataDictionary color;
	/**
	 * 数量
	 */
	private Double qty;
	/**
	 * 单价
	 */
	private Double price;
	/**
	 * 折扣
	 */
	private Double discount;
	
	 /**
	  * 备注1
	  */
	private String note1;
	/**
	 * 备注2
	 */
	private String note2;
	/**
	 * 备注3
	 */
	private String note3;
	/**
	 * 已销售
	 */
	private Double hadSaleQty;
	/**
	 * 出库明细
	 */
	private Set<DeliverDetail> deliverDetails = new HashSet<DeliverDetail>(0);

	// Constructors

	/** default constructor */
	public SaleDetail() {
	}

	/** minimal constructor */
	public SaleDetail(String saleDetailId) {
		this.saleDetailId = saleDetailId;
	}

	// Property accessors
	@Id
	@Column(name = "saleDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getSaleDetailId() {
		return this.saleDetailId;
	}

	public void setSaleDetailId(String saleDetailId) {
		this.saleDetailId = saleDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product Product) {
		this.product = Product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saleId")
	public Sale getSale() {
		return this.sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	@Column(name = "qty", precision = 12, scale = 0)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "price", precision = 12, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "discount", precision = 12, scale = 0)
	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "note1", length = 50)
	public String getNote1() {
		return this.note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	@Column(name = "note2", length = 50)
	public String getNote2() {
		return this.note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	@Column(name = "note3", length = 50)
	public String getNote3() {
		return this.note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

	@Column(name = "hadSaleQty", precision = 12, scale = 0)
	public Double getHadSaleQty() {
		return this.hadSaleQty;
	}

	public void setHadSaleQty(Double hadSaleQty) {
		this.hadSaleQty = hadSaleQty;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saleDetail")
	public Set<DeliverDetail> getDeliverDetails() {
		return this.deliverDetails;
	}

	public void setDeliverDetails(Set<DeliverDetail> deliverDetails) {
		this.deliverDetails = deliverDetails;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colorId")
	public DataDictionary getColor() {
		return color;
	}

	public void setColor(DataDictionary color) {
		this.color = color;
	}
	@Transient
	public Double getAmount() {
		Double amount = qty*price;
		if(discount!=null){
			amount = amount*discount;
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(amount));
	}


}