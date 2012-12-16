package org.linys.model;

// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @Description: 角色权限
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-27
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_RoleRight")
public class RoleRight implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private RoleRightId id;
	private Right right;
	private Role role;
	private Boolean state;

	// Constructors

	/** default constructor */
	public RoleRight() {
	}

	/** minimal constructor */
	public RoleRight(RoleRightId id, Right right, Role role) {
		this.id = id;
		this.right = right;
		this.role = role;
	}

	/** full constructor */
	public RoleRight(RoleRightId id, Right right, Role role, Boolean state) {
		this.id = id;
		this.right = right;
		this.role = role;
		this.state = state;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rightId", column = @Column(name = "rightId", nullable = false, length = 32)),
			@AttributeOverride(name = "roleId", column = @Column(name = "roleId", nullable = false, length = 32)) })
	public RoleRightId getId() {
		return this.id;
	}

	/**
	 * @Description: 用于接收树节点展开不明原因发出的值
	 * @Create: 2012-10-27 下午11:39:53
	 * @author lys
	 * @update logs
	 * @param id
	 * @throws Exception
	 */
	public void setId(String id) {}
	
	public void setId(RoleRightId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rightId", nullable = false, insertable = false, updatable = false)
	public Right getRight() {
		return this.right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", nullable = false, insertable = false, updatable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "state")
	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

}