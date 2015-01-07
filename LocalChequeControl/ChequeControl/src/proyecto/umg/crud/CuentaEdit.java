package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ChkBanco;
import model.ChkChequera;
import model.ChkCuenta;
import model.ChkCuenta;
import model.ChkMovimientosCuenta;
import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CuentaEdit extends ViewBase implements ClickListener {

	/**
	* 
	*/
		private static final long serialVersionUID = 1L;
		private VerticalLayout layout;
		TextField numeroCuenta;
		
		
		private ComboBox banco;

		Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));
		List<Integer> itemsSeleccionados = new ArrayList<Integer>();
		PasswordField txtPass = new PasswordField();
		PasswordField txtPassConfirm = new PasswordField();
			
		private String tipo;
		private ChkCuenta seleccion;
		private ViewBase base;
		private ComboBox monedas;
		

		public CuentaEdit(String tipo, ViewBase base,ChkCuenta seleccion) {
			this.base = base;
			this.tipo = tipo;
			this.seleccion = seleccion;
			setStyleName("contenido-ventana");
			layout = new VerticalLayout();
			layout.setMargin(true);
			setCompositionRoot(layout);
			layout.addComponent(getEtiqueta(tipo+" Cuenta"));
			obtenerBancos();
			construirFormulario();
		}
		private void obtenerBancos(){
			ProjectDao<ChkBanco> dao = new ProjectDao<ChkBanco>(new ChkBanco());
			BeanItemContainer<ChkBanco> containerBanco = new BeanItemContainer<ChkBanco>(ChkBanco.class);
			
			BeanItemContainer<String> contMoneda = new BeanItemContainer<String>(String.class);
					
			try {
				List<String> moneditas = new ArrayList<String>();
				moneditas.add("Q");
				moneditas.add("€");
				moneditas.add("$");
				contMoneda.addAll(moneditas);
				monedas = new ComboBox("Moneda...",contMoneda);
				monedas.setValue(seleccion.getMoneda());
				containerBanco.addAll(dao.findAll("ChkBanco.findAll"));
				banco = new ComboBox("Seleccione Banco...", containerBanco);
				banco.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_PROPERTY);
				banco.setItemCaptionPropertyId("nombre");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				dao.closeEntityManager();
			}
			Iterator<ChkBanco> it = (Iterator<ChkBanco>) banco.getItemIds().iterator();
			while (it.hasNext()){
				ChkBanco temp = it.next();
				if (temp.getNombre().equals(seleccion.getChkBanco().getNombre())){
					banco.setValue(temp);
					break;
				}
			}
			
		}
		private void construirFormulario() {
			FormLayout formulario = new FormLayout();
			
			boolean enabled = !tipo.equals(VIEW);
			numeroCuenta = getTextField("Número de Cuenta", String.valueOf(seleccion.getNumeroCuenta()),
					VIEW);
			banco.setEnabled(false);

			
			formulario.setSizeUndefined();
			formulario.addComponent(numeroCuenta);
			formulario.addComponent(monedas);
			
			formulario.addComponent(banco);
						
			if (!enabled) {
				formulario.addComponent(getTextField("Saldo", seleccion.getSaldoActual().toString(),tipo));
				
			}	
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
			ChkCuenta item = new ChkCuenta();
			if (monedas.getValue() == null){
				error("Campo vacío", "Debe ingresar el tipo de Moneda", Page.getCurrent(), ERROR);
				return;
			}
			
			ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(seleccion);
			List<ChkMovimientosCuenta> movs = seleccion.getChkMovimientosCuentas();
			List<ChkChequera> cheqs = seleccion.getChkChequeras();
//			boolean tiene = true;
//			if (movs != null){
//				if (movs.size() == 0){
//					tiene = false;
//				}else{
//					tiene = true;
//				}
//			}
//			if (tiene){
//				error("No se puede eliminar", "Imposible editar el número de cuenta, ya que tiene movimientos de cuenta asociados", Page.getCurrent(), ERROR);
//				return;
//			}
//			tiene = true;
//			if (cheqs != null){
//				if (cheqs.size() == 0){
//					tiene = false;
//				}else{
//					tiene = true;
//				}
//			}
//			if (tiene){
//				error("No se puede eliminar", "Imposible editar el número de cuenta, ya que tiene chequeras asociadas", Page.getCurrent(), ERROR);
//				return;
//			}
			
			try {
				BigDecimal estado = new BigDecimal(seleccion.getEstado().toString());
				dao.findElementById(seleccion.getNumeroCuenta());
				
				dao.getEntity().setNumeroCuenta(numeroCuenta.getValue());
				dao.getEntity().setChkBanco((ChkBanco) banco.getValue());
				dao.getEntity().setSaldoActual(seleccion.getSaldoActual());
				dao.getEntity().setEstado(estado);
				dao.getEntity().setFechaApertura(seleccion.getFechaApertura());
				dao.getEntity().setMoneda((String) monedas.getValue());
				System.out.println("Moneda seleccionada "+monedas.getValue());
				dao.merge();
				info("Exito", "El registro fue editado Correctamente",
						Page.getCurrent(), OK);
				ejecutarMetodo(base, LISTA_ITEMS_METHOD);
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dao.closeEntityManager();
			}

		}

}
