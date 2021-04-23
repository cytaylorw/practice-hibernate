package com.taylorw.DemoHibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
	SessionFactory sf;
	List<Session> session = new ArrayList<Session>();
	
    public static void main( String[] args )
    {
    	App app = new App();
    	Class<?>[] annotatedClasses = {
			Alien.class, 
			Weapon.class,
			Student.class
    	};
    	app.createSessionFactory(annotatedClasses).createSession().createSession();
    	
//    	AlienName aname = new AlienName();
//    	aname.setFname("Chin-Yue");
//    	aname.setMname("Taylor");
//    	aname.setLname("Wong");
//    	
//    	
//    	Weapon w = new Weapon();
//    	w.setId(101);
//    	w.setName("Laser");
//    	
//    	Alien a = new Alien();
//    	a.setAid(103);
//    	a.setAname(aname);
//    	a.setAcolor("Green");
//    	a.getWeapon().add(w);
//    	
//    	w.setAlien(a);
//    	
//    	try 
//    	{
//    		app.begin().save(a);
//    		app.save(w).commit();
//    	}catch(ConstraintViolationException e){}
    	
    	
    	
//    	System.out.println(app.get(Alien.class, 103));
//    	System.out.println(app.get(Alien.class, 103,1));
//    	System.out.println(app.get(Weapon.class, 101));
    	System.out.println(app.queryUnqiue("FROM Alien WHERE aid=103"));
    	System.out.println(app.queryUnqiue("FROM Alien WHERE aid=103",1));
//    	app.generateStudent(50);
    	List<Student> students = App.castList(Student.class, app.queryList("FROM Student WHERE gpa < 1"));
    	for(Student s : students)
    	{
    		System.out.println(s);
    	}
    	
    	List<Object[]> students2 = App.castList(Object[].class, app.queryList("SELECT rollNo,name,gpa FROM Student"));
    	for(Object[] s : students2)
    	{
    		for(Object s2: s)
    		{
    			
    			System.out.print(s2 + " ");
    		}
    		System.out.print("\n");
    	}
    	
    	Double avg = (Double) app.queryUnqiue("SELECT AVG(gpa) FROM Student s WHERE gpa > 1");
    	System.out.println(avg);
    	
    	double gpaMax = 1;
    	Query q1 = app.query("SELECT AVG(gpa) FROM Student s WHERE gpa > :gpaMax");
    	q1.setParameter("gpaMax", gpaMax);
    	Double avg2 = (Double) q1.uniqueResult();
    	System.out.println(avg2);
    	
    	
    	SQLQuery query = app.getSession(0).createSQLQuery("SELECT * FROM student WHERE gpa > 1");
    	query.addEntity(Student.class);
    	List<Student> students3 = App.castList(Student.class, query.list());
    	
    	for(Student student: students3)
    	{
    		System.out.println(student);
    	}
    	
    	SQLQuery query2 = app.getSession(0).createSQLQuery("SELECT name,gpa FROM student WHERE gpa > 1");
    	query2.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
    	List<Map<?,?>> students4 = App.castListOfMap(query2.list());
    	
    	for(Map<?,?> student: students4)
    	{
    		System.out.println(student.get("name") + " " + student.get("gpa"));
    	}
    	
    	app.jpaTest();
    }
    
    public App createSessionFactory(Class<?>[] annotatedClasses)
    {
    	 Configuration cfg = new Configuration().configure();
    	 
    	 for(Class<?> annotatedClass : annotatedClasses)
    	 {
    		 cfg.addAnnotatedClass(annotatedClass);
    	 }
    	 
         ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
         this.sf = cfg.buildSessionFactory(reg);
		return this;
    }
    
    public App createSession() {
    	
    	this.session.add(sf.openSession());
		return this;
    }
    
    public Session getSession(int index)
    {
    	return this.session.get(index);
    }
    
    public App begin()
    {
    	return this.begin(0);
    }
    
    public App begin(int index)
    {
    	this.session.get(index).beginTransaction();
    	return this;
    }
    public App commit()
    {
    	return this.commit(0);
    }
    public App commit(int index)
    {
    	 this.session.get(index).getTransaction().commit();
    	return this;
    }
    public App save(Object obj)
    {
		return this.save(obj, 0);
    }
    public App save(Object obj, int index)
    {
    	     
        session.get(index).save(obj);

		return this;
    }
    
    public Object get(Class<?> type, int id)
    {
    	return this.get(type, id, 0);
    }
    
    public Object get(Class<?> type, int id, int index)
    {
    	return type.cast(this.getSession(index).get(type, id));
    }
    
    public Query query(String queryString, int index) 
    {
    	Query q1 = this.getSession(index).createQuery(queryString);
    	q1.setCacheable(true);
    	return q1;
    }
    
    public Query query(String queryString) 
    {
    	return this.query(queryString, 0);
    }
    
    public Object queryUnqiue(String queryString, int index)
    {
    	Query q1 = this.query(queryString, index);
    	return q1.uniqueResult();
    }
    
    public Object queryUnqiue(String queryString)
    {
    	Query q1 = this.query(queryString, 0);
    	return q1.uniqueResult();
    }
    public List<?> queryList(String queryString, int index) 
    {
    	Query q1 = this.query(queryString, index);
    	return q1.list();
    }
    
    
    public List<?> queryList(String queryString) 
    {
    	return this.queryList(queryString, 0);
    }

    public boolean generateStudent(int length)
    {
    	Random r = new Random();
    	this.begin();
    	for(int i = 1; i <= length; i++)
    	{
    		Student s = new Student();
    		s.setRollNo(i);
    		s.setName("Name " + i);
    		s.setGpa(r.nextDouble() * 4);
//    		System.out.println(s);
    		this.save(s);
    	}
    	try
    	{    		
    		this.commit();
    		return true;
    	}
    	catch(Exception e) { 
    		return false;
		}
    }
    
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        List<T> result = new ArrayList<>(rawCollection.size());
        for (Object o : rawCollection) {
            try {
                result.add(clazz.cast(o));
            } catch (ClassCastException e) {
                // log the exception or other error handling
            }
        }
        return result;
    }
    
    
	public static List<Map<?,?>> castListOfMap(Collection<?> rawCollection) {
    	List<Map<?,?>> result = new ArrayList<Map<?,?>>(rawCollection.size());
        for (Object o : rawCollection) {
            try {
                result.add((Map<?,?>) o);
            } catch (ClassCastException e) {
                // log the exception or other error handling
            }
        }
        return result;
    }
    
	
	public void jpaTest()
	{
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		Student s1 = new Student();
		s1.setRollNo(100);
		s1.setName("Alien Alien");
		s1.setGpa(4);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnitName");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(s1);
		em.getTransaction().commit();
		Student a = em.find(Student.class, 100);
		System.out.println(a);
	}
}
