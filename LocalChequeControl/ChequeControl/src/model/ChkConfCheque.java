package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CHK_CONF_CHEQUES database table.
 * 
 */
@Entity
@Table(name="CHK_CONF_CHEQUES")
@NamedQuery(name="ChkConfCheque.findAll", query="SELECT c FROM ChkConfCheque c")
public class ChkConfCheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_CONF_CHEQUES_IDCONF_GENERATOR", sequenceName="CHK_CONF_CHEQUES", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_CONF_CHEQUES_IDCONF_GENERATOR")
	@Column(name="ID_CONF")
	private long idConf;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ULTIMA_EDICION")
	private Date fechaUltimaEdicion;

	@Column(name="MONTO_MAXIMO")
	private BigDecimal montoMaximo;

	@Column(name="MONTO_MINIMO")
	private BigDecimal montoMinimo;

	@Column(name="USUARIO_ULTIMA_EDICION")
	private String usuarioUltimaEdicion;

	//bi-directional many-to-one association to ChkRol
	@ManyToOne
	@JoinColumn(name="ROL")
	private ChkRol chkRol;

	public ChkConfCheque() {
	}

	public long getIdConf() {
		return this.idConf;
	}

	public void setIdConf(long idConf) {
		this.idConf = idConf;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaEdicion() {
		return this.fechaUltimaEdicion;
	}

	public void setFechaUltimaEdicion(Date fechaUltimaEdicion) {
		this.fechaUltimaEdicion = fechaUltimaEdicion;
	}

	public BigDecimal getMontoMaximo() {
		return this.montoMaximo;
	}

	public void setMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public BigDecimal getMontoMinimo() {
		return this.montoMinimo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public String getUsuarioUltimaEdicion() {
		return this.usuarioUltimaEdicion;
	}

	public void setUsuarioUltimaEdicion(String usuarioUltimaEdicion) {
		this.usuarioUltimaEdicion = usuarioUltimaEdicion;
	}

	public ChkRol getChkRol() {
		return this.chkRol;
	}

	public void setChkRol(ChkRol chkRol) {
		this.chkRol = chkRol;
	}

}