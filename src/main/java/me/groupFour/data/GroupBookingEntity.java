package me.groupFour.data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@Entity
//Flight entity is used to map the flight table from the database using hibernate.
@Table(name = "GroupBooking")
public class GroupBookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer GroupID;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="AccountID")
    private AccountEntity AccountID;
    @OneToOne()
    @JoinColumn(name="destinationStart")
    private DestinationEntity destinationStart;
    @OneToOne()
    @JoinColumn(name="destinationEnd")
    private DestinationEntity destinationEnd;
    @OneToMany(mappedBy = "GroupID",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<BookingEntity>  BookingsList;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="HotelID")
    private HotelBookingEntity HotelBooking;

    public AccountEntity getAccountID() {
        return AccountID;
    }

    public void setAccountID(AccountEntity accountID) {
        AccountID = accountID;
    }

    public HotelBookingEntity getHotelBooking() {
        return HotelBooking;
    }

    public void setHotelBooking(HotelBookingEntity hotelBooking) {
        HotelBooking = hotelBooking;
    }

    public GroupBookingEntity() {
        BookingsList = new LinkedList<>();
    }

    public Integer getGroupID() {
        return GroupID;
    }

    public void setGroupID(Integer groupID) {
        GroupID = groupID;
    }

    public AccountEntity getAccountLeader() {
        return AccountID;
    }

    public void setAccountLeader(AccountEntity accountLeader) {
        AccountID = accountLeader;
    }

    public DestinationEntity getDestinationStart() {
        return destinationStart;
    }

    public void setDestinationStart(DestinationEntity destinationStart) {
        this.destinationStart = destinationStart;
    }

    public DestinationEntity getDestinationEnd() {
        return destinationEnd;
    }

    public void setDestinationEnd(DestinationEntity destinationEnd) {
        this.destinationEnd = destinationEnd;
    }

    public List<BookingEntity> getBookingsList() {
        return BookingsList;
    }

    public void setBookingsList(List<BookingEntity> bookingsList) {
        BookingsList = bookingsList;
    }
    public void setBooking(BookingEntity bookingEntity){
        BookingsList.add(bookingEntity);
    }
}