package proyecto.umg.componentes;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.TextField;

public class IntegerField extends TextField implements TextChangeListener {
	String lastValue = "";

	public IntegerField() {
		setImmediate(true);
		setTextChangeEventMode(TextChangeEventMode.EAGER);
		addTextChangeListener(this);
	}

	@Override
	public void textChange(TextChangeEvent event) {
		String text = event.getText();
		if (event.getText().equals("")){
			setValue("");
			return;
		}
		try {
			new Integer(text);
			lastValue = text;
		} catch (NumberFormatException e) {
			setValue(lastValue);
		}
	}

}