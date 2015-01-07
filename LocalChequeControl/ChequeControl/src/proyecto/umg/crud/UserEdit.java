package proyecto.umg.crud;

import java.util.ArrayList;
import java.util.List;

import model.ChkRol;
import model.ChkRolesPorUsuario;
import model.ChkUsuario;
import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UserEdit extends Window implements ValueChangeListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;
	private ViewBase base;
	
	TextField txtUserName;
	
	TextField txtPNom;
	
	TextField txtSNom;
	TextField txtPAp;
	TextField txtSAp;
	TextField txtMail;
	Table tablaRoles;
	Button aceptar = new Button("Guardar");
	List<Integer> itemsSeleccionados = new ArrayList<Integer>();
	PasswordField txtPass = new PasswordField();
	PasswordField txtPassConfirm = new PasswordField();
	List<ChkRol> roles = null;
	private String tipo;
	private ChkUsuario seleccion;
	
	public UserEdit(ViewBase bas, String tipo, ChkUsuario seleccion){
		super("Creacion de Usuario");
		base = bas;
		this.tipo = tipo;
		this.seleccion = seleccion;
		setWidth("500px");
		setHeight("600px");
		setStyleName("window-sub");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		layout.addComponent(getEtiqueta("Nuevo Usuario"));
		center();
		construirFormulario();
		
	}
	
	private TextField getTextField(String text){
		TextField tf = new TextField();
		
		if(tipo.equals("VIEW")){
			tf.setEnabled(false);
		}
		tf.setInputPrompt(text);
		return tf;
	}
	private TextField getTextField(String caption,String text){
		TextField tf = new TextField(caption,text);
		if(tipo.equals("VIEW")){
			tf.setEnabled(false);
		}
		tf.setStyleName("textolabel");
		return tf;
	}
	private Label getEtiqueta(String text){
		Label eti = new Label(text);
		eti.setStyleName("textolabel");
		return eti;
	}
	
	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		txtUserName =  getTextField("User Name",seleccion.getUsername());
		
		txtPNom =  getTextField("Primer Nombre",seleccion.getPrimerNombre());
		
		txtSNom =  getTextField("Segundo Nombre",seleccion.getSegundoNombre());
		txtPAp =  getTextField("Primer Apellido",seleccion.getPrimerApellido());
		txtSAp =  getTextField("Segundo Apellido",seleccion.getSegundoApellido());
		txtMail =  getTextField("Correo Electronico",seleccion.getCorreoElectronico());
		formulario.setSizeUndefined();
		formulario.addComponent(txtUserName);
		formulario.addComponent(txtPNom);
		formulario.addComponent(txtSNom);
		formulario.addComponent(txtPAp);
		formulario.addComponent(txtSAp);
		formulario.addComponent(txtMail);
		formulario.addComponent(getTextField("Intentos Fallidos",""+seleccion.getIntentosFallidos()));
		formulario.addComponent(getTextField("Estado",seleccion.getStatus().intValue() == 1 ? "ACTIVO" : "INACTIVO"));
		//formulario.addComponent(getEtiqueta("Contraseña"));
		
		if (!tipo.equals("VIEW")){
			armaTablaEdit();
		}else{
			armaTablaView();
		}
		tablaRoles.setSizeUndefined();
		formulario.addComponent(tablaRoles);		
		formulario.addComponent(getEtiqueta(""));
		
		layout.addComponent(formulario);
		
	}
	private void armaTablaEdit() {
		tablaRoles = new Table("Roles Asignados");
		tablaRoles.setStyleName("textolabel");
		ProjectDao<ChkRol> dao = new ProjectDao<ChkRol>(new ChkRol());
		try{
			roles = dao.findAll("ChkRol.findAll");
			tablaRoles.addContainerProperty("Rol", String.class, "");
		
			tablaRoles.setMultiSelectMode(MultiSelectMode.SIMPLE);
			tablaRoles.setSelectable(true);
			tablaRoles.setMultiSelect(true);
			tablaRoles.setPageLength(10);
			int fila = 1;
			for (ChkRol rol: roles){
				System.out.println("Agregando rol "+rol.getRol());
				tablaRoles.addItem(new Object[]{rol.getRol()}, fila);
				fila ++;
			}
			tablaRoles.addListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void armaTablaView(){
		tablaRoles = new Table("Roles Asignados");
		
		tablaRoles.setStyleName("textolabel");
		tablaRoles.addContainerProperty("Rol", String.class, "");
		//tablaRoles.setWidth("100px");
//		tablaRoles.setHeight("100px");
		
		tablaRoles.setPageLength(5);
		int fila = 1;
		System.out.println("Agregando roles ..."+seleccion.getChkRolesPorUsuarios());
		
		
		for (ChkRolesPorUsuario rol: seleccion.getChkRolesPorUsuarios()){
			if (rol.getEstado().intValue() == 1){
				System.out.println("Rol a agregar  "+rol.getChkRol().getRol());
				tablaRoles.addItem(new Object[]{rol.getChkRol().getRol()}, fila);
				
				fila ++;
			}
			
		}
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
