package me.groupFour.dao;

import me.groupFour.data.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class TicketEntityDAO extends AbstractEntityDAO<TicketEntity, String> implements ITicketEntityDAO {
    HashMap<String,TicketEntity> cacheMap = new HashMap<>();
    @Autowired
    public TicketEntityDAO(EntityManager em) {
        super(TicketEntity.class, em);
    }
    public TicketEntity findById(String id) {
        if(cacheMap.containsKey(id)){
            return cacheMap.get(id);
        }
        else{
            TicketEntity temp = super.findById(id);
            cacheMap.put(id,temp);
            return temp;
        }

    }

}