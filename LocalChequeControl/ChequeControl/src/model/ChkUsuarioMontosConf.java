package model;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ChkUsuarioMontosConf
 *
 */
@Entity
@Table(name="CHK_USUARIO_MONTOS_CONF")
@NamedQuery(name="ChkUsuarioMontosConf.findAll", query="SELECT c FROM ChkUsuarioMontosConf c")
public class ChkUsuarioMontosConf implements Serializable {
	@Id
	@SequenceGenerator(name="CHK_USUARIO_MONTOS_GENERATOR", sequenceName="CHK_USUARIO_MONTOS_CONF_SQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_USUARIO_MONTOS_GENERATOR")
	@Column(name="IDCONF")
	private String idConf;
	
	@ManyToOne
	@JoinColumn(name="USERNAME")
	private ChkUsuario chkUsuario;
	
	@Column(name="MONTO_MAXIMO")
	private BigDecimal montoMaximo;
	
	@Column(name="MONTO_MINIMO")
	private BigDecimal montoMinimo;
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ULTIMA_EDICION")
	private Date fechaUltimaEdicion;
	
	@Column(name="USUARIO_ULTIMA_EDICION")
	private String usuarioUltimaEdicion;
	
	private static final long serialVersionUID = 1L;

	public ChkUsuarioMontosConf() {
		super();
	}   
	public ChkUsuario getChkUsuario() {
		return this.chkUsuario;
	}

	public void setChkUsuario(ChkUsuario chkUsuario) {
		this.chkUsuario = chkUsuario;
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
	public Date getFechaUltimaEdicion() {
		return this.fechaUltimaEdicion;
	}

	public void setFechaUltimaEdicion(Date fechaUltimaEdicion) {
		this.fechaUltimaEdicion = fechaUltimaEdicion;
	}   
	public String getUsuarioUltimaEdicion() {
		return this.usuarioUltimaEdicion;
	}

	public void setUsuarioUltimaEdicion(String usuarioUltimaEdicion) {
		this.usuarioUltimaEdicion = usuarioUltimaEdicion;
	}
	public String getIdConf() {
		return idConf;
	}
	public void setIdConf(String idConf) {
		this.idConf = idConf;
	}
   
}
