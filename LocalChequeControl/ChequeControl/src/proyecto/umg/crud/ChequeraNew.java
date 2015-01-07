package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.Date;

import model.ChkBanco;
import model.ChkChequera;
import model.ChkCuenta;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ChequeraNew extends ViewBase implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;
	TextField numeroSerie = getTextField("Numero de Serie", EMPTY, true);
	private ComboBox cuentas;
	private ComboBox cantidadCheques;
	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));

	private Ventana papa;
	private ViewBase base;

	public ChequeraNew() {
		construct();

	}
	
	
	private void obtenerCuentas(){
		ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(new ChkCuenta());
		BeanItemContainer<ChkCuenta> containerCuentas = new BeanItemContainer<ChkCuenta>(ChkCuenta.class);	
		try {
			containerCuentas.addAll(dao.findElements("select c from ChkCuenta c where c.estado = ?1", new Object[]{BigDecimal.ONE}));
			cuentas = new ComboBox("Cuenta Bancaria", containerCuentas);
			cuentas.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			cuentas.setItemCaptionPropertyId("valueCombo");			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
	}
	public ChequeraNew(Ventana papa, ViewBase base) {
		this.base = base;
		this.papa = papa;
		construct();

	}

	private void construct() {
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		obtenerCuentas();
		layout.addComponent(getEtiqueta("Nueva Chequera"));
		construirFormulario();
	}

	@SuppressWarnings("deprecation")
	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		formulario.addComponent(cuentas);
		cantidadCheques = new ComboBox("Cantidad de Cheques");
		cantidadCheques.addItem("25");
		cantidadCheques.addItem("50");
		cantidadCheques.addItem("100");		
		formulario.addComponent(cantidadCheques);		
		formulario.addComponent(numeroSerie);
		aceptar.addClickListener(this);
		setStyleButtons(aceptar);
		formulario.addComponent(aceptar);

		layout.addComponent(formulario);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectDao<ChkChequera> dao = new ProjectDao<ChkChequera>(new ChkChequera());

		try {

			ChkChequera item = new ChkChequera();
			ChkCuenta cuenta = (ChkCuenta) cuentas.getValue();
			item.setChkCuenta(cuenta);
			item.setChkBanco(cuenta.getChkBanco());
			item.setNumeroSerie(numeroSerie.getValue());
			item.setCantidadCheques(new BigDecimal(cantidadCheques.getValue().toString()));
			item.setEstado(BigDecimal.ONE);
			item.setChequesEmitidos(BigDecimal.ZERO);			
			dao.setEntity(item);
			dao.save();
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
