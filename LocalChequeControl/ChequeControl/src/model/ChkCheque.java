package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CHK_CHEQUE database table.
 * 
 */
@Entity
@Table(name="CHK_CHEQUE")
@NamedQuery(name="ChkCheque.findAll", query="SELECT c FROM ChkCheque c")
public class ChkCheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_CHEQUE_IDCHEQUE_GENERATOR", sequenceName="CHK_CHEQUE_SQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_CHEQUE_IDCHEQUE_GENERATOR")
	@Column(name="ID_CHEQUE")
	private long idCheque;

	private BigDecimal estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_EMISION")
	private Date fechaEmision;

	private BigDecimal monto;

	@Column(name="NUMERO_CHEQUE")
	private BigDecimal numeroCheque;

	//bi-directional many-to-one association to ChkChequera
	@ManyToOne
	@JoinColumn(name="ID_CHEQUERA")
	private ChkChequera chkChequera;

	//bi-directional many-to-one association to ChkProveedor
	@ManyToOne
	@JoinColumn(name="COD_PROVEEDOR")
	private ChkProveedor chkProveedor;

	public ChkCheque() {
	}

	public long getIdCheque() {
		return this.idCheque;
	}

	public void setIdCheque(long idCheque) {
		this.idCheque = idCheque;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getNumeroCheque() {
		return this.numeroCheque;
	}

	public void setNumeroCheque(BigDecimal numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public ChkChequera getChkChequera() {
		return this.chkChequera;
	}

	public void setChkChequera(ChkChequera chkChequera) {
		this.chkChequera = chkChequera;
	}

	public ChkProveedor getChkProveedor() {
		return this.chkProveedor;
	}

	public void setChkProveedor(ChkProveedor chkProveedor) {
		this.chkProveedor = chkProveedor;
	}

}