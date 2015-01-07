package proyecto.umg.reportes;

import java.io.File;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;




public  class ConvertirPDF
{
  public static void convertPDF(File input, File output)
    throws Exception
  {
    OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
    connection.connect();
    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
    converter.convert(input, output);
    connection.disconnect();
  }

  public static boolean convertPDF2(File input, File output) throws Exception {
    boolean status = false;
    OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
    connection.connect();
    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
    try {
      converter.convert(input, output);
      status = true;
    } catch (Exception e) {
    	e.printStackTrace();
    	status = false;
    }
    connection.disconnect();
    return status;
  }
  
  
 
}