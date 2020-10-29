package me.groupFour.data;

import javax.persistence.*;

//Booking Entity is used to store bookings from the user. It associates a user account with the journey.
@Entity
@Table(name = "booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BookingID;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "AccountID")
    private AccountEntity AccountID;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "JourneyID")
    private BookedJourneyEntity JourneyID;
    private String Status;
    @ManyToOne()
    @JoinColumn(name="GroupID")
    private GroupBookingEntity GroupID;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="HotelID")
    private HotelBookingEntity HotelID;
    public GroupBookingEntity getGroupBookingID() {
        return GroupID;
    }

    public void setGroupBookingID(GroupBookingEntity groupBookingID) {
        GroupID = groupBookingID;
    }

    public BookingEntity() {
    }

    public HotelBookingEntity getHotelID() {
        return HotelID;
    }

    public void setHotelID(HotelBookingEntity hotelID) {
        HotelID = hotelID;
    }

    public BookingEntity(int bookingID, AccountEntity accountID, BookedJourneyEntity journeyID) {
        BookingID = bookingID;
        AccountID = accountID;
        JourneyID = journeyID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public HotelBookingEntity getHotelBooking() {
        return HotelID;
    }

    public void setHotelBooking(HotelBookingEntity hotelBooking) {
        HotelID = hotelBooking;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(Integer bookingID) {
        BookingID = bookingID;
    }

    public AccountEntity getAccountID() {
        return AccountID;
    }

    public void setAccountID(AccountEntity accountID) {
        AccountID = accountID;
    }

    public BookedJourneyEntity getJourneyID() {
        return JourneyID;
    }

    public void setJourneyID(BookedJourneyEntity journeyID) {
        JourneyID = journeyID;
    }
}

//@ManyToMany(targetEntity=FlightEntity.class,cascade =CascadeType.PERSIST, mappedBy="BookingEntityList")
    /*
@Query(
        value = "SELECT * FROM price x where x.AirlineCode =:AirlineCode and x.FlightNumber =:FlightNumber and x.ClassCode = :classCode and x.TicketCode =:ticketcode and CURDATE() between x.StartDate and x.EndDate",
        nativeQuery = true)
List<PriceEntity> findPrice(@Param("AirlineCode") String AirlineCode,("FlightNumber") String FlightNumber)
*/