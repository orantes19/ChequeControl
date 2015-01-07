package proyecto.umg.crud;

import java.util.ArrayList;
import java.util.List;

import model.ChkBanco;
import model.ChkRol;
import model.ChkRolesPorUsuario;
import model.ChkBanco;
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

public class BancoEdit extends ViewBase implements ClickListener,
		ValueChangeListener {

	/**
* 
*/
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;
	TextField idBanco;
	TextField nombre;
	TextField direccion;
	TextField telefono;

	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));
	List<Integer> itemsSeleccionados = new ArrayList<Integer>();
	PasswordField txtPass = new PasswordField();
	PasswordField txtPassConfirm = new PasswordField();
	List<ChkRol> roles = null;
	private String tipo;
	private ChkBanco seleccion;
	private ViewBase base;
	

	public BancoEdit(String tipo, ViewBase base,ChkBanco seleccion) {
		this.base = base;
		this.tipo = tipo;
		this.seleccion = seleccion;
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		layout.addComponent(getEtiqueta("Editar Banco"));
		construirFormulario();
	}

	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		
		boolean enabled = !tipo.equals(VIEW);
		idBanco = getTextField("ID", String.valueOf(seleccion.getIdBanco()),
				tipo);
		idBanco.setEnabled(false);

		nombre = getTextField("Nombre", seleccion.getNombre(), tipo);

		direccion = getTextField("Direccion", seleccion.getDireccion(), tipo);
		telefono = getTextField("Telefono", seleccion.getTelefono(),
				tipo);
		
		formulario.setSizeUndefined();
		formulario.addComponent(idBanco);
		formulario.addComponent(nombre);
		formulario.addComponent(direccion);
		formulario.addComponent(telefono);
		
		if (!enabled) {
			formulario.addComponent(getTextField("Usuario Creacion", seleccion.getUsuarioCreacion(),tipo));
			formulario.addComponent(getTextField("Fecha de Creación", seleccion.getFechaCreacion(),tipo));
		}	
		if (enabled) {
			
			aceptar.setStyleName("button");
			aceptar.addClickListener(this);
			formulario.addComponent(aceptar);
		}

		formulario.addComponent(getEtiqueta(""));

		layout.addComponent(formulario);

	}

	

	@Override
	public void valueChange(ValueChangeEvent event) {

	}

	@Override
	public void buttonClick(ClickEvent event) {
		ChkBanco item = new ChkBanco();
		item.setIdBanco(seleccion.getIdBanco());
		
		ProjectDao<ChkBanco> dao = new ProjectDao<ChkBanco>(item);
		try {
			dao.findElementById(item.getIdBanco());
			dao.getEntity().setNombre(nombre.getValue());
			dao.getEntity().setDireccion(direccion.getValue());
			dao.getEntity().setTelefono(telefono.getValue());			
			dao.merge();
			info("Exito", "El registro fue editado Correctamente",
					Page.getCurrent(), OK);
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeEntityManager();
		}

	}

}
