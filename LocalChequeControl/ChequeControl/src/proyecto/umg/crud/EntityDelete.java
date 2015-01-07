package proyecto.umg.crud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.ChkUsuario;
import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EntityDelete<T> extends Window implements ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Label eti;
	private VerticalLayout layout;
	private ViewBase base;
	private String nombreEntidad;
	private String nameId;
	
	private ProjectDao<T> dao;	
	private Button botonAceptar;
	private Button botonCancelar;
	private Button botonEliminar;
	
	
	
	public EntityDelete(ViewBase base, String nombreEntidad, String nameId, ProjectDao<T> dao, Button bot){
		this.base = base;
		this.nameId = nameId;
		this.nombreEntidad = nombreEntidad;
		this.dao = dao;
		
		botonEliminar = bot;
		eti = new Label("Esta Seguro que desea borrar el registro "+nombreEntidad+"?");
		layout = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		botonAceptar = new Button("Eliminar");
		botonAceptar.addClickListener(this);
		botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(this);
		hl.addComponent(botonAceptar);
		hl.addComponent(botonCancelar);
		layout.addComponent(eti);
		layout.addComponent(hl);
		center();		
		setContent(layout);
		
	}
	
	private void eliminar() {
		try {
			if (dao.getEntity() instanceof ChkUsuario){
				System.out.println("Se eliminara "+((ChkUsuario)dao.getEntity()).getUsername());
			}
			
			dao.merge();			
			ejecutarMetodo("obtenerListaItems");
			base.info("Información", "El registro fue eliminado correctamente", Page.getCurrent(), "ok.png");			
			this.close();
		} catch (Exception e) {			
			e.printStackTrace();
			base.error("Error", "Ocurrio un error al realizar la transacción", Page.getCurrent(), "stop2.png");
		}
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton().getCaption().equals("Cancelar")){
			this.close();
			return;
		}
		
		eliminar();
		
	}
	
	private void ejecutarMetodo(String methodName){
		
		Method[] metodos = base.getClass().getMethods();
		for(Method metodo: metodos){
			if (metodo.getName().equalsIgnoreCase(methodName)){
				try {
					metodo.invoke(base);					
					return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
		}
		
	}

}
