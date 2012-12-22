package org.linys.model;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.linys.model.ProductType;


/**
 * Product entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Product"
    ,schema="dbo"
    ,catalog="pss"
)

public class Product  implements java.io.Serializable {


    // Fields    

     private String productId;
     private ProductType productType;
     private DataDictionary dataDictionaryByColorId;
     private DataDictionary dataDictionaryBySizeId;
     private DataDictionary dataDictionaryByUnitId;
     private String productCode;
     private String productName;
     private Double qtyStore;
     private Double amountStore;
     private String note;
     private Set<RejectDetail> rejectDetails = new HashSet<RejectDetail>(0);
     private Set<ReceiveDetail> receiveDetails = new HashSet<ReceiveDetail>(0);


    // Constructors

    /** default constructor */
    public Product() {
    }

	/** minimal constructor */
    public Product(String productId) {
        this.productId = productId;
    }
    
    /** full constructor */
    public Product(String productId, ProductType productType, DataDictionary dataDictionaryByColorId, DataDictionary dataDictionaryBySizeId, DataDictionary dataDictionaryByUnitId, String productCode, String productName, Double qtyStore, Double amountStore, String note, Set<RejectDetail> rejectDetails, Set<ReceiveDetail> receiveDetails) {
        this.productId = productId;
        this.productType = productType;
        this.dataDictionaryByColorId = dataDictionaryByColorId;
        this.dataDictionaryBySizeId = dataDictionaryBySizeId;
        this.dataDictionaryByUnitId = dataDictionaryByUnitId;
        this.productCode = productCode;
        this.productName = productName;
        this.qtyStore = qtyStore;
        this.amountStore = amountStore;
        this.note = note;
        this.rejectDetails = rejectDetails;
        this.receiveDetails = receiveDetails;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="productId", unique=true, nullable=false, length=32)

    public String getProductId() {
        return this.productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="productTypeID")

    public ProductType getProductType() {
        return this.productType;
    }
    
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="colorId")

    public DataDictionary getDataDictionaryByColorId() {
        return this.dataDictionaryByColorId;
    }
    
    public void setDataDictionaryByColorId(DataDictionary dataDictionaryByColorId) {
        this.dataDictionaryByColorId = dataDictionaryByColorId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="sizeId")

    public DataDictionary getDataDictionaryBySizeId() {
        return this.dataDictionaryBySizeId;
    }
    
    public void setDataDictionaryBySizeId(DataDictionary dataDictionaryBySizeId) {
        this.dataDictionaryBySizeId = dataDictionaryBySizeId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="unitId")

    public DataDictionary getDataDictionaryByUnitId() {
        return this.dataDictionaryByUnitId;
    }
    
    public void setDataDictionaryByUnitId(DataDictionary dataDictionaryByUnitId) {
        this.dataDictionaryByUnitId = dataDictionaryByUnitId;
    }
    
    @Column(name="productCode", length=50)

    public String getProductCode() {
        return this.productCode;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    @Column(name="productName", length=100)

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    @Column(name="qtyStore", precision=53, scale=0)

    public Double getQtyStore() {
        return this.qtyStore;
    }
    
    public void setQtyStore(Double qtyStore) {
        this.qtyStore = qtyStore;
    }
    
    @Column(name="amountStore", precision=53, scale=0)

    public Double getAmountStore() {
        return this.amountStore;
    }
    
    public void setAmountStore(Double amountStore) {
        this.amountStore = amountStore;
    }
    
    @Column(name="note", length=50)

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")

    public Set<RejectDetail> getRejectDetails() {
        return this.rejectDetails;
    }
    
    public void setRejectDetails(Set<RejectDetail> rejectDetails) {
        this.rejectDetails = rejectDetails;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")

    public Set<ReceiveDetail> getReceiveDetails() {
        return this.receiveDetails;
    }
    
    public void setReceiveDetails(Set<ReceiveDetail> receiveDetails) {
        this.receiveDetails = receiveDetails;
    }
   








}