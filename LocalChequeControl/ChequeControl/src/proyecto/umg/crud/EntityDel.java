package proyecto.umg.crud;

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

public class EntityDel<T> extends ViewBase implements ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Label eti;
	private VerticalLayout layout;
	private ViewBase base;
	private String nombreEntidad;
	private String nameId;
	private Window papa;

	private ProjectDao<T> dao;
	private Button botonAceptar;
	private Button botonCancelar;
	private boolean borradoInactivar = true;
	private Button botonEliminar;

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton().getCaption().equals("Cancelar")) {
			papa.close();
			return;
		}
		if (borradoInactivar){
			actualizar();
		}else{
			eliminar();
		}
		

	}
	
	public void setPapa(Window papa){
		this.papa = papa;
	}

	public EntityDel(ViewBase base,  String nombreEntidad, String nameId,
			ProjectDao<T> dao, Window papa) {
		this.base = base;
		this.papa = papa;
		this.nameId = nameId;
		this.nombreEntidad = nombreEntidad;
		this.dao = dao;		
		eti = new Label("Esta Seguro que desea borrar el registro "
				+ nombreEntidad + "?");
		eti.setStyleName("textolabel");
		layout = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		botonAceptar = new Button("Eliminar");
		botonAceptar.addClickListener(this);
		botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(this);
		setStyleButtons(botonAceptar, botonCancelar, botonEliminar);
		hl.addComponent(botonAceptar);
		hl.addComponent(botonCancelar);
		layout.addComponent(eti);
		layout.addComponent(hl);
		setCompositionRoot(layout);

	}

	private void actualizar() {
		try {
			if (dao.getEntity() instanceof ChkUsuario) {
				System.out.println("Se eliminara "
						+ ((ChkUsuario) dao.getEntity()).getUsername());
			}

			dao.merge();
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);
			info("Información",
			"El registro fue eliminado correctamente", Page.getCurrent(),
			OK);
			papa.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	private void eliminar() {
		try {
			
			dao.borrar();
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);
			info("Información",
			"El registro fue eliminado correctamente", Page.getCurrent(),
			OK);
			papa.close();
		} catch (Exception e) {
			e.printStackTrace();
			// base.error("Error",
			// "Ocurrio un error al realizar la transacción", Page.getCurrent(),
			// "stop.jpg");
		}
	}

	public boolean isBorradoInactivar() {
		return borradoInactivar;
	}

	public void setBorradoInactivar(boolean borradoInactivar) {
		this.borradoInactivar = borradoInactivar;
	}


}
