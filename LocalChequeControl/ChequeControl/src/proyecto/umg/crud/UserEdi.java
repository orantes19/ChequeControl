package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ChkRol;
import model.ChkRolesPorUsuario;
import model.ChkRolesPorUsuarioPK;
import model.ChkRolesPorUsuarioPK_;
import model.ChkUsuario;
import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.utils.Utils;







import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class UserEdi extends ViewBase implements ClickListener,
		ValueChangeListener {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;
	TextField txtUserName;
	TextField txtPNom;
	TextField txtSNom;
	TextField txtPAp;
	TextField txtSAp;
	TextField txtMail;
	
	private ViewBase base;
	Table tablaRoles;
	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));
	List<Integer> itemsSeleccionados = new ArrayList<Integer>();
	PasswordField txtPass = new PasswordField("Password");
	PasswordField txtPassConfirm = new PasswordField("Confirmación de Password");
	List<ChkRol> roles = null;
	private String tipo;
	private ChkUsuario seleccion;
	
	public  UserEdi(String tipo,ViewBase base, ChkUsuario seleccion) {
		this.base = base;
		this.tipo = tipo;
		this.seleccion = seleccion;		
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		layout.addComponent(getEtiqueta(tipo+" Usuario"));		
		construirFormulario();
	}
	
	
	
	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		boolean enabled = !tipo.equals(VIEW);
		txtUserName =  getTextField("User Name",seleccion.getUsername(),tipo);
		
		txtPNom =  getTextField("Primer Nombre",seleccion.getPrimerNombre(),tipo);
		
		txtSNom =  getTextField("Segundo Nombre",seleccion.getSegundoNombre(),tipo);
		txtPAp =  getTextField("Primer Apellido",seleccion.getPrimerApellido(),tipo);
		txtSAp =  getTextField("Segundo Apellido",seleccion.getSegundoApellido(),tipo);
		txtMail =  getTextField("Correo Electronico",seleccion.getCorreoElectronico(),tipo);
		formulario.setSizeUndefined();
		
		addComponentsForm(formulario, txtUserName,txtPNom,txtSNom,txtPAp, txtSAp, txtMail);
		
		if (!enabled){
			formulario.addComponent(getTextField("Intentos Fallidos",""+seleccion.getIntentosFallidos(),enabled));
			formulario.addComponent(getTextField("Estado",seleccion.getStatus().intValue() == 1 ? "ACTIVO" : "INACTIVO",enabled));
		}else{
			addComponentsForm(formulario, txtPass,txtPassConfirm);
			txtPass.setValue("********");
			txtPassConfirm.setValue("********");
			txtPass.setEnabled(false);
			txtPassConfirm.setEnabled(false);
		}
		
		//formulario.addComponent(getEtiqueta("Contraseña"));
		
		if (enabled){
			armaTablaEdit();
		}else{
			armaTablaView();
		}
		tablaRoles.setSizeUndefined();		
		formulario.addComponent(tablaRoles);
		if (enabled){
			aceptar = new Button("Guardar");
			aceptar.setStyleName("button");
			aceptar.addClickListener(this);
			formulario.addComponent(aceptar);
		}
		
		formulario.addComponent(getEtiqueta(""));
		
		layout.addComponent(formulario);
		
	}
	
	
	private void armaTablaEdit() {
		tablaRoles = new Table("Roles Asignados");
		
		tablaRoles.setStyleName("textolabel");
		ProjectDao<ChkRol> dao = new ProjectDao<ChkRol>(new ChkRol());
		Map<String, String> rolesTiene = new HashMap<String,String>();
		for (ChkRolesPorUsuario r: seleccion.getChkRolesPorUsuarios()){
			rolesTiene.put(r.getChkRol().getRol(), "S");
		}
		try{
			roles = dao.findAll("ChkRol.findAll");
			Set<Integer> valores = new HashSet<Integer>();
			
			tablaRoles.addContainerProperty("Rol", String.class, "");
		
			tablaRoles.setMultiSelectMode(MultiSelectMode.SIMPLE);
			tablaRoles.setSelectable(true);
			tablaRoles.setMultiSelect(true);
			tablaRoles.setPageLength(5);
			int fila = 1;
			
			for (ChkRol rol: roles){
				tablaRoles.addItem(new Object[]{rol.getRol()}, fila);
				if (rolesTiene.containsKey(rol.getRol())){
					valores.add(new Integer(fila));
				}
					
				fila ++;
			}
			tablaRoles.setValue(valores);
			valueChange(null);
			tablaRoles.addValueChangeListener(this);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

	private void armaTablaView(){
		tablaRoles = new Table("Roles Asignados");
		
		tablaRoles.setStyleName("textolabel");
		tablaRoles.addContainerProperty("Rol", String.class, "");
		tablaRoles.setPageLength(5);
		int fila = 1;
		
		for (ChkRolesPorUsuario rol: seleccion.getChkRolesPorUsuarios()){
			if (rol.getEstado().intValue() == 1){
				tablaRoles.addItem(new Object[]{rol.getChkRol().getRol()}, fila);	
				fila ++;
			}
			
		}
		tablaRoles.setEnabled(false);
		
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

	@Override
	public void buttonClick(ClickEvent event) {
		ChkUsuario user = new ChkUsuario();
		user.setUsername(txtUserName.getValue());
		if (!txtMail.getValue().trim().matches(Utils.REGEXP_MAIL)){
			advertencia("Error", "La dirección de Correo Electronico Ingresada no es valida",
					Page.getCurrent(), ERROR);
			return;
		}
		ProjectDao<ChkUsuario> dao = new ProjectDao<ChkUsuario>(user);
		ProjectDao<ChkRol> rolDao = null;
		try {
			dao.findElementById(user.getUsername());
			dao.getEntity().setPrimerNombre(txtPNom.getValue());
			dao.getEntity().setSegundoNombre(txtSNom.getValue());
			dao.getEntity().setPrimerApellido(txtPAp.getValue());
			dao.getEntity().setSegundoApellido(txtSAp.getValue());
			dao.getEntity().setCorreoElectronico(txtMail.getValue());
			dao.getEntity().setPassword(seleccion.getPassword());
			List<ChkRolesPorUsuario> rolesUser = new ArrayList<ChkRolesPorUsuario>();
			System.out.println("Se setean los roles  YA CON CAMBIOS");
			for (Integer i : itemsSeleccionados) {
				rolDao = new ProjectDao<ChkRol>(new ChkRol());
				ChkRolesPorUsuario rolU = new ChkRolesPorUsuario();
				rolU.setChkRol(rolDao
						.findElementById(roles.get(i - 1).getRol()));
				
				
				rolU.setChkUsuario(dao.getEntity());
				ChkRolesPorUsuarioPK pk = new ChkRolesPorUsuarioPK();
				pk.setUsername(dao.getEntity().getUsername());
				pk.setRol(rolDao.getEntity().getRol());
				rolU.setId(pk);
				System.out.println("usuario para rol:  "+roles.get(i-1).getRol()+"   :   "+dao.getEntity().getUsername() + rolU);
				rolU.setFechaAsignacion(formatearFecha(new Date()));
				rolU.setEstado(new BigDecimal(1));
				rolU.setUsuarioAsignacion(((ChkUsuario) obtieneVariableSesion("USUARIO"))
						.getUsername());
				rolesUser.add(rolU);

			}
			dao.getEntity().setChkRolesPorUsuarios(rolesUser);
			dao.merge();		
			info("Exito", "El registro fue editado Correctamente", Page.getCurrent(), OK);
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
			rolDao.closeEntityManager();
		}
				

	}

}
