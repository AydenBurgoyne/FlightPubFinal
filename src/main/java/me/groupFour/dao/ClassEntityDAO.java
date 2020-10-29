package me.groupFour.dao;

import me.groupFour.data.ClassEntity;
import me.groupFour.data.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.HashMap;

public class ClassEntityDAO extends AbstractEntityDAO<ClassEntity, String> implements IClassEntityDAO {
    HashMap<String, ClassEntity> cacheMap = new HashMap<>();

    @Autowired
    public ClassEntityDAO(EntityManager em) {
        super(ClassEntity.class, em);
    }

    public ClassEntity findById(String id) {
        if(cacheMap.containsKey(id)){
            return cacheMap.get(id);
        }
        else{
            ClassEntity temp = super.findById(id);
            cacheMap.put(id,temp);
            return temp;
        }

    }

}