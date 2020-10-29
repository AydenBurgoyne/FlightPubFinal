package me.groupFour.data;
//stores the seat value and will status, will be sent to the page and the matrix built from that.
public class SeatEntity {
    String SeatID;
    String SeatStatus; //only values Reserved, Booked, Available.

    public SeatEntity() {
    }

    public String getSeatID() {
        return SeatID;
    }

    public void setSeatID(String seatID) {
        SeatID = seatID;
    }

    public String getSeatStatus() {
        return SeatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        SeatStatus = seatStatus;
    }
}
