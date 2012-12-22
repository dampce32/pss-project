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
 * Employee entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Employee"
    ,schema="dbo"
    ,catalog="pss"
)

public class Employee  implements java.io.Serializable {


    // Fields    

     private String employeeId;
     private Pay pay;
     private String employeeName;
     private Set<Reject> rejects = new HashSet<Reject>(0);
     private Set<Receive> receives = new HashSet<Receive>(0);


    // Constructors

    /** default constructor */
    public Employee() {
    }

	/** minimal constructor */
    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }
    
    /** full constructor */
    public Employee(String employeeId, Pay pay, String employeeName, Set<Reject> rejects, Set<Receive> receives) {
        this.employeeId = employeeId;
        this.pay = pay;
        this.employeeName = employeeName;
        this.rejects = rejects;
        this.receives = receives;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="employeeId", unique=true, nullable=false, length=32)

    public String getEmployeeId() {
        return this.employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="payId")

    public Pay getPay() {
        return this.pay;
    }
    
    public void setPay(Pay pay) {
        this.pay = pay;
    }
    
    @Column(name="employeeName", length=50)

    public String getEmployeeName() {
        return this.employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="employee")

    public Set<Reject> getRejects() {
        return this.rejects;
    }
    
    public void setRejects(Set<Reject> rejects) {
        this.rejects = rejects;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="employee")

    public Set<Receive> getReceives() {
        return this.receives;
    }
    
    public void setReceives(Set<Receive> receives) {
        this.receives = receives;
    }
   








}