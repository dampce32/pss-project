package org.linys.model;
// default package

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Reject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Reject"
    ,schema="dbo"
    ,catalog="pss"
)

public class Reject  implements java.io.Serializable {


    // Fields    

     private String receiveId;
     private Employee employee;
     private Warehouse warehouse;
     private String supplierId;
     private String rejectCode;
     private String buyCode;
     private Date rejectDate;
     private Double amount;
     private Double payAmount;
     private String receipt;
     private Integer shzt;
     private String note;
     private Set<RejectDetail> rejectDetails = new HashSet<RejectDetail>(0);


    // Constructors

    /** default constructor */
    public Reject() {
    }

	/** minimal constructor */
    public Reject(String receiveId) {
        this.receiveId = receiveId;
    }
    
    /** full constructor */
    public Reject(String receiveId, Employee employee, Warehouse warehouse, String supplierId, String rejectCode, String buyCode, Date rejectDate, Double amount, Double payAmount, String receipt, Integer shzt, String note, Set<RejectDetail> rejectDetails) {
        this.receiveId = receiveId;
        this.employee = employee;
        this.warehouse = warehouse;
        this.supplierId = supplierId;
        this.rejectCode = rejectCode;
        this.buyCode = buyCode;
        this.rejectDate = rejectDate;
        this.amount = amount;
        this.payAmount = payAmount;
        this.receipt = receipt;
        this.shzt = shzt;
        this.note = note;
        this.rejectDetails = rejectDetails;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="receiveId", unique=true, nullable=false, length=32)

    public String getReceiveId() {
        return this.receiveId;
    }
    
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="employeeId")

    public Employee getEmployee() {
        return this.employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="warehouseId")

    public Warehouse getWarehouse() {
        return this.warehouse;
    }
    
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    @Column(name="supplierId", length=32)

    public String getSupplierId() {
        return this.supplierId;
    }
    
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    
    @Column(name="rejectCode", length=50)

    public String getRejectCode() {
        return this.rejectCode;
    }
    
    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }
    
    @Column(name="buyCode", length=50)

    public String getBuyCode() {
        return this.buyCode;
    }
    
    public void setBuyCode(String buyCode) {
        this.buyCode = buyCode;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="rejectDate", length=10)

    public Date getRejectDate() {
        return this.rejectDate;
    }
    
    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }
    
    @Column(name="amount", precision=53, scale=0)

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    @Column(name="payAmount", precision=53, scale=0)

    public Double getPayAmount() {
        return this.payAmount;
    }
    
    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }
    
    @Column(name="receipt", length=20)

    public String getReceipt() {
        return this.receipt;
    }
    
    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
    
    @Column(name="shzt")

    public Integer getShzt() {
        return this.shzt;
    }
    
    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }
    
    @Column(name="note", length=50)

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="reject")

    public Set<RejectDetail> getRejectDetails() {
        return this.rejectDetails;
    }
    
    public void setRejectDetails(Set<RejectDetail> rejectDetails) {
        this.rejectDetails = rejectDetails;
    }
   








}