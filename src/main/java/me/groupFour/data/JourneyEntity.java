package me.groupFour.data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

//Journey entity is the equivelent of Booked Journey but differs in the fact it's not stored in the database. Once a customer books a journey then the information stored in this entity will be transfered over to a booked journey bean to be stored in the database.
//non-persistent journey entity
public class JourneyEntity implements Cloneable {
    private Timestamp searchTime;
    private LinkedList<LegEntity> legsOfJourney = new LinkedList<LegEntity>();
    private BigDecimal price; //probs just caculate this in the constructor by adding all the prices from the legs.
    private Integer totalDuration = 0;
    private Boolean Restricted = false; //defaults to false;
    private boolean SponsoredAirline = false;

    public Boolean getRestricted() {
        return Restricted;
    }

    public void setRestricted(Boolean restricted) {
        Restricted = restricted;
    }

    public JourneyEntity Clone() throws CloneNotSupportedException {
        JourneyEntity temp = (JourneyEntity) super.clone();
        temp.setLegsOfJourney((LinkedList<LegEntity>) temp.getLegsOfJourney().clone());
        return temp;
    }

    public boolean isSponsoredAirline() {
        return SponsoredAirline;
    }

    public void setSponsoredAirline(boolean sponsoredAirline) {
        SponsoredAirline = sponsoredAirline;
    }

    public void addLeg(LegEntity newLeg) {
        legsOfJourney.add(newLeg);
    }

    public LinkedList<LegEntity> getLegsOfJourney() {
        return legsOfJourney;
    }

    //calculating price is used to calculate the total price by adding all the prices from the legs up.
    public void calculatingPrice() {
        Iterator<LegEntity> it = legsOfJourney.iterator();
        price = new BigDecimal(0);
        while (it.hasNext()) {
            price = price.add(it.next().getPriceEntity().getPrice());
        }
    }

    //calculates the total duration by adding all the leg durations from the legs.
    public void sumDuration() {
        int sum = 0;
        for (LegEntity leg : legsOfJourney) {
            sum += leg.getFlightID().getDuration();
        }
        totalDuration = sum;
    }

    //calculates jet lag based on total duration
    public Integer calculateJetLag() {
        sumDuration();

        int jetlag = 1;
        double totalD = this.getTotalDuration();
        if (totalD >= 3 && totalD <= 12) {
            jetlag = 2;
        } else if (totalD > 12) {
            jetlag = 3;
        }

        return jetlag;
    }

    //calculates the total duration by adding all the leg durations from the legs.
    public double getRandom() {

        double a = (Math.random() * 5) + 0;
        return Math.round(a * 100.0) / 100.0;
    }

    //calculates average review based on all airlines in the legs of journey
    public double calculateReview() {
        double count1 = 0;
        double count2 = 0;
        for (LegEntity leg : legsOfJourney) {
            FlightEntity id = leg.getFlightID();
            int rating = id.getAirlineCode().getAirlineRating();
            count1 = count1 + rating;
            count2++;
        }
        for (LegEntity leg : legsOfJourney) {
            DestinationEntity id = leg.getFlightID().getDestinationCode();
            int rating1 = id.getAirportRating();
            count1 = count1 + rating1;
            count2++;
        }
        return count1/count2;
    }

    public void setLegsOfJourney(LinkedList<LegEntity> legsOfJourney) {
        this.legsOfJourney = legsOfJourney;
    }


    public JourneyEntity(LinkedList<LegEntity> legsOfJourney) {
        this.legsOfJourney = legsOfJourney;
        calculatingPrice();
    }

    public JourneyEntity() {
        Date date = new Date();
        searchTime = new Timestamp(date.getTime());
    }

    public Timestamp getSearchTime() {
        return searchTime;
    }

    public void generateDuration() {
        for (LegEntity l : legsOfJourney) {
            // add time.
        }
    }

    public void setSearchTime(Timestamp searchTime) {
        this.searchTime = searchTime;
    }

    public BigDecimal getPrice() {
        return price;
    }


    //Makes a string to input into the url for booking.
    public String getFlightIDs() {
        StringBuilder output = new StringBuilder();

        Iterator<LegEntity> legsIterator = legsOfJourney.iterator();

        while (legsIterator.hasNext()) {
            output.append(legsIterator.next().getFlightID().getFlightID());
            if (legsIterator.hasNext()) {
                output.append(",");
            }
        }

        output.append("&ticketType=");
        output.append(legsOfJourney.getLast().getTicketType().getTicketCode());
        output.append("&classCode=");
        output.append(legsOfJourney.getLast().getTicketClass().getClassCode());

        return output.toString();
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getDepartureTime() {
        return legsOfJourney.getFirst().getFlightID().getDepartureTime();
    }


    public Timestamp getArrivalTime() {
        return legsOfJourney.getLast().getFlightID().getArrivalTime();
    }


    @Override
    public String toString() {
        return "JourneyEntity{" +
                "legsOfJourney=" + legsOfJourney +
                ", price=" + price +
                '}';
    }


    public Double getTotalDuration() {
        if (totalDuration == 0) {
            sumDuration();
        }
        double td = totalDuration;
        td /= 60;
        return Math.round(td * 100.0) / 100.0;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }
}
