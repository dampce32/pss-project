package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:系统配置
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_SystemConfig")
public class SystemConfig extends BaseModel {

	// Fields
	private static final long serialVersionUID = -8607163710543537067L;
	/**
	 * 系统配置Id
	 */
	private String systemConfigId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司电话
	 */
	private String companyPhone;
	/**
	 * 公司传真
	 */
	private String companyFax;
	/**
	 * 公司地址
	 */
	private String companyAddr;
	/**
	 * 是否实付金额默认等于应付金额 
	 */
	private Integer isAmountEqPayAmount;
	/**
	 * 运费是否加入到应付金额
	 */
	private Integer isOtherAmountInPayAmount;

	// Constructors

	/** default constructor */
	public SystemConfig() {
	}

	/** minimal constructor */
	public SystemConfig(String systemConfigId) {
		this.systemConfigId = systemConfigId;
	}

	/** full constructor */
	public SystemConfig(String systemConfigId, String companyName,
			String companyPhone, String companyFax, String companyAddr) {
		this.systemConfigId = systemConfigId;
		this.companyName = companyName;
		this.companyPhone = companyPhone;
		this.companyFax = companyFax;
		this.companyAddr = companyAddr;
	}

	// Property accessors
	@Id
	@Column(name = "systemConfigId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getSystemConfigId() {
		return this.systemConfigId;
	}

	public void setSystemConfigId(String systemConfigId) {
		this.systemConfigId = systemConfigId;
	}

	@Column(name = "companyName", length = 50)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "companyPhone", length = 50)
	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Column(name = "companyFax", length = 50)
	public String getCompanyFax() {
		return this.companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	@Column(name = "companyAddr", length = 100)
	public String getCompanyAddr() {
		return this.companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	@Column(name = "isAmountEqPayAmount")
	public Integer getIsAmountEqPayAmount() {
		return isAmountEqPayAmount;
	}

	public void setIsAmountEqPayAmount(Integer isAmountEqPayAmount) {
		this.isAmountEqPayAmount = isAmountEqPayAmount;
	}
	@Column(name = "isOtherAmountInPayAmount")
	public Integer getIsOtherAmountInPayAmount() {
		return isOtherAmountInPayAmount;
	}

	public void setIsOtherAmountInPayAmount(Integer isOtherAmountInPayAmount) {
		this.isOtherAmountInPayAmount = isOtherAmountInPayAmount;
	}

}