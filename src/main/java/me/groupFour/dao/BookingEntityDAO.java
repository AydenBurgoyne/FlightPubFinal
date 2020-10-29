package me.groupFour.dao;

import me.groupFour.data.AccountEntity;
import me.groupFour.data.BookingEntity;
import me.groupFour.data.DestinationEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class BookingEntityDAO extends AbstractEntityDAO<BookingEntity, Integer> implements IBookingEntityDAO {
    @Autowired
    public BookingEntityDAO(EntityManager em) {
        super(BookingEntity.class, em);
    }

    public List<BookingEntity> searchByAccount(AccountEntity accountID) {
        return em.createQuery("Select p FROM BookingEntity p WHERE p.AccountID =:email", BookingEntity.class)
                .setParameter("email", accountID)
                .getResultList();

    }
}
