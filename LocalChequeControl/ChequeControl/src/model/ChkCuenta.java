package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CHK_CUENTA database table.
 * 
 */
@Entity
@Table(name="CHK_CUENTA")
@NamedQuery(name="ChkCuenta.findAll", query="SELECT c FROM ChkCuenta c")
public class ChkCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_CUENTA_NUMEROCUENTA_GENERATOR", sequenceName="CHK_CUENTA_SQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_CUENTA_NUMEROCUENTA_GENERATOR")
	@Column(name="NUMERO_CUENTA")
	private String numeroCuenta;

	private BigDecimal estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_APERTURA")
	private Date fechaApertura;

	@Column(name="SALDO_ACTUAL")
	private BigDecimal saldoActual;

	//bi-directional many-to-one association to ChkChequera
	@OneToMany(mappedBy="chkCuenta")
	private List<ChkChequera> chkChequeras;

	//bi-directional many-to-one association to ChkBanco
	@ManyToOne
	@JoinColumn(name="ID_BANCO")
	private ChkBanco chkBanco;

	//bi-directional many-to-one association to ChkMovimientosCuenta
	@OneToMany(mappedBy="chkCuenta")
	private List<ChkMovimientosCuenta> chkMovimientosCuentas;

	public ChkCuenta() {
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public BigDecimal getSaldoActual() {
		return this.saldoActual;
	}

	public void setSaldoActual(BigDecimal saldoActual) {
		this.saldoActual = saldoActual;
	}

	public List<ChkChequera> getChkChequeras() {
		return this.chkChequeras;
	}

	public void setChkChequeras(List<ChkChequera> chkChequeras) {
		this.chkChequeras = chkChequeras;
	}

	public ChkChequera addChkChequera(ChkChequera chkChequera) {
		getChkChequeras().add(chkChequera);
		chkChequera.setChkCuenta(this);

		return chkChequera;
	}

	public ChkChequera removeChkChequera(ChkChequera chkChequera) {
		getChkChequeras().remove(chkChequera);
		chkChequera.setChkCuenta(null);

		return chkChequera;
	}

	public ChkBanco getChkBanco() {
		return this.chkBanco;
	}

	public void setChkBanco(ChkBanco chkBanco) {
		this.chkBanco = chkBanco;
	}

	public List<ChkMovimientosCuenta> getChkMovimientosCuentas() {
		return this.chkMovimientosCuentas;
	}

	public void setChkMovimientosCuentas(List<ChkMovimientosCuenta> chkMovimientosCuentas) {
		this.chkMovimientosCuentas = chkMovimientosCuentas;
	}

	public ChkMovimientosCuenta addChkMovimientosCuenta(ChkMovimientosCuenta chkMovimientosCuenta) {
		getChkMovimientosCuentas().add(chkMovimientosCuenta);
		chkMovimientosCuenta.setChkCuenta(this);

		return chkMovimientosCuenta;
	}

	public ChkMovimientosCuenta removeChkMovimientosCuenta(ChkMovimientosCuenta chkMovimientosCuenta) {
		getChkMovimientosCuentas().remove(chkMovimientosCuenta);
		chkMovimientosCuenta.setChkCuenta(null);

		return chkMovimientosCuenta;
	}

}