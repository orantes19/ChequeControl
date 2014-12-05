package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CHK_ROL database table.
 * 
 */
@Entity
@Table(name="CHK_ROL")
@NamedQuery(name="ChkRol.findAll", query="SELECT c FROM ChkRol c")
public class ChkRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_ROL_ROL_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_ROL_ROL_GENERATOR")
	private String rol;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;

	//bi-directional many-to-one association to ChkConfCheque
	@OneToMany(mappedBy="chkRol")
	private List<ChkConfCheque> chkConfCheques;

	//bi-directional many-to-one association to ChkRolesPorUsuario
	@OneToMany(mappedBy="chkRol")
	private List<ChkRolesPorUsuario> chkRolesPorUsuarios;

	public ChkRol() {
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public List<ChkConfCheque> getChkConfCheques() {
		return this.chkConfCheques;
	}

	public void setChkConfCheques(List<ChkConfCheque> chkConfCheques) {
		this.chkConfCheques = chkConfCheques;
	}

	public ChkConfCheque addChkConfCheque(ChkConfCheque chkConfCheque) {
		getChkConfCheques().add(chkConfCheque);
		chkConfCheque.setChkRol(this);

		return chkConfCheque;
	}

	public ChkConfCheque removeChkConfCheque(ChkConfCheque chkConfCheque) {
		getChkConfCheques().remove(chkConfCheque);
		chkConfCheque.setChkRol(null);

		return chkConfCheque;
	}

	public List<ChkRolesPorUsuario> getChkRolesPorUsuarios() {
		return this.chkRolesPorUsuarios;
	}

	public void setChkRolesPorUsuarios(List<ChkRolesPorUsuario> chkRolesPorUsuarios) {
		this.chkRolesPorUsuarios = chkRolesPorUsuarios;
	}

	public ChkRolesPorUsuario addChkRolesPorUsuario(ChkRolesPorUsuario chkRolesPorUsuario) {
		getChkRolesPorUsuarios().add(chkRolesPorUsuario);
		chkRolesPorUsuario.setChkRol(this);

		return chkRolesPorUsuario;
	}

	public ChkRolesPorUsuario removeChkRolesPorUsuario(ChkRolesPorUsuario chkRolesPorUsuario) {
		getChkRolesPorUsuarios().remove(chkRolesPorUsuario);
		chkRolesPorUsuario.setChkRol(null);

		return chkRolesPorUsuario;
	}

}