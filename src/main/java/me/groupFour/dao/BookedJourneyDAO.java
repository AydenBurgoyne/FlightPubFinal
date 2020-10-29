package me.groupFour.dao;

import me.groupFour.data.BookedJourneyEntity;
import me.groupFour.data.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.HashMap;

public class BookedJourneyDAO extends AbstractEntityDAO<BookedJourneyEntity, Integer>  {

    @Autowired
    public BookedJourneyDAO(EntityManager em) {
        super(BookedJourneyEntity.class, em);
    }


}