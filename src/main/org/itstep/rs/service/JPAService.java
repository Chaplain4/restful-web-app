package main.org.itstep.rs.service;

import main.org.itstep.rs.model.*;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class JPAService {
    // private static SessionFactory sessionFactory = null;
    private static EntityManagerFactory entityManagerFactory;
    private static JPAService jpaService = null;

    public static JPAService getInstance() {
        if (jpaService == null) {
            jpaService = new JPAService();
        }
        return jpaService;
    }

    static {
        if (entityManagerFactory == null) {
            //load
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            props.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            props.put(Environment.URL, "jdbc:mysql://127.0.0.1:3307/persons_db");
            props.put(Environment.USER, "root");
            props.put(Environment.PASS, "");
            props.put(Environment.SHOW_SQL, "true");
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            props.put(Environment.HBM2DDL_AUTO, "update");
            configuration.setProperties(props);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Response.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            entityManagerFactory = configuration.buildSessionFactory(serviceRegistry);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public <T> T runInTransaction(Function<EntityManager, T> function) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        T entity = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            entity = function.apply(em);
            tx.commit();
        } catch (Throwable th) {
            th.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
        return entity;
    }


    public void runInTransaction(Consumer<EntityManager> function) {
        runInTransaction(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }

    public <T> T run(Function<EntityManager, T> function) {
        EntityManager em = getEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    public <T> void delete(T entity) {
        runInTransaction(entityManager -> {
            entityManager.remove(entity);
        });
    }

    public <T> T update(T entity) {
        return runInTransaction(entityManager -> {
            return entityManager.merge(entity);
        });
    }

    public <T> void saveOrUpdate(T entity) {
        runInTransaction(entityManager -> {
            entityManager.unwrap(Session.class).saveOrUpdate(entity);
        });
    }

    public <T> void create(T entity) {
        runInTransaction(entityManager -> {
            entityManager.persist(entity);
        });
    }

    public <T> T findById(Class<T> entityClass, Object pk) {
        return run(entityManager -> {
            return entityManager.find(entityClass, pk);
        });
    }

    public <T> T findByCondition(Class<T> entityClass, String condition) {
        return run(entityManager -> {
            return (T) entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o " + condition).getSingleResult();
        });
    }

    public <T> void deleteById(Class<T> entityClass, Object pk) {
        runInTransaction(entityManager -> {
            T entity = entityManager.find(entityClass, pk);
            if (entity == null) {
                throw new EntityNotFoundException("Entity does not exist");
            } else {
                entityManager.remove(entity);
            }
        });
    }

    public <T> List findAll(Class<T> entityClass, String condition) {
        return run(entityManager -> {
            return entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o " + condition).getResultList();
        });
    }

    public <T> List findAll(Class<T> entityClass) {
        return run(entityManager -> {
            return entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o ").getResultList();
        });
    }

    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }
}

