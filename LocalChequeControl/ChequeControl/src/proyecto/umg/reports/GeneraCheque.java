package proyecto.umg.reports;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import model.ChkCheque;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class GeneraCheque {
	private FileInputStream instream;
	private FileOutputStream outstream ;
    private POIFSFileSystem fsin;
	private HSSFWorkbook    workbook;         
    private HSSFSheet sheet;
    private HSSFRow row;
    private HSSFCell cell; 
    private String i_usuario;
    private String i_fecha;
	
    public GeneraCheque(){
		
	}
	
	private HSSFRichTextString getData(String dato){
		return new HSSFRichTextString(dato);
	}	
	

	
	public void setUsuario(String a){		
		i_usuario = a;
	}
	
	public String getUsuario(){
		return i_usuario;
	}
	
	public void setFecha(String a){		
		i_fecha = a;
	}
	
	public String getFecha(){
		return i_fecha;
	}
		
	public String getGenerarReporte(String archivoBase, String archivoSalida, ChkCheque cheque){
		String respuesta = "OK";
		try {
			instream = new FileInputStream(archivoBase);
	        fsin     = new POIFSFileSystem(instream);
	        workbook = new HSSFWorkbook(fsin, true);        
	        sheet    = workbook.getSheetAt(0);
	        row	 	 = null;
	        cell	 = null;
	        HSSFCellStyle style = workbook.createCellStyle();
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        
	       
                                  
            HSSFCell cellMonto,cellNumCheque,cellNombreCheque,cellMontoCheque;
            row = sheet.getRow(3);
            
            cellNumCheque = row.createCell(10);
            cellNumCheque.setCellStyle(style);
            cellNumCheque.setCellValue(getData(cheque.getChkChequera().getNumeroSerie()+" "+cheque.getNumeroCheque()));
            
            row  = sheet.getRow(6);
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(getData("Guatemala  "+getFecha()));
            
            
            row = sheet.getRow(6);
            cellMonto = row.createCell(9);
            
            cellMonto.setCellStyle(style);
            cellMonto.setCellValue(getData(cheque.getChkChequera().getChkCuenta().getMoneda()+". "+cheque.getMonto().setScale(2, BigDecimal.ROUND_CEILING).toString()));
            
            row = sheet.getRow(9);
            
            cellNombreCheque = row.createCell(3);
            cellNombreCheque.setCellStyle(style);
            cellNombreCheque.setCellValue(getData(cheque.getChkProveedor().getNombreParaCheques()));
            
            row = sheet.getRow(12);
            cellMontoCheque = row.createCell(3);
            
            
            cellMontoCheque.setCellStyle(style);
            cellMontoCheque.setCellValue(getData("********** "+cheque.getChkChequera().getChkCuenta().getMoneda()+". "+cheque.getMonto().setScale(2,BigDecimal.ROUND_CEILING).toString()+"**********"));
            
              
                       
            
	        outstream = new FileOutputStream(archivoSalida);  
	        
	        
	        workbook.write(outstream);
	        
	        instream.close();
	        outstream.close();
		} catch (Exception e) {
			respuesta = e.getMessage();
		}
		return respuesta;
	}
}

