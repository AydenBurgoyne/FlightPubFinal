package me.groupFour.data;

import javax.persistence.*;
import javax.swing.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="HotelBooking")
public class HotelBookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer HotelBookingID;

    @Basic
    private Integer People;

    @Basic
    private Date startDate, endDate;
    //toDate Date,
    
    @ManyToOne()
    @JoinColumn(name="HotelID")
    private HotelEntity hotelEntityID;

    @OneToOne(mappedBy = "HotelID")
        BookingEntity booking;

    @OneToMany(mappedBy = "HotelBooking")
    private List<GroupBookingEntity> groupBookings = new LinkedList<>();

    public List<GroupBookingEntity> getGroupBookings() {
        return groupBookings;
    }

    public void setGroupBookings(List<GroupBookingEntity> groupBookings) {
        this.groupBookings = groupBookings;
    }

    public HotelBookingEntity(){

    }
    public void addGroupBooking(GroupBookingEntity g){
        groupBookings.add(g);
    }

    public HotelBookingEntity(Integer roomNumber, Date startDate, Date endDate, HotelEntity hotelEntityID) {
        this.People = roomNumber;
        this.startDate = startDate;
        this.hotelEntityID = hotelEntityID;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public HotelBookingEntity setStartDate(Date fromDate) {
        this.startDate = fromDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public HotelBookingEntity setEndDate(Date toDate) {
        this.endDate = toDate;
        return this;
    }

    public Integer getHotelBookingID() {
        return HotelBookingID;
    }

    public HotelBookingEntity setHotelBookingID(Integer hotelBookingID) {
        HotelBookingID = hotelBookingID;
        return this;
    }

    public Integer getPeople() {
        return People;
    }

    public HotelBookingEntity setPeople(Integer people) {
        this.People = people;
        return this;
    }

    public HotelEntity getHotelEntityID() {
        return hotelEntityID;
    }

    public HotelBookingEntity setHotelEntityID(HotelEntity hotelEntityID) {
        this.hotelEntityID = hotelEntityID;
        return this;
    }
}
