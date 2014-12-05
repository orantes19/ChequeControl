package proyecto.umg.base;

import model.ChkRol;
import model.ChkRolesPorUsuario;
import model.ChkUsuario;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
/**
 * Clase principal para la generacion de las vistas, esta es la pantalla principal
 * @author Ronald Orantes
 *
 */
public class ViewBase extends CustomComponent {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Muestra notificacion de error en pantalla
	 * @param titulo titulo de lerror
	 * @param mensajeHTML descripcion del error
	 * @param padre Pagina padre
	 * @return
	 */
	protected void error(String titulo, String mensajeHTML, Page padre,String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.ERROR_MESSAGE,true);
		notificacion.setDelayMsec(1500);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		notificacion.setStyleName("notificacion-error");
		notificacion.show(padre);
	}
	
	/**
	 * Muestra una notificacion de advertencia en la pantalla
	 * @param titulo titulo de la advertencia
	 * @param mensajeHTML mensaje de la advertencia
	 * @param padre Pagina padre
	 */
	protected void advertencia(String titulo, String mensajeHTML, Page padre,String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.WARNING_MESSAGE,true);
		notificacion.setDelayMsec(1500);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		//notificacion.setStyleName("notificacion-advertencia");
		notificacion.show(padre);
	}
	
	/**
	 * Muestra una notificacion de exito en la pantalla
	 * @param titulo titulo de la notificacion de exito
	 * @param mensajeHTML mensaje de la notificacion
	 * @param padre Pagina padre
	 */
	public void exito(String titulo, String mensajeHTML, Page padre,String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.HUMANIZED_MESSAGE,true);
		notificacion.setDelayMsec(1500);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		//notificacion.setStyleName("notificacion-exito");
		notificacion.show(padre);
	}
	
	/**
	 * Muestra una notificacion informativa en pantalla
	 * @param titulo titulo de la notificacion
	 * @param mensajeHTML mensaje de la notificacion
	 * @param padre Pagina padre
	 */
	public void info(String titulo, String mensajeHTML, Page padre, String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.TRAY_NOTIFICATION,true);
		notificacion.setDelayMsec(1500);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		notificacion.setStyleName("notificacion-info");
		notificacion.show(padre);
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
			info("Inicio de Sesion","Favor iniciar sesion",Page.getCurrent(),"vostumadre.jpg");
			navegar("");
			return false;
		}
		return true;
	}
	
	protected boolean validaRol(String rol){
		ChkUsuario user = (ChkUsuario) obtieneVariableSesion("USUARIO");
		System.out.println("usuario obtenido  "+user);
		if (user != null){
			for (ChkRolesPorUsuario rolact: user.getChkRolesPorUsuarios()){
				String r = rolact.getChkRol().getRol();
				System.out.println("rol obtenido  "+r);
				if (r.equalsIgnoreCase(rol)){
					return true;
				}
			}
		}
		
		return false;
		
	}
}
