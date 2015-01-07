package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-27T16:13:44.913-0600")
@StaticMetamodel(ChkUsuarioMontosConf.class)
public class ChkUsuarioMontosConf_ {
	public static volatile SingularAttribute<ChkUsuarioMontosConf, String> idConf;
	public static volatile SingularAttribute<ChkUsuarioMontosConf, ChkUsuario> chkUsuario;
	public static volatile SingularAttribute<ChkUsuarioMontosConf, BigDecimal> montoMaximo;
	public static volatile SingularAttribute<ChkUsuarioMontosConf, BigDecimal> montoMinimo;
	public static volatile SingularAttribute<ChkUsuarioMontosConf, Date> fechaUltimaEdicion;
	public static volatile SingularAttribute<ChkUsuarioMontosConf, String> usuarioUltimaEdicion;
}
