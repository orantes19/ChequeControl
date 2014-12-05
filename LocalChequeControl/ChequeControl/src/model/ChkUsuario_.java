package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.186-0600")
@StaticMetamodel(ChkUsuario.class)
public class ChkUsuario_ {
	public static volatile SingularAttribute<ChkUsuario, String> username;
	public static volatile SingularAttribute<ChkUsuario, String> correoElectronico;
	public static volatile SingularAttribute<ChkUsuario, Date> fechaCreacion;
	public static volatile SingularAttribute<ChkUsuario, BigDecimal> intentosFallidos;
	public static volatile SingularAttribute<ChkUsuario, String> password;
	public static volatile SingularAttribute<ChkUsuario, String> primerApellido;
	public static volatile SingularAttribute<ChkUsuario, String> primerNombre;
	public static volatile SingularAttribute<ChkUsuario, String> segundoApellido;
	public static volatile SingularAttribute<ChkUsuario, String> segundoNombre;
	public static volatile SingularAttribute<ChkUsuario, BigDecimal> status;
	public static volatile SingularAttribute<ChkUsuario, String> usuarioCreacion;
	public static volatile ListAttribute<ChkUsuario, ChkRolesPorUsuario> chkRolesPorUsuarios;
}
