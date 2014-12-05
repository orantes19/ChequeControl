package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.174-0600")
@StaticMetamodel(ChkMovimientosCuentaLog.class)
public class ChkMovimientosCuentaLog_ {
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, Long> movimientoid;
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, Date> fechaMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, BigDecimal> idMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, BigDecimal> montoMovimiento;
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, String> numeroCuenta;
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, BigDecimal> resultado;
	public static volatile SingularAttribute<ChkMovimientosCuentaLog, BigDecimal> tipoMovimiento;
}
