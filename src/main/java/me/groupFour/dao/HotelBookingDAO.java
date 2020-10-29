package me.groupFour.dao;

import me.groupFour.data.HotelBookingEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class HotelBookingDAO extends AbstractEntityDAO<HotelBookingEntity, Integer> implements IHotelBookingDAO {

    @Autowired
    public HotelBookingDAO(EntityManager em) {
        super(HotelBookingEntity.class, em);
    }

}