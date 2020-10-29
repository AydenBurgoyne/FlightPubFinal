package me.groupFour.data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//This Entity is used to store journeys that have been booked.
@Entity
@Table(name = "bookedjourney")
public class BookedJourneyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer JourneyID;
    @OneToMany(mappedBy = "JourneyID",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<LegEntity> LegsOfJourney = new LinkedList<>();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="BookingID")
    private BookingEntity booking;
    @Transient
    private BigDecimal price; //probs just caculate this in the constructor by adding all the prices from the legs.

    public int getJourneyID() {
        return JourneyID;
    }

    public void setJourneyID(Integer journeyID) {
        JourneyID = journeyID;
    }

    public List<LegEntity> getLegsOfJourney() {
        return LegsOfJourney;
    }

    public void setLegsOfJourney(List<LegEntity> legsOfJourney) {
        LegsOfJourney = legsOfJourney;
        Iterator<LegEntity> it = LegsOfJourney.iterator();
        while(it.hasNext()){
            LegEntity temp = it.next();
            temp.setJourneyID(this);
        }
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public void addLeg(LegEntity newLeg) {
        LegsOfJourney.add(newLeg);
    }
    public void setLegTimestamp(Timestamp timestamp){
        Iterator<LegEntity> it = LegsOfJourney.iterator();
        while(it.hasNext()){
            LegEntity temp = it.next();
            temp.setBookedTime(timestamp);
        }

    }
    public void calculatingPrice() { //caclulating price is used to add the price for all the legs in the journey
        Iterator<LegEntity> it = LegsOfJourney.iterator();
        price = new BigDecimal(0);
        while (it.hasNext()) {
            price = price.add(it.next().getPriceEntity().getPrice());
        }
    }

    public BookedJourneyEntity(Integer journeyID, List<LegEntity> legsOfJourney) {
        JourneyID = journeyID;
        LegsOfJourney = legsOfJourney;
        calculatingPrice();
    }

    public BookedJourneyEntity() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
