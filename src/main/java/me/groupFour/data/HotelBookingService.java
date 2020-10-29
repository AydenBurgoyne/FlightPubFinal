package me.groupFour.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;


public class HotelBookingService {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate, endDate;
    private int people;
    private String location;

    public HotelBookingService(){

    }

    public HotelBookingService(Date startDate, Date endDate, int people, String location) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.people = people;
        this.location = location;
    }


    public Date getStartDate() {
        return startDate;
    }

    public HotelBookingService setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public HotelBookingService setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public int getPeople() {
        return people;
    }

    public HotelBookingService setPeople(int people) {
        this.people = people;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public HotelBookingService setLocation(String location) {
        this.location = location;
        return this;
    }


}
