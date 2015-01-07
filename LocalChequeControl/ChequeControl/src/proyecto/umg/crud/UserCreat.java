package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.ChkRol;
import model.ChkRolesPorUsuario;
import model.ChkRolesPorUsuarioPK;
import model.ChkUsuario;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.utils.Utils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UserCreat extends ViewBase implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;

	TextField txtUserName = getTextField("User Name", EMPTY,true);

	TextField txtPNom = getTextField("Primer Nombre", EMPTY,true);

	TextField txtPAp = getTextField("Primer Apellido", EMPTY,true);
	TextField txtSNom = getTextField("Segundo Nombre", EMPTY,true);
	TextField txtSAp = getTextField("Segundo Apellido", EMPTY,true);
	TextField txtMail = getTextField("Correo Electronico", EMPTY,true);
	FormLayout formulario = new FormLayout();
	Table tablaRoles = new Table("Roles a Asignar");
	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));
	List<Integer> itemsSeleccionados = new ArrayList<Integer>();
	PasswordField txtPass = new PasswordField("Contraseña");
	PasswordField txtPassConfirm = new PasswordField("Confirmar Contraseña");
	List<ChkRol> roles = null;
	private Ventana papa;
	private ViewBase base;
	
	
	public UserCreat(){
		construct();
		
	}
	public UserCreat(Ventana papa, ViewBase base){
		this.base = base;
		this.papa = papa;
		construct();
		
	}
	
	
	private void construct(){
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		layout.addComponent(getEtiqueta("Nuevo Usuario"));		
		construirFormulario();
	}
	@SuppressWarnings("deprecation")
	private void construirFormulario() {
		
		formulario.addComponent(txtUserName);
		formulario.addComponent(txtPNom);
		formulario.addComponent(txtSNom);
		formulario.addComponent(txtPAp);
		formulario.addComponent(txtSAp);
		formulario.addComponent(txtMail);
		setRequiredElements(txtUserName,txtPNom,txtPAp,txtPass,txtPassConfirm,txtMail, tablaRoles);		
		txtPass.setStyleName("textolabel");
		formulario.addComponent(txtPass);
		txtPassConfirm.setStyleName("textolabel");
		formulario.addComponent(txtPassConfirm);
		ProjectDao<ChkRol> dao = new ProjectDao<ChkRol>(new ChkRol());
		tablaRoles.setSizeUndefined();
		try {
			roles = dao.findAll("ChkRol.findAll");
			tablaRoles.addContainerProperty("Rol", String.class, "");
			// tablaRoles.setWidth("300px");
			// tablaRoles.setHeight("100px");

			tablaRoles.setMultiSelectMode(MultiSelectMode.SIMPLE);
			tablaRoles.setSelectable(true);
			tablaRoles.setMultiSelect(true);
			tablaRoles.setPageLength(5);
			tablaRoles.setRequired(true);
			int fila = 1;
			for (ChkRol rol : roles) {
				System.out.println("Agregando rol " + rol.getRol());
				tablaRoles.addItem(new Object[] { rol.getRol() }, fila);
				fila++;
			}
			tablaRoles.addListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		formulario.addComponent(tablaRoles);
		aceptar.addClickListener(this);
		setStyleButtons(aceptar);
		formulario.addComponent(aceptar);
		
		layout.addComponent(formulario);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		try{
			txtPass.validate();
		}catch(InvalidValueException e){
			e.printStackTrace();
		}
		
		
		if (!txtPass.getValue().equals(txtPassConfirm.getValue())) {
			advertencia("Error", "Contraseñas no coinciden", Page.getCurrent(),
					ERROR);
			return;
		}
		if (itemsSeleccionados == null || itemsSeleccionados.size() == 0) {
			advertencia("Error", "Debe Seleccionar al menos Un Rol",
					Page.getCurrent(), ERROR);
			return;
		}
		if (!txtMail.getValue().trim().matches(Utils.REGEXP_MAIL)){
			advertencia("Error", "La dirección de Correo Electronico Ingresada no es valida",
					Page.getCurrent(), ERROR);
			return;
		}

		ProjectDao<ChkUsuario> dao = new ProjectDao<ChkUsuario>(
				new ChkUsuario());
		ProjectDao<ChkRol> rolDao = null;

		try {
			dao.findElementById(txtUserName.getValue());
			ChkUsuario usuario = new ChkUsuario();
			usuario.setUsername(txtUserName.getValue());

			if (dao.getEntity() != null) {
				if (dao.getEntity().getUsername().equals(usuario.getUsername())) {
					advertencia(
							"Error",
							"El UserName Ingresado ya Existe, intente con otro",
							Page.getCurrent(), ERROR);
					return;
				}
			}
			usuario.setPrimerNombre(txtPNom.getValue());
			usuario.setSegundoNombre(txtSNom.getValue());
			usuario.setPrimerApellido(txtPAp.getValue());
			usuario.setSegundoApellido(txtSAp.getValue());
			usuario.setCorreoElectronico(txtMail.getValue());
			usuario.setPassword(Utils.convertSha2(txtPass.getValue()));
			usuario.setIntentosFallidos(new BigDecimal(0));
			usuario.setStatus(new BigDecimal(1));
			usuario.setFechaCreacion(new Date());
			usuario.setUsuarioCreacion(((ChkUsuario) obtieneVariableSesion("USUARIO"))
					.getUsername());
			dao.setEntity(usuario);
			
			List<ChkRolesPorUsuario> rolesUser = new ArrayList<ChkRolesPorUsuario>();
			System.out.println("Se setean los roles  YA CON CAMBIOS");
			for (Integer i : itemsSeleccionados) {
				rolDao = new ProjectDao<ChkRol>(new ChkRol());
				ChkRolesPorUsuario rolU = new ChkRolesPorUsuario();
				rolU.setChkRol(rolDao
						.findElementById(roles.get(i - 1).getRol()));
				
				
				rolU.setChkUsuario(usuario);
				ChkRolesPorUsuarioPK pk = new ChkRolesPorUsuarioPK();
				pk.setUsername(usuario.getUsername());
				pk.setRol(rolDao.getEntity().getRol());
				rolU.setId(pk);
				System.out.println("usuario para rol:  "+roles.get(i-1).getRol()+"   :   "+usuario.getUsername() + rolU);
				rolU.setFechaAsignacion(formatearFecha(new Date()));
				rolU.setEstado(new BigDecimal(1));
				rolU.setUsuarioAsignacion(((ChkUsuario) obtieneVariableSesion("USUARIO"))
						.getUsername());
				rolesUser.add(rolU);

			}
			dao.getEntity().setChkRolesPorUsuarios(rolesUser);
			dao.save();
			info("Exito", "Los Datos fueron Guardados Correctamente",
					Page.getCurrent(), OK);
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);
			
			if (papa != null){
				papa.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
			error("Error",
					"Ocurrio un error al guardar el usuario " + e.getMessage(),
					Page.getCurrent(), ERROR);

		}

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object values = tablaRoles.getValue();		
        if (values instanceof Set) {
            // Multiple selected values returned as a Set
            itemsSeleccionados = new ArrayList<Integer>((Set) values);
           
        } else if (values != null) {
            // If only one value is selected it will be a String
        	itemsSeleccionados = new ArrayList<Integer>();
        	itemsSeleccionados.add(new Integer(values.toString()));
        } else {
           itemsSeleccionados = null;
        }
        
        System.out.println("Elementos Seleccionados "+itemsSeleccionados);    

	}

}
