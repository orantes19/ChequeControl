package proyecto.umg.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.ChkRolesPorUsuario;
import model.ChkUsuario;
import model.ChkUsuarioMontosConf;
import proyecto.umg.base.ViewBase;
import proyecto.umg.componentes.Ventana;
import proyecto.umg.crud.ChequeraNew;
import proyecto.umg.dao.ProjectDao;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ConfigurarMontosUsuariosPagos extends ViewBase implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout principal;
	private HorizontalLayout lyBotones;
	private Table listadoItems;
	private Button btnAdd;
	
	private List<ChkUsuarioMontosConf> listaItems;
	private ChkUsuarioMontosConf selected;
	Panel panelFormPanel;
	
	
	private ProjectDao<ChkUsuarioMontosConf> daoEliminar;
	
	
	public ConfigurarMontosUsuariosPagos(){
		buildMainLayout();	
		
	}
	private void addListener(Button b){
		b.addClickListener(this);
	}
	public void obtenerListaItems(){
		ProjectDao<ChkUsuarioMontosConf> dao = new ProjectDao<ChkUsuarioMontosConf>(new ChkUsuarioMontosConf());
		ProjectDao<ChkUsuario> daoUser = new ProjectDao<ChkUsuario>(new ChkUsuario());
		HashMap<String, ChkUsuarioMontosConf> mapa = new HashMap<String, ChkUsuarioMontosConf>();
		List<ChkUsuarioMontosConf> listaTemp = new ArrayList<ChkUsuarioMontosConf>();
		try {
			
			listaItems = dao.findElements("SELECT c FROM ChkUsuarioMontosConf c",null);			
			listadoItems = new Table("Configuracion de Montos");
			listadoItems.addContainerProperty("Usuario", Object.class, "");
			listadoItems.addContainerProperty("Monto Minimo", Object.class, "");
			listadoItems.addContainerProperty("Monto Maximo", Object.class, "");
			listadoItems.addContainerProperty("Fecha de Ultima Edicion", Object.class, "");
			List<ChkUsuario> usuarios = new ArrayList<ChkUsuario>();
			for (ChkUsuario u: daoUser.findAll("ChkUsuario.findAll")){
					for (ChkRolesPorUsuario ru: u.getChkRolesPorUsuarios()){
						if (ru.getChkRol().getRol().equals("PAGOS")){
							usuarios.add(u);
							break;
						}
					}
			}
			
			int fila = 1;
			
			for (ChkUsuarioMontosConf u: listaItems){
				mapa.put(u.getChkUsuario().getUsername(), u);
								
			}
			for (ChkUsuario u: usuarios){
				ChkUsuarioMontosConf conf = mapa.get(u.getUsername());
				if (conf != null){
					listadoItems.addItem(new Object[]{u.getUsername(),conf.getMontoMinimo()
							, conf.getMontoMaximo(), formatearFecha(conf.getFechaUltimaEdicion())}, fila);
					listaTemp.add(conf);
				}else{
					
					ChkUsuarioMontosConf it = new ChkUsuarioMontosConf();
					it.setChkUsuario(u);
					it.setMontoMinimo(new BigDecimal(0));
					it.setMontoMaximo(new BigDecimal(0));
					it.setFechaUltimaEdicion(new Date());
					it.setUsuarioUltimaEdicion(getLoggerUser());
					listaTemp.add(it);
					listadoItems.addItem(new Object[]{u.getUsername(),0
							, 0, ""}, fila);
				}
				listaItems.clear();
				listaItems.addAll(listaTemp);
				
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
		btnAdd = new Button("Configurar Montos");	
		addListener(btnAdd);
		setStyleButtons(btnAdd);
		btnAdd.setEnabled(false);
		lyBotones = new HorizontalLayout();
		lyBotones.addComponent(btnAdd);		
		principal.addComponent(lyBotones);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String caption = event.getButton().getCaption();
		if (caption.equals("Configurar Montos")){
			Ventana create = new Ventana("Configuracion de Montos Para Generacion de Cheques", 40, 90);
			create.setModal(true);
			ConfigurarMontosEdit crear = new ConfigurarMontosEdit(EDIT,this, selected);
			create.setContenido(crear);
			UI.getCurrent().addWindow(create);
		}		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		selected = null;
		boolean val = false;
		if (listadoItems.getValue()!= null){
			if (!listadoItems.getValue().toString().equals("")){
				val = true;				
				Integer value = new Integer(listadoItems.getValue().toString());
				selected = listaItems.get(value -1);				
			}			
			
		}
			btnAdd.setEnabled(val);
		
		
		
		
		
	}

	

}
