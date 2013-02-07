package org.linys.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
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
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 婚否
	 */
	private String isMarry;
	/**
	 * 学历
	 */
	private String education;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 籍贯
	 */
	private String nativePlace;
	/**
	 * 户口所在地
	 */
	private String residence;
	/**
	 * 专业
	 */
	private String major;
	
	/**
	 * 入职日期
	 */
	private Date startWorkDate;
	/**
	 * 工资
	 */
	private Double salary;
	/**
	 * 银行账号
	 */
	private String bankNo;
	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 手机
	 */
	private String telPhone;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * Email
	 */
	private String email;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态
	 */
	private Integer status;
	
	
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
	@Column(name = "sex", length = 2)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 10)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Column(name = "isMarry", length = 2)
	public String getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(String isMarry) {
		this.isMarry = isMarry;
	}
	@Column(name = "education", length = 50)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	@Column(name = "nation", length = 50)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	@Column(name = "nativePlace", length = 100)
	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	@Column(name = "residence", length = 500)
	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}
	@Column(name = "major", length = 100)
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "startWorkDate", length = 10,nullable=false)
	public Date getStartWorkDate() {
		return startWorkDate;
	}

	public void setStartWorkDate(Date startWorkDate) {
		this.startWorkDate = startWorkDate;
	}
	@Column(name = "salary")
	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	@Column(name = "bankNo", length = 50)
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	@Column(name = "idNo", length = 50)
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	@Column(name = "phone", length = 50)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "telPhone", length = 50)
	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	@Column(name = "qq", length = 50)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "note", length = 500)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@Column(name = "status", length = 2,nullable=false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}