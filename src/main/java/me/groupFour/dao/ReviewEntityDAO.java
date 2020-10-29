package me.groupFour.dao;

import me.groupFour.data.ReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class ReviewEntityDAO extends AbstractEntityDAO<ReviewEntity, Integer> implements IReviewEntityDAO {
    @Autowired
    public ReviewEntityDAO(EntityManager em) {
        super(ReviewEntity.class, em);
    }

    //Create a list of all reviews where the rating matches
    public List<ReviewEntity> searchReviews(int rating, String type) {
        return em.createQuery("select p from ReviewEntity p where p.rate = :rating and p.Type = :type" , ReviewEntity.class)
                .setParameter("rating", rating)
                .setParameter("type", type)
                .getResultList();
    }
}