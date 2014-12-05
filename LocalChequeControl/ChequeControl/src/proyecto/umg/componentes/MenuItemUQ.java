package proyecto.umg.componentes;

import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;


public class MenuItemUQ extends MenuBar.MenuItem {
	private int idUq;
	public MenuItemUQ(MenuBar menuBar, String caption, Resource icon,
			Command command) {
		menuBar.super(caption, icon, command);
		
	}
	
	public MenuItemUQ(MenuBar menuBar, String caption, Resource icon,
			Command command, int id) {
		menuBar.super(caption, icon, command);
		this.idUq = id;
		
	}

	public int getIdUq() {
		return idUq;
	}

	public void setIdUq(int idUq) {
		this.idUq = idUq;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
