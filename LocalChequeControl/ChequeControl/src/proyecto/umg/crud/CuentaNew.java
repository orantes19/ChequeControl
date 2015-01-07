package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ChkBanco;
import model.ChkCuenta;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CuentaNew extends ViewBase implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;

	
	private ComboBox banco;
	TextField numeroCuenta = getTextField("Numero de Cuenta", EMPTY,true);
	private ComboBox monedas;

	
	Button aceptar = new Button("Guardar",getResource(BASE_R32+"guardar.png"));
	
	
	private Ventana papa;
	private ViewBase base;
	
	
	public CuentaNew(){
		construct();
		
	}
	public CuentaNew(Ventana papa, ViewBase base){
		this.base = base;
		this.papa = papa;
		construct();
		
	}
	
	
	private void construct(){
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
		layout.addComponent(getEtiqueta("Nueva Cuenta"));
		obtenerBancos();
		construirFormulario();
	}
	private void obtenerBancos(){
		ProjectDao<ChkBanco> dao = new ProjectDao<ChkBanco>(new ChkBanco());
		BeanItemContainer<ChkBanco> containerBanco = new BeanItemContainer<ChkBanco>(ChkBanco.class);
		BeanItemContainer<String> contMoneda = new BeanItemContainer<String>(String.class);
		
		
		
		try {
			containerBanco.addAll(dao.findAll("ChkBanco.findAll"));
			List<String> moneditas = new ArrayList<String>();
			moneditas.add("Q");
			moneditas.add("€");
			moneditas.add("$");
			contMoneda.addAll(moneditas);
			monedas = new ComboBox("Moneda...",contMoneda);
			banco = new ComboBox("Seleccione Banco...", containerBanco);
			banco.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_PROPERTY);
			banco.setItemCaptionPropertyId("nombre");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
	}
	@SuppressWarnings("deprecation")
	private void construirFormulario() {
		FormLayout formulario = new FormLayout();
		formulario.addComponent(monedas);
		formulario.addComponent(banco);
		formulario.addComponent(numeroCuenta);
		setStyleButtons(aceptar);
		aceptar.addClickListener(this);
		formulario.addComponent(aceptar);
		
		layout.addComponent(formulario);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(
				new ChkCuenta());
		
		if (numeroCuenta.getValue().trim().equals(EMPTY)){
			error("Campo vacío", "Debe ingresar el número de cuenta", Page.getCurrent(), ERROR);
			return;
		}
		if (monedas.getValue() == null){
			error("Campo vacío", "Debe ingresar el tipo de Moneda", Page.getCurrent(), ERROR);
			return;
		}
		try {
			ChkCuenta cuentaExiste = dao.findElementById(numeroCuenta.getValue());
			if (cuentaExiste != null){
				error("Cuenta Existe", "El número de cuenta ya existe", Page.getCurrent(), ERROR);
				return;
			}
			ChkCuenta item = new ChkCuenta();		
			item.setChkBanco((ChkBanco) banco.getValue());
			item.setNumeroCuenta(numeroCuenta.getValue());
			item.setFechaApertura(new Date());
			item.setSaldoActual(BigDecimal.ZERO);
			item.setEstado(BigDecimal.ONE);
			item.setMoneda((String) monedas.getValue());
			
			dao.setEntity(item);			
			dao.save();
			info("Exito", "Los Datos fueron Guardados Correctamente",
					Page.getCurrent(), OK);
			ejecutarMetodo(base, LISTA_ITEMS_METHOD);
			
			if (papa != null){
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
