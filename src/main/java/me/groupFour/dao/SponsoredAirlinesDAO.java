package me.groupFour.dao;

import me.groupFour.data.AirlineEntity;
import me.groupFour.data.DestinationEntity;
import me.groupFour.data.RestrictedAirportsEntity;
import me.groupFour.data.SponsoredAirlines;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class SponsoredAirlinesDAO extends AbstractEntityDAO<SponsoredAirlines, Integer>{
    @Autowired
    public SponsoredAirlinesDAO(EntityManager em) {
        super(SponsoredAirlines.class, em);
    }
    public List<SponsoredAirlines> getSponsored(AirlineEntity airlineCode){
        List<SponsoredAirlines> list = em.createQuery("Select p FROM SponsoredAirlines p where p.airlinecode=:SponsoredAirline", SponsoredAirlines.class)
                    .setParameter("SponsoredAirline",airlineCode)
                    .getResultList();
        if(list==null){
            list = new LinkedList<>();
        }
        return list;
        }
}
