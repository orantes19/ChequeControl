package proyecto.umg.transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import model.ChkCheque;
import model.ChkChequera;
import model.ChkCuenta;
import model.ChkProveedor;
import model.ChkUsuario;
import model.ChkUsuarioMontosConf;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.IntegerField;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.utils.Correo;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class GenerarChequeForm extends ViewBase implements ClickListener, ValueChangeListener {
	private VerticalLayout principal;
	Panel panel;
	private HorizontalLayout firstFile;
	private HorizontalLayout secondFile;
	private HorizontalLayout thirdFile;
	private FormLayout layoutForm;
	private Button buscarProveedor;
	private Button generarCheque = new Button("Generar Cheque", new ThemeResource(IMG_CHEQUE));
	private IntegerField idProveedor;
	private TextField nombreProveedor;
	private ComboBox comboChequera;
	private ComboBox comboCuentas;
	private ChkProveedor proveedorSelect;
	private TextField montoCheque;
	
	
	public GenerarChequeForm(){
		buildMainLayout();	
		
	}
	
	
	public void buildMainLayout() {
		principal = new VerticalLayout();
		panel = new Panel();
		panel.setCaption("Generación de Cheques");
		panel.setStyleName("content-style");
		firstFile = new HorizontalLayout();
		firstFile.setSpacing(true);
		secondFile = new HorizontalLayout();
		secondFile.setSpacing(true);
		thirdFile = new HorizontalLayout();
		secondFile.setSpacing(true);
		setSizeFull();		
		setCompositionRoot(principal);
		setStyleName("content-style");
		idProveedor = new IntegerField();
		idProveedor.setCaption("Codigo de Proveedor");		
		buscarProveedor = new Button("Buscar Proveedor");
		montoCheque = getTextField("Monto del Cheque");
		montoCheque.setRequired(true);
		
		buscarProveedor.setIcon(new ThemeResource(IMG_BUSCAR));
		buscarProveedor.addClickListener(this);
		buscarProveedor.setCaption("Buscar Proveedor");
		setStyleButtons(buscarProveedor, generarCheque);
		armaComboCuenta();
		comboCuentas.addValueChangeListener(this);
		generarCheque.addClickListener(this);
		
		comboChequera = new ComboBox("Chequera");
		comboChequera.setEnabled(false);

		//idProveedor.addValueChangeListener(this);
		
		nombreProveedor = getTextField("","");
		nombreProveedor.setEnabled(false);
		firstFile.addComponents(comboCuentas, comboChequera);
		secondFile.addComponents(idProveedor,nombreProveedor,buscarProveedor);
		secondFile.setComponentAlignment(buscarProveedor, Alignment.BOTTOM_CENTER);
		thirdFile.addComponents(montoCheque, generarCheque);
		
		
		
		
		layoutForm = new FormLayout(firstFile,secondFile,thirdFile);
		panel.setContent(layoutForm);		
		principal.setWidth(100, Unit.PERCENTAGE);
	    //principal.setHeight(90, Unit.PERCENTAGE);		
		principal.addComponent(panel);
		
	}
	private void armaComboChequera() {
		ProjectDao<ChkChequera> dao = new ProjectDao<ChkChequera>(new ChkChequera());
		BeanItemContainer<ChkChequera> item = new BeanItemContainer<ChkChequera>(ChkChequera.class);
		List<ChkChequera> listaChequeras = new ArrayList<ChkChequera>();
		comboChequera.removeAllItems();
		
		try {
			
			listaChequeras = dao.findElements("Select c from ChkChequera c where c.estado = ?1 "
					+ "and c.chkCuenta = ?2", new Object[]{BigDecimal.ONE, comboCuentas.getValue()});
			comboChequera.setEnabled(false);
			if (listaChequeras == null){
				return;
			}
			if (listaChequeras.size() == 0){
				return;
			}
			comboChequera.setEnabled(true);			
			item.addAll(listaChequeras);
			comboChequera.setContainerDataSource(item);
			comboChequera.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			comboChequera.setItemCaptionPropertyId("name");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void armaComboCuenta() {
		ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(new ChkCuenta());
		BeanItemContainer<ChkCuenta> item = new BeanItemContainer<ChkCuenta>(ChkCuenta.class);
		List<ChkCuenta> listaCuentas = new ArrayList<ChkCuenta>();
		
		
		try {
			listaCuentas = dao.findElements("Select c from ChkCuenta c where c.estado = ?1", new Object[]{BigDecimal.ONE});
			item.addAll(listaCuentas);
			comboCuentas = new ComboBox("Cuentas Bancarias", item);
			comboCuentas.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			comboCuentas.setItemCaptionPropertyId("valueCombo");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton().equals(buscarProveedor)){
			proveedorSelect = new ChkProveedor();
			realizaBusquedaProveedor();
		}
		if (event.getButton().equals(generarCheque)){
			generarCheque();
		}
		
		
		
	}


	private void generarCheque() {
		if (comboCuentas.getValue() == null){
			error("Error en cuenta","Debe Seleccionar una Cuenta",Page.getCurrent(), ERROR);
			return;
		}
		if (comboChequera.getValue() == null){
			error("Error en chequera","Debe Seleccionar una Chequera",Page.getCurrent(), ERROR);
			return;
		}
		if (montoCheque.getValue().trim().equals(EMPTY)){
			error("Monto Invalido","Debe ingresar un monto "+montoCheque.getValue(),Page.getCurrent(), ERROR);
			return;
		}
		if (!montoCheque.getValue().matches("-?[0-9]*\\.?[0-9]*")){
			error("Monto Invalido","El monto debe ser un número",Page.getCurrent(), ERROR);
			return;
		}
		if (proveedorSelect.getCodProveedor() == 0){
			error("Proveedor no valido","Debe seleccionar un Proveedor",Page.getCurrent(), ERROR);
			return;
		}
		Map<String, String> permisos = (Map<String, String>) obtieneVariableSesion("PERMISOS");
		BigDecimal monto = null;
		try{
			monto = new BigDecimal(montoCheque.getValue());
		}catch(NumberFormatException e){
			
		}
		if (permisos.get("JEFE_PAGOS") == null){
			if (!validaMontos(monto)){
				return;
			}
		}
		
		
		ProjectDao<ChkCheque> dao = new ProjectDao<ChkCheque>(new ChkCheque());
		dao.getEntity().setChkChequera((ChkChequera) comboChequera.getValue());
		dao.getEntity().setChkProveedor(proveedorSelect);
		dao.getEntity().setEstado(BigDecimal.ONE);
		dao.getEntity().setFechaEmision(new Date());
		dao.getEntity().setMonto(monto);
		dao.getEntity().setUsuario(getLoggerUser());
		Long id = null;
		
		try {
			dao.save();
			id = dao.getEntity().getIdCheque();
			
		} catch (Exception e) {
			if (e instanceof RuntimeException){
				RuntimeException ex = (RuntimeException) e;
				Throwable t = getLastThrowable(ex);
				SQLException sqlx = (SQLException) t;
				int code = 0;
				if (sqlx.getMessage().contains("-20701")){
					//monto no permitido
					code = 20701;
				}
				if (sqlx.getMessage().contains("-20702")){
					//tipo de movimiento
					code = 20702;
				}
				if (sqlx.getMessage().contains("-20703")){
					//saldo insuficiente
					code = 20703;
				}
				if (code == 20703){
					error("Error en cheque","La cuenta no dispone de saldo suficiente ",Page.getCurrent(), ERROR);
					return;
				}
				
			}
			error("Error en cheque","Ocurrio un error interno al tratar de generar el cheque ",Page.getCurrent(), ERROR);
			//e.printStackTrace();
		}finally{
			System.out.println("Se cierra entity");
			dao.closeEntityManager();
		}
		
		if (id != null){
			dao = new ProjectDao<ChkCheque>(new ChkCheque());
			try {
				dao.findElementById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			info("Exito", "El cheque fue generado correctamente Numero de Cheque "+dao.getEntity().getChkChequera().getNumeroSerie()+dao.getEntity().getNumeroCheque(), Page.getCurrent(), OK);
			limpiarCampos();
		}
		
		
		
		
		
		
	}
	private boolean validaMontos(BigDecimal monto) {
		ProjectDao<ChkUsuarioMontosConf> daoMontos = null;
		
		try{
			List<ChkUsuarioMontosConf> montos = null;
			ChkUsuario usr = (ChkUsuario) obtieneVariableSesion("USUARIO");
			daoMontos = new ProjectDao<ChkUsuarioMontosConf>(new ChkUsuarioMontosConf());
			try {
				montos = daoMontos.findElements("Select m from ChkUsuarioMontosConf m where m.chkUsuario = ?1", new Object[]{usr});
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			if (montos == null){
				error("Faltan Configuraciones","Su usuario aún no ha sido configurado para generar cheques",Page.getCurrent(), ERROR);
				return false;
			}
			if (montos.size() == 0){
				error("Faltan Configuraciones","Su Aun no ha sido configurado para generar cheques",Page.getCurrent(), ERROR);
				return false;
			}
			
			ChkUsuarioMontosConf configuracionMontos = montos.get(0);
			final ChkUsuarioMontosConf configuracionMont = configuracionMontos;
			
			final BigDecimal mnt = monto;
			
			if (configuracionMontos.getMontoMaximo().doubleValue() < monto.doubleValue()){
				new java.lang.Thread(new Runnable() {
				
				@Override
				public void run() {
					enviarCorreoJefes(configuracionMont.getChkUsuario().getPrimerNombre()
							+" "+configuracionMont.getChkUsuario().getPrimerApellido(), 
							proveedorSelect.getNombreParaCheques() +"(Proveedor:  "+proveedorSelect.getNombreComercial()+"),  "
							,mnt.toString());
					
				}
				}).start();
				System.out.println("sigue el proceso");
				
				
				error("Monto no permitido","Su Usuario no tiene permisos para generar cheques mayores a "+configuracionMontos.getMontoMaximo(),Page.getCurrent(), ERROR);
				return false;
			}
			if (configuracionMontos.getMontoMinimo().doubleValue() > monto.doubleValue()){
				error("Monto minimo","Su Usuario solo puede generar cheques mayores o iguales a "+configuracionMontos.getMontoMinimo(),Page.getCurrent(), ERROR);
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (daoMontos != null){
				daoMontos.closeEntityManager();
			}
		}
		return true;
		
		
	}


	private void enviarCorreoJefes(String usuario, String prov, String monto) {
		ProjectDao<ChkUsuario> usuarios = new ProjectDao<ChkUsuario>(new ChkUsuario());		
		
		
		try {
			List<ChkUsuario> users = usuarios.findElements("Select u from ChkUsuario u join ChkRolesPorUsuario cr on u = cr.chkUsuario"
					+ " where cr.chkRol.rol = ?1", new Object[]{"JEFE_PAGOS"});
			List<String> destinatarios = new ArrayList<String>();
			for (ChkUsuario u: users){
				destinatarios.add(u.getCorreoElectronico());
			}
			if (destinatarios.size() > 0){
				Correo.sendExcelToMail(destinatarios, "Notificación de Generación de cheque Fallida", 
						"Esta es una notificación Automatica, <br><br>"
						+ "El usuario <b>"+usuario+"</b>, ha tratado de generar un cheque a nombre de <b>"+prov+"</b>por un "
								+ "monto (<b>"+monto+"</b>) mayor a lo permitido según su perfil.<br><br>"
										+ "La transacción fue denegada por el sistema.<br><br>"+
								"Administración de Sistemas", null, null);
			}
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void info(String titulo, String mensajeHTML, Page padre, String imagen){
		Notification notificacion = new Notification("<b>"+titulo+"</b>",mensajeHTML,Notification.Type.TRAY_NOTIFICATION,true);
		notificacion.setDelayMsec(3000);
		notificacion.setIcon(new ThemeResource("images/notificaciones/"+imagen));
		notificacion.setStyleName("notificacion-info");
		notificacion.show(Page.getCurrent());
	}

	private void limpiarCampos() {
		comboCuentas.setValue(null);
		comboChequera.setValue(null);
		idProveedor.setValue("");
		montoCheque.setValue("");
		
	}


	private void realizaBusquedaProveedor() {
		if (idProveedor.getValue().equals(EMPTY)){
			Ventana v = new Ventana("Busqueda de Proveedores", 45, 90);
			BuscarProveedor b = new BuscarProveedor(idProveedor, nombreProveedor,proveedorSelect, v);
			v.setContenido(b);
			v.setModal(true);
			UI.getCurrent().addWindow(v);
		}else{
			buscarProveedor();
		}
		
	}


	@Override
	public void valueChange(ValueChangeEvent event) {
		
		if(event.getProperty().equals(comboCuentas)){
			armaComboChequera();
		}

		if(event.getProperty().equals(idProveedor)){			
			
		}
		
		
		
	}


	private void buscarProveedor() {
		synchronized (this) {
			nombreProveedor.setValue("");
			if (!idProveedor.getValue().equals(EMPTY)){
				ProjectDao<ChkProveedor> dao = new ProjectDao<ChkProveedor>(new ChkProveedor());
				try {
					proveedorSelect = dao.findElementById(new Long(idProveedor.getValue()));
					if (proveedorSelect != null){
						nombreProveedor.setValue(proveedorSelect.getNombreComercial());
					}
					
				
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					dao.closeEntityManager();
				}
			}		
		}		
	}

}
