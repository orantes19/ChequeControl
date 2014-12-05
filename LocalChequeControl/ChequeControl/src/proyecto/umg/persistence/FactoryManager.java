package proyecto.umg.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryManager {
	
	private EntityManagerFactory factory;
	private static final  String ENTITY_CHEQUE = "ChequeControl";
	
	
	protected EntityManagerFactory getEntityManagerFactory(){
		if (factory == null){
			factory = Persistence.createEntityManagerFactory(ENTITY_CHEQUE);
		}
		
		return factory;
		
		
	}

}
