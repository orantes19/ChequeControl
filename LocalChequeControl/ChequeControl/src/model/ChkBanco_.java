package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-03T10:19:42.017-0600")
@StaticMetamodel(ChkBanco.class)
public class ChkBanco_ {
	public static volatile SingularAttribute<ChkBanco, Long> idBanco;
	public static volatile SingularAttribute<ChkBanco, String> direccion;
	public static volatile SingularAttribute<ChkBanco, String> fechaCreacion;
	public static volatile SingularAttribute<ChkBanco, String> nombre;
	public static volatile SingularAttribute<ChkBanco, String> telefono;
	public static volatile SingularAttribute<ChkBanco, String> usuarioCreacion;
	public static volatile ListAttribute<ChkBanco, ChkChequera> chkChequeras;
	public static volatile ListAttribute<ChkBanco, ChkCuenta> chkCuentas;
}
