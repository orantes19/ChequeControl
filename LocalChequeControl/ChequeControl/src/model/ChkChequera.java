package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CHK_CHEQUERA database table.
 * 
 */
@Entity
@Table(name="CHK_CHEQUERA")
@NamedQuery(name="ChkChequera.findAll", query="SELECT c FROM ChkChequera c")
public class ChkChequera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_CHEQUERA_IDCHEQUERA_GENERATOR", sequenceName="CHK_CHEQUE_SQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_CHEQUERA_IDCHEQUERA_GENERATOR")
	@Column(name="ID_CHEQUERA")
	private long idChequera;

	@Column(name="CANTIDAD_CHEQUES")
	private BigDecimal cantidadCheques;
	
	@Column(name="CHEQUES_EMITIDOS")
	private BigDecimal chequesEmitidos;
	
	@Column(name="ESTADO")
	private BigDecimal estado;

	@Column(name="NUMERO_SERIE")
	private String numeroSerie;

	//bi-directional many-to-one association to ChkCheque
	@OneToMany(mappedBy="chkChequera")
	private List<ChkCheque> chkCheques;

	//bi-directional many-to-one association to ChkCuenta
	@ManyToOne
	@JoinColumn(name="NUMERO_CUENTA")
	private ChkCuenta chkCuenta;

	//bi-directional many-to-one association to ChkBanco
	@ManyToOne
	@JoinColumn(name="ID_BANCO")
	private ChkBanco chkBanco;

	public ChkChequera() {
	}

	public long getIdChequera() {
		return this.idChequera;
	}

	public void setIdChequera(long idChequera) {
		this.idChequera = idChequera;
	}

	public BigDecimal getChequesEmitidos() {
		return chequesEmitidos;
	}

	public void setChequesEmitidos(BigDecimal chequesEmitidos) {
		this.chequesEmitidos = chequesEmitidos;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BigDecimal getCantidadCheques() {
		return this.cantidadCheques;
	}

	public void setCantidadCheques(BigDecimal cantidadCheques) {
		this.cantidadCheques = cantidadCheques;
	}

	public String getNumeroSerie() {
		return this.numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public List<ChkCheque> getChkCheques() {
		return this.chkCheques;
	}

	public void setChkCheques(List<ChkCheque> chkCheques) {
		this.chkCheques = chkCheques;
	}

	public ChkCheque addChkCheque(ChkCheque chkCheque) {
		getChkCheques().add(chkCheque);
		chkCheque.setChkChequera(this);

		return chkCheque;
	}

	public ChkCheque removeChkCheque(ChkCheque chkCheque) {
		getChkCheques().remove(chkCheque);
		chkCheque.setChkChequera(null);

		return chkCheque;
	}

	public ChkCuenta getChkCuenta() {
		return this.chkCuenta;
	}

	public void setChkCuenta(ChkCuenta chkCuenta) {
		this.chkCuenta = chkCuenta;
	}

	public ChkBanco getChkBanco() {
		return this.chkBanco;
	}

	public void setChkBanco(ChkBanco chkBanco) {
		this.chkBanco = chkBanco;
	}

}