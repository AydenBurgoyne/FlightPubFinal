package me.groupFour.dao;

import me.groupFour.data.ReviewEntity;

import java.util.List;

public interface IReviewEntityDAO extends IEntityDAO<ReviewEntity, Integer> {
    List<ReviewEntity> searchReviews(int rate, String type);
}
