package me.groupFour.dao;

import me.groupFour.data.DestinationEntity;
import me.groupFour.data.FlightEntity;

import java.sql.Timestamp;
import java.util.List;

public interface IFlightEntityDAO extends IEntityDAO<FlightEntity, Integer> {
    List<FlightEntity> getAllDistinct();
    List<FlightEntity> searchFlight(Timestamp rangeStart, Timestamp rangeEnd, DestinationEntity dest1, DestinationEntity dest2);
}
