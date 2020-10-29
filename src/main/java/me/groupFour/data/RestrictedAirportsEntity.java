package me.groupFour.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="restrictedairports")
public class RestrictedAirportsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer RestrictionID;
    @ManyToOne()
    @JoinColumn(name="DestinationCode")
    private DestinationEntity DestinationCode;
    Timestamp timefrom;
    @Transient
    @NotNull
    String Airport;
    Timestamp timeto;
    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date TimeFrom;
    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date TimeTo;

    public Integer getRestrictionID() {
        return RestrictionID;
    }

    public void setRestrictionID(Integer restrictionID) {
        RestrictionID = restrictionID;
    }

    public DestinationEntity getDestinationCode() {
        return DestinationCode;
    }

    public void setDestinationCode(DestinationEntity destinationCode) {
        DestinationCode = destinationCode;
    }

    public Timestamp getTimefromTimestamp() {
        return timefrom;
    }

    public void setTimefromTimestamp(Timestamp timefrom) {
        this.timefrom = timefrom;
    }

    public Timestamp getTimetoTimestamp() {
        return timeto;
    }

    public void setTimetoTimestamp(Timestamp timeto) {
        this.timeto = timeto;
    }

    public String getAirport() {
        return Airport;
    }

    public Date getTimeFrom() {
        return TimeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        TimeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return TimeTo;
    }

    public void setTimeTo(Date timeTo) {
        TimeTo = timeTo;
    }

    public void setAirport(String airport) {
        Airport = airport;
    }
}
