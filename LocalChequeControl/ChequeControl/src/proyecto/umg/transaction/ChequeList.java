package proyecto.umg.transaction;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.ChkCheque;
import model.ChkUsuario;
import proyecto.umg.admin.MyPdfSource;
import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;
import proyecto.umg.reportes.ConvertirPDF;
import proyecto.umg.reports.GeneraCheque;
import proyecto.umg.utils.Utils;

import com.aspose.cells.Workbook;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ChequeList extends ViewBase implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout principal;
	private HorizontalLayout lyBotones;
	private Table listadoItems;
	private Button btnAdd;
	private Button btnSearch;
	
	private Button imprimirCheque;
	
	
	private List<ChkCheque> listaItems;
	private ChkCheque selected;
	
	private TextField nombreProveedor;
	
	private int tipoUsuario = 0;
	
	
	
	public ChequeList(int tipo){
		String context = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		System.out.println("*********************   " +context);
		tipoUsuario = tipo;
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	public void obtenerListaItems(){
		ProjectDao<ChkCheque> dao = new ProjectDao<ChkCheque>(new ChkCheque());
		listadoItems.removeAllItems();
		if (listaItems!= null){
			listaItems.clear();
		}
		
		try {
			String criterio = nombreProveedor.getValue();
			if (criterio.trim().equals(EMPTY)){
				listaItems = dao.findElements("SELECT c FROM ChkCheque c ",null);
				
			}else{
				listaItems = dao.findElements("SELECT c FROM ChkCheque c where lower(c.chkProveedor.nombreComercial)  like lower(?1)",new Object[]{"%"+criterio+"%"});
				
			}
			System.out.println("hola   "+listaItems);	
						
			
			listadoItems.addContainerProperty("Numero de Cheque", Object.class, "");
			listadoItems.addContainerProperty("Proveedor", Object.class, "");
			listadoItems.addContainerProperty("Monto", Object.class, "");
			listadoItems.addContainerProperty("Estado", Object.class, null);			
			
			
			int fila = 1;
			
			for (ChkCheque u: listaItems){
				System.out.println(u.getChkProveedor().getNombreComercial());
				AutorizarCheque au = new AutorizarCheque(u, (ChkUsuario) obtieneVariableSesion("USUARIO"), (Map<String, String>) obtieneVariableSesion("PERMISOS"));
				int siguiente = au.siguienteStatus();
				String estado = Utils.getStatusText(siguiente);
//				siguiente == 2 ? "PENDIENTE LIBERACION AUDITORIA" :
//					siguiente == 3 ? "PENDIENTE LIBERACION GERENCIA" :
//							siguiente == 4  ? "PENDIENTE LIBERACION CONSEJO" :
//								siguiente == 10 ? "PENDIENTE ENTREGA" :
//										siguiente == 11 ? "PENDIENTE ENTREGA" :
//											siguiente == 12 ? "ENTREGADO" : "";
				listadoItems.addItem(new Object[]{u.getChkChequera().getNumeroSerie()+"  "+u.getNumeroCheque(),
						u.getChkProveedor().getNombreComercial(), u.getChkChequera().getChkCuenta().getMoneda()+". "+u.getMonto().setScale(2, BigDecimal.ROUND_CEILING),
						estado}, fila);
				fila++;
				
				
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
		
	public void buildMainLayout() {
		principal = new VerticalLayout();
		setSizeFull();		
		setCompositionRoot(principal);
		setStyleName("content-style");
		nombreProveedor = getTextField("Nombre del Proveedor", EMPTY);
		listadoItems = new Table("Listado de Cheques");
		obtenerListaItems();
		listadoItems.setSelectable(true);
		listadoItems.setPageLength(5);		
		listadoItems.setSizeFull();
		principal.setWidth(100, Unit.PERCENTAGE);
		
	    //principal.setHeight(90, Unit.PERCENTAGE);
		listadoItems.addValueChangeListener(this);
		HorizontalLayout l = new HorizontalLayout();
		btnSearch = new Button("Buscar", new ThemeResource(IMG_BUSCAR));
		l.addComponent(nombreProveedor);
		l.addComponent(btnSearch);
		l.setComponentAlignment(btnSearch, Alignment.BOTTOM_CENTER);
		addListener(btnSearch);
		principal.addComponent(l);
		principal.addComponent(listadoItems);
		principal.setComponentAlignment(listadoItems, Alignment.MIDDLE_CENTER);
		
		imprimirCheque = new Button ("Imprimir",getResource(BASE_R32+"impresora.png"));
		
		
		addListener(imprimirCheque);
		
		
		btnAdd = new Button("Liberar",getResource(BASE_R32+"cheque.png"));
		addListener(btnAdd);
		btnAdd.setEnabled(true);

		lyBotones = new HorizontalLayout();
		Map<String, String> permisos = (Map<String, String>) obtieneVariableSesion("PERMISOS");
		if (permisos.get("AUDITORIA") != null | permisos.get("GERENCIA") != null | permisos.get("CONSEJO")!= null){
			lyBotones.addComponent(btnAdd);
			btnAdd.setEnabled(false);
		}
		
		if (tipoUsuario == 4){
			lyBotones.addComponent(imprimirCheque);
			
			imprimirCheque.setEnabled(false);
			
		}
		setStyleButtons(btnAdd, btnSearch,imprimirCheque);

		principal.addComponent(lyBotones);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		if (event.getButton().equals(btnAdd)){
			liberarCheque();
		}
		if (event.getButton().equals(btnSearch)){
			obtenerListaItems();
		}
		if (event.getButton().equals(imprimirCheque)){
			entregarCheque();			
		}
		
		
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		selected = null;
		boolean val = false;
		btnAdd.setEnabled(false);
		imprimirCheque.setEnabled(false);
		imprimirCheque.setDisableOnClick(true);
		if (listadoItems.getValue()!= null){
			if (!listadoItems.getValue().toString().equals("")){
				val = true;					
				Integer value = new Integer(listadoItems.getValue().toString());
				selected = listaItems.get(value -1);
				habilitaBoton();
				if (tipoUsuario == 4){
					
					habilitaImpresion();
				}
			}
			
		}
		
		
		
		
	}
	
	private void generarCheque(String archivo) throws Exception {
		// TODO Auto-generated method stub
		
		SimpleDateFormat now = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");

		String archivoSalida = archivo;
		String context = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		String pathArchivoEntrada = context+"/plantillas/" + archivo;
		
		String pathArchivoSalida = context +"/plantillas/";

			archivo = "Cheque_";
			String pdfSalida = archivo + now.format(new Date()) + ".pdf";
			archivoSalida = archivo + now.format(new Date()) + ".xls";

			GeneraCheque rpt = new GeneraCheque();
			rpt.setUsuario(getLoggerUser());
			java.util.Calendar cal = java.util.Calendar.getInstance();
			String  fecha =  cal.get(java.util.Calendar.DAY_OF_MONTH)+"/"+(cal.get(java.util.Calendar.MONTH)+1)+
					"/"+cal.get(java.util.Calendar.YEAR) ;
			rpt.setFecha(fecha);

			// rpt.setUsuario("Reporte de Fecha " +
			// dateFormat.format(fechaGestion.getValue()) );

			String resp = rpt.getGenerarReporte(pathArchivoEntrada, pathArchivoSalida
					+ archivoSalida, selected);
			Workbook wb = new Workbook(pathArchivoSalida+archivoSalida);
			wb.save(pathArchivoSalida+pdfSalida);
			File fi = new File(pathArchivoSalida+archivoSalida);
			fi.delete();
			descargarReporte(pathArchivoSalida, pdfSalida);	

	}
	
	public void descargarReporte(String path, String nombreArchivo){
		// Create the PDF source and pass the data model to it
        StreamSource source =
            new MyPdfSource((String) path + nombreArchivo);
        
        // Create the stream resource and give it a file name
        String filename = path + nombreArchivo;
        StreamResource resource =
                new StreamResource(source, filename);
        
        // These settings are not usually necessary. MIME type
        // is detected automatically from the file name, but
        // setting it explicitly may be necessary if the file
        // suffix is not ".pdf".
        resource.setMIMEType("application/pdf");
        resource.getStream().setParameter(
                "Content-Disposition",
                "attachment; filename="+filename);

        // Extend the print button with an opener
        // for the PDF resource
        BrowserWindowOpener opener =
                new BrowserWindowOpener(resource);
        
        opener.extend(imprimirCheque);
        imprimirCheque.setDisableOnClick(true);
		}
	
	private void entregarCheque(){
		ProjectDao<ChkCheque> daoCheque = new ProjectDao<ChkCheque>(new ChkCheque());
		
		try {
			daoCheque.findElementById(selected.getIdCheque());
			daoCheque.getEntity().setEstado(new BigDecimal(11));
			daoCheque.merge();			
			btnAdd.setEnabled(false);
			obtenerListaItems();
		} catch (Exception e) {				
			e.printStackTrace();
		}finally{
			daoCheque.closeEntityManager();
		}
		info("Cheque Impreso", "Impresión de Cheque Completa", Page.getCurrent(), OK);
		buildMainLayout();
	}
	
	private void liberarCheque(){
		Map<String,String> permisos = (Map<String,String>)obtieneVariableSesion("PERMISOS");
		AutorizarCheque au = new AutorizarCheque(selected, (ChkUsuario)obtieneVariableSesion("USUARIO"), permisos);
		int siguienteEstado = au.siguienteStatus();
		
		if ( permisos.get("AUDITORIA")!= null |  permisos.get("GERENCIA")!= null |  permisos.get("CONSEJO")!= null){
			ProjectDao<ChkCheque> daoCheque = new ProjectDao<ChkCheque>(new ChkCheque());
			
			try {
				daoCheque.findElementById(selected.getIdCheque());
				daoCheque.getEntity().setEstado(new BigDecimal(au.getActualizaStatus()));
				daoCheque.merge();
				String lib = siguienteEstado == 2 ? "Auditoria" : siguienteEstado == 3 ? "Gerencia" : "Consejo";
				info("Exito", "Liberación de "+lib+", fue realizada correctamente ", Page.getCurrent(), OK);
				btnAdd.setEnabled(false);
				obtenerListaItems();
			} catch (Exception e) {				
				e.printStackTrace();
			}finally{
				daoCheque.closeEntityManager();
			}
			
				
			
		}
		
	}
	
	private void habilitaBoton() {
		Map<String,String> permisos = (Map<String,String>)obtieneVariableSesion("PERMISOS");
		AutorizarCheque au = new AutorizarCheque(selected, (ChkUsuario)obtieneVariableSesion("USUARIO"), permisos);
		int siguienteEstado = au.siguienteStatus();
		System.out.println("siguiente estado  "+siguienteEstado+"  permisos  "+permisos);
		
		if (siguienteEstado == 2 && permisos.get("AUDITORIA")!= null){
			btnAdd.setEnabled(true);
			return;
		}
		
		if (siguienteEstado == 3 && permisos.get("GERENCIA")!= null){
			btnAdd.setEnabled(true);
			return;
		}
		
		if (siguienteEstado == 4 && permisos.get("CONSEJO")!= null){
			btnAdd.setEnabled(true);
			return;
		}

		
		
	}
	
	private void habilitaImpresion() {
		Map<String,String> permisos = (Map<String,String>)obtieneVariableSesion("PERMISOS");
		AutorizarCheque au = new AutorizarCheque(selected, (ChkUsuario)obtieneVariableSesion("USUARIO"), permisos);
		int siguienteEstado = au.siguienteStatus();
		System.out.println("siguiente estado  "+siguienteEstado+"  permisos  "+permisos);
		
		if (siguienteEstado == 11){ 
			
			try{
				imprimirCheque.setEnabled(true);
				if (selected.getChkChequera().getChkBanco().getNombre().toLowerCase().contains("america")){
					generarCheque("plantilla_bac.xls");
				}else if (selected.getChkChequera().getChkBanco().getNombre().toLowerCase().contains("industrial")){
					generarCheque("plantilla_industrial.xls");
				}else if (selected.getChkChequera().getChkBanco().getNombre().toLowerCase().contains("g&t")){
					generarCheque("plantilla_gyt.xls");
				}else if (selected.getChkChequera().getChkBanco().getNombre().toLowerCase().contains("rural")){
					generarCheque("plantilla_banrural.xls");
				}else {
					generarCheque("plantilla_industrial.xls");
				}
					
				
			}catch(Exception e){
				e.printStackTrace();
				
			}
						
		}	
	}

}
