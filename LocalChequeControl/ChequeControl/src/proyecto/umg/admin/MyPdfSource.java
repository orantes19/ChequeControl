package proyecto.umg.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

public class MyPdfSource implements StreamSource {
	private String fileName;
	public MyPdfSource(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public InputStream getStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
	}

}
