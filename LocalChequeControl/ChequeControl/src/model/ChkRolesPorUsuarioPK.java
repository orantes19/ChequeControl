package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CHK_ROLES_POR_USUARIO database table.
 * 
 */
@Embeddable
public class ChkRolesPorUsuarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String username;

	@Column(insertable=false, updatable=false)
	private String rol;

	public ChkRolesPorUsuarioPK() {
	}
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRol() {
		return this.rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChkRolesPorUsuarioPK)) {
			return false;
		}
		ChkRolesPorUsuarioPK castOther = (ChkRolesPorUsuarioPK)other;
		return 
			this.username.equals(castOther.username)
			&& this.rol.equals(castOther.rol);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.username.hashCode();
		hash = hash * prime + this.rol.hashCode();
		
		return hash;
	}
}