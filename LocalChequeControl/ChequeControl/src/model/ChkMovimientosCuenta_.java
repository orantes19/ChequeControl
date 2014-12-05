package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.172-0600")
@StaticMetamodel(ChkMovimientosCuenta.class)
public class ChkMovimientosCuenta_ {
	public static volatile SingularAttribute<ChkMovimientosCuenta, Long> idMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuenta, Date> fechaMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuenta, BigDecimal> montoMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuenta, BigDecimal> tipoMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuenta, ChkCuenta> chkCuenta;
}
