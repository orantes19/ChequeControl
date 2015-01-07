package proyecto.umg.componentes;

import com.vaadin.ui.Window;

import java.util.Hashtable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseListener;

public class Ventana extends Window implements CloseListener{

	private static final long serialVersionUID = 7335110857371345143L;

	private Hashtable<String, Ventana> ventanasAbiertas;
	private VerticalLayout vl;

	public Ventana(String titulo, float ancho, float alto, Component contenido) {
		super();
		VerticalLayout vl = new VerticalLayout();
		vl.setHeightUndefined();
		vl.setWidth(100, Unit.PERCENTAGE);
		vl.addComponent(contenido);
		vl.setComponentAlignment(contenido, Alignment.MIDDLE_CENTER);
		this.setCaption(titulo);
		this.setClosable(true);
		this.setDraggable(true);
		this.setWidth(ancho, Unit.PERCENTAGE);
		this.setHeight(alto, Unit.PERCENTAGE);
		this.setContent(vl);
		this.setStyleName("foo");
		this.center();
		this.addCloseListener(this);
		//this.ventanasAbiertas = ventanasAbiertas;
		//this.ventanasAbiertas.put(titulo, this);
	}
	public void setContenido(CustomComponent comp){
		vl.addComponent(comp);
		vl.setComponentAlignment(comp, Alignment.MIDDLE_CENTER);		
	}
	
	public Ventana(String titulo, float ancho, float alto) {
		super();
		vl = new VerticalLayout();
		vl.setHeightUndefined();
		vl.setWidth(100, Unit.PERCENTAGE);
		
		this.setCaption(titulo);
		this.setClosable(true);
		this.setDraggable(true);
		this.setWidth(ancho, Unit.PERCENTAGE);
		this.setHeight(alto, Unit.PERCENTAGE);
		this.setContent(vl);
		this.setStyleName("foo");
		this.center();
		this.addCloseListener(this);
		//this.ventanasAbiertas = ventanasAbiertas;
		//this.ventanasAbiertas.put(titulo, this);
	}

	@Override
	public void windowClose(CloseEvent e) {
		
	}
}
