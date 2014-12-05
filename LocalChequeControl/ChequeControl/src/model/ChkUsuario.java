package model;

import java.io.Serializable;

import javax.persistence.*;

import proyecto.umg.persistence.EntityBase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CHK_USUARIO database table.
 * 
 */
@Entity
@Table(name="CHK_USUARIO")
@NamedQuery(name="ChkUsuario.findAll", query="SELECT c FROM ChkUsuario c")
public class ChkUsuario  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name="INTENTOS_FALLIDOS")
	private BigDecimal intentosFallidos;

	private String password;

	@Column(name="PRIMER_APELLIDO")
	private String primerApellido;

	@Column(name="PRIMER_NOMBRE")
	private String primerNombre;

	@Column(name="SEGUNDO_APELLIDO")
	private String segundoApellido;

	@Column(name="SEGUNDO_NOMBRE")
	private String segundoNombre;

	private BigDecimal status;

	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;

	//bi-directional many-to-one association to ChkRolesPorUsuario
	@OneToMany(mappedBy="chkUsuario")
	private List<ChkRolesPorUsuario> chkRolesPorUsuarios;
	
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public BigDecimal getIntentosFallidos() {
		return this.intentosFallidos;
	}

	public void setIntentosFallidos(BigDecimal intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimerApellido() {
		return this.primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoApellido() {
		return this.segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoNombre() {
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public List<ChkRolesPorUsuario> getChkRolesPorUsuarios() {
		return this.chkRolesPorUsuarios;
	}

	public void setChkRolesPorUsuarios(List<ChkRolesPorUsuario> chkRolesPorUsuarios) {
		this.chkRolesPorUsuarios = chkRolesPorUsuarios;
	}

	public ChkRolesPorUsuario addChkRolesPorUsuario(ChkRolesPorUsuario chkRolesPorUsuario) {
		getChkRolesPorUsuarios().add(chkRolesPorUsuario);
		chkRolesPorUsuario.setChkUsuario(this);

		return chkRolesPorUsuario;
	}

	public ChkRolesPorUsuario removeChkRolesPorUsuario(ChkRolesPorUsuario chkRolesPorUsuario) {
		getChkRolesPorUsuarios().remove(chkRolesPorUsuario);
		chkRolesPorUsuario.setChkUsuario(null);

		return chkRolesPorUsuario;
	}

}