package me.groupFour.data;

import javax.persistence.*;

@Entity
@Table(name="sponsoredairlines")
public class SponsoredAirlines {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer SponsoredAirlineID;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="AirlineCode")        
    AirlineEntity airlinecode;

}
