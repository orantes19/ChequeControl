package model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.181-0600")
@StaticMetamodel(ChkRolesPorUsuario.class)
public class ChkRolesPorUsuario_ {
	public static volatile SingularAttribute<ChkRolesPorUsuario, ChkRolesPorUsuarioPK> id;
	public static volatile SingularAttribute<ChkRolesPorUsuario, BigDecimal> estado;
	public static volatile SingularAttribute<ChkRolesPorUsuario, String> fechaAsignacion;
	public static volatile SingularAttribute<ChkRolesPorUsuario, String> usuarioAsignacion;
	public static volatile SingularAttribute<ChkRolesPorUsuario, ChkUsuario> chkUsuario;
	public static volatile SingularAttribute<ChkRolesPorUsuario, ChkRol> chkRol;
}
