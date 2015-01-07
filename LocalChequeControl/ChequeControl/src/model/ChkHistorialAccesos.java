package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CHK_HISTORIAL_ACCESOS")
@NamedQuery(name="ChkHistorialAccesos.findAll", query="SELECT c FROM ChkHistorialAccesos c order by c.fecha desc")
public class ChkHistorialAccesos {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_ID_HISTORIAL", sequenceName="chk_historial_accesos_sq",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_ID_HISTORIAL")
	@Column(name="ID")
	private String id;

	@Column(name="IP_REMOTA")
	private String 	ip;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA")
	private Date fecha;

	@Column(name="USUARIO")
	private String usuario;

	private String resultado;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}


}
