package ec.gob.ambiente.enlisy.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.Contact;
import ec.gob.ambiente.enlisy.model.Organization;
import ec.gob.ambiente.enlisy.model.People;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.exceptions.ServiceException;
import ec.gob.ambiente.vo.NotificacionesMails;

@Stateless
public class UserFacade extends AbstractFacade<User, Integer> implements Serializable {
	
	private static final long serialVersionUID = -4594424897085245484L;


	public UserFacade() {
		super(User.class, Integer.class);		
	}
	
	
	
	@EJB
	private RolesUserFacade rolesUserFacade;
	
	@EJB
	private RoleFacade roleFacade;
	
	
		
	@EJB
	private OrganizationFacade organizationFacade;

	@EJB
	private PeopleFacade peopleFacade;

	@EJB
	private GeographicalLocationFacade geographicalLocationFacade;
	
	@EJB
	private ContactFacade contactFacade;

	@SuppressWarnings("unchecked")
	public User getUserById(Integer userId)
	{
		//return userfacade.find(userId);
		try {
			Query query = getEntityManager().createQuery(" SELECT u FROM User u where u.userId= :userId");		
			query.setParameter("userId",	userId);
			return (User) query.getSingleResult();			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void save(User user, NotificacionesMails notificacionesMail, boolean usuarioExterno, String rolProponente) throws ServiceException {
		
		//user.getPeople().setPeopPin(user.getUserName());
        
        if(user.getUserId() != null)
        {
        	edit(user);
        }
        else
        {
        	create(user);
        }        
              
        
        if (usuarioExterno) {            
            rolesUserFacade.save(user,roleFacade.findByName(rolProponente));
        }
    }
	
	/**
	 * Actualizar datos de perfil de usuario
	 * @param user
	 */
	public void updateUserData(User user,Organization organization) {

		//Actualizar Usuario
		//Se actualiza en cascada la tabla personas
		edit(user);
		
		//Actualizar, crear o remover contactos
		//ContactService contactService=new ContactService();
		for (Contact contact : user.getPeople().getContacts()) {
			if(contact.getContId()==null)				
				contactFacade.create(contact);							
			else
				contactFacade.edit(contact);
		}
		
		if(organization!=null&&organization.getOrgaId()!=null)
			organizationFacade.edit(organization);

		
		
	}
	
	public Organization getOrganizationByPeopleAndRuc(People people,String orgaRuc)
	{
		return organizationFacade.findByPeopleAndRuc(people,orgaRuc);
	}
	
	public People getPeopleByPin(String peopPin) {
	
		return peopleFacade.findByPin(peopPin);
	}
	
	
	
	
	public String getCompleteNameByUserName(String userName)
	{
		Organization orga=organizationFacade.findByRuc(userName);
		if(orga!=null&&orga.getOrgaNameOrganization()!=null)
			return orga.getOrgaNameOrganization();
		
		TypedQuery<User> query = super.getEntityManager().createQuery("select u from User u where u.userName = :userName", User.class);
		
		query.setParameter("userName", userName);
		
		String completeName = "";
		if(query.getResultList().size() > 0)
		{
			completeName = query.getResultList().get(0).getPeople().getPeopName();
		}
		
		return completeName;
	}
	
	public User findByUserName(String userName)
	{
		TypedQuery<User> query = super.getEntityManager().createQuery("select u from User u "
				+ "where u.userName = :userName and u.userStatus = true", User.class);
		
		query.setParameter("userName", userName);
		
		User user = new User();
		
		if(query.getResultList().size() > 0)
		{
			user = query.getResultList().get(0);
		}
		
		return user;
	}
	
	
	public User findByUserNameDisabled(String userName)
	{
		TypedQuery<User> query = super.getEntityManager().createQuery("select u from User u "
				+ "where u.userName = :userName and u.userStatus = false", User.class);
		
		query.setParameter("userName", userName);
		
		User user = new User();
		
		if(query.getResultList().size() > 0)
		{
			user = query.getResultList().get(0);
		}
		
		return user;
	}
	
	public List<User> find_all(){		
		return findAll();
	}
	
	public User updateUser(User usuario){		
		return edit(usuario);
	}
	
	public User findByPeople(People persona) {
		User result = null;
		
		Query q = getEntityManager().createQuery("SELECT u FROM User u WHERE u.people=:persona");
		q.setParameter("persona", persona);
		
		if(q.getResultList().size() > 0) {
			result = (User)q.getResultList().get(0);
		}
		
		return result;
	}
	
	
	
	

}