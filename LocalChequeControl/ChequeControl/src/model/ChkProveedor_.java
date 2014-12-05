package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.176-0600")
@StaticMetamodel(ChkProveedor.class)
public class ChkProveedor_ {
	public static volatile SingularAttribute<ChkProveedor, Long> codProveedor;
	public static volatile SingularAttribute<ChkProveedor, String> correoElectronico;
	public static volatile SingularAttribute<ChkProveedor, String> direccion;
	public static volatile SingularAttribute<ChkProveedor, String> nit;
	public static volatile SingularAttribute<ChkProveedor, String> nombreComercial;
	public static volatile SingularAttribute<ChkProveedor, String> nombreParaCheques;
	public static volatile SingularAttribute<ChkProveedor, String> telefono;
	public static volatile ListAttribute<ChkProveedor, ChkCheque> chkCheques;
}
