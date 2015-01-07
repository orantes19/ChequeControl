package proyecto.umg.admin;

import java.math.BigDecimal;
import java.util.List;

import model.ChkRolesPorUsuario;
import model.ChkUsuario;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.crud.EntityDel;
import proyecto.umg.crud.UserCreat;
import proyecto.umg.crud.UserEdi;
import proyecto.umg.dao.ProjectDao;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
/**
 * Pantalla con lista de usuarios
 * @author Ronald Orantes
 *
 */
public class UsuarioList extends ViewBase implements ClickListener, Property.ValueChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout principal;
	private HorizontalLayout lyBotones;
	private Table listadoUsuarios;
	private Button btnAdd;
	private Button btnRmv;
	private Button btnEdit;
	private Button btnView;
	private List<ChkUsuario> listaItems;
	private ChkUsuario selected;
	
	private ProjectDao<ChkUsuario> daoEliminar;
	
	
	public UsuarioList(){
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	private void obtenerListaItems(){
		System.out.println("Entro a obtener lista");
		ProjectDao<ChkUsuario> dao = new ProjectDao<ChkUsuario>(new ChkUsuario());
		try {
			listaItems = dao.findElements("SELECT c FROM ChkUsuario c where c.status = ?1",new Object[]{new BigDecimal(1)});
			
//			final BeanItemContainer<ChkUsuario> container =
//			        new BeanItemContainer<ChkUsuario>(ChkUsuario.class);
//			container.addAll(listaItems);
			
			listadoUsuarios = new Table("Listado de Usuarios");
			listadoUsuarios.addContainerProperty("User Name", String.class, "");
			listadoUsuarios.addContainerProperty("Nombre", String.class, "");
			listadoUsuarios.addContainerProperty("Apellido", String.class, "");
			listadoUsuarios.addContainerProperty("Intentos Fallidos", BigDecimal.class, null);
			listadoUsuarios.addContainerProperty("Roles Asignados", String.class, null);
			int fila = 1;
			String roles = "";
			for (ChkUsuario u: listaItems){
				if (u.getStatus().intValue() == 1){
					for (ChkRolesPorUsuario ru: u.getChkRolesPorUsuarios()){
						roles = roles+ru.getChkRol().getRol()+", ";					
					}
					roles = roles.substring(0,roles.length()-2);
					listadoUsuarios.addItem(new Object[]{u.getUsername(),u.getPrimerNombre()+ " "+(u.getSegundoNombre() == null ? "" : u.getSegundoNombre()) 
							, u.getPrimerApellido()+" "+(u.getSegundoApellido() == null ? "" :u.getSegundoApellido()), u.getIntentosFallidos(), roles}, fila);
					fila++;
					roles = "   ";
				}
				
				
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
		listadoUsuarios.setSelectable(true);
		listadoUsuarios.setPageLength(5);		
		listadoUsuarios.setSizeFull();
		principal.setWidth(100, Unit.PERCENTAGE);
	    //principal.setHeight(90, Unit.PERCENTAGE);
		listadoUsuarios.addValueChangeListener(this);
		principal.addComponent(listadoUsuarios);
		principal.setComponentAlignment(listadoUsuarios, Alignment.MIDDLE_CENTER);
		btnAdd = new Button("Nuevo",getResource(BASE_R32+"nuevo.png"));
		btnEdit= new Button("Editar",getResource(BASE_R32+"editar.png"));
		btnRmv = new Button ("Eliminar",getResource(BASE_R32+"borrar.png"));
		btnAdd.setStyleName("button");
		btnView = new Button ("Ver");
		addListener(btnAdd);
		btnAdd.setEnabled(true);
		btnEdit.setEnabled(false);
		btnEdit.setStyleName("button");
		btnRmv.setEnabled(false);
		btnRmv.setStyleName("button");
		btnView.setEnabled(false);
		btnView.setStyleName("button");
		addListener(btnEdit);
		addListener(btnRmv);
		addListener(btnView);
		lyBotones = new HorizontalLayout();
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
			Ventana create = new Ventana("Crear Usuario", 40, 90);
			create.setModal(true);
			UserCreat crear = new UserCreat(create,this);
			create.setContenido(crear);
			UI.getCurrent().addWindow(create);
		}
		
		if (caption.equals("Ver")){
			Ventana usEd = new Ventana("Ver Usuario", 40,90,new UserEdi(VIEW,this, selected));
			UI.getCurrent().addWindow(usEd);
		}
		if (caption.equals("Editar")){
			Ventana usEd = new Ventana("Editar Usuario", 40,90,new UserEdi(EDIT,this, selected));
			UI.getCurrent().addWindow(usEd);
		}
		if (caption.equals("Eliminar")){			 
			daoEliminar = new ProjectDao<ChkUsuario>(selected);
			try {
				daoEliminar.findElementById(selected.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}			
			daoEliminar.getEntity().setStatus(new BigDecimal(0));
			Ventana delete = new Ventana("Eliminar", 30, 20);
			delete.setModal(true);
			EntityDel<ChkUsuario> contenido = new EntityDel<ChkUsuario>(this, selected.getUsername(), "usuario", daoEliminar, delete);
			contenido.setBorradoInactivar(false);
			delete.setContenido(contenido);		
			UI.getCurrent().addWindow(delete);
		}
		
		
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		selected = null;
		boolean val = false;
		if (listadoUsuarios.getValue()!= null){
			if (!listadoUsuarios.getValue().toString().equals("")){
				val = true;
				btnEdit.setEnabled(val);
				btnRmv.setEnabled(val);
				btnView.setEnabled(val);				
				Integer value = new Integer(listadoUsuarios.getValue().toString());
				selected = listaItems.get(value -1);
				
			}
			
			
		}else{
			btnEdit.setEnabled(val);
			btnRmv.setEnabled(val);
			btnView.setEnabled(val);
		}
		
		
		
		
	}
	
	
	
	
}
