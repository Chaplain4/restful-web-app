package main.org.itstep.rs.dao;

import main.org.itstep.rs.service.JPAService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractJpaDAO<PK extends Serializable, T> implements AutoCloseable {

    private Class<T> clazz;
    protected JPAService jpaService;

    public AbstractJpaDAO(){
        this.clazz = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.jpaService = JPAService.getInstance();
    }

    public T findById(PK pk){
        return jpaService.findById(clazz, pk);
    }

    public List<T> findAll(){
        return jpaService.findAll(clazz);
    }

    public void deleteById(PK pk){
        jpaService.deleteById(clazz, pk);
    }

    public void saveOrUpdate(T t){
        jpaService.saveOrUpdate(t);
    }

    public void update(T t){
        jpaService.update(t);
    }

    public void create(T t){
        jpaService.create(t);
    }

    public void delete(T t){
        jpaService.delete(t);
    }

    public List<T> findAllByCondition(String condition){
        return jpaService.findAll(clazz , condition);
    }


    public T findByCondition(String condition){
        return jpaService.findByCondition(clazz , condition);
    }

    @Override
    public void close() throws Exception {
        jpaService.closeEntityManagerFactory();
    }
}
