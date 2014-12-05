package com.example.chequecontrol;

import javax.servlet.annotation.WebServlet;

import model.ChkUsuario;

import org.eclipse.persistence.expressions.spatial.SpatialParameters.Units;

import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.login.Login;
import proyecto.umg.utils.Utils;
import proyecto.umg.vistas.Menu;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
/**
 * contenedor principal para la aplicación
 * @author Ronald Orantes
 *
 */
@SuppressWarnings("serial")
@Theme("chequecontrol")
public class ChequecontrolUI extends UI {
	private Navigator navegador;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ChequecontrolUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		navegador = new Navigator(this, this);
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		
//		setContent(layout);
		
//		Login login = new Login();
//		
//		layout.addComponent(login);
//		layout.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
		
		
		
		navegador.addView("", new Login());
		navegador.addView("MENU", new Menu());
		
		
		
		
		try{
			VaadinSession.getCurrent().getLockInstance().lock();
		    VaadinSession.getCurrent().setAttribute("NAVEGADOR", navegador);
		}finally{
			VaadinSession.getCurrent().getLockInstance().unlock();
		}
		
		
		
//		final BeanItemContainer<Planet> container =
//			    new BeanItemContainer<Planet>(Planet.class);
//
//			// Put some example data in it
//			container.addItem(new Planet(1, "Mercury"));
//			container.addItem(new Planet(2, "Venus"));
//			container.addItem(new Planet(3, "Earth"));
//			container.addItem(new Planet(4, "Mars"));
//
//			final ComboBox select =
//			    new ComboBox("Select or Add a Planet", container);
//			select.setNullSelectionAllowed(false);
//			        
//			// Use the name property for item captions
//			select.setItemCaptionPropertyId("nombre");
		
		
		

		// Button button = new Button("Click Me");
		// button.addClickListener(new Button.ClickListener() {
		// public void buttonClick(ClickEvent event) {
		// layout.addComponent(new Label("Thank you for clicking"));
		// }
		// });
		// layout.addComponent(button);
	}

	

	

	
		
	
	
	

}