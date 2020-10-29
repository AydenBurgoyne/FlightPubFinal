package me.groupFour.dao;

import me.groupFour.data.HotelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.util.List;

public class HotelEntityDAO extends AbstractEntityDAO<HotelEntity, Integer> implements IHotelEntityDAO {

    @Autowired
    public HotelEntityDAO(EntityManager em) {
        super(HotelEntity.class, em);
    }

    @Override
    public List<HotelEntity> findAllByLocation(String input) {
        return em.createQuery("Select p FROM HotelEntity p where p.location.DestinationCode = :inputString", HotelEntity.class)
                .setParameter("inputString", input)
                .getResultList();
    }
}