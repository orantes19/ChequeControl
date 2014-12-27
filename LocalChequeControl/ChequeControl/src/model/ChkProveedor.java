package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CHK_PROVEEDOR database table.
 * 
 */
@Entity
@Table(name="CHK_PROVEEDOR")
@NamedQuery(name="ChkProveedor.findAll", query="SELECT c FROM ChkProveedor c")
public class ChkProveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHK_PROVEEDOR_CODPROVEEDOR_GENERATOR", sequenceName="CHK_PROVEEDOR_SQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHK_PROVEEDOR_CODPROVEEDOR_GENERATOR")
	@Column(name="COD_PROVEEDOR")
	private long codProveedor;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	private String direccion;

	private String nit;

	@Column(name="NOMBRE_COMERCIAL")
	private String nombreComercial;

	@Column(name="NOMBRE_PARA_CHEQUES")
	private String nombreParaCheques;

	private String telefono;

	//bi-directional many-to-one association to ChkCheque
	@OneToMany(mappedBy="chkProveedor")
	private List<ChkCheque> chkCheques;

	public ChkProveedor() {
	}

	public long getCodProveedor() {
		return this.codProveedor;
	}

	public void setCodProveedor(long codProveedor) {
		this.codProveedor = codProveedor;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombreComercial() {
		return this.nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getNombreParaCheques() {
		return this.nombreParaCheques;
	}

	public void setNombreParaCheques(String nombreParaCheques) {
		this.nombreParaCheques = nombreParaCheques;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<ChkCheque> getChkCheques() {
		return this.chkCheques;
	}

	public void setChkCheques(List<ChkCheque> chkCheques) {
		this.chkCheques = chkCheques;
	}

	public ChkCheque addChkCheque(ChkCheque chkCheque) {
		getChkCheques().add(chkCheque);
		chkCheque.setChkProveedor(this);

		return chkCheque;
	}

	public ChkCheque removeChkCheque(ChkCheque chkCheque) {
		getChkCheques().remove(chkCheque);
		chkCheque.setChkProveedor(null);

		return chkCheque;
	}

}