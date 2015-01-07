package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ChkBanco;
import model.ChkChequera;
import model.ChkCuenta;

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

import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

public class ChequeraEdit extends ViewBase implements ClickListener{

	/**
	* 
	*/
		private static final long serialVersionUID = 1L;
		private VerticalLayout layout;
		TextField numeroSerie;
		private ComboBox cuentas;
		private ComboBox cantidadCheques;
		Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));

		private Ventana papa;
		private ViewBase base;
		private String tipo;
		private ChkChequera seleccion;
		

		public ChequeraEdit(String tipo, ViewBase base,ChkChequera seleccion) {
			this.base = base;
			this.tipo = tipo;
			this.seleccion = seleccion;
			setStyleName("contenido-ventana");
			layout = new VerticalLayout();
			layout.setMargin(true);
			setCompositionRoot(layout);
			layout.addComponent(getEtiqueta(tipo.equals(VIEW) ? "Nueva Chequera": "Ver Chequera"));
			obtenerCuentas();
			construirFormulario();
		}
		
		
		private void obtenerCuentas(){
			ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(new ChkCuenta());
			BeanItemContainer<ChkCuenta> containerCuentas = new BeanItemContainer<ChkCuenta>(ChkCuenta.class);	
			try {
				containerCuentas.addAll(dao.findElements("select c from ChkCuenta c where c.estado = ?1", new Object[]{BigDecimal.ONE}));
				cuentas = new ComboBox("Cuenta Bancaria", containerCuentas);
				cuentas.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_PROPERTY);
				cuentas.setItemCaptionPropertyId("valueCombo");			
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				dao.closeEntityManager();
			}
			Iterator<ChkCuenta> it = (Iterator<ChkCuenta>) cuentas.getItemIds().iterator();
			while (it.hasNext()){
				ChkCuenta temp = it.next();
				if (temp.getNumeroCuenta().equals(seleccion.getChkCuenta().getNumeroCuenta())){
					cuentas.setValue(temp);
					break;
				}
			}
		}
		private void construirFormulario() {
			FormLayout formulario = new FormLayout();
			
			boolean enabled = !tipo.equals(VIEW);
			numeroSerie = getTextField("Numero de Serie", EMPTY, tipo);
			cantidadCheques = new ComboBox("Cantidad de Cheques");
			cantidadCheques.addItem("25");
			cantidadCheques.addItem("50");
			cantidadCheques.addItem("100");
			cantidadCheques.setEnabled(enabled);
			cantidadCheques.setValue(seleccion.getCantidadCheques().toString());
			cuentas.setEnabled(enabled);

			
			formulario.setSizeUndefined();
			formulario.addComponent(cuentas);
			formulario.addComponent(cantidadCheques);
			numeroSerie.setValue(seleccion.getNumeroSerie().toString());
			formulario.addComponent(numeroSerie);
			
						
			
			formulario.addComponent(getTextField("Cheques Emitidos", seleccion.getChequesEmitidos().toString(),VIEW));
			formulario.addComponent(getTextField("Estado", seleccion.getEstado().intValue() == 0 ? "INACTIVA" : "ACTIVA",VIEW));
				
				
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
			ProjectDao<ChkChequera> dao = new ProjectDao<ChkChequera>(new ChkChequera());

			try {

				ChkChequera item = new ChkChequera();
				ChkCuenta cuenta = (ChkCuenta) cuentas.getValue();
				dao.findElementById(seleccion.getIdChequera());
				dao.getEntity().setIdChequera(seleccion.getIdChequera());
				dao.getEntity().setChkCuenta(cuenta);
				dao.getEntity().setChkBanco(cuenta.getChkBanco());
				dao.getEntity().setNumeroSerie(numeroSerie.getValue());
				dao.getEntity().setCantidadCheques(new BigDecimal(cantidadCheques.getValue().toString()));
				dao.getEntity().setEstado(seleccion.getEstado());
				dao.getEntity().setChequesEmitidos(seleccion.getChequesEmitidos());				
				dao.merge();
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
