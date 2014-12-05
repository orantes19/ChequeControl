package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CHK_MOVIMIENTOS_CUENTA database table.
 * 
 */
@Entity
@Table(name="CHK_MOVIMIENTOS_CUENTA")
@NamedQuery(name="ChkMovimientosCuenta.findAll", query="SELECT c FROM ChkMovimientosCuenta c")
public class ChkMovimientosCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_MOVIMIENTOS_CUENTA_IDMOVIMIENTO_GENERATOR", sequenceName="CHK_MOVIMIENTOS_CUENTA_SQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_MOVIMIENTOS_CUENTA_IDMOVIMIENTO_GENERATOR")
	@Column(name="ID_MOVIMIENTO")
	private long idMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_MOVIMIENTO")
	private Date fechaMovimiento;

	@Column(name="MONTO_MOVIMIENTO")
	private BigDecimal montoMovimiento;

	@Column(name="TIPO_MOVIMIENTO")
	private BigDecimal tipoMovimiento;

	//bi-directional many-to-one association to ChkCuenta
	@ManyToOne
	@JoinColumn(name="NUMERO_CUENTA")
	private ChkCuenta chkCuenta;

	public ChkMovimientosCuenta() {
	}

	public long getIdMovimiento() {
		return this.idMovimiento;
	}

	public void setIdMovimiento(long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public Date getFechaMovimiento() {
		return this.fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public BigDecimal getMontoMovimiento() {
		return this.montoMovimiento;
	}

	public void setMontoMovimiento(BigDecimal montoMovimiento) {
		this.montoMovimiento = montoMovimiento;
	}

	public BigDecimal getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(BigDecimal tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public ChkCuenta getChkCuenta() {
		return this.chkCuenta;
	}

	public void setChkCuenta(ChkCuenta chkCuenta) {
		this.chkCuenta = chkCuenta;
	}

}