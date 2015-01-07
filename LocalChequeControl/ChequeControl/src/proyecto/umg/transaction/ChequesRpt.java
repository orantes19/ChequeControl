package proyecto.umg.transaction;

import java.io.File;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.TemporalType;

import model.ChkCheque;
import model.ChkUsuario;
import proyecto.umg.admin.MyPdfSource;
import proyecto.umg.base.ViewBase;
import proyecto.umg.dao.ProjectDao;
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
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ChequesRpt extends ViewBase implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout principal;
	private HorizontalLayout lyBotones;
	private Table listadoItems;
	private Button btnPrint;
	private Button btnBuscar;
	private ComboBox cuentas;
	private Button generarReporte;
	private PopupDateField fechaInicial;
	private PopupDateField fechaFinal;
	private List<ChkCheque> listaItems;
	private ChkCheque selected;
	
	
	
	
	public ChequesRpt(){
		fechaInicial =  new PopupDateField("Hasta");
		fechaInicial.setValue(null);
		fechaInicial.setWidth(100.0f, Unit.PERCENTAGE);
		fechaInicial.setImmediate(true);
		fechaInicial.setTimeZone(TimeZone.getTimeZone("UTC"));
		fechaInicial.setLocale(Locale.getDefault());
		fechaInicial.setResolution(Resolution.MINUTE);
		fechaFinal =  new PopupDateField( "Desde");
		fechaFinal.setValue(null);
		fechaFinal.setWidth(100.0f, Unit.PERCENTAGE);
		fechaFinal.setImmediate(true);
		fechaFinal.setTimeZone(TimeZone.getTimeZone("UTC"));
		fechaFinal.setLocale(Locale.getDefault());
		fechaFinal.setResolution(Resolution.MINUTE);
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	public void obtenerListaItems(){
		if ((fechaInicial.getValue() == null & fechaFinal.getValue()!= null)
				| (fechaInicial.getValue() != null & fechaFinal.getValue()== null)){
			error("Fechas", "Para filtrar por fecha debe ingresar tanto la fecha inicial como la fecha final", Page.getCurrent(), ERROR);
			return;
		}
		ProjectDao<ChkCheque> dao = null;
		try {
			
			if (fechaInicial.getValue() == null && fechaFinal.getValue() == null){
				dao =new ProjectDao<ChkCheque>(new ChkCheque());
				listaItems = dao.findAll("ChkCheque.findAll");
			}else if (fechaInicial.getValue() != null && fechaFinal.getValue() != null){
				dao =new ProjectDao<ChkCheque>(new ChkCheque());
				listaItems = dao.findElements("SELECT c FROM ChkCheque c "
						+ " where c.fechaEmision between ?1 and ?2",
						new Object[]{fechaInicial.getValue(), fechaFinal.getValue()}, TemporalType.DATE);
			}
			listadoItems.addContainerProperty("Numero de Cheque", Object.class, "");
			listadoItems.addContainerProperty("Fecha de Emision", Object.class, "");
			listadoItems.addContainerProperty("Proveedor", Object.class, null);
			listadoItems.addContainerProperty("Monto", Object.class, "");	
			
			int fila = 1;
			if (listaItems == null){
				generarReporte.setEnabled(false);
				return;
			}
			if (listaItems.size() == 0){
				generarReporte.setEnabled(false);
				return;
			}
			
			for (ChkCheque u: listaItems){
				AutorizarCheque au = new AutorizarCheque(u, (ChkUsuario)obtieneVariableSesion("USUARIO"), (Map<String, String>) obtieneVariableSesion("PERMISOS"));
				int sig = au.siguienteStatus();
				String estado = Utils.getStatusText(sig);
				
				listadoItems.addItem(new Object[]{u.getChkChequera().getNumeroSerie()+""+u.getNumeroCheque(),
						formatearFecha(u.getFechaEmision()),	 
									u.getChkProveedor().getNombreComercial(),
										u.getChkChequera().getChkCuenta().getMoneda()+". "+u.getMonto().setScale(2, RoundingMode.CEILING).toString()}, fila);
				fila++;				
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (dao != null){
				dao.closeEntityManager();
			}
		}
		
		
		
		
	}
		
	public void buildMainLayout() {
		principal = new VerticalLayout();
		setSizeFull();		
		setCompositionRoot(principal);
		setStyleName("content-style");
		generarReporte = new Button("Generar reporte ", getResource(BASE_R32+"nuevo.png"));
		btnPrint = new Button("Imprimir",getResource(BASE_R32+"impresora.png"));
		btnBuscar = new Button("Buscar", new ThemeResource(IMG_BUSCAR));
		listadoItems = new Table("Reporte de Cheques Emitidos");
		obtenerListaItems();
		listadoItems.setPageLength(5);		
		listadoItems.setSizeFull();
		principal.setWidth(100, Unit.PERCENTAGE);
		listadoItems.addValueChangeListener(this);
		HorizontalLayout l = new HorizontalLayout();
		l.addComponents(fechaInicial, fechaFinal, btnBuscar);
		principal.addComponents(l,listadoItems);
		principal.setComponentAlignment(listadoItems, Alignment.MIDDLE_CENTER);
		l.setComponentAlignment(btnBuscar, Alignment.BOTTOM_CENTER);
		addListener(btnBuscar);
		lyBotones = new HorizontalLayout();
		generarReporte.addClickListener(this);
		generarReporte.setDisableOnClick(true);
		setStyleButtons(btnBuscar, btnPrint, generarReporte);
		lyBotones.addComponent(generarReporte);
		generarReporte.setEnabled(true);
		lyBotones.addComponent(btnPrint);
		btnPrint.setEnabled(false);
		
		principal.addComponent(lyBotones);
		
	}
	private void generarReporte() throws Exception {
		// TODO Auto-generated method stub
		String archivo = "reporte_cheques.xls";
		SimpleDateFormat now = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");

		String archivoSalida = archivo;
		String context = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		String pathArchivoEntrada = context+"/plantillas/" + archivo;
		
		String pathArchivoSalida = context +"/plantillas/";

			archivo = "ReporteChequesEmitidos_";
			String pdfSalida = archivo + now.format(new Date()) + ".pdf";
			archivoSalida = archivo + now.format(new Date()) + ".xls";

			ReporteCheques rpt = new ReporteCheques();
			rpt.setUsuario(getLoggerUser());
			java.util.Calendar cal = java.util.Calendar.getInstance();
			String  fecha =  cal.get(java.util.Calendar.DAY_OF_MONTH)+"/"+(cal.get(java.util.Calendar.MONTH)+1)+
					"/"+cal.get(java.util.Calendar.YEAR) ;
			rpt.setFecha(fecha);

			// rpt.setUsuario("Reporte de Fecha " +
			// dateFormat.format(fechaGestion.getValue()) );

			String resp = rpt.getGenerarReporte(pathArchivoEntrada, pathArchivoSalida
					+ archivoSalida, listaItems,(ChkUsuario) obtieneVariableSesion("USUARIO"),(Map<String, String>) obtieneVariableSesion("PERMISOS"));
			if (resp.equals("OK")){
				Workbook wb = new Workbook(pathArchivoSalida+archivoSalida);
				wb.save(pathArchivoSalida+pdfSalida);
			}
			
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
        
        opener.extend(btnPrint);
        btnPrint.setEnabled(true);
        btnPrint.setDisableOnClick(true);
		}

	@Override
	public void buttonClick(ClickEvent event) {
		String caption = event.getButton().getCaption();

		if (event.getButton().equals(btnBuscar)){
			System.out.println("buscando....");
			buildMainLayout();
		}
		if (event.getButton().equals(generarReporte)){
			try {
				System.out.println("Generando Reporte");
				generarReporte();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		selected = null;
		boolean val = false;
		if (listadoItems.getValue()!= null){
			if (!listadoItems.getValue().toString().equals("")){
				val = true;
				btnPrint.setEnabled(val);
								
				Integer value = new Integer(listadoItems.getValue().toString());
				selected = listaItems.get(value -1);
				
			}
			
			
		}else{
			btnPrint.setEnabled(val);			
		}
		
		
		
		
	}

}
