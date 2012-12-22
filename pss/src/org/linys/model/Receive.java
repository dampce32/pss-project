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
 * Receive entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Receive"
    ,schema="dbo"
    ,catalog="pss"
)

public class Receive  implements java.io.Serializable {


    // Fields    

     private String receiveId;
     private Employee employee;
     private Warehouse warehouse;
     private Supplier supplier;
     private Bank bank;
     private String receiveCode;
     private String deliverCode;
     private Date receiveDate;
     private Double amount;
     private Double discountAmount;
     private Double payAmount;
     private String receipt;
     private Integer shzt;
     private String note;
     private Set<Pay> paies = new HashSet<Pay>(0);
     private Set<ReceiveDetail> receiveDetails = new HashSet<ReceiveDetail>(0);


    // Constructors

    /** default constructor */
    public Receive() {
    }

	/** minimal constructor */
    public Receive(String receiveId) {
        this.receiveId = receiveId;
    }
    
    /** full constructor */
    public Receive(String receiveId, Employee employee, Warehouse warehouse, Supplier supplier, Bank bank, String receiveCode, String deliverCode, Date receiveDate, Double amount, Double discountAmount, Double payAmount, String receipt, Integer shzt, String note, Set<Pay> paies, Set<ReceiveDetail> receiveDetails) {
        this.receiveId = receiveId;
        this.employee = employee;
        this.warehouse = warehouse;
        this.supplier = supplier;
        this.bank = bank;
        this.receiveCode = receiveCode;
        this.deliverCode = deliverCode;
        this.receiveDate = receiveDate;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.payAmount = payAmount;
        this.receipt = receipt;
        this.shzt = shzt;
        this.note = note;
        this.paies = paies;
        this.receiveDetails = receiveDetails;
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
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="supplierId")

    public Supplier getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="bankId")

    public Bank getBank() {
        return this.bank;
    }
    
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    @Column(name="receiveCode", length=50)

    public String getReceiveCode() {
        return this.receiveCode;
    }
    
    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }
    
    @Column(name="deliverCode", length=50)

    public String getDeliverCode() {
        return this.deliverCode;
    }
    
    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="receiveDate", length=10)

    public Date getReceiveDate() {
        return this.receiveDate;
    }
    
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    
    @Column(name="amount", precision=53, scale=0)

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    @Column(name="discountAmount", precision=53, scale=0)

    public Double getDiscountAmount() {
        return this.discountAmount;
    }
    
    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="receive")

    public Set<Pay> getPaies() {
        return this.paies;
    }
    
    public void setPaies(Set<Pay> paies) {
        this.paies = paies;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="receive")

    public Set<ReceiveDetail> getReceiveDetails() {
        return this.receiveDetails;
    }
    
    public void setReceiveDetails(Set<ReceiveDetail> receiveDetails) {
        this.receiveDetails = receiveDetails;
    }
   








}