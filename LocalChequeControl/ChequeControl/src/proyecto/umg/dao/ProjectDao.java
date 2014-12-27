package proyecto.umg.dao;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
	
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	public List<T> findElements(String namedQuery, Object[] params) throws Exception{
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		try{
			Query query = em.createQuery(namedQuery);
			int num = 1;
			if (params !=null){
				for (Object o: params){
					query.setParameter(num, o);
					num++;
					
				}
			}
						
			listaItems = (List<T>) query.getResultList();			
			
			return listaItems;
			
			
			
		}catch(RuntimeException e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
		}		
		
	}
	
	@SuppressWarnings("unchecked")
	public void getReference(Object id){
		if (!em.contains(entity)){
			entity = (T) em.getReference(entity.getClass(), id);	
		}
	}
	
	public T save() throws Exception{	
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		
		try{
			em.persist(entity);
			em.getTransaction().commit();
					
			
		}catch(RuntimeException e){
			if (em.getTransaction().isActive())
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
			System.out.println("Antes de eliminar");
			if (em.contains(entity)){
				System.out.println("Listo para la transaccion");
				em.merge(entity);
				em.getTransaction().commit();
				
			}
			
					
			
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
			
		}
		return entity;
		
	}
	
	public void borrar() throws Exception {
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		
		try{
			
			if (em.contains(entity)){
				em.remove(entity);
				em.getTransaction().commit();
				
			}
			
					
			
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
			throw new Exception("Ha ocurrido un error:  "+e.getMessage());
			
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public T merge(String objectId) throws Exception {
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		 
		//entity = (T) em.getReference(entity.getClass(), obtieneValor(objectId));
		try{	
			System.out.println("Listo para eliminar");
				if (!em.contains(entity)){
					entity = (T) em.getReference(entity.getClass(), obtieneValor(objectId));
					System.out.println("Contiene la entidad  "+entity);
				}
				em.merge(entity);				
				em.getTransaction().commit();				
			
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
	
	private Object obtieneValor(String atributo) throws Exception{
		Object valor = null;
		Method[] metodos = entity.getClass().getMethods();
		for(Method metodo: metodos){
			if (metodo.getName().equalsIgnoreCase("get"+atributo)){
				valor = metodo.invoke(entity);
				return valor;
			}
		}
		return valor;
	}
}
