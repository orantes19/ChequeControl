package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CHK_MOVIMIENTOS_CUENTA_LOG database table.
 * 
 */
@Entity
@Table(name="CHK_MOVIMIENTOS_CUENTA_LOG")
@NamedQuery(name="ChkMovimientosCuentaLog.findAll", query="SELECT c FROM ChkMovimientosCuentaLog c")
public class ChkMovimientosCuentaLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_MOVIMIENTOS_CUENTA_LOG_MOVIMIENTOID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_MOVIMIENTOS_CUENTA_LOG_MOVIMIENTOID_GENERATOR")
	private long movimientoid;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_MOVIMIENTO")
	private Date fechaMovimiento;

	@Column(name="ID_MOVIMIENTO")
	private BigDecimal idMovimiento;

	@Column(name="MONTO_MOVIMIENTO")
	private BigDecimal montoMovimiento;

	@Column(name="NUMERO_CUENTA")
	private String numeroCuenta;

	private BigDecimal resultado;

	@Column(name="TIPO_MOVIMIENTO")
	private BigDecimal tipoMovimiento;

	public ChkMovimientosCuentaLog() {
	}

	public long getMovimientoid() {
		return this.movimientoid;
	}

	public void setMovimientoid(long movimientoid) {
		this.movimientoid = movimientoid;
	}

	public Date getFechaMovimiento() {
		return this.fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public BigDecimal getIdMovimiento() {
		return this.idMovimiento;
	}

	public void setIdMovimiento(BigDecimal idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public BigDecimal getMontoMovimiento() {
		return this.montoMovimiento;
	}

	public void setMontoMovimiento(BigDecimal montoMovimiento) {
		this.montoMovimiento = montoMovimiento;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public BigDecimal getResultado() {
		return this.resultado;
	}

	public void setResultado(BigDecimal resultado) {
		this.resultado = resultado;
	}

	public BigDecimal getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(BigDecimal tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

}