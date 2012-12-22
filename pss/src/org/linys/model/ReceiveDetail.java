package org.linys.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * ReceiveDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_ReceiveDetail"
    ,schema="dbo"
    ,catalog="pss"
)

public class ReceiveDetail  implements java.io.Serializable {


    // Fields    

     private String receiveDetailId;
     private Receive receive;
     private Product product;
     private DataDictionary dataDictionary;
     private Double qty;
     private Double price;
     private String note1;
     private String note2;
     private String note3;


    // Constructors

    /** default constructor */
    public ReceiveDetail() {
    }

	/** minimal constructor */
    public ReceiveDetail(String receiveDetailId) {
        this.receiveDetailId = receiveDetailId;
    }
    
    /** full constructor */
    public ReceiveDetail(String receiveDetailId, Receive receive, Product product, DataDictionary dataDictionary, Double qty, Double price, String note1, String note2, String note3) {
        this.receiveDetailId = receiveDetailId;
        this.receive = receive;
        this.product = product;
        this.dataDictionary = dataDictionary;
        this.qty = qty;
        this.price = price;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="receiveDetailId", unique=true, nullable=false, length=32)

    public String getReceiveDetailId() {
        return this.receiveDetailId;
    }
    
    public void setReceiveDetailId(String receiveDetailId) {
        this.receiveDetailId = receiveDetailId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="receiveId")

    public Receive getReceive() {
        return this.receive;
    }
    
    public void setReceive(Receive receive) {
        this.receive = receive;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="productId")

    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="colorId")

    public DataDictionary getDataDictionary() {
        return this.dataDictionary;
    }
    
    public void setDataDictionary(DataDictionary dataDictionary) {
        this.dataDictionary = dataDictionary;
    }
    
    @Column(name="qty", precision=53, scale=0)

    public Double getQty() {
        return this.qty;
    }
    
    public void setQty(Double qty) {
        this.qty = qty;
    }
    
    @Column(name="price", precision=53, scale=0)

    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Column(name="note1", length=50)

    public String getNote1() {
        return this.note1;
    }
    
    public void setNote1(String note1) {
        this.note1 = note1;
    }
    
    @Column(name="note2", length=50)

    public String getNote2() {
        return this.note2;
    }
    
    public void setNote2(String note2) {
        this.note2 = note2;
    }
    
    @Column(name="note3", length=50)

    public String getNote3() {
        return this.note3;
    }
    
    public void setNote3(String note3) {
        this.note3 = note3;
    }
   








}