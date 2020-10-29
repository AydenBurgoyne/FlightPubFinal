package me.groupFour.data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer HotelID;

    //assume that a single room can house/fit as many people as required for the booking

    @Basic
    private Integer RoomsAvailable;

    @Basic
    private boolean type;

    @Basic
    private double price;

    @ManyToOne()
    @JoinColumn(name = "location")
    private DestinationEntity location;

    @OneToMany(mappedBy = "HotelBookingID")
    private List<HotelBookingEntity> bookingEntityList;


    public HotelEntity() {

    }

    public Integer getHotelID() {
        return HotelID;
    }

    public boolean isType() {
        return type;
    }

    public HotelEntity setType(boolean type) {
        this.type = type;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public HotelEntity setPrice(double price) {
        this.price = price;
        return this;
    }

    public HotelEntity setHotelID(Integer hotelID) {
        this.HotelID = hotelID;
        return this;
    }

    public Integer getRoomsAvailable() {
        return RoomsAvailable;
    }

    public HotelEntity setRoomsAvailable(Integer roomsAvailable) {
        this.RoomsAvailable = roomsAvailable;
        return this;
    }

    public DestinationEntity getLocation() {
        return location;
    }

    public HotelEntity setLocation(DestinationEntity location) {
        this.location = location;
        return this;
    }

    public List<HotelBookingEntity> getBookingEntityList() {
        return bookingEntityList;
    }

    public HotelEntity setBookingEntityList(List<HotelBookingEntity> bookingEntityList) {
        this.bookingEntityList = bookingEntityList;
        return this;
    }
}
