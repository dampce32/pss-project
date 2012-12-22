package org.linys.model;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Supplier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Supplier"
    ,schema="dbo"
    ,catalog="pss"
)

public class Supplier  implements java.io.Serializable {


    // Fields    

     private String supplierId;
     private String supplierName;
     private String supplierCode;
     private Set<Receive> receives = new HashSet<Receive>(0);


    // Constructors

    /** default constructor */
    public Supplier() {
    }

	/** minimal constructor */
    public Supplier(String supplierId) {
        this.supplierId = supplierId;
    }
    
    /** full constructor */
    public Supplier(String supplierId, String supplierName, String supplierCode, Set<Receive> receives) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.receives = receives;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="supplierId", unique=true, nullable=false, length=32)

    public String getSupplierId() {
        return this.supplierId;
    }
    
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    
    @Column(name="supplierName", length=100)

    public String getSupplierName() {
        return this.supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    @Column(name="supplierCode", length=50)

    public String getSupplierCode() {
        return this.supplierCode;
    }
    
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="supplier")

    public Set<Receive> getReceives() {
        return this.receives;
    }
    
    public void setReceives(Set<Receive> receives) {
        this.receives = receives;
    }
   








}