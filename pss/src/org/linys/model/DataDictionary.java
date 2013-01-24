package org.linys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Description: 数据字典
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-18
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_DataDictionary")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DataDictionary extends BaseModel {

	// Fields
	private static final long serialVersionUID = -8349598887172194695L;
	/**
	 * 数据字典Id
	 */
	private String dataDictionaryId;
	/**
	 * 数据字典类型
	 */
	private String dataDictionaryKind;
	/**
	 * 数据字典名称
	 */
	private String dataDictionaryName;

	// Constructors

	/** default constructor */
	public DataDictionary() {
	}

	/** minimal constructor */
	public DataDictionary(String dataDictionaryId) {
		this.dataDictionaryId = dataDictionaryId;
	}


	// Property accessors
	@Id
	@Column(name = "dataDictionaryId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getDataDictionaryId() {
		return this.dataDictionaryId;
	}

	public void setDataDictionaryId(String dataDictionaryId) {
		this.dataDictionaryId = dataDictionaryId;
	}

	@Column(name = "dataDictionaryKind", length = 20)
	public String getDataDictionaryKind() {
		return this.dataDictionaryKind;
	}

	public void setDataDictionaryKind(String dataDictionaryKind) {
		this.dataDictionaryKind = dataDictionaryKind;
	}

	@Column(name = "dataDictionaryName", length = 50)
	public String getDataDictionaryName() {
		return this.dataDictionaryName;
	}

	public void setDataDictionaryName(String dataDictionaryName) {
		this.dataDictionaryName = dataDictionaryName;
	}

}