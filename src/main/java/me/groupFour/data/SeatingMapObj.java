package me.groupFour.data;

import me.groupFour.dao.LegEntityDAO;

import java.util.List;

public class SeatingMapObj {
    //variables
    private TicketEntity tickettype;
    private ClassEntity classcode;
    private PriceEntity price;
    private List<FlightEntity> proposedFlights;
    private LegEntityDAO legEntityDAO;




    //getters and setters

    public List<FlightEntity> getProposedFlights() {
        return proposedFlights;
    }

    public void setProposedFlights(List<FlightEntity> proposedFlights) {
        this.proposedFlights = proposedFlights;
    }

    public LegEntityDAO getLegEntityDAO() {
        return legEntityDAO;
    }

    public void setLegEntityDAO(LegEntityDAO legEntityDAO) {
        this.legEntityDAO = legEntityDAO;
    }

    public TicketEntity getTickettype() {
        return tickettype;
    }

    public void setTickettype(TicketEntity tickettype) {
        this.tickettype = tickettype;
    }

    public ClassEntity getClasscode() {
        return classcode;
    }

    public void setClasscode(ClassEntity classcode) {
        this.classcode = classcode;
    }

    public PriceEntity getPrice() {
        return price;
    }

    public void setPrice(PriceEntity price) {
        this.price = price;
    }

    public FlightEntity getFlight() {
        return proposedFlights.get(0);
    }

}
