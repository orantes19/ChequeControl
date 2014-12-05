package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.167-0600")
@StaticMetamodel(ChkConfCheque.class)
public class ChkConfCheque_ {
	public static volatile SingularAttribute<ChkConfCheque, Long> idConf;
	public static volatile SingularAttribute<ChkConfCheque, Date> fechaCreacion;
	public static volatile SingularAttribute<ChkConfCheque, Date> fechaUltimaEdicion;
	public static volatile SingularAttribute<ChkConfCheque, BigDecimal> montoMaximo;
	public static volatile SingularAttribute<ChkConfCheque, BigDecimal> montoMinimo;
	public static volatile SingularAttribute<ChkConfCheque, String> usuarioUltimaEdicion;
	public static volatile SingularAttribute<ChkConfCheque, ChkRol> chkRol;
}
