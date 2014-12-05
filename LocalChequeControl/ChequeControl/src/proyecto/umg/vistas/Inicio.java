package proyecto.umg.vistas;

import proyecto.umg.base.ViewBase;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class Inicio extends ViewBase implements View, ClickListener{

	private static final long serialVersionUID = -8259295936699091243L;
	
	@AutoGenerated
	private VerticalLayout mainLayout;
	private Label lblMensaje = new Label("Pagina Inicial");
	
	public Inicio() {
		setSizeFull();
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}
	
	@AutoGenerated
	private void buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponent(lblMensaje);
		mainLayout.setComponentAlignment(lblMensaje, Alignment.MIDDLE_CENTER);
	}
	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Menu Principal Cheque Control");
		verificaSesion();
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
}
