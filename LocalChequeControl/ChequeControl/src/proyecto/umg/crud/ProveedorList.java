/**
 * 
 */
package proyecto.umg.crud;

import java.util.List;

import model.ChkProveedor;
import model.ChkProveedor;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * @author usuario
 *
 */
public class ProveedorList extends ViewBase implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout principal;
	private HorizontalLayout lyBotones;
	private Table listadoItems;
	private Button btnAdd;
	private Button btnRmv;
	private Button btnEdit;
	private Button btnView;
	private List<ChkProveedor> listaItems;
	private ChkProveedor selected;
	
	private ProjectDao<ChkProveedor> daoEliminar;
	
	
	public ProveedorList(){
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	public void obtenerListaItems(){
		ProjectDao<ChkProveedor> dao = new ProjectDao<ChkProveedor>(new ChkProveedor());
		try {
			listaItems = dao.findElements("SELECT c FROM ChkProveedor c",null);			
			listadoItems = new Table("Listado de Chequeras");
			listadoItems.addContainerProperty("Id", Object.class, "");
			listadoItems.addContainerProperty("Nombre", Object.class, "");
			listadoItems.addContainerProperty("Dirección", Object.class, "");
			listadoItems.addContainerProperty("Telefono", Object.class, null);			
			
			
			int fila = 1;
			
			for (ChkProveedor u: listaItems){
				
				listadoItems.addItem(new Object[]{u.getCodProveedor(),u.getNombreComercial()
				, u.getDireccion(), u.getTelefono()}, fila);
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
		
		obtenerListaItems();
		listadoItems.setSelectable(true);
		listadoItems.setPageLength(5);		
		listadoItems.setSizeFull();
		principal.setWidth(100, Unit.PERCENTAGE);
	    //principal.setHeight(90, Unit.PERCENTAGE);
		listadoItems.addValueChangeListener(this);
		principal.addComponent(listadoItems);
		principal.setComponentAlignment(listadoItems, Alignment.MIDDLE_CENTER);
		btnAdd = new Button("Nuevo",getResource(BASE_R32+"nuevo.png"));
		btnEdit= new Button("Editar",getResource(BASE_R32+"editar.png"));
		btnRmv = new Button ("Eliminar",getResource(BASE_R32+"borrar.png"));
		btnView = new Button ("Ver");
		addListener(btnAdd);
		btnAdd.setEnabled(true);
		btnEdit.setEnabled(false);
		btnRmv.setEnabled(false);
		btnView.setEnabled(false);
		addListener(btnEdit);
		addListener(btnRmv);
		addListener(btnView);
		lyBotones = new HorizontalLayout();
		setStyleButtons(btnAdd, btnEdit, btnRmv, btnView);
		
		lyBotones.addComponent(btnAdd);
		lyBotones.addComponent(btnEdit);
		lyBotones.addComponent(btnRmv);
		lyBotones.addComponent(btnView);
		principal.addComponent(lyBotones);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String caption = event.getButton().getCaption();
		if (caption.equals("Nuevo")){
			Ventana create = new Ventana("Nuevo Proveedor", 40, 90);
			create.setModal(true);
			ProveedorNew crear = new ProveedorNew(create,this);
			create.setContenido(crear);
			UI.getCurrent().addWindow(create);
		}
		
		if (caption.equals("Ver")){
			Ventana itemView = new Ventana("Ver Proveedor", 40,90);
			ProveedorEdit prov = new ProveedorEdit(VIEW, this, selected);
			itemView.setContenido(prov);
			itemView.setModal(true);
			
			
			UI.getCurrent().addWindow(itemView);
		}
		if (caption.equals("Editar")){
			Ventana itemEdit = new Ventana("Edicion de Proveedor", 40,90);
			ProveedorEdit prov = new ProveedorEdit(EDIT, this, selected);
			itemEdit.setContenido(prov);
			itemEdit.setModal(true);
			UI.getCurrent().addWindow(itemEdit);
		}
		if (caption.equals("Eliminar")){			 
			daoEliminar = new ProjectDao<ChkProveedor>(selected);
			
			try {
				daoEliminar.findElementById(selected.getCodProveedor());
			} catch (Exception e) {
				e.printStackTrace();
			}			
			Ventana delete = new Ventana("Eliminar", 30, 20);
			delete.setModal(true);
			EntityDel<ChkProveedor> contenido = new EntityDel<ChkProveedor>(this, selected.getNombreComercial(), "Proveedor", daoEliminar, delete);
			contenido.setBorradoInactivar(false);
			delete.setContenido(contenido);		
			UI.getCurrent().addWindow(delete);
		}
		
		
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		selected = null;
		boolean val = false;
		if (listadoItems.getValue()!= null){
			if (!listadoItems.getValue().toString().equals("")){
				val = true;
				btnEdit.setEnabled(val);
				btnRmv.setEnabled(val);
				btnView.setEnabled(val);				
				Integer value = new Integer(listadoItems.getValue().toString());
				selected = listaItems.get(value -1);
				
			}
			
			
		}else{
			btnEdit.setEnabled(val);
			btnRmv.setEnabled(val);
			btnView.setEnabled(val);
		}
		
		
		
		
	}

}
