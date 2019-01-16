package com.faurecia.dao;

import com.faurecia.vo.BaseArg;
import com.faurecia.vo.BaseResult;
import com.faurecia.vo.Filter;
import com.faurecia.vo.Rule;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/3/8
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */

@Component
public  class BaseDao {
    @Value("${env}")
    private String env;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <T> void save(T o){
        entityManager.persist(o);
    }
    @Transactional
    public <T> void update(T o){
        try{
            entityManager.merge(o);
            entityManager.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Transactional
    public <T> void del(Class<T> o,int id){
        entityManager.remove(entityManager.find(o, id));
    }
    @Transactional
    public <T> T findById(final Class<T> o,Object id){
        return entityManager.find(o, id);
    }

    public List findAll(String sql){
        Query queryObject = entityManager.createQuery(sql);
        return  queryObject.getResultList();
    }


    public <T> List<T> findByParamAndPage(Class<T> o, BaseArg arg){
        try {
            CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(o);
            Root<T> root = query.from(o);
            List<Predicate> predicatesList = new ArrayList<>();
//          拼装条件
            if(null!=arg.getFilters()&&!arg.getFilters().equals("")){
                Filter filter = new Gson().fromJson(arg.getFilters(),Filter.class);
                if(filter.getRules().length>0){
                    createParam(filter,predicatesList,criteriaBuilder,root);
                }
            }
            //排序
            if(null!=arg.getSidx()&&!arg.getSidx().equals("")){
                if(arg.getSord().equals("desc")){
                    query.orderBy(criteriaBuilder.desc(root.get(arg.getSidx())));
                }else{
                    query.orderBy(criteriaBuilder.asc(root.get(arg.getSidx())));
                }
            }
            if(predicatesList.size()>0){
                query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            }
            TypedQuery<T> typedQuery = entityManager.createQuery(query);
            typedQuery.setFirstResult(arg.getRows()*(arg.getPage()-1));
            typedQuery.setMaxResults(arg.getRows());
            return typedQuery.getResultList();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public <T> int countByParamAndPage(Class<T> o, BaseArg arg){
        try {
            CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(o);
            Root<T> root = query.from(o);
            List<Predicate> predicatesList = new ArrayList<>();
            //拼装条件
            if(null!=arg.getFilters()&&!arg.getFilters().equals("")){
                Filter filter = new Gson().fromJson(arg.getFilters(),Filter.class);
                if(filter.getRules().length>0){
                    createParam(filter,predicatesList,criteriaBuilder,root);
                }
            }

            if(predicatesList.size()>0){
                query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            }
            return entityManager.createQuery(query).getResultList().size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public <T> void createParam(Filter filter,List<Predicate> predicatesList,CriteriaBuilder criteriaBuilder,Root<T> root){
        if(filter.getGroupOp().equals("AND")){
            for(Rule r:filter.getRules()){
                if(r.getData().equals(""))continue;
                switch (r.getOp()){
                    case "eq":
                        predicatesList.add(criteriaBuilder.and(eq(criteriaBuilder, root, r)));
                        break;
                    case "ne":
                        predicatesList.add(criteriaBuilder.and(ne(criteriaBuilder, root, r)));
                        break;
                    case "cn":
                        predicatesList.add(criteriaBuilder.and(cn(criteriaBuilder, root, r)));
                        break;
                }
            }

        }else{
            for(Rule r:filter.getRules()){
                switch (r.getOp()){
                    case "eq":
                        predicatesList.add(criteriaBuilder.or(eq(criteriaBuilder, root, r)));
                        break;
                    case "ne":
                        predicatesList.add(criteriaBuilder.and(ne(criteriaBuilder, root, r)));
                        break;
                    case "cn":
                        predicatesList.add(criteriaBuilder.or(cn(criteriaBuilder, root, r)));
                        break;
                }
            }
        }
    }

    public <T> Predicate eq(CriteriaBuilder criteriaBuilder,Root<T> root,Rule r){
        return criteriaBuilder.equal(root.get(r.getField()), r.getData());
    }
    public <T> Predicate ne(CriteriaBuilder criteriaBuilder,Root<T> root,Rule r){
        return criteriaBuilder.notEqual(root.get(r.getField()), r.getData());
    }
    public <T> Predicate cn(CriteriaBuilder criteriaBuilder,Root<T> root,Rule r){
        return criteriaBuilder.like(
                root.get(r.getField()),"%"+r.getData()+"%");
    }


    public List findByParam(String sql, HashMap<String,Object> map){
        Query queryObject = entityManager.createQuery(sql);
        map.forEach((k,v)->{
            queryObject.setParameter(k,v);
        });
        return  queryObject.getResultList();
    }

    public List findByParamAndLastId(String sql, HashMap<String,Object> map,int LastId,int size){
        Query queryObject = entityManager.createQuery(sql);
        map.forEach((k,v)->{
            queryObject.setParameter(k,v);
        });
        queryObject.setFirstResult(LastId);
        queryObject.setMaxResults(size);

        return  queryObject.getResultList();
    }

    public List findAllByPage(String sql,int LastId,int size){
        Query queryObject = entityManager.createQuery(sql);
        queryObject.setFirstResult(LastId);
        queryObject.setMaxResults(size);

        return  queryObject.getResultList();
    }

}
