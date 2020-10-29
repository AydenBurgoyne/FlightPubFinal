package me.groupFour.dao;


import me.groupFour.data.DestinationEntity;

import java.util.List;

public interface IDestinationEntityDAO extends IEntityDAO<DestinationEntity, String> {

    DestinationEntity searchByAirportNameSingle(String fromDest);

    List<DestinationEntity> searchByAirportName(String term);
}
