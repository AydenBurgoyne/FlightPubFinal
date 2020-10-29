package me.groupFour.data;

import javax.persistence.*;
@Entity
@Table(name="travelagentworkertable")
public class TravelAgentWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int TravelAgentID;
    @ManyToOne()
    @JoinColumn(name="email")
    AccountEntity email;
    @ManyToOne()
    @JoinColumn(name = "TravelAgentCode")
    TravelAgentEntity TravelAgentCode;

}
