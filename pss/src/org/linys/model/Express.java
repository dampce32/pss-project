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
 * @Description:快递
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "t_express")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Express extends BaseModel {

	// Fields

	private static final long serialVersionUID = -8728573158683638847L;
	/**
	 * 快递Id
	 */
	private String expressId;
	/**
	 * 快递名称
	 */
	private String expressName;

	@Id
	@Column(name = "expressId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getExpressId() {
		return this.expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	@Column(name = "expressName", nullable = false, length = 100)
	public String getExpressName() {
		return this.expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

}