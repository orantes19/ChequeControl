package proyecto.umg.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ChkBanco;
import model.ChkCuenta;
import model.ChkMovimientosCuenta;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.IntegerField;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class DepositosCuenta extends ViewBase implements ClickListener, ValueChangeListener {

	private VerticalLayout principal;
	Panel panel;
	private HorizontalLayout firstFile;
	private HorizontalLayout secondFile;
	
	private FormLayout layoutForm;
	
	private Button generarDeposito = new Button("Ejecutar Deposito", new ThemeResource(IMG_CHEQUE));
	private IntegerField idProveedor;
	private TextField nombreProveedor;
	private ComboBox comboBanco;
	private ComboBox comboCuentas;
	private TextField numeroDocumento;
	private TextField montoDeposito;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  DepositosCuenta() {
		buildMainLayout();	
	}
	
	public void buildMainLayout() {
		principal = new VerticalLayout();
		panel = new Panel();
		panel.setCaption("Registro de Depósitos");
		panel.setStyleName("content-style");
		firstFile = new HorizontalLayout();
		firstFile.setSpacing(true);
		secondFile = new HorizontalLayout();
		secondFile.setSpacing(true);
		
		secondFile.setSpacing(true);
		setSizeFull();		
		setCompositionRoot(principal);
		setStyleName("content-style");
		idProveedor = new IntegerField();
		idProveedor.setCaption("Codigo de Proveedor");		
		
		montoDeposito = getTextField("Monto del depósito");
		montoDeposito.setRequired(true);
		numeroDocumento = getTextField("Numero de Boleta");
		numeroDocumento.setRequired(true);
//		buscarProveedor.setIcon(new ThemeResource(IMG_BUSCAR));
//		buscarProveedor.addClickListener(this);
//		buscarProveedor.setCaption("Buscar Proveedor");
		comboBanco = new ComboBox("Banco");
		armaComboBanco();
		comboCuentas = new ComboBox("Cuenta");
		comboCuentas.addValueChangeListener(this);
		generarDeposito.addClickListener(this);		
		setStyleButtons(generarDeposito);
		comboBanco.addValueChangeListener(this);
		comboCuentas.setEnabled(false);
		//idProveedor.addValueChangeListener(this);	
		nombreProveedor = getTextField("","");
		nombreProveedor.setEnabled(false);
		firstFile.addComponents(comboBanco, comboCuentas);
		secondFile.addComponents(numeroDocumento, montoDeposito,generarDeposito);
		//thirdFile.addComponents(montoDeposito, generarDeposito);		
		layoutForm = new FormLayout(firstFile,secondFile);
		panel.setContent(layoutForm);		
		principal.setWidth(100, Unit.PERCENTAGE);
	    //principal.setHeight(90, Unit.PERCENTAGE);		
		principal.addComponent(panel);
		
	}
	
	private void armaComboCuenta() {
		
		ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(new ChkCuenta());
		BeanItemContainer<ChkCuenta> item = new BeanItemContainer<ChkCuenta>(ChkCuenta.class);
		List<ChkCuenta> listaCuentas = new ArrayList<ChkCuenta>();
		
		
		try {
			listaCuentas = dao.findElements("Select c from ChkCuenta c where c.estado = ?1 and c.chkBanco = ?2", new Object[]{BigDecimal.ONE, comboBanco.getValue()});
			comboCuentas.setEnabled(false);
			if (listaCuentas == null){
				return;
			}
			if (listaCuentas.size() == 0){
				return;
			}
			comboCuentas.setEnabled(true);
			
			item.addAll(listaCuentas);
			comboCuentas.setContainerDataSource(item);;
			comboCuentas.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			comboCuentas.setItemCaptionPropertyId("numeroCuenta");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		
	}
	
	private void armaComboBanco() {
		ProjectDao<ChkBanco> dao = new ProjectDao<ChkBanco>(new ChkBanco());
		BeanItemContainer<ChkBanco> item = new BeanItemContainer<ChkBanco>(ChkBanco.class);
		List<ChkBanco> listaBancos = new ArrayList<ChkBanco>();
		comboBanco.removeAllItems();
		
		try {
			
			listaBancos = dao.findElements("Select c from ChkBanco c ", null);
			comboBanco.setEnabled(false);
			if (listaBancos == null){
				return;
			}
			if (listaBancos.size() == 0){
				return;
			}
			comboBanco.setEnabled(true);
			item.addAll(listaBancos);
			comboBanco.setContainerDataSource(item);
			comboBanco.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			comboBanco.setItemCaptionPropertyId("nombre");						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton().equals(generarDeposito)){
			generarCheque();
		}

	}

	private void generarCheque() {
		if (comboCuentas.getValue() == null){
			error("Error en cuenta","Debe Seleccionar una Cuenta",Page.getCurrent(), ERROR);
			return;
		}
		if (comboBanco.getValue() == null){
			error("Error en chequera","Debe Seleccionar una Chequera",Page.getCurrent(), ERROR);
			return;
		}
		if (montoDeposito.getValue().trim().equals(EMPTY)){
			error("Monto Invalido","Debe ingresar un monto ",Page.getCurrent(), ERROR);
			return;
		}
		if (!montoDeposito.getValue().matches("-?[0-9]*\\.?[0-9]*")){
			error("Monto Invalido","El monto debe ser un número",Page.getCurrent(), ERROR);
			return;
		}
		if (numeroDocumento.getValue().trim().equals(EMPTY)){
			error("Numero Documento","Debe ingresar El número de documento del banco",Page.getCurrent(), ERROR);
			return;
		}
		
				
		
		
		ProjectDao<ChkMovimientosCuenta> dao = new ProjectDao<ChkMovimientosCuenta>(new ChkMovimientosCuenta());
		dao.getEntity().setChkCuenta((ChkCuenta) comboCuentas.getValue());
		dao.getEntity().setDocumentoBanco(numeroDocumento.getValue().trim());
		dao.getEntity().setTipoMovimiento(BigDecimal.ONE);
		dao.getEntity().setFechaMovimiento(new Date());
		dao.getEntity().setMontoMovimiento(new BigDecimal(montoDeposito.getValue().trim()));
		dao.getEntity().setUsuario(getLoggerUser());
		
		
		try {
			dao.save();			
			info("Exito", "El Deposito fue registrado en el sistema.", Page.getCurrent(), OK);
			limpiarCampos();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			dao.closeEntityManager();
		}
		
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (event.getProperty().equals(comboBanco)){
			if (comboBanco.getValue() != null){
				armaComboCuenta();
			}
			
		}
		
	}
	
	private void limpiarCampos() {
		comboCuentas.setValue(null);
		comboBanco.setValue(null);
		montoDeposito.setValue("");
		numeroDocumento.setValue("");
		
	}

}
