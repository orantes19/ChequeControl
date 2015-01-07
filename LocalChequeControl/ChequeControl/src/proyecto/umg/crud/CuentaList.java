package proyecto.umg.crud;

import java.math.BigDecimal;
import java.util.List;

import model.ChkCuenta;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CuentaList extends ViewBase implements ClickListener, ValueChangeListener {

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
	private List<ChkCuenta> listaItems;
	private ChkCuenta selected;
	
	private ProjectDao<ChkCuenta> daoEliminar;
	
	
	public CuentaList(){
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	public void obtenerListaItems(){
		ProjectDao<ChkCuenta> dao = new ProjectDao<ChkCuenta>(new ChkCuenta());
		try {
			listaItems = dao.findElements("SELECT c FROM ChkCuenta c order by c.chkBanco.nombre asc",null);			
			listadoItems = new Table("Listado de Cuentas");
			listadoItems.addContainerProperty("Numero de Cuenta", Object.class, "");
			listadoItems.addContainerProperty("Banco", Object.class, "");
			listadoItems.addContainerProperty("Saldo Actual", Object.class, "");
			listadoItems.addContainerProperty("Fecha de Apertura", Object.class, "");
						
			int fila = 1;
			
			for (ChkCuenta u: listaItems){
				
				listadoItems.addItem(new Object[]{u.getNumeroCuenta(),u.getChkBanco().getNombre()
				, u.getMoneda()+"."+u.getSaldoActual().setScale(2, BigDecimal.ROUND_CEILING), formatearFecha(u.getFechaApertura())}, fila);
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
			Ventana create = new Ventana("Nueva Cuenta", 40, 90);
			create.setModal(true);
			CuentaNew crear = new CuentaNew(create,this);
			create.setContenido(crear);
			UI.getCurrent().addWindow(create);
		}
		
		if (caption.equals("Ver")){
			Ventana itemView = new Ventana("Ver Cuenta", 40,90,new CuentaEdit(VIEW, this, selected));
			UI.getCurrent().addWindow(itemView);
		}
		if (caption.equals("Editar")){
			Ventana itemEdit = new Ventana("Editar Cuenta", 40,90,new CuentaEdit(EDIT, this, selected));
			UI.getCurrent().addWindow(itemEdit);
		}
		if (caption.equals("Eliminar")){	
			try {
				afterDelete();
			} catch (Exception e1) {
				error("Error", e1.getMessage(), Page.getCurrent(), ERROR);
				return;
			}
			daoEliminar = new ProjectDao<ChkCuenta>(selected);
			
			try {
				daoEliminar.findElementById(selected.getNumeroCuenta());
			} catch (Exception e) {
				e.printStackTrace();
			}			
			Ventana delete = new Ventana("Eliminar", 30, 20);
			delete.setModal(true);
			EntityDel<ChkCuenta> contenido = new EntityDel<ChkCuenta>(this, selected.getNumeroCuenta(), "banco", daoEliminar, delete);
			contenido.setBorradoInactivar(false);
			delete.setContenido(contenido);		
			UI.getCurrent().addWindow(delete);
		}
		
		
		
	}

	private void afterDelete()throws Exception {
		if (selected.getChkMovimientosCuentas() != null){
			if (selected.getChkMovimientosCuentas().size() > 0){
				throw new Exception("No se puede eliminar la cuenta debido a que tiene movimientos de cuenta relacionados");
			}
			
		}
		if (selected.getChkChequeras() != null){
			if (selected.getChkChequeras().size() > 0){
				throw new Exception("No se puede eliminarm la cuenta porque tiene chequeras asignadas");
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
