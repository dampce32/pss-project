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
 * Pay entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Pay"
    ,schema="dbo"
    ,catalog="pss"
)

public class Pay  implements java.io.Serializable {


    // Fields    

     private String payId;
     private Receive receive;
     private Bank bank;
     private String payCode;
     private Double payedAmount;
     private Double discountAmount;
     private String payway;
     private Date payDate;
     private String note;
     private Set<Employee> employees = new HashSet<Employee>(0);


    // Constructors

    /** default constructor */
    public Pay() {
    }

	/** minimal constructor */
    public Pay(String payId) {
        this.payId = payId;
    }
    
    /** full constructor */
    public Pay(String payId, Receive receive, Bank bank, String payCode, Double payedAmount, Double discountAmount, String payway, Date payDate, String note, Set<Employee> employees) {
        this.payId = payId;
        this.receive = receive;
        this.bank = bank;
        this.payCode = payCode;
        this.payedAmount = payedAmount;
        this.discountAmount = discountAmount;
        this.payway = payway;
        this.payDate = payDate;
        this.note = note;
        this.employees = employees;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="payId", unique=true, nullable=false, length=3)

    public String getPayId() {
        return this.payId;
    }
    
    public void setPayId(String payId) {
        this.payId = payId;
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
        @JoinColumn(name="bankId")

    public Bank getBank() {
        return this.bank;
    }
    
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    @Column(name="payCode", length=50)

    public String getPayCode() {
        return this.payCode;
    }
    
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    
    @Column(name="payedAmount", precision=53, scale=0)

    public Double getPayedAmount() {
        return this.payedAmount;
    }
    
    public void setPayedAmount(Double payedAmount) {
        this.payedAmount = payedAmount;
    }
    
    @Column(name="discountAmount", precision=53, scale=0)

    public Double getDiscountAmount() {
        return this.discountAmount;
    }
    
    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    @Column(name="payway", length=50)

    public String getPayway() {
        return this.payway;
    }
    
    public void setPayway(String payway) {
        this.payway = payway;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="payDate", length=10)

    public Date getPayDate() {
        return this.payDate;
    }
    
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    
    @Column(name="note", length=50)

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pay")

    public Set<Employee> getEmployees() {
        return this.employees;
    }
    
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
   








}