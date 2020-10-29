package me.groupFour.dao;

import me.groupFour.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class RestrictedAirportDAO extends AbstractEntityDAO<RestrictedAirportsEntity, Integer>{
    @Autowired
    public RestrictedAirportDAO(EntityManager em) {
        super(RestrictedAirportsEntity.class, em);
    }
    public List<RestrictedAirportsEntity> getRestrictions(Timestamp Arrival, Timestamp Leaving,DestinationEntity destinationCode){
        List<RestrictedAirportsEntity> list = em.createQuery("Select p FROM RestrictedAirportsEntity p WHERE p.DestinationCode=:destinationCode AND ( (:Arrival Between p.timefrom and p.timeto ) or (:Leaving Between p.timefrom and p.timeto))", RestrictedAirportsEntity.class)
                    .setParameter("Arrival", Arrival)
                    .setParameter("Leaving",Leaving)
                    .setParameter("destinationCode",destinationCode)
                    .getResultList();
        if(list==null){
            list = new LinkedList<>();
        }
        return list;
        }
}
