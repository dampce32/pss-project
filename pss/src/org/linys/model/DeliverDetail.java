package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:出库明细
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "t_deliverdetail")
public class DeliverDetail extends BaseModel {

	// Fields

	private static final long serialVersionUID = -3896848807005160069L;
	/**
	 * 出库单明细Id
	 */
	private String deliverDetailId;
	/**
	 * 出库单
	 */
	private Deliver deliver;
	/**
	 * 商品
	 */
	private Product product;
	/**
	 * 销售明细
	 */
	private SaleDetail saleDetail;
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

	// Constructors

	/** default constructor */
	public DeliverDetail() {
	}

	/** minimal constructor */
	public DeliverDetail(String deliverDetailId) {
		this.deliverDetailId = deliverDetailId;
	}

	// Property accessors
	@Id
	@Column(name = "deliverDetailId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getDeliverDetailId() {
		return this.deliverDetailId;
	}

	public void setDeliverDetailId(String deliverDetailId) {
		this.deliverDetailId = deliverDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverId")
	public Deliver getDeliver() {
		return this.deliver;
	}

	public void setDeliver(Deliver deliver) {
		this.deliver = deliver;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saleDetailId")
	public SaleDetail getSaleDetail() {
		return this.saleDetail;
	}

	public void setSaleDetail(SaleDetail saleDetail) {
		this.saleDetail = saleDetail;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colorId")
	public DataDictionary getColor() {
		return color;
	}

	public void setColor(DataDictionary color) {
		this.color = color;
	}

}