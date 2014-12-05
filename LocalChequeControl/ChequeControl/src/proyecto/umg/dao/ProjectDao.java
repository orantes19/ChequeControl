package proyecto.umg.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.ChkUsuario;
import proyecto.umg.persistence.FactoryManager;
/**
 * 
 * @autor Ronald Orantes
 */
public class ProjectDao <T> extends FactoryManager{
	protected T entity;
	protected List<T> listaItems;
	private EntityManager em;
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	private EntityManagerFactory factory;
	
	public ProjectDao(T b){
		this.entity = b;
		factory = getEntityManagerFactory();
		em = factory.createEntityManager();
	}
	
	public T findElementById(Object id) throws Exception{
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		try{
			
			entity = (T) em.find(entity.getClass(), id);
			
			return entity;			
			
		}catch(RuntimeException e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
		}
	}
	
	/**
	 * Retorna una lista de items de la entidad según el namedQuery enviado
	 * @param namedQuery namedQuery declarado en la entidad
	 * @return Lista de items
	 * @throws Exception  si hay un error lo retorna como excepcion
	 */
	public List<T> findAll(String namedQuery) throws Exception{
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		try{
			
			listaItems = (List<T>) em.createNamedQuery(namedQuery, 
					entity.getClass()).getResultList();			
			
			return listaItems;
			
			
			
		}catch(RuntimeException e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
		}
		
		
		
		
		
	}
	
	public T save() throws Exception{	
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		
		try{
			em.persist(entity);
			em.getTransaction().commit();
					
			
		}catch(RuntimeException e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
			
		}
		return entity;
		
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

	public T merge() throws Exception {
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		try{
			if (em.contains(entity)){
				System.out.println("Entity contiene entidad   ");
				em.merge(entity);
				em.getTransaction().commit();
			}
			
					
			
		}catch(Exception e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
			
		}
		return entity;
		
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	public void closeEntityManager(){
		if (em != null){
			if (em.getTransaction().isActive()){
				em.flush();
			}
			
			em.close();
		}
		if (factory != null){
			factory.close();
		}
		
	}
}
