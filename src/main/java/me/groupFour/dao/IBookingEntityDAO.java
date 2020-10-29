package me.groupFour.dao;

import me.groupFour.data.AccountEntity;
import me.groupFour.data.BookingEntity;

import java.util.List;

public interface IBookingEntityDAO extends IEntityDAO<BookingEntity, Integer> {
     abstract List<BookingEntity> searchByAccount(AccountEntity accountID);
}