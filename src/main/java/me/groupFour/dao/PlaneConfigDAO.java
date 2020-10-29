package me.groupFour.dao;

import me.groupFour.data.PlaneSeatingArrangementsEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class PlaneConfigDAO extends AbstractEntityDAO<PlaneSeatingArrangementsEntity, String> implements IPlaneConfigDAO {
    @Autowired
    public PlaneConfigDAO(EntityManager em) {
        super(PlaneSeatingArrangementsEntity.class, em);
    }
}
