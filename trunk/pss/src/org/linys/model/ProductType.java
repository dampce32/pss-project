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


/**
 * ProductType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_ProductType"
    ,schema="dbo"
    ,catalog="pss"
)

public class ProductType  implements java.io.Serializable {


    // Fields    

     private String productTypeId;
     private ProductType productType;
     private String productTypeName;
     private String productTypeCode;
     private Set<ProductType> productTypes = new HashSet<ProductType>(0);


    // Constructors

    /** default constructor */
    public ProductType() {
    }

	/** minimal constructor */
    public ProductType(String productTypeId) {
        this.productTypeId = productTypeId;
    }
    
    /** full constructor */
    public ProductType(String productTypeId, ProductType productType, String productTypeName, String productTypeCode, Set<ProductType> productTypes) {
        this.productTypeId = productTypeId;
        this.productType = productType;
        this.productTypeName = productTypeName;
        this.productTypeCode = productTypeCode;
        this.productTypes = productTypes;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="productTypeID", unique=true, nullable=false, length=32)

    public String getProductTypeId() {
        return this.productTypeId;
    }
    
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="parentProductTypeID")

    public ProductType getProductType() {
        return this.productType;
    }
    
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    @Column(name="productTypeName", length=50)

    public String getProductTypeName() {
        return this.productTypeName;
    }
    
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
    
    @Column(name="productTypeCode", length=50)

    public String getProductTypeCode() {
        return this.productTypeCode;
    }
    
    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="productType")

    public Set<ProductType> getProductTypes() {
        return this.productTypes;
    }
    
    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }
   








}