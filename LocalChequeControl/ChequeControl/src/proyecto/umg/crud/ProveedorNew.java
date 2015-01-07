package proyecto.umg.crud;

import java.math.BigDecimal;

import model.ChkChequera;
import model.ChkCuenta;
import model.ChkProveedor;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.utils.Utils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ProveedorNew extends ViewBase implements ClickListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;
	TextField nombre;
	TextField direccion;
	TextField telefono;
	TextField nit;
	TextField email;
	TextField nombreCheques;
	
	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));

	private Ventana papa;
	private ViewBase base;

	public ProveedorNew() {
		construct();

	}
	
	
	
	public ProveedorNew(Ventana papa, ViewBase base) {
		this.base = base;
		this.papa = papa;
		construct();

	}

	private void construct() {
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		
		layout.addComponent(getEtiqueta("Nuevo Proveedor"));
		construirFormulario();
	}

	@SuppressWarnings("deprecation")
	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		nombre = getTextField("Nombre Comercial", EMPTY, EDIT);			
		direccion = getTextField("Direccion",EMPTY,EDIT);
		telefono = getTextField("Numero de Telefono", EMPTY, EDIT);
		nit = getTextField("NIT", EMPTY, EDIT);
		email = getTextField("Correo Electronico",EMPTY,EDIT);
		nombreCheques= getTextField("Nombre para Emitir Cheques",EMPTY,EDIT);
		
		addComponentsForm(formulario,nombre,direccion,telefono,nit,email,nombreCheques);	
		
		aceptar.addClickListener(this);
		setStyleButtons(aceptar);
		formulario.addComponent(aceptar);
		layout.addComponent(formulario);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectDao<ChkProveedor> dao = new ProjectDao<ChkProveedor>(new ChkProveedor());
		if (!email.getValue().trim().matches(Utils.REGEXP_MAIL)){
			advertencia("Error", "La dirección de Correo Electronico Ingresada no es valida",
					Page.getCurrent(), ERROR);
			return;
		}
		try {

			ChkProveedor item = new ChkProveedor();
			item.setNombreComercial(nombre.getValue());
			item.setDireccion(direccion.getValue());
			item.setTelefono(telefono.getValue());
			item.setNit(nit.getValue());
			item.setCorreoElectronico(email.getValue());	
			item.setNombreParaCheques(nombreCheques.getValue());
			dao.setEntity(item);
			dao.save();
			
			info("Exito", "Los Datos fueron Guardados Correctamente",
					Page.getCurrent(), OK);
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);

			if (papa != null) {
				papa.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			error("Error",
					"Ocurrio un error al guardar el registro " + e.getMessage(),
					Page.getCurrent(),ERROR);

		}

	}

}
