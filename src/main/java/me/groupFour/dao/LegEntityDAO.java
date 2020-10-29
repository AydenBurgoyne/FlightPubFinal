package me.groupFour.dao;

import me.groupFour.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class LegEntityDAO extends AbstractEntityDAO<LegEntity, Integer> implements ILegEntityDAO {
    @Autowired
    public LegEntityDAO(EntityManager em) {
        super(LegEntity.class, em);
    }
    public List<LegEntity> getLegs(ClassEntity classCode, FlightEntity flight){
            return em.createQuery("Select p FROM LegEntity p WHERE p.ticketclass =:classCode AND p.flightID=:flightID", LegEntity.class)
                    .setParameter("classCode", classCode)
                    .setParameter("flightID",flight)
                    .getResultList();
        }
}
