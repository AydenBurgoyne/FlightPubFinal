#
SET FOREIGN_KEY_CHECKS=0;
DROP SCHEMA FlightPub;
CREATE SCHEMA FlightPub;
USE FlightPub;

CREATE TABLE `Country` (
  `countryCode2` char(2) NOT NULL,
  `countryCode3` char(3) NOT NULL,
  `countryName` varchar(80) NOT NULL DEFAULT '',
  `alternateName1` varchar(80) NOT NULL DEFAULT '',
  `alternateName2` varchar(80) NOT NULL DEFAULT '',
  `motherCountryCode3` char(3) NOT NULL DEFAULT '',
  `motherCountryComment` varchar(80) NOT NULL DEFAULT '',
  PRIMARY KEY (`countryCode3`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Airlines` (
  `AirlineCode` char(2) NOT NULL,
  `AirlineName` varchar(30) NOT NULL,
  `CountryCode3` char(3) NOT NULL,
  `AirlineRating` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`AirlineCode`),
  KEY `AirlinesCountryCode3_FK` (`CountryCode3`),
  CONSTRAINT `AirlinesCountryCode3_FK` FOREIGN KEY (`CountryCode3`) REFERENCES `Country` (`countryCode3`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `PlaneType` (
  `PlaneCode` varchar(20) NOT NULL,
  `Details` varchar(50) NOT NULL,
  `NumFirstClass` int(11) NOT NULL,
  `NumBusiness` int(11) NOT NULL,
  `NumPremiumEconomy` int(11) NOT NULL,
  `Economy` int(11) NOT NULL,
  PRIMARY KEY (`PlaneCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Destinations` (
  `DestinationCode` char(3) NOT NULL,
  `Airport` varchar(30) NOT NULL,
  `CountryCode3` char(3) NOT NULL,
  `AirportRating` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`DestinationCode`),
  KEY `DestinationCountryCode_FK` (`CountryCode3`),
  CONSTRAINT `DestinationCountryCode_FK` FOREIGN KEY (`CountryCode3`) REFERENCES `Country` (`countryCode3`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `TicketClass` (
  `ClassCode` char(3) NOT NULL,
  `Details` varchar(20) NOT NULL,
  PRIMARY KEY (`ClassCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `TicketType` (
  `TicketCode` char(1) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Transferable` bit(1) NOT NULL,
  `Refundable` bit(1) NOT NULL,
  `Exchangeable` bit(1) NOT NULL,
  `FrequentFlyerPoints` bit(1) NOT NULL,
  PRIMARY KEY (`TicketCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Availability` (
  `AirlineCode` char(2) NOT NULL,
  `FlightNumber` varchar(6) NOT NULL,
  `DepartureTime` datetime NOT NULL,
  `ClassCode` char(3) NOT NULL,
  `TicketCode` char(1) NOT NULL,
  `NumberAvailableSeatsLeg1` int(11) NOT NULL,
  `NumberAvailableSeatsLeg2` int(11) DEFAULT NULL,
  PRIMARY KEY (`AirlineCode`,`FlightNumber`,`DepartureTime`,`ClassCode`,`TicketCode`),
  KEY `AvailabilityClassCode_FK` (`ClassCode`),
  KEY `AvailabilityTicketCode_FK` (`TicketCode`),
  CONSTRAINT `AvailabilityTicketCode_FK` FOREIGN KEY (`TicketCode`) REFERENCES `TicketType` (`TicketCode`),
  CONSTRAINT `AvailabilityAirlineCode_FK` FOREIGN KEY (`AirlineCode`) REFERENCES `Airlines` (`AirlineCode`),
  CONSTRAINT `AvailabilityClassCode_FK` FOREIGN KEY (`ClassCode`) REFERENCES `TicketClass` (`ClassCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Distances` (
  `DestinationCode1` char(3) NOT NULL,
  `DestinationCode2` char(3) NOT NULL,
  `DistancesInKms` int(11) NOT NULL,
  PRIMARY KEY (`DestinationCode1`,`DestinationCode2`),
  KEY `DestinationCode2_FK` (`DestinationCode2`),
  CONSTRAINT `DestinationCode2_FK` FOREIGN KEY (`DestinationCode2`) REFERENCES `Destinations` (`DestinationCode`),
  CONSTRAINT `DestinationCode1_FK` FOREIGN KEY (`DestinationCode1`) REFERENCES `Destinations` (`DestinationCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Price` (
	priceID int auto_increment,
  `AirlineCode` char(2) NOT NULL,
  `FlightNumber` varchar(6) NOT NULL,
  `ClassCode` char(3) NOT NULL,
  `TicketCode` char(1) NOT NULL,
  `StartDate` datetime NOT NULL,
  `EndDate` datetime NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  `PriceLeg1` decimal(10,2) DEFAULT NULL,
  `PriceLeg2` decimal(10,2) DEFAULT NULL,
  primary key(priceID),
  unique(`AirlineCode`,`FlightNumber`,`ClassCode`,`TicketCode`,`StartDate`),
  KEY `PriceClassCode_FK` (`ClassCode`),
  KEY `PriceTicketCode_FK` (`TicketCode`),
  CONSTRAINT `PriceAirlineCode_FK` FOREIGN KEY (`AirlineCode`) REFERENCES `Airlines` (`AirlineCode`),
  CONSTRAINT `PriceClassCode_FK` FOREIGN KEY (`ClassCode`) REFERENCES `TicketClass` (`ClassCode`),
  CONSTRAINT `PriceTicketCode_FK` FOREIGN KEY (`TicketCode`) REFERENCES `TicketType` (`TicketCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Flights` (
   FlightID int auto_increment,
  `FlightNumber` varchar(6) NOT NULL,
  `AirlineCode` char(2) NOT NULL,
  `AirlineRating` int(1) NOT NULL default 0,
  `DepartureCode` char(3) NOT NULL,
  `StopOverCode` char(3) DEFAULT NULL,
  `DestinationCode` char(3) NOT NULL,
   `AirportRating` int(1) NOT NULL DEFAULT 0,
  `DepartureTime` datetime NOT NULL,
  `ArrivalTimeStopOver` datetime DEFAULT NULL,
  `DepartureTimeStopOver` datetime DEFAULT NULL,
  `ArrivalTime` datetime NOT NULL,
  `PlaneCode` varchar(20) NOT NULL,
  `Duration` int(11) NOT NULL,
  `DurationSecondLeg` int(11) DEFAULT NULL,
  primary key(FlightID),
  unique(`AirlineCode`,`FlightNumber`,`DepartureTime`, `AirlineRating`, `AirportRating`),
  KEY `FlightsDepartureCode_FK` (`DepartureCode`),
  KEY `FlightsStopOverCode_FK` (`StopOverCode`),
  KEY `FlightsDestinationCode_FK` (`DestinationCode`),
  KEY `FlightsPlaneCode_FK` (`PlaneCode`),
  CONSTRAINT `FlightsPlaneCode_FK` FOREIGN KEY (`PlaneCode`) REFERENCES `PlaneType` (`PlaneCode`),
  CONSTRAINT `FlightsAirlineCode_FK` FOREIGN KEY (`AirlineCode`) REFERENCES `Airlines` (`AirlineCode`),
  CONSTRAINT `FlightsDepartureCode_FK` FOREIGN KEY (`DepartureCode`) REFERENCES `Destinations` (`DestinationCode`),
  CONSTRAINT `FlightsDestinationCode_FK` FOREIGN KEY (`DestinationCode`) REFERENCES `Destinations` (`DestinationCode`),
  CONSTRAINT `FlightsStopOverCode_FK` FOREIGN KEY (`StopOverCode`) REFERENCES `Destinations` (`DestinationCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*account table*/
create table `account` (
    `email` varchar(50) not null default '',
    `first_name` varchar(50) not null,
    `last_name` varchar(50) not null,
    `address` varchar(50) not null,
    `DateOfBirth` Date not null,
    `aType` BIT not null,
    `pword` varchar(1000) not null,
    `status` varchar(50) not null default 'active',
    primary key(`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


# destination table is made via hibernate mappings but is till used in this join table with accounts to attach destinations to the wishlist

# CREATE TABLE `account_wishlist` (
#     `email` varchar(50) not null,
#     `DestinationCode` char(3) NOT NULL,
#     PRIMARY KEY (`email`,`DestinationCode`),
#
#     KEY `DestinationCode` (`DestinationCode`),
#     constraint foreign key(AccountID) references account(email);
#
#     CONSTRAINT `email` FOREIGN KEY (`email`) REFERENCES `account` (`email`)
#     CONSTRAINT `DestinationCode` FOREIGN KEY (`DestinationCode`) REFERENCES `destinations` (`DestinationCode`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*booked journey*/
Create Table bookedjourney(
    JourneyID int auto_increment,
    BookingID int,
    primary key(JourneyID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
Create Table Leg(
    LegID Int auto_increment,
    flightID int not null,
    seat varchar(3) not null,
    Status varchar(10),
    JourneyID int not null,
    bookedTime datetime null,
    TicketCode char(1) not null,
    ClassCode char(3) not null,
    primary key(LegID),

    constraint foreign key(TicketCode) references TicketType(TicketCode)
    on update cascade
    on delete cascade,
    constraint foreign key(ClassCode) references ticketclass(ClassCode)
    on update cascade
    on delete cascade,
    constraint foreign key(JourneyID) references bookedjourney(JourneyID)
    on update cascade
    on delete cascade,
	constraint foreign key(flightID) references `Flights`(FlightID)
    on update cascade
    on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Price to Leg relation*/
create Table Price_Leg_Relation(
    LegID int not null,
    PriceID int not null,
    constraint foreign key(PriceID) references price(priceID)
    on update cascade
    on delete cascade,
    constraint foreign key(LegID) references Leg(LegID)
    on update cascade
    on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*booking table*/
Create table booking(
    Status varchar(10) default 'good',
	BookingID int auto_increment,
	hotelID int,
    AccountID varchar(50),/*foreign key*/
    JourneyID int,/*foreign key*/
    GroupID int,
    constraint foreign key(AccountID) references account(email)
    on delete cascade
    on update cascade,
    constraint foreign key(JourneyID) references bookedjourney(JourneyID)
    on delete no action
    on update cascade,

    primary key(BookingID)
)auto_increment = 10,
ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
# Create Table wish_relation(
# 	accountIDfk varchar (50) not null,
#     CountryCode3fk char(3) not null,
#     constraint foreign key(accountIDfk) references account(email)
#     on delete cascade
#     on update  cascade,
# 	constraint foreign key(CountryCode3fk) references Country(countryCode3)
#     on delete cascade
#     on update cascade
# )ENGINE=InnoDB DEFAULT CHARSET=latin1;

Create Table wishlist_destinations(
    accountIDfk varchar (50) not null,
    CountryCode3fk char(3) not null,
    constraint foreign key(accountIDfk) references account(email)
      on delete cascade
      on update  cascade,
    constraint foreign key(CountryCode3fk) references Country(countryCode3)
      on delete cascade
      on update cascade
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

Create table GroupBooking(
    GroupID int auto_increment,
    AccountID varchar(50),
    HotelID int,
    constraint foreign key(HotelID) references hotel(HotelID)
    on delete cascade
    on update cascade,
    destinationStart char(3),
    destinationEnd char(3),
    constraint foreign key(AccountID) references account(email)
    on delete cascade
    on update cascade,
    constraint foreign key(destinationStart) references destinations(DestinationCode)
    on delete cascade
    on update cascade,
    constraint foreign key(destinationEnd) references destinations(DestinationCode)
    on delete cascade
    on update cascade,
    primary key(GroupID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;



Create Table Hotel(
    HotelID int auto_increment,
    RoomsAvailable int,
    type bit,
    price decimal(6,2),
    location char(3),
    constraint foreign key(location) references destinations(DestinationCode)
    on delete cascade
    on update cascade,
    primary key(HotelID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


Create Table HotelBooking(
    HotelBookingID int auto_increment,
    People int,
    startDate Date,
    endDate Date,
    HotelID int,
    constraint foreign key(HotelID) references Hotel(HotelID)
    on delete cascade
    on update cascade,
    primary key(HotelBookingID)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE planeSeatingArrangements (
    id int,
    `planeType` VARCHAR(15),
    `FC_arrangement` VARCHAR(10),
    `FC_row_count` INT,
    `FC_row_range` VARCHAR(6),
    `FC_seat_count` INT,
    `FC_num_excluded` INT,
    `FC_seats_excluded` VARCHAR(8),
    `BC_arrangement` VARCHAR(10),
    `BC_row_count` INT,
    `BC_row_range` VARCHAR(7),
    `BC_seat_count` INT,
    `BC_num_excluded` INT,
    `BC_seats_excluded` VARCHAR(19),
    `PE_arrangement` VARCHAR(10),
    `PE_row_count` INT,
    `PE_row_range` VARCHAR(7),
    `PE_seat_count` INT,
    `PE_num_excluded` INT,
    `PE_seats_excluded` VARCHAR(19),
    `EE_arrangement` VARCHAR(10),
    `EE_row_count` INT,
    `EE_row_range` VARCHAR(7),
    `EE_seat_count` INT,
    `EE_num_excluded` INT,
    `EE_seats_excluded` VARCHAR(27),
    primary key(id),
    constraint foreign key(planeType) references planetype(PlaneCode)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
create table HolidayPackage(
    PackageID int auto_increment,
    JourneyTo int,
    constraint foreign key(JourneyTo) references bookedjourney(JourneyID)
    on update cascade
    on delete cascade,
    JourneyReturn int,
    constraint foreign key(JourneyReturn) references bookedjourney(JourneyID)
    on update cascade
    on delete cascade,
    HotelBookingID int,
    constraint foreign key(HotelBookingID) references hotel(HotelID)
    on update cascade
    on delete cascade,
    Primary Key(PackageID)
);
create table TravelAgents(
    TravelAgentCode int auto_increment,
    Name varchar(50),
    Description varchar(100),
    Primary Key (TravelAgentCode)
);
create table AdminTable(
    adminID int auto_increment,
    email varchar(50),
    constraint foreign key(email) references account(email)
        on update cascade
        on delete cascade,
    Description varchar(100),
    primary key (adminID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create table TravelAgentWorkerTable(
    TravelAgentID int auto_increment,
    email varchar(50),
    constraint foreign key (email) references Account(email)
        on update cascade
        on delete cascade,
    TravelAgentCode int,
    constraint foreign key(TravelAgentCode) references TravelAgents(TravelAgentCode)
        on update cascade
        on delete cascade,
    primary key (TravelAgentID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
create table RestrictedAirports
(
    RestrictionID   int auto_increment,
    DestinationCode char(3),
    constraint foreign key (DestinationCode) references destinations (DestinationCode)
        on update cascade
        on delete cascade,
    timeFrom  datetime,
    timeTo    datetime,
    Primary key (RestrictionID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
create table SponsoredAirlines(
    SponsoredAirlineID int auto_increment,
    AirlineCode char(2),
    constraint foreign key(AirlineCode) references Airlines(AirlineCode)
        on update cascade
        on delete cascade,
    PRIMARY KEY (SponsoredAirlineID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
create table SponsoredTravelAgent(
    SponsoredTravelAgentID int auto_increment,
    TravelAgentCode int,
    constraint foreign key(TravelAgentCode) references TravelAgents(TravelAgentCode)
        on update cascade
        on delete cascade,
    primary key (SponsoredTravelAgentID)
);
alter table booking
    add constraint fk_GroupID
        foreign key (GroupID) references GroupBooking(GroupID)
            on update cascade
            on delete cascade,
    add constraint foreign key (hotelID) references HotelBooking(HotelBookingID)
    on delete no action
    on update cascade;
SHOW ENGINE INNODB STATUS;


Create Table reviews (
    ReviewID int auto_increment,
    `Review` varchar (1000) not null,
    rate int,
    `Type` varchar (10),
    primary key(ReviewID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


alter table bookedjourney
add constraint fk_booking
foreign key (BookingID) references booking(BookingID)
on update cascade
on delete cascade;

SHOW ENGINE INNODB STATUS;



