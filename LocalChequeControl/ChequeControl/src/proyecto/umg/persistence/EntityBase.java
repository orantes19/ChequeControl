package proyecto.umg.persistence;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.ChkBanco;

public class EntityBase<T> extends FactoryManager {
	protected T entity;
	private EntityManager em;	
	
	public EntityBase(T b){
		this.entity = b;
	}
	protected void afterSave(){
		
	}
	
	public void save() throws Exception{
		afterSave();
		EntityManagerFactory factory = getEntityManagerFactory();
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		try{
			em.persist(entity);
			em.getTransaction().commit();
			
			
		}catch(RuntimeException e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
			
		}finally{
			if (factory != null){
				factory.close();
			}
			if (em != null){
				em.close();
			}
			
		}
		
	}
	/**
	 * @return the t
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param t the t to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	
	
}
