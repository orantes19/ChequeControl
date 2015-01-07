package proyecto.umg.base;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.ChkRolesPorUsuario;
import model.ChkUsuario;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
/**
 * Clase principal para la generacion de las vistas, esta es la pantalla principal
 * @author Ronald Orantes
 *
 */
public class ViewBase extends CustomComponent {
	
	
	
	
	/**
	 * 
	 */
	
	public static final int TIEMPO_DELAY_NOTIFICACIONES = 1000;
	private static final long serialVersionUID = 1L;
	
	protected static final String OK = "ok.png";
	
	protected static final String ERROR = "stop2.png";
	
	protected static final String VOS_TU_MADRE = "vostumadre.png";
	
	protected static final String ADVERTENCIA = "advertencia.jpg";
	
	protected static final String VIEW = "VIEW";
	
	protected static final String EDIT = "EDIT";
	
	protected static final String EMPTY = "";
	
	protected static final String LISTA_ITEMS_METHOD = "buildMainLayout";
	
	protected static final String IMG_BUSCAR = "images/32px/busqueda.png";
	protected static final String IMG_CHEQUE = "images/32px/cheque.png";
	
	protected static final String BASE_R32 = "images/32px/";
	
	protected static final String BASE_R20 = "images/20px/";
	/**
	 * Muestra notificacion de error en pantalla
	 * @param titulo titulo de lerror
	 * @param mensajeHTML descripcion del error
	 * @param padre Pagina padre
	 * @return
	 */
	public void error(String titulo, String mensajeHTML, Page padre,String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.ERROR_MESSAGE,true);
		notificacion.setDelayMsec(TIEMPO_DELAY_NOTIFICACIONES);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		notificacion.setStyleName("notificacion-error");
		notificacion.show(Page.getCurrent());
	}
	protected String formatearFecha(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	public void cerrarSesion(Page pagina){ 
		  info("Cerrar Sesion","Sesion finalizada",pagina,OK);
		  getSession().close();
		  Page.getCurrent().setLocation("/ChequeControl");
		 }
	
	protected TextField getTextField(String caption,String text){
		TextField tf = new TextField(caption, text);
		tf.setStyleName("textolabel");
		return tf;
	}
	protected TextField getTextField(String caption,String text, boolean nuevo){
		TextField tf = new TextField(caption, text);
		if (nuevo){
			tf.setInputPrompt(caption);	
		}
		tf.setStyleName("textolabel");
		tf.setEnabled(nuevo);
		return tf;
	}
	protected TextField getTextField(String caption,String text, String tipo){
		
		boolean enabled = tipo.equals("EDIT");
		TextField tf = new TextField(caption, text);
		tf.setInputPrompt(text);
		tf.setStyleName("textolabel");
		tf.setEnabled(enabled);
		return tf;
	}
	
	protected void setRequiredElements(Component ... comps){
		for (Component c: comps){
			if (c instanceof AbstractTextField){
				((AbstractTextField)c).setRequired(true);
			}
			if (c instanceof AbstractSelect){
				((AbstractSelect)c).setRequired(true);
			}
			
			
			
		}
	}
	
	protected void setStyleButtons(Button ... botones){
		for (Button b: botones){
			b.setStyleName("button");
		}
	}
	
	protected void addComponentsForm(FormLayout form,Component ... comps ){
		for (Component c: comps){
			if (c != null){
				form.addComponent(c);
			}
				
		}
	}
	protected String getString (Object valor){
		return valor == null ? "" : valor.toString();
	}
	protected TextField getTextField(String text){
		TextField tf = new TextField();
		tf.setInputPrompt(text);
		return tf;
	}
	protected Label getEtiqueta(String text){
		Label eti = new Label(text);
		eti.setStyleName("textolabel");
		return eti;
	}
	
	/**
	 * Muestra una notificacion de advertencia en la pantalla
	 * @param titulo titulo de la advertencia
	 * @param mensajeHTML mensaje de la advertencia
	 * @param padre Pagina padre
	 */
	public void advertencia(String titulo, String mensajeHTML, Page padre,String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.WARNING_MESSAGE,true);
		notificacion.setDelayMsec(TIEMPO_DELAY_NOTIFICACIONES);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		//notificacion.setStyleName("notificacion-advertencia");
		notificacion.show(Page.getCurrent());
	}
	
	protected void ejecutarMetodo(ViewBase base, String method) {

		Method[] metodos = base.getClass().getMethods();
		for (Method metodo : metodos) {
			if (metodo.getName().equalsIgnoreCase(method)) {
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
	/**
	 * Muestra una notificacion de exito en la pantalla
	 * @param titulo titulo de la notificacion de exito
	 * @param mensajeHTML mensaje de la notificacion
	 * @param padre Pagina padre
	 */
	public void exito(String titulo, String mensajeHTML, Page padre,String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.HUMANIZED_MESSAGE,true);
		notificacion.setDelayMsec(TIEMPO_DELAY_NOTIFICACIONES);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		//notificacion.setStyleName("notificacion-exito");
		notificacion.show(Page.getCurrent());
	}
	
	/**
	 * Muestra una notificacion informativa en pantalla
	 * @param titulo titulo de la notificacion
	 * @param mensajeHTML mensaje de la notificacion
	 * @param padre Pagina padre
	 */
	public void info(String titulo, String mensajeHTML, Page padre, String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.TRAY_NOTIFICATION,true);
		notificacion.setDelayMsec(TIEMPO_DELAY_NOTIFICACIONES);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		notificacion.setStyleName("notificacion-info");
		notificacion.show(Page.getCurrent());
	}
	
	public ThemeResource getResource(String img){
		return new ThemeResource(img);
	}
	
	/**
	 * Obtiene un objeto de sesion
	 * @param id identificador del objeto
	 * @return
	 */
	public Object obtieneVariableSesion(String id){
		Object res = null;
		try{
			VaadinSession.getCurrent().getLockInstance().lock();
			res = VaadinSession.getCurrent().getAttribute(id);
		}finally{
			VaadinSession.getCurrent().getLockInstance().unlock();
		}
		return res;
	}
	
	protected String getLoggerUser(){
		ChkUsuario us = (ChkUsuario) obtieneVariableSesion("USUARIO");
		return us.getUsername();
	}
	
	/**
	 * Coloca un objeto en sesion
	 * @param id id del objeto a colocar
	 * @param objeto objeto a colocar
	 */
	public void colocaObjetoSesion(String id, Object objeto){
		try{
			VaadinSession.getCurrent().getLockInstance().lock();
		    VaadinSession.getCurrent().setAttribute(id, objeto);
		}finally{
			VaadinSession.getCurrent().getLockInstance().unlock();
		}
	}
	
	/**
	 * Navega a una vista por medio del ID de la vista
	 * @param paginaId
	 */
	public void navegar(String paginaId){
		Navigator nav = (Navigator)obtieneVariableSesion("NAVEGADOR");
		nav.navigateTo(paginaId);
	}
	
	/**
	 * VERIFICA SI EL USUARIO ESTA EN SESION
	 */
	public boolean verificaSesion(){
		if (obtieneVariableSesion("USUARIO")==null){
			info("Inicio de Sesion","Favor iniciar sesion",Page.getCurrent(),VOS_TU_MADRE);
			navegar("");
			return false;
		}
		return true;
	}
	
	protected boolean validaRol(String rol){
		ChkUsuario user = (ChkUsuario) obtieneVariableSesion("USUARIO");
		
		if (user != null){
			for (ChkRolesPorUsuario rolact: user.getChkRolesPorUsuarios()){
				String r = rolact.getChkRol().getRol();
				
				if (r.equalsIgnoreCase(rol)){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	protected Throwable getLastThrowable(Exception e) {
		Throwable t = null;
		
			for(t = e.getCause(); t.getCause() != null; t = t.getCause());

		return t;
	} 
}
