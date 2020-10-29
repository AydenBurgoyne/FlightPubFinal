package me.groupFour.data;

import javax.persistence.*;

@Entity()
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    private int ReviewID;
    private String Review;
    private int rate;
    private String Type;

    public ReviewEntity (){}

    public int getReviewID() {
        return ReviewID;
    }

    public void setReviewID(int reviewID) {
        ReviewID = reviewID;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
