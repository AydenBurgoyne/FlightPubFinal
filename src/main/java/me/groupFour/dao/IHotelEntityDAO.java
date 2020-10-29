package me.groupFour.dao;

import me.groupFour.data.HotelEntity;

import java.util.LinkedList;
import java.util.List;

public interface IHotelEntityDAO extends IEntityDAO<HotelEntity, Integer> {

    List<HotelEntity > findAllByLocation(String location);
}
