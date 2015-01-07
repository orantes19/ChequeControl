package proyecto.umg.crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ChkBanco;
import model.ChkUsuario;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BancoNew extends ViewBase implements ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;

	

	TextField nombre = getTextField("Nombre", EMPTY,true);

	TextField direccion = getTextField("Direccion", EMPTY,true);
	TextField telefono = getTextField("Telefono", EMPTY,true);
	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));
	
	
	private Ventana papa;
	private ViewBase base;
	
	
	public BancoNew(){
		construct();
		
	}
	public BancoNew(Ventana papa, ViewBase base){
		this.base = base;
		this.papa = papa;
		construct();
		
	}
	
	
	private void construct(){
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		layout.addComponent(getEtiqueta("Nuevo Banco"));		
		construirFormulario();
	}
	@SuppressWarnings("deprecation")
	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		formulario.addComponent(nombre);
		formulario.addComponent(direccion);
		formulario.addComponent(telefono);		
		aceptar.addClickListener(this);
		setStyleButtons(aceptar);
		formulario.addComponent(aceptar);
		
		layout.addComponent(formulario);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectDao<ChkBanco> dao = new ProjectDao<ChkBanco>(
				new ChkBanco());
		
		try {
			
			ChkBanco item = new ChkBanco();		
			item.setNombre(nombre.getValue());
			item.setDireccion(direccion.getValue());
			item.setTelefono(telefono.getValue());
			item.setUsuarioCreacion(getLoggerUser());
			item.setFechaCreacion(formatearFecha(new Date()));
			dao.setEntity(item);			
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
					"Ocurrio un error al guardar el registro " + e.getMessage(),
					Page.getCurrent(), ERROR);

		}

	}

	

}
