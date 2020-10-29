package me.groupFour.dao;

import me.groupFour.data.*;

import java.sql.Timestamp;

public interface IPriceEntityDAO extends IEntityDAO<PriceEntity, Integer> {

    PriceEntity findPrice(Timestamp searchTime, FlightEntity temp, ClassEntity classcode, TicketEntity ticketcode, AirlineEntity airlineCode);
}
