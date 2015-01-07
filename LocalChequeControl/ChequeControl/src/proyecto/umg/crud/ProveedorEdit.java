package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.Iterator;

import org.jboss.netty.handler.codec.frame.CorruptedFrameException;

import model.ChkProveedor;
import model.ChkCuenta;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.utils.Utils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ProveedorEdit extends ViewBase implements ClickListener {

	/**
	* 
	*/
		private static final long serialVersionUID = 1L;
		private VerticalLayout layout;
		TextField nombre;
		TextField direccion;
		TextField telefono;
		TextField nit;
		TextField email;
		TextField nombreCheques;
		TextField codProveedor;
		
		Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));

		private Ventana papa;
		private ViewBase base;
		private String tipo;
		private ChkProveedor seleccion;
		

		public ProveedorEdit(String tipo, ViewBase base,ChkProveedor seleccion) {
			this.base = base;
			this.tipo = tipo;
			this.seleccion = seleccion;
			setStyleName("contenido-ventana");
			layout = new VerticalLayout();
			layout.setMargin(true);
			codProveedor = getTextField("Código de Proveedor", ""+seleccion.getCodProveedor(), false);
			codProveedor.setEnabled(false);
			setCompositionRoot(layout);
			layout.addComponent(getEtiqueta(tipo.equals(VIEW) ? "Nuevo Proveedor": "Ver Proveedor"));
			
			construirFormulario();
		}
		
		
		
		private void construirFormulario() {
			FormLayout formulario = new FormLayout();
			
			boolean enabled = !tipo.equals(VIEW);
			nombre = getTextField("Nombre Comercial", getString(seleccion.getNombreComercial()), tipo);			
			direccion = getTextField("Direccion",getString(seleccion.getDireccion()),tipo);
			telefono = getTextField("Numero de Telefono", getString(seleccion.getTelefono()), tipo);
			nit = getTextField("NIT", getString(seleccion.getNit()), tipo);
			email = getTextField("Correo Electronico",getString(seleccion.getCorreoElectronico()),tipo);
			nombreCheques= getTextField("Nombre para Emitir Cheques",getString(seleccion.getNombreParaCheques()),tipo);			
			addComponentsForm(formulario,codProveedor, nombre,direccion,telefono,nit,email,nombreCheques);			
			formulario.setSizeUndefined();					
				
			if (enabled) {
				aceptar = new Button("Guardar");
				aceptar.setStyleName("button");
				aceptar.addClickListener(this);
				formulario.addComponent(aceptar);
			}

			formulario.addComponent(getEtiqueta(""));

			layout.addComponent(formulario);

		}

		@Override
		public void buttonClick(ClickEvent event) {
			if (nombre.getValue().trim().equals("")){
				error("Error", "Debe ingresar un nombre",Page.getCurrent() , ERROR);
				return; 
			}
			if (nombreCheques.getValue().trim().equals("")){
				error("Error", "Debe ingresar el nombre para emision de cheques, si es el mismo copielo",Page.getCurrent() , ERROR);
				return;
			}
			if (!email.getValue().trim().matches(Utils.REGEXP_MAIL)){
				advertencia("Error", "La dirección de Correo Electronico Ingresada no es valida",
						Page.getCurrent(), ERROR);
				return;
			}
			
			ProjectDao<ChkProveedor> dao = new ProjectDao<ChkProveedor>(new ChkProveedor());
			
			try {

				ChkProveedor item = new ChkProveedor();
				System.out.println("Preparando información para actualizar...");
				item.setCodProveedor(seleccion.getCodProveedor());
				dao.setEntity(item);
				dao.findElementById(item.getCodProveedor());
				dao.getEntity().setNombreComercial(nombre.getValue());
				dao.getEntity().setDireccion(direccion.getValue());
				dao.getEntity().setTelefono(telefono.getValue());
				dao.getEntity().setNit(nit.getValue());
				dao.getEntity().setCorreoElectronico(email.getValue());
				dao.getEntity().setNombreParaCheques(nombreCheques.getValue());
				dao.merge();
				System.out.println("nombre entidad antes  "+item.getNombreParaCheques()+"   despues   "+dao.getEntity().getNombreParaCheques());
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
