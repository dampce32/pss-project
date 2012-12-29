package org.linys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * @Description:员工
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Employee")
public class Employee extends BaseModel {

	// Fields
	private static final long serialVersionUID = -2996832325176740455L;
	/**
	 * 员工Id
	 */
	private String employeeId;
	/**
	 * 员工名称
	 */
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

	// Property accessors
	@Id
	@Column(name = "employeeId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "employeeName", length = 50)
	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<Reject> getRejects() {
		return this.rejects;
	}

	public void setRejects(Set<Reject> rejects) {
		this.rejects = rejects;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<Receive> getReceives() {
		return this.receives;
	}

	public void setReceives(Set<Receive> receives) {
		this.receives = receives;
	}
}