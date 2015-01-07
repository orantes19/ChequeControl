package model;

import java.io.Serializable;

import javax.persistence.*;

import proyecto.umg.persistence.EntityBase;

import java.util.List;


/**
 * The persistent class for the CHK_BANCO database table.
 * 
 */
@Entity
@Table(name="CHK_BANCO")
@NamedQuery(name="ChkBanco.findAll", query="SELECT c FROM ChkBanco c")
public class ChkBanco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_BANCO_IDBANCO_GENERATOR", sequenceName="CHK_BANCO_SQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_BANCO_IDBANCO_GENERATOR")
	@Column(name="ID_BANCO")
	private long idBanco;

	private String direccion;

	@Column(name="FECHA_CREACION")
	private String fechaCreacion;

	@Column(name="NOMBRE")
	private String nombre;

	private String telefono;

	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;

	//bi-directional many-to-one association to ChkChequera
	@OneToMany(mappedBy="chkBanco")
	private List<ChkChequera> chkChequeras;

	//bi-directional many-to-one association to ChkCuenta
	@OneToMany(mappedBy="chkBanco")
	private List<ChkCuenta> chkCuentas;

	public ChkBanco() {
	}

	public long getIdBanco() {
		return this.idBanco;
	}

	public void setIdBanco(long idBanco) {
		this.idBanco = idBanco;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public List<ChkChequera> getChkChequeras() {
		return this.chkChequeras;
	}

	public void setChkChequeras(List<ChkChequera> chkChequeras) {
		this.chkChequeras = chkChequeras;
	}

	public ChkChequera addChkChequera(ChkChequera chkChequera) {
		getChkChequeras().add(chkChequera);
		chkChequera.setChkBanco(this);

		return chkChequera;
	}

	public ChkChequera removeChkChequera(ChkChequera chkChequera) {
		getChkChequeras().remove(chkChequera);
		chkChequera.setChkBanco(null);

		return chkChequera;
	}

	public List<ChkCuenta> getChkCuentas() {
		return this.chkCuentas;
	}

	public void setChkCuentas(List<ChkCuenta> chkCuentas) {
		this.chkCuentas = chkCuentas;
	}

	public ChkCuenta addChkCuenta(ChkCuenta chkCuenta) {
		getChkCuentas().add(chkCuenta);
		chkCuenta.setChkBanco(this);

		return chkCuenta;
	}

	public ChkCuenta removeChkCuenta(ChkCuenta chkCuenta) {
		getChkCuentas().remove(chkCuenta);
		chkCuenta.setChkBanco(null);

		return chkCuenta;
	}

}