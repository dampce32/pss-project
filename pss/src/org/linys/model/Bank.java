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
 * Bank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Bank"
    ,schema="dbo"
    ,catalog="pss"
)

public class Bank  implements java.io.Serializable {


    // Fields    

     private String bankId;
     private String bankName;
     private String bankShortName;
     private Double amount;
     private Set<Pay> paies = new HashSet<Pay>(0);
     private Set<Receive> receives = new HashSet<Receive>(0);


    // Constructors

    /** default constructor */
    public Bank() {
    }

	/** minimal constructor */
    public Bank(String bankId) {
        this.bankId = bankId;
    }
    
    /** full constructor */
    public Bank(String bankId, String bankName, String bankShortName, Double amount, Set<Pay> paies, Set<Receive> receives) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.bankShortName = bankShortName;
        this.amount = amount;
        this.paies = paies;
        this.receives = receives;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="bankId", unique=true, nullable=false, length=32)

    public String getBankId() {
        return this.bankId;
    }
    
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
    
    @Column(name="bankName", length=100)

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    @Column(name="bankShortName", length=50)

    public String getBankShortName() {
        return this.bankShortName;
    }
    
    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }
    
    @Column(name="amount", precision=53, scale=0)

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="bank")

    public Set<Pay> getPaies() {
        return this.paies;
    }
    
    public void setPaies(Set<Pay> paies) {
        this.paies = paies;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="bank")

    public Set<Receive> getReceives() {
        return this.receives;
    }
    
    public void setReceives(Set<Receive> receives) {
        this.receives = receives;
    }
   








}