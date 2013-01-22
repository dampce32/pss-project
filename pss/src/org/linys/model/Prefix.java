package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Description:编号前缀
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-21
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Prefix")
public class Prefix extends BaseModel {

	private static final long serialVersionUID = 1801013312116500300L;
	// Fields
	/**
	 * 前缀Id
	 */
	private String prefixId;
	/**
	 * 编号前缀编号
	 */
	private String prefixCode;
	/**
	 * 前缀
	 */
	private String prefix;
	/**
	 * 编号名称
	 */
	private String prefixName;

	// Constructors

	/** default constructor */
	public Prefix() {
	}

	/** minimal constructor */
	public Prefix(String prefixId) {
		this.prefixId = prefixId;
	}

	/** full constructor */
	public Prefix(String prefixId, String prefixCode, String prefix,
			String prefixName) {
		this.prefixId = prefixId;
		this.prefixCode = prefixCode;
		this.prefix = prefix;
		this.prefixName = prefixName;
	}

	// Property accessors
	@Id
	@Column(name = "prefixId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getPrefixId() {
		return this.prefixId;
	}

	public void setPrefixId(String prefixId) {
		this.prefixId = prefixId;
	}

	@Column(name = "prefixCode", length = 50,unique= true)
	public String getPrefixCode() {
		return this.prefixCode;
	}

	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}

	@Column(name = "prefix", length = 50)
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "prefixName", length = 100)
	public String getPrefixName() {
		return this.prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

}