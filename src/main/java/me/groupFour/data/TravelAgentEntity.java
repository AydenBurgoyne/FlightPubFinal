package me.groupFour.data;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="travelagents")
public class TravelAgentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int TravelAgentCode;
    String name;
    String Description;
    @OneToMany(mappedBy = "TravelAgentCode")
    List<TravelAgentWorker> list;

}
