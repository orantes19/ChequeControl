package proyecto.umg.transaction;

import java.util.HashMap;
import java.util.List;



import java.util.Map;

import proyecto.umg.dao.ProjectDao;
import model.ChkCheque;
import model.ChkConfCheque;
import model.ChkRol;
import model.ChkRolesPorUsuario;
import model.ChkUsuario;



public class AutorizarCheque {
	private Map<String, String> rolMap;
	private static Map<String, Map<String, Double>> rolMontosMap;
	private ChkCheque cheque;
	
	
	public AutorizarCheque(ChkCheque cheque, ChkUsuario usuario, Map<String, String> rolMap){
		this.rolMap = rolMap;
		this.cheque = cheque;
		obtenerRoles(cheque, usuario);
	}
	
	private void obtenerRoles(ChkCheque cheque, ChkUsuario usuario){
			ProjectDao<ChkRolesPorUsuario> daoRol = new ProjectDao<ChkRolesPorUsuario>(new ChkRolesPorUsuario());
			List<ChkRolesPorUsuario> roles = null;
			ProjectDao<ChkConfCheque> daoMontos = new ProjectDao<ChkConfCheque>(new ChkConfCheque()); 
			List<ChkConfCheque> montos = null;
			try {
				if (rolMap == null){
					roles = daoRol.findElements("select r from ChkRolesPorUsuario r"
							+ " where r.chkUsuario = ?1", new Object[]{usuario});
					rolMap = new HashMap<String, String>();
				}
				if (rolMontosMap == null){
					montos = daoMontos.findAll("ChkConfCheque.findAll");
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				daoRol.closeEntityManager();
				daoMontos.closeEntityManager();
			}
			
			if (rolMap == null){
				for (ChkRolesPorUsuario r: roles){
					rolMap.put(r.getChkRol().getRol(), "S");
				}
			}
			
			
			if (rolMontosMap == null){
				rolMontosMap = new HashMap<String, Map<String, Double>>();
				for (ChkConfCheque r: montos){
					Map <String,Double> m = new HashMap<String, Double>();
					m.put("MINIMO", r.getMontoMinimo().doubleValue());
					m.put("MAXIMO", r.getMontoMaximo().doubleValue());
					rolMontosMap.put(r.getChkRol().getRol(), m);
					
				}
			}
			
				
			}
	
	//estado 1  Solo emitido y no liberado por nadie
	//estado 2  Emitido y liberado por auditoria falta otra
	//estado 3  Emitido y liberado por auditoria y gerencia, falta otra
	//estado 10  Liberado, listo para entrega
	//estado 11 Impreso -- ya no se permiten modificaciones
	
	
	public int siguienteStatus(){
		int estado = cheque.getEstado().intValue();
			
		double valor = cheque.getMonto().doubleValue();
		
		if (estado == 11){
			return estado+1;
		}
		
		if (estado == 1 &&  valor >= rolMontosMap.get("AUDITORIA").get("MINIMO")){
			return 2;
		}
		
		
		if (estado == 1 
				&& valor < rolMontosMap.get("AUDITORIA").get("MINIMO")){
			return 11;
		}
		
		if (estado == 2 &&  valor >= rolMontosMap.get("AUDITORIA").get("MAXIMO")){
			return 3;
		}
		
		if (estado == 3 &&  valor > rolMontosMap.get("CONSEJO").get("MINIMO")){
			return 4;
		}
		
		
		
		if (estado == 4 ){
			return 10;
		}
		
		
		
		if (estado == 10){
			return 11;
		}
		
		
		return 0;
		
	}
	
	
	public int getActualizaStatus(){
		int estado = cheque.getEstado().intValue();
		double valor = cheque.getMonto().doubleValue();
		
		if (estado == 1 &&  valor < rolMontosMap.get("AUDITORIA").get("MINIMO")){
			return 10;
		}
		if (estado == 1 &&  valor >= rolMontosMap.get("AUDITORIA").get("MINIMO") &&
				valor < rolMontosMap.get("GERENCIA").get("MINIMO")){
			return 10;
		}
		
		if (estado == 1 &&  valor >= rolMontosMap.get("AUDITORIA").get("MINIMO") ){
			return 2;
		}
		
		if (estado == 2 && valor >= rolMontosMap.get("GERENCIA").get("MINIMO")
				&& valor < rolMontosMap.get("CONSEJO").get("MINIMO")){
			return 10;
		}
		
		
		if (estado == 2 && valor >= rolMontosMap.get("GERENCIA").get("MINIMO")){
			return 3;
		}
		
		
		
		if (estado == 3 &&  valor >= rolMontosMap.get("CONSEJO").get("MINIMO")){
			return 10;
		}
		
		if (estado == 3 &&  valor < rolMontosMap.get("CONSEJO").get("MINIMO")){
			return 10;
		}
		if (estado == 4){
			return 10;
		}	
		
		if (estado == 10){
			return 11;
		}
		
		
		return 0;
		
	}
	
	

}
