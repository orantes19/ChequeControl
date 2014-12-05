package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CHK_ROLES_POR_USUARIO database table.
 * 
 */
@Entity
@Table(name="CHK_ROLES_POR_USUARIO")
@NamedQuery(name="ChkRolesPorUsuario.findAll", query="SELECT c FROM ChkRolesPorUsuario c")
public class ChkRolesPorUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChkRolesPorUsuarioPK id;

	private BigDecimal estado;

	@Column(name="FECHA_ASIGNACION")
	private String fechaAsignacion;

	@Column(name="USUARIO_ASIGNACION")
	private String usuarioAsignacion;

	//bi-directional many-to-one association to ChkUsuario
	@ManyToOne
	@JoinColumn(name="USERNAME")
	private ChkUsuario chkUsuario;

	//bi-directional many-to-one association to ChkRol
	@ManyToOne
	@JoinColumn(name="ROL")
	private ChkRol chkRol;

	public ChkRolesPorUsuario() {
	}

	public ChkRolesPorUsuarioPK getId() {
		return this.id;
	}

	public void setId(ChkRolesPorUsuarioPK id) {
		this.id = id;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getUsuarioAsignacion() {
		return this.usuarioAsignacion;
	}

	public void setUsuarioAsignacion(String usuarioAsignacion) {
		this.usuarioAsignacion = usuarioAsignacion;
	}

	public ChkUsuario getChkUsuario() {
		return this.chkUsuario;
	}

	public void setChkUsuario(ChkUsuario chkUsuario) {
		this.chkUsuario = chkUsuario;
	}

	public ChkRol getChkRol() {
		return this.chkRol;
	}

	public void setChkRol(ChkRol chkRol) {
		this.chkRol = chkRol;
	}

}