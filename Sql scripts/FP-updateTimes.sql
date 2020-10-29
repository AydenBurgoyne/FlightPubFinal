USE FlightPub;

SET SQL_SAFE_UPDATES = 0;

UPDATE price
set
    EndDate=DATE_ADD(EndDate, interval 23 hour),
    EndDate=DATE_ADD(EndDate, interval 59 minute),
    EndDate=DATE_ADD(EndDate, interval 59 second ),
    EndDate=DATE_ADD(EndDate, interval 59 second_microsecond)
where true;

SET SQL_SAFE_UPDATES = 1;