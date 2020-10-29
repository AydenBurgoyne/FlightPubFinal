package me.groupFour.dao;

import me.groupFour.data.AccountEntity;
import me.groupFour.data.BookingEntity;
import me.groupFour.data.GroupBookingEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class GroupBookingEntityDAO extends AbstractEntityDAO<GroupBookingEntity, Integer> {
    @Autowired
    public GroupBookingEntityDAO(EntityManager em) {
        super(GroupBookingEntity.class, em);
    }

    public List<GroupBookingEntity> searchByAccount(AccountEntity accountID) {
        return em.createQuery("Select p FROM GroupBookingEntity p WHERE p.AccountID =:email", GroupBookingEntity.class)
                .setParameter("email", accountID)
                .getResultList();

    }
}
