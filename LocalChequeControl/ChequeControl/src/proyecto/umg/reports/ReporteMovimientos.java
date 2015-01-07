package proyecto.umg.reports;




import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import model.ChkCheque;
import model.ChkMovimientosCuenta;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;





public class ReporteMovimientos {
	
	private FileInputStream instream;
	private FileOutputStream outstream ;
    private POIFSFileSystem fsin;
	private HSSFWorkbook    workbook;         
    private HSSFSheet sheet;
    private HSSFRow row;
    private HSSFCell cell; 
    private String i_usuario;
    private String i_fecha;
	
    public ReporteMovimientos(){
		
	}
	
	private HSSFRichTextString getData(String dato){
		return new HSSFRichTextString(dato);
	}	
	
//	private HSSFCell getCell(HSSFRow row, int index){
//		HSSFCell cell = null;
//		try{			
//	    	cell = row.getCell(index);
//	    	return cell;
//	    }catch(Exception _celda){	    		
//	    	cell = row.createCell(index);
//	    	return cell;
//	    }
//	}
	
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
		
	public String getGenerarReporte(String archivoBase, String archivoSalida, List<ChkMovimientosCuenta> listaReporte){
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
	        HSSFCellStyle style2 = workbook.createCellStyle();
	        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        row  = sheet.getRow(3);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(getData(getUsuario()));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(getData(getFecha()));
	       
	        outstream = new FileOutputStream(archivoSalida);
	        int fila = 6;
	        @SuppressWarnings("rawtypes")
			Iterator it = listaReporte.iterator();
	        HSSFCell cellA,cellB,cellC,cellD,cellE,cellF, cell8;
	        while (it.hasNext()){
	        	ChkMovimientosCuenta dto = (ChkMovimientosCuenta) it.next();
        		row	 = sheet.createRow(fila);
	        	cellA = row.createCell(0);
        		cellA.setCellValue(getData(dto.getChkCuenta().getNumeroCuenta()));
        		cellA.setCellStyle(style2);
        		cell8 = row.createCell(1);
        		cell8.setCellStyle(style2);
        		cell8.setCellValue(getData(dto.getChkCuenta().getChkBanco().getNombre()));
        		cellB = row.createCell(2);
        		cellB.setCellValue(getData(dto.getTipoMovimiento().intValue() == 1 ? "DEPOSITO" : "RETIRO"));
        		cellB.setCellStyle(style2);
        		cellC = row.createCell(3);
        		cellC.setCellValue(getData(new SimpleDateFormat("dd/MM/yyyy").format(dto.getFechaMovimiento())));
        		cellC.setCellStyle(style2);
        		cellD = row.createCell(4);
        		cellD.setCellValue(getData(dto.getChkCuenta().getMoneda()+". "+dto.getMontoMovimiento().setScale(2, RoundingMode.CEILING).toString()));
        		cellD.setCellStyle(style2);
        		cellE = row.createCell(5);
        		cellE.setCellValue(getData(dto.getDocumentoBanco() == null ? "" : dto.getDocumentoBanco()));
        		cellE.setCellStyle(style2);
        		cellF = row.createCell(6);
        		cellF.setCellValue(getData(dto.getUsuario()));
        		cellF.setCellStyle(style2);
        		fila++;
	        }
	        workbook.write(outstream);
	        
	        instream.close();
	        outstream.close();
		} catch (Exception e) {
			respuesta = e.getMessage();
		}
		return respuesta;
		
	}
}