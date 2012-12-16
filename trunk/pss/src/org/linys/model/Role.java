package org.linys.model;

// default package

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * @Description: 角色
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-27
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Role")
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String roleId;
	private String roleName;
	/**
	 * 该角色是否被选中
	 */
	private String checked;
	private Set<RoleRight> roleRights = new HashSet<RoleRight>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleId) {
		this.roleId = roleId;
	}

	// Property accessors
	@Id
	@Column(name = "roleId", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "roleName", length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<RoleRight> getRoleRights() {
		return this.roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}
	@Transient
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}