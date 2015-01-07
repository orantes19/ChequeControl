package proyecto.umg.transaction;

import java.math.BigDecimal;
import java.util.Date;

import model.ChkUsuarioMontosConf;
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

public class ConfigurarMontosEdit extends ViewBase implements ClickListener {

	/**
	* 
	*/
		private static final long serialVersionUID = 1L;
		private VerticalLayout layout;
		TextField username;
		TextField montoMaximo;
		TextField montoMinimo;
		TextField fechaEdicion;
		TextField usuarioEdicion;
		
		
		Button aceptar = new Button("Guardar");

		private Ventana papa;
		private ViewBase base;
		private String tipo;
		private ChkUsuarioMontosConf seleccion;
		

		public ConfigurarMontosEdit(String tipo, ViewBase base,ChkUsuarioMontosConf seleccion) {
			this.base = base;
			this.tipo = tipo;
			this.seleccion = seleccion;
			setStyleName("contenido-ventana");
			layout = new VerticalLayout();
			layout.setMargin(true);
			setCompositionRoot(layout);
			layout.addComponent(getEtiqueta(tipo.equals(VIEW) ? "Configuración Montos": "Configuración Montos"));
			construirFormulario();
		}
		
		
		
		private void construirFormulario() {
			FormLayout formulario = new FormLayout();
			
			boolean enabled = !tipo.equals(VIEW);
			username = getTextField("Usuario", seleccion.getChkUsuario().getUsername(), false);
			
			montoMinimo = getTextField("Monto Minimo", seleccion.getMontoMinimo().toString(), tipo);
			montoMaximo = getTextField("Monto Maximo", seleccion.getMontoMaximo().toString(), tipo);
			montoMaximo.setRequired(true);
			montoMinimo.setRequired(true);
			fechaEdicion = getTextField("Fecha Ultima Edicion",formatearFecha(seleccion.getFechaUltimaEdicion()),false);
			usuarioEdicion = getTextField("Usuario Ultima Edicion", seleccion.getChkUsuario().getUsername(), false);
			addComponentsForm(formulario, username, montoMinimo,montoMaximo,fechaEdicion,usuarioEdicion);
			formulario.addComponent(aceptar);
			setStyleButtons(aceptar);
			aceptar.addClickListener(this);
			
			formulario.setSizeUndefined();
			layout.addComponent(formulario);

		}

		@Override
		public void buttonClick(ClickEvent event) {
			ProjectDao<ChkUsuarioMontosConf> dao = new ProjectDao<ChkUsuarioMontosConf>(new ChkUsuarioMontosConf());
			if (montoMinimo.getValue().trim().equals(EMPTY)){
				error("Error","Debe ingresar un Monto Minimo",Page.getCurrent(),ERROR);
				return;
			}
			if (montoMaximo.getValue().trim().equals(EMPTY)){
				error("Error","Debe ingresar un Monto Minimo",Page.getCurrent(),ERROR);
				return;
			}
			try {

				ChkUsuarioMontosConf item = new ChkUsuarioMontosConf();
				if (seleccion.getIdConf() != null){
					dao.findElementById(seleccion.getIdConf());
				}
				dao.getEntity().setMontoMaximo(new BigDecimal(montoMaximo.getValue()));
				dao.getEntity().setMontoMinimo(new BigDecimal(montoMinimo.getValue()));
				dao.getEntity().setChkUsuario(seleccion.getChkUsuario());
				dao.getEntity().setFechaUltimaEdicion(new Date());
				dao.getEntity().setUsuarioUltimaEdicion(getLoggerUser());
				if (seleccion.getIdConf()!= null){
					dao.merge();
				}else{
					dao.save();
				}
				
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
						Page.getCurrent(), ERROR);

			}

		}

}
