package proyecto.umg.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

import proyecto.umg.base.ViewBase;
/**
 * Pantalla con lista de usuarios
 * @author Ronald Orantes
 *
 */
public class UsuarioList extends ViewBase implements View, ClickListener{
	private Label etiquet;
	private HorizontalLayout principal;
	
	public UsuarioList(){
		
		etiquet = new Label("Hola mundo");
		principal = new HorizontalLayout();
		setSizeFull();
		buildMainLayout();
		setCompositionRoot(principal);
		setStyleName("content-style");
		etiquet.setStyleName("textolabel");
		principal.addComponent(etiquet);
	}

	private void buildMainLayout() {
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if(verificaSesion()){
			etiquet.setStyleName("textolabel");
			
		}
		
	}
	
	
	
	
}
