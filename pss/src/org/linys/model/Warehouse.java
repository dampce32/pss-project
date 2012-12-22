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
 * Warehouse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Warehouse"
    ,schema="dbo"
    ,catalog="pss"
)

public class Warehouse  implements java.io.Serializable {


    // Fields    

     private String warehouseId;
     private String warehouseName;
     private String warehouseCode;
     private String warehouseContactor;
     private String warehouseTel;
     private String warehouseAddr;
     private String warehouseNode;
     private Set<Reject> rejects = new HashSet<Reject>(0);
     private Set<Receive> receives = new HashSet<Receive>(0);


    // Constructors

    /** default constructor */
    public Warehouse() {
    }

	/** minimal constructor */
    public Warehouse(String warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    /** full constructor */
    public Warehouse(String warehouseId, String warehouseName, String warehouseCode, String warehouseContactor, String warehouseTel, String warehouseAddr, String warehouseNode, Set<Reject> rejects, Set<Receive> receives) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.warehouseContactor = warehouseContactor;
        this.warehouseTel = warehouseTel;
        this.warehouseAddr = warehouseAddr;
        this.warehouseNode = warehouseNode;
        this.rejects = rejects;
        this.receives = receives;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="warehouseId", unique=true, nullable=false, length=32)

    public String getWarehouseId() {
        return this.warehouseId;
    }
    
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    @Column(name="warehouseName", length=100)

    public String getWarehouseName() {
        return this.warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    @Column(name="warehouseCode", length=50)

    public String getWarehouseCode() {
        return this.warehouseCode;
    }
    
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    
    @Column(name="warehouseContactor", length=50)

    public String getWarehouseContactor() {
        return this.warehouseContactor;
    }
    
    public void setWarehouseContactor(String warehouseContactor) {
        this.warehouseContactor = warehouseContactor;
    }
    
    @Column(name="warehouseTel", length=50)

    public String getWarehouseTel() {
        return this.warehouseTel;
    }
    
    public void setWarehouseTel(String warehouseTel) {
        this.warehouseTel = warehouseTel;
    }
    
    @Column(name="warehouseAddr", length=200)

    public String getWarehouseAddr() {
        return this.warehouseAddr;
    }
    
    public void setWarehouseAddr(String warehouseAddr) {
        this.warehouseAddr = warehouseAddr;
    }
    
    @Column(name="warehouseNode", length=500)

    public String getWarehouseNode() {
        return this.warehouseNode;
    }
    
    public void setWarehouseNode(String warehouseNode) {
        this.warehouseNode = warehouseNode;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="warehouse")

    public Set<Reject> getRejects() {
        return this.rejects;
    }
    
    public void setRejects(Set<Reject> rejects) {
        this.rejects = rejects;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="warehouse")

    public Set<Receive> getReceives() {
        return this.receives;
    }
    
    public void setReceives(Set<Receive> receives) {
        this.receives = receives;
    }
   








}