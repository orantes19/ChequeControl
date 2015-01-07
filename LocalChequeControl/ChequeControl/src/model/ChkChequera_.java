package model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-29T10:02:50.575-0600")
@StaticMetamodel(ChkChequera.class)
public class ChkChequera_ {
	public static volatile SingularAttribute<ChkChequera, Long> idChequera;
	public static volatile SingularAttribute<ChkChequera, BigDecimal> cantidadCheques;
	public static volatile SingularAttribute<ChkChequera, BigDecimal> chequesEmitidos;
	public static volatile SingularAttribute<ChkChequera, BigDecimal> estado;
	public static volatile SingularAttribute<ChkChequera, String> numeroSerie;
	public static volatile ListAttribute<ChkChequera, ChkCheque> chkCheques;
	public static volatile SingularAttribute<ChkChequera, ChkCuenta> chkCuenta;
	public static volatile SingularAttribute<ChkChequera, ChkBanco> chkBanco;
}
