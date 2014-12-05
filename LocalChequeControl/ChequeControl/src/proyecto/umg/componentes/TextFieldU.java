package proyecto.umg.componentes;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.TextField;

public class TextFieldU extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor para armar un Textfield con todas las caracteristicas predefinidas
	 * @param nombre
	 * @param maxLength
	 * @param minLength
	 * @param label
	 * @param required
	 * @param immediate
	 */
	public TextFieldU(String nombre, int maxLength, int minLength,  String label, boolean required, boolean immediate){
		super(label);
		setStyleName("textfieldStyle");
		addValidator(new StringLengthValidator(
			    "El "+nombre+" solo puede tener de 1 a 10 letras (actualmente tiene {0})",
			    1, 10, true));
		setImmediate(true);
		setValidationVisible(true);
		setRequired(true);
	}
	/**
	 * Constructor en el cual solo se envía la etiqueta inicial
	 * @param label
	 */
	public TextFieldU(String label){
		super(label);
		setStyleName("textfieldStyle");
	}

}
