package me.groupFour.data;

import javax.persistence.*;
import java.util.List;

//TicketType
//maps to the tickettype table in the database using hiberate
@Entity
@Table(name = "tickettype")
public class TicketEntity {
    @Id
    private String TicketCode;
    private String Name;
    private Integer Transferable;
    private Integer Refundable;
    private Integer Exchangeable;
    private Integer FrequentFlyerPoints;
    @OneToMany(mappedBy = "tickettype")
    private List<LegEntity> LegEntityList;
    @OneToMany(mappedBy = "TicketCode")
    private List<PriceEntity> ticketlist;

    public TicketEntity() {
    }

    public TicketEntity(String ticketCode, String name, Integer transferable, Integer refundable, Integer exchangeable, Integer frequentFlyerPoints, List<LegEntity> legEntityList, List<PriceEntity> ticketlist) {
        this.TicketCode = ticketCode;
        this.Name = name;
        this.Transferable = transferable;
        this.Refundable = refundable;
        this.Exchangeable = exchangeable;
        this.FrequentFlyerPoints = frequentFlyerPoints;
        this.LegEntityList = legEntityList;
        this.ticketlist = ticketlist;
    }


    public String getTicketCode() {
        return TicketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.TicketCode = ticketCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Integer getTransferable() {
        return Transferable;
    }

    public void setTransferable(Integer transferable) {
        this.Transferable = transferable;
    }

    public Integer getRefundable() {
        return Refundable;
    }

    public void setRefundable(Integer refundable) {
        this.Refundable = refundable;
    }

    public Integer getExchangeable() {
        return Exchangeable;
    }

    public void setExchangeable(Integer exchangeable) {
        this.Exchangeable = exchangeable;
    }

    public Integer getFrequentFlyerPoints() {
        return FrequentFlyerPoints;
    }

    public void setFrequentFlyerPoints(Integer frequentFlyerPoints) {
        this.FrequentFlyerPoints = frequentFlyerPoints;
    }

    public List<LegEntity> getLegEntityList() {
        return LegEntityList;
    }

    public void setLegEntityList(List<LegEntity> legEntityList) {
        LegEntityList = legEntityList;
    }
}
