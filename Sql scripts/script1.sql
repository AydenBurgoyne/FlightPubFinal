/*create database localdb;*/

use localdb;

create table Account (

    id INT NOT NULL auto_increment,
    first_name VARCHAR(20) default NULL,
    last_name  VARCHAR(20) default NULL,
    address     varchar(100)  default NULL,
    DateOfBirth date default null,
    aType varchar(100) default null,
    PRIMARY KEY (id)

);
create table Country(
CountryID varchar(20),
id Integer,
constraint foreign key (id) references account(id)
on delete cascade
on update no action,
constraint foreign key(CountryID) references countryinfo(CountryName)
on delete cascade
on update cascade
);

create table CountryInfo(
CountryName varchar(20) primary key
);

insert into account (first_name,last_name,address,DateOfBirth,aType) Values('Big','Boy','32 Big Ave','1954-04-10','Business');


insert into countryInfo values("Austrlalia"),("China"),("USA");
insert into Country values('USA',1);

