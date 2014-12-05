package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.178-0600")
@StaticMetamodel(ChkRol.class)
public class ChkRol_ {
	public static volatile SingularAttribute<ChkRol, String> rol;
	public static volatile SingularAttribute<ChkRol, String> descripcion;
	public static volatile SingularAttribute<ChkRol, Date> fechaCreacion;
	public static volatile SingularAttribute<ChkRol, String> usuarioCreacion;
	public static volatile ListAttribute<ChkRol, ChkConfCheque> chkConfCheques;
	public static volatile ListAttribute<ChkRol, ChkRolesPorUsuario> chkRolesPorUsuarios;
}
