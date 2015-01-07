package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-03T18:30:28.215-0600")
@StaticMetamodel(ChkHistorialAccesos.class)
public class ChkHistorialAccesos_ {
	public static volatile SingularAttribute<ChkHistorialAccesos, String> id;
	public static volatile SingularAttribute<ChkHistorialAccesos, String> ip;
	public static volatile SingularAttribute<ChkHistorialAccesos, Date> fecha;
	public static volatile SingularAttribute<ChkHistorialAccesos, String> usuario;
	public static volatile SingularAttribute<ChkHistorialAccesos, String> resultado;
}
