package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.162-0600")
@StaticMetamodel(ChkCheque.class)
public class ChkCheque_ {
	public static volatile SingularAttribute<ChkCheque, Long> idCheque;
	public static volatile SingularAttribute<ChkCheque, BigDecimal> estado;
	public static volatile SingularAttribute<ChkCheque, Date> fechaEmision;
	public static volatile SingularAttribute<ChkCheque, BigDecimal> monto;
	public static volatile SingularAttribute<ChkCheque, BigDecimal> numeroCheque;
	public static volatile SingularAttribute<ChkCheque, ChkChequera> chkChequera;
	public static volatile SingularAttribute<ChkCheque, ChkProveedor> chkProveedor;
}
