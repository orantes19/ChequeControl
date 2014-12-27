package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-23T16:15:29.873-0600")
@StaticMetamodel(ChkCuenta.class)
public class ChkCuenta_ {
	public static volatile SingularAttribute<ChkCuenta, String> numeroCuenta;
	public static volatile SingularAttribute<ChkCuenta, BigDecimal> estado;
	public static volatile SingularAttribute<ChkCuenta, Date> fechaApertura;
	public static volatile SingularAttribute<ChkCuenta, BigDecimal> saldoActual;
	public static volatile ListAttribute<ChkCuenta, ChkChequera> chkChequeras;
	public static volatile SingularAttribute<ChkCuenta, ChkBanco> chkBanco;
	public static volatile ListAttribute<ChkCuenta, ChkMovimientosCuenta> chkMovimientosCuentas;
}
