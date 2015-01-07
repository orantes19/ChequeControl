package proyecto.umg.crud;

import java.util.List;

import model.ChkChequera;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ChequeraList extends ViewBase implements ClickListener,
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
	private List<ChkChequera> listaItems;
	private ChkChequera selected;
	
	private ProjectDao<ChkChequera> daoEliminar;
	
	
	public ChequeraList(){
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	public void obtenerListaItems(){
		ProjectDao<ChkChequera> dao = new ProjectDao<ChkChequera>(new ChkChequera());
		try {
			listaItems = dao.findElements("SELECT c FROM ChkChequera c",null);			
			listadoItems = new Table("Listado de Chequeras");
			listadoItems.addContainerProperty("Id", Object.class, "");
			listadoItems.addContainerProperty("Banco", Object.class, "");
			listadoItems.addContainerProperty("Numero de Cuenta", Object.class, "");
			listadoItems.addContainerProperty("Cantidad de Cheques", Object.class, null);			
			listadoItems.addContainerProperty("Cantidad de Cheques", Object.class, null);
			listadoItems.addContainerProperty("No. Serie", Object.class, null);
			listadoItems.addContainerProperty("Cheques Emitidos", Object.class, null);
			listadoItems.addContainerProperty("Estado", Object.class, null);
			
			int fila = 1;
			
			for (ChkChequera u: listaItems){
				
				listadoItems.addItem(new Object[]{u.getIdChequera(),u.getChkBanco().getNombre()
				, u.getChkCuenta().getNumeroCuenta(), u.getChkCuenta().getNumeroCuenta(), u.getNumeroSerie(),u.getChequesEmitidos(), 
				u.getEstado().intValue() == 1 ? "ACTIVA" : "INACTIVA"}, fila);
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
		setStyleButtons(btnAdd, btnEdit, btnView, btnRmv);
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
			Ventana create = new Ventana("Nueva Chequera", 40, 90);
			create.setModal(true);
			ChequeraNew crear = new ChequeraNew(create,this);
			create.setContenido(crear);
			UI.getCurrent().addWindow(create);
		}
		
		if (caption.equals("Ver")){
			Ventana itemView = new Ventana("Ver Chequera", 40,90,new ChequeraEdit(VIEW, this, selected));
			UI.getCurrent().addWindow(itemView);
		}
		if (caption.equals("Editar")){
			Ventana itemEdit = new Ventana("Edicion de Chequera", 40,90,new ChequeraEdit(EDIT, this, selected));
			UI.getCurrent().addWindow(itemEdit);
		}
		if (caption.equals("Eliminar")){			 
			daoEliminar = new ProjectDao<ChkChequera>(selected);
			
			try {
				daoEliminar.findElementById(selected.getIdChequera());
			} catch (Exception e) {
				e.printStackTrace();
			}			
			Ventana delete = new Ventana("Eliminar", 30, 20);
			delete.setModal(true);
			EntityDel<ChkChequera> contenido = new EntityDel<ChkChequera>(this, selected.getNumeroSerie(), "Chequera", daoEliminar, delete);
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
