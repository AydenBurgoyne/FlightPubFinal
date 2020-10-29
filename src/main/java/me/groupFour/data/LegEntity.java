package me.groupFour.data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

//Leg Entity is the objeect that stores the flight for each part of the journey. It is stored in the database when a booking is made.
@Entity()
@Table(name = "Leg")
public class LegEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer LegID;
    @ManyToOne()
    @JoinColumn(name = "flightID")
    private FlightEntity flightID;
    private String seat;
    @ManyToOne()
    @JoinColumn(name = "JourneyID")
    private BookedJourneyEntity JourneyID;
    private Timestamp bookedTime; //time that the leg is booked.
    @JoinColumn(name = "TicketCode")
    @ManyToOne()
    private TicketEntity tickettype;
    @JoinColumn(name = "ClassCode")
    @ManyToOne()
    private ClassEntity ticketclass;
    @Column(name="Status")
    private String Status;
    @Transient
    private Boolean RestrictedAirport = false; //defaults that the airport isn't restricted.
    @Transient
    private boolean SponsoredAirline = false; //is assumed that the leg does not contain a sponsored airline unless shown otherwise.

    public boolean isSponsoredAirline() {
        return SponsoredAirline;
    }

    public void setSponsoredAirline(boolean sponsoredAirline) {
        SponsoredAirline = sponsoredAirline;
    }

    public Boolean getRestrictedAirport() {
        return RestrictedAirport;
    }

    public void setRestrictedAirport(Boolean restrictedAirport) {
        RestrictedAirport = restrictedAirport;
    }

    public TicketEntity getTickettype() {
        return tickettype;
    }

    public void setTickettype(TicketEntity tickettype) {
        this.tickettype = tickettype;
    }

    public ClassEntity getTicketclass() {
        return ticketclass;
    }

    public void setTicketclass(ClassEntity ticketclass) {
        this.ticketclass = ticketclass;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    //might be redundant
    @ManyToMany()
    @JoinTable(
            name = "Price_Leg_Relation",
            joinColumns = @JoinColumn(name = "LegID"),
            inverseJoinColumns = @JoinColumn(name = "flightID")
    )
    private List<PriceEntity> price;

    @Transient
    private PriceEntity priceEntity;


    public LegEntity(
            Integer legID, FlightEntity flightID, String seat, Timestamp bookedTime,
            TicketEntity ticketType, ClassEntity ticketClass, List<PriceEntity> price) {
        this.LegID = legID;
        this.flightID = flightID;
        this.seat = seat;
        this.bookedTime = bookedTime;
        this.tickettype = ticketType;
        this.ticketclass = ticketClass;
        this.price = price;
        setPriceEntity();

    }

    public LegEntity() {

    }

    //get review rating for a leg of journey
    public double legReview() {
        double rating = flightID.getAirlineCode().getAirlineRating();
        return rating;
    }

    //get airline name for leg of journey
    public String legAirline(LegEntity leg) {
        AirlineEntity code = flightID.getAirlineCode();
        String name = code.getAirlineName();
        return name;
    }

    public void setPriceEntity() {
        for (PriceEntity temp : price) {
            Timestamp start = temp.getStartDate();
            Timestamp end = temp.getEndDate();
            if (temp.getClassCode().getClassCode().equals(ticketclass.getClassCode()) && temp.getTicketCode().getTicketCode().equals(tickettype.getTicketCode()) && bookedTime.after(start) && bookedTime.before(end)) {


                priceEntity = temp;
                return;
            }
        }
    }

    public Integer getLegID() {
        return this.LegID;
    }

    public void setLegID(Integer legID) {
        this.LegID = legID;
    }

    public FlightEntity getFlightID() {
        return flightID;
    }

    public void setFlightID(FlightEntity flightID) {
        this.flightID = flightID;
    }

    public String getSeat() {
        return this.seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BookedJourneyEntity getJourneyID() {
        return this.JourneyID;
    }

    public void setJourneyID(BookedJourneyEntity journeyID) {
        this.JourneyID = journeyID;
    }

    public Timestamp getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(Timestamp bookedTime) {
        this.bookedTime = bookedTime;
    }

    public TicketEntity getTicketType() {
        return tickettype;
    }

    public void setTicketType(TicketEntity ticketType) {
        this.tickettype = ticketType;
    }

    public ClassEntity getTicketClass() {
        return ticketclass;
    }

    public void setTicketClass(ClassEntity ticketClass) {
        this.ticketclass = ticketClass;
    }

    public List<PriceEntity> getPrice() {
        return price;
    }

    public void setPrice(List<PriceEntity> price) {
        this.price = price;
    }

    public PriceEntity getPriceEntity() {
        return priceEntity;
    }

    public void setPriceEntity(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
    }

}
