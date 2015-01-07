package proyecto.umg.transaction;

import java.util.ArrayList;
import java.util.List;

import model.ChkProveedor;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.sun.org.apache.xerces.internal.dom.TextImpl;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class BuscarProveedor extends ViewBase implements ClickListener,
		ValueChangeListener {
	
	
	private static final long serialVersionUID = 1L;
	private VerticalLayout layout;
	GenerarChequeForm padre;
	Ventana ventana;
	private Panel panel;
	private Table contenido;
	private TextField nombreProveedor;
	private TextField idPadre;
	private TextField nombrePadre;
	private Button buscar;
	VerticalLayout principal = new VerticalLayout();
	
	List<ChkProveedor> proveedores = new ArrayList<ChkProveedor>();
	ChkProveedor proveedorSelect;

	public  BuscarProveedor(TextField idProv, TextField nombreProv, ChkProveedor proveedorSelect, Ventana ventana) {
		nombrePadre = nombreProv;
		idPadre = idProv;
		this.proveedorSelect = proveedorSelect;
		this.ventana = ventana;
		construct();
		
	}
	
	
	private void construct(){
		setStyleName("contenido-ventana");
		layout = new VerticalLayout();
		layout.setMargin(true);
		setCompositionRoot(layout);
				
		construirFormulario();
		
		
	}
	private void construirFormulario() {
		panel=new Panel("Busqueda de Proveedor");		
		nombreProveedor = getTextField("Buscar Por nombre", EMPTY);
		buscar = new Button("Buscar");
		buscar.setIcon(new ThemeResource(IMG_BUSCAR));
		buscar.addClickListener(this);
		setStyleButtons(buscar);
		contenido = new Table("Lista de Proveedores");
		contenido.addContainerProperty("Codigo de Proveedor", Object.class, "");
		contenido.addContainerProperty("Nombre Comercial", Object.class, "");
		contenido.addContainerProperty("Nombre para Cheques", Object.class, "");
		contenido.setSelectable(true);
		contenido.addValueChangeListener(this);
		contenido.setPageLength(5);
		armaTablaContenido();
		principal.addComponents(nombreProveedor, buscar, contenido);
		panel.setContent(principal);
		layout.addComponent(panel);
		
	}


	private void armaTablaContenido() {
		contenido.removeAllItems();
		busquedaProveedores();
		int fila = 1;
		if (proveedores != null){
			for (ChkProveedor p: proveedores){
				contenido.addItem(new Object[]{p.getCodProveedor(),p.getNombreComercial(),p.getNombreParaCheques()},fila);
						fila++;
			}
			
			
		}
		
		
	}
	
	private void busquedaProveedores(){
		String query= "Select p from ChkProveedor p";
		boolean empty = nombreProveedor.getValue().trim().equals(EMPTY);
		if (!empty){
			query = query + " where lower(p.nombreComercial) like  lower(?1)";
		}
		
		ProjectDao<ChkProveedor> dao = new ProjectDao<ChkProveedor>(new ChkProveedor());
		try {
			proveedores = dao.findElements(query, empty ? null : new Object[]{"%"+nombreProveedor.getValue()+"%"});			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		
		
	}



	@Override
	public void buttonClick(ClickEvent event) {
		armaTablaContenido();

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		boolean val = false;
		if (contenido.getValue()!= null){
			if (!contenido.getValue().toString().equals("")){
				val = true;							
				Integer value = new Integer(contenido.getValue().toString());
				ChkProveedor prov =  proveedores.get(value -1);
				nombrePadre.setValue(prov.getNombreComercial());
				idPadre.setValue(prov.getCodProveedor()+"");
				
				proveedorSelect.setChkCheques(prov.getChkCheques());
				proveedorSelect.setCodProveedor(prov.getCodProveedor());
				proveedorSelect.setCorreoElectronico(prov.getCorreoElectronico());
				proveedorSelect.setNit(prov.getNit());
				proveedorSelect.setNombreComercial(prov.getNombreComercial());
				proveedorSelect.setNombreParaCheques(prov.getNombreParaCheques());
				proveedorSelect.setTelefono(prov.getTelefono());
				proveedorSelect.setDireccion(prov.getDireccion());
				
				System.out.println("Se envia el proveedor a la pantalla de abajo  "+proveedorSelect);
			}		
		}
		ventana.close();
		
	}

}
