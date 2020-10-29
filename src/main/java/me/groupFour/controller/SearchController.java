package me.groupFour.controller;

import me.groupFour.dao.*;
import me.groupFour.data.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;


@Controller
@RequestMapping("search")
//Search Controllers deals with processing the info provided by the search query entity
public class SearchController {
    //Initialising DAOs
    private final ITicketEntityDAO ticketEntityDAO;
    private final IClassEntityDAO classEntityDAO;
    private final IFlightEntityDAO flightEntityDAO;
    private final IDestinationEntityDAO destinationEntityDAO;
    private final IPriceEntityDAO priceEntityDAO;
    private final FlightGraphed graph;
    private SearchQueryEntity searchQuery;
    private final RestrictedAirportDAO restrictedAirportDAO;
    private final SponsoredAirlinesDAO sponsoredAirlinesDAO;

    //Constructor
    public SearchController(SponsoredAirlinesDAO sponsoredAirlinesDAO, RestrictedAirportDAO restrictedAirportDAO,FlightGraphed graph, ITicketEntityDAO iTicketEntityDAO, IClassEntityDAO iClassEntityDAO, IFlightEntityDAO iFlightEntityDAO, IDestinationEntityDAO iDestinationEntityDAO, IPriceEntityDAO iPriceEntityDAO) {
        this.ticketEntityDAO = iTicketEntityDAO;
        this.graph = graph;
        this.sponsoredAirlinesDAO = sponsoredAirlinesDAO;
        this.classEntityDAO = iClassEntityDAO;
        this.destinationEntityDAO = iDestinationEntityDAO;
        this.flightEntityDAO = iFlightEntityDAO;
        this.priceEntityDAO = iPriceEntityDAO;
        this.restrictedAirportDAO = restrictedAirportDAO;
    }


    //Search page to find flights using JSON
    @GetMapping("searchJSON")
    public ModelAndView searchJSON() {
        return new ModelAndView("Flight/searchJSON");
    }

    @ResponseBody
    @GetMapping(value = "searchFlightInJSON", produces = "text/plain")
    public String searchFlightInJSON(@Valid @ModelAttribute("SearchQueryEntity") SearchQueryEntity sq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            JSONObject errorJSON = new JSONObject();
            errorJSON.put("error", "Validation error");

            return errorJSON.toString();
        }

        LinkedList<JourneyEntity> journeyList = findJourneyList(sq);

        return generateJourneysJSON(journeyList).toString();
    }


    //You have to register an InitBinder in your controller to let spring convert your date string to java.util.Date object and set it in command object.

    @GetMapping
    public ModelAndView search(@Valid @ModelAttribute("SearchQueryEntity") SearchQueryEntity sq, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/");
        }

        LinkedList<JourneyEntity> journeyList = findJourneyList(sq);

        ModelAndView view = new ModelAndView("SearchResults");
        view.addObject("journeyList", journeyList);


        //call json method
        //add the json object to the view

        //Convert list of destination entity to a JSON format
        // System.out.println(generateJourneysJSON(journeyList));

        return view;

//        DestinationEntity FromDest = destinationEntityDAO.searchByAirportNameSingle(sq.getFromDest());
//        DestinationEntity ToDest = destinationEntityDAO.searchByAirportNameSingle(sq.getToDest());
//        LinkedList<DestinationEntity> DList = new LinkedList<>();
//        LinkedList<JourneyEntity> finalList = new LinkedList<>();
//        LinkedList<LinkedList<DestinationEntity>> list;
//
//
//        FlightMap localFlightMap = new FlightMap(destinationEntityDAO, flightEntityDAO, graph);
//
//        if (!sq.getViaDest().equals("")) {
//            DestinationEntity viaDest = destinationEntityDAO.searchByAirportNameSingle(sq.getViaDest());
//            list = localFlightMap.viaPath(FromDest.getDestinationCode(), viaDest.getDestinationCode(), ToDest.getDestinationCode());
//        } else {
//            list = localFlightMap.getPaths(FromDest.getDestinationCode(), ToDest.getDestinationCode());
//        }
//        LinkedList<LinkedList<DestinationEntity>> copyl = (LinkedList<LinkedList<DestinationEntity>>) list.clone();
//
//        Iterator<LinkedList<DestinationEntity>> it = copyl.iterator();
//        while (it.hasNext()) {
//            LinkedList<DestinationEntity> temp = (LinkedList<DestinationEntity>) it.next().clone();
//
//            generateJourneys(temp, sq, null, finalList, null, null, (temp.size() - 1)); //destamount needs to be the amount of legs it needs
//        }

    }

    private LinkedList<JourneyEntity> findJourneyList(SearchQueryEntity sq) {

        //Initializing Linked list for processing
        LinkedList<DestinationEntity> DList = new LinkedList<>();
        LinkedList<JourneyEntity> finalList = new LinkedList<>();
        LinkedList<LinkedList<DestinationEntity>> list;

        //Setup Destination entities
        DestinationEntity FromDest = destinationEntityDAO.searchByAirportNameSingle(sq.getFromDest());
        DestinationEntity ToDest = destinationEntityDAO.searchByAirportNameSingle(sq.getToDest());

        //Generate a flightMap to search for journeys
        FlightMap localFlightMap = new FlightMap(destinationEntityDAO, flightEntityDAO, graph);

        //Create a via destination entity if needed
        if (!sq.getViaDest().equals("")) {
            DestinationEntity viaDest = destinationEntityDAO.searchByAirportNameSingle(sq.getViaDest());
            list = localFlightMap.viaPath(FromDest.getDestinationCode(), viaDest.getDestinationCode(), ToDest.getDestinationCode());
        } else {
            list = localFlightMap.getPaths(FromDest.getDestinationCode(), ToDest.getDestinationCode());
        }

        //Clone list no I dear why ??????????????????????????????
        LinkedList<LinkedList<DestinationEntity>> copyl = (LinkedList<LinkedList<DestinationEntity>>) list.clone();

        for (LinkedList<DestinationEntity> it : copyl) {
            LinkedList<DestinationEntity> temp = (LinkedList<DestinationEntity>) it.clone();

            //destamount needs to be the amount of legs it needs
            generateJourneys(temp, sq, null, finalList, null, null, (temp.size() - 1));
        }

        return finalList;
    }
    public LinkedList<JourneyEntity> findSponsoredJourney(SearchQueryEntity sq){
        LinkedList<JourneyEntity> list = findJourneyList(sq);
        LinkedList<JourneyEntity> SponsoredList = new LinkedList<>();
        Iterator<JourneyEntity> it = list.listIterator();
        while(it.hasNext()){
            JourneyEntity temp = it.next();
            if(temp.isSponsoredAirline()){
                SponsoredList.add(temp);
            }
        }
        return SponsoredList;
    }


    //Generate Journeys aka stitches together different flights forming legs that make up journeys.
    public void generateJourneys(LinkedList<DestinationEntity> destinationList, SearchQueryEntity search, JourneyEntity journey, LinkedList<JourneyEntity> finalList, DestinationEntity lastDest, Timestamp lastArrival, int destAmount) {
        DestinationEntity dest1; //Destination 1
        DestinationEntity dest2;//Destination 2
        Timestamp RangeStart; //Start of time searching range
        Timestamp RangeEnd; //End of time searching range
        JourneyEntity tempJourney; //declaring tempJourney entity
        int max_wait = search.getMaxLayoverTime(); //int for max wait time at any segment of the journey.
        //Checks that the Destination list is empty and that the journey has the amount of legs expected

        if (journey != null && destinationList.isEmpty() && journey.getLegsOfJourney().size() == destAmount) {
            journey.calculatingPrice(); //calculates the total price for the journey based on the individual prices for the legs.
            finalList.add(journey); // adds the journey to the journey linkedlist.
            return;
        }
        //checks if the journey is null, meaning that it's the first time the generateJourney method has been called.
        if (journey == null) {
            tempJourney = new JourneyEntity();
            dest1 = destinationList.poll();//starting destination
            dest2 = destinationList.poll();//End destination for this particular flight
            RangeStart = search.getFromTimeSQL(); //Start of time range for searching for flights on this particular leg.
            RangeEnd = search.getToTimeSQL(); //End of range for searching for flights for this particular leg.
        }
        //if the journey is not null e.g not the first time that the generateJourney method has been run.
        else {
            tempJourney = journey;
            dest1 = lastDest; //uses the destination from the last leg as the deparature.
            dest2 = destinationList.poll(); //Grabs the next destination from the destination list to use as the destination.
            RangeStart = new Timestamp(lastArrival.getTime() + (3600000 * 2)); //adds offset for transfering flights
            RangeEnd = new Timestamp(RangeStart.getTime() + (3600000 * max_wait)); //adds the max wait to the buffered range start
        }
        //Using flightDAO to search for flights returns as List of flight entities.
        List<FlightEntity> FlightResultList = flightEntityDAO.searchFlight(RangeStart, RangeEnd, dest1, dest2);//assuming searchFlight uses (Timestamp start, Timestamp end, DestinationEntity dest1, DestinationEntity dest2
        Iterator<FlightEntity> it = FlightResultList.listIterator(); //gets list iterator.
        while (it.hasNext()) { //while loop to go through the flight list.
            //cloning existing journeys and destination list to prevent Destinations from accidentally being affected.
            JourneyEntity tempJ = new JourneyEntity();
            LinkedList<DestinationEntity> list = destinationList;
            try {
                list = (LinkedList<DestinationEntity>) destinationList.clone();
                tempJ = tempJourney.Clone();
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
            FlightEntity temp = it.next(); //grabs the flight from the list.
            TicketEntity ticketcode = ticketEntityDAO.findById(search.getTicketCode()); //converts the ticketcode string stored in the search entity to TicketEntity by grabing from DB
            ClassEntity classcode = classEntityDAO.findById(search.getClassCode()); //grabs the ClassEntity from the db based on the string stored in the search entity.
            LegEntity leg = new LegEntity(); //creates a new leg for a journey.
            leg.setFlightID(temp); //sets the flight for the leg.
            leg.setTicketClass(classcode); //sets the classcode for the leg
            leg.setTicketType(ticketcode); //sets the ticketcode for the leg.
            //leg.setPriceEn(PDAO.findById(16686));
            //Check if airport is restricted
            Timestamp Leaving = temp.getDepartureTime();
            Timestamp Arrival;
            if(lastArrival!=null){
                 Arrival = new Timestamp(lastArrival.getTime());

            }
            else{
                 Arrival= Leaving;
            }
           List<RestrictedAirportsEntity> RestrictedList = restrictedAirportDAO.getRestrictions(Arrival,Leaving,temp.getDepartureCode());
           if(RestrictedList.size()!=0){
               leg.setRestrictedAirport(true);
           }
           //Checking if the stopOverAirport is restricted;
            DestinationEntity stopOver = temp.getStopOverCode();
           if(stopOver!=null){
               Arrival = temp.getArrivalTimeStopOver();
               Leaving = temp.getDepartureTimeStopOver();
               RestrictedList = restrictedAirportDAO.getRestrictions(Arrival,Leaving,temp.getStopOverCode());
               if(RestrictedList.size()!=0){
                   leg.setRestrictedAirport(true);
               }
           }
           //Checking if leg contains a sponsoredAirline:
            List<SponsoredAirlines> sponsoredList = sponsoredAirlinesDAO.getSponsored(temp.getAirlineCode());
            if(!sponsoredList.isEmpty()){
                leg.setSponsoredAirline(true);
                tempJ.setSponsoredAirline(true); //sets in journey that the journey contains a leg with a sponsored airline.
            }
           //checking if the end airport is restricted This is kinda like a double check and is only essential when it's the last leg but doesn't hurt doing it on all of them.
           Arrival = temp.getArrivalTime();
           RestrictedList = restrictedAirportDAO.getRestrictions(Arrival,Arrival,temp.getDestinationCode());
           if(RestrictedList.size()!=0){
               leg.setRestrictedAirport(true);
           }
           if(leg.getRestrictedAirport()){
               tempJ.setRestricted(true);
           }

            leg.setPriceEntity(priceEntityDAO.findPrice(tempJ.getSearchTime(), temp, classcode, ticketcode, temp.getAirlineCode())); //sets the price by searching for the price entity in the database.
            tempJ.addLeg(leg); //adds the leg to the journey
            generateJourneys(list, search, tempJ, finalList, dest2, temp.getArrivalTime(), destAmount); //keeps going through until no more destinations are left. //calls the generate journey method recursively to continue generating the journeys.

        }
        //if the journey is not null and the destiantion list is empty and the destination amount is the size expected.
        if (journey != null && destinationList.isEmpty() && journey.getLegsOfJourney().size() == destAmount) {
            journey.calculatingPrice();
            finalList.add(journey);
        }

    }


    //Method returns json with City names that are similar to those inputted by the user. This enables the autocompete destination fields.
    //todo
    // ensure that any city that is only a stopover location is not shown, as they cannot be flown from
    @GetMapping
    @RequestMapping(value = "AirportAutoComplete")
    @ResponseBody
    public List<String> airportAutoComplete(@RequestParam(value = "term", defaultValue = "") String term) {
        List<DestinationEntity> list = destinationEntityDAO.searchByAirportName(term);
        List<String> AirportNameList = new ArrayList<>();
        for (DestinationEntity destinationEntity : list) {
            AirportNameList.add(destinationEntity.getAirport());
//            if (!destinationEntity.getFlightDestinationList().isEmpty() && !destinationEntity.getFlightDepartureList().isEmpty()) {
//
//            }
        }
        //todo
        // this currently returns the following exception, upon inspection i do not understand what is making is
        // removing the if condition currently is the only known fix for the error
        // org.apache.catalina.core.StandardWrapperValve.invoke Servlet.service() for servlet [SpringDispatcher] in
        // context with path [] threw exception [Request processing failed; nested exception is
        // org.hibernate.exception.GenericJDBCException:
        // Could not read entity state from ResultSet : EntityKey[me.groupFour.data.FlightEntity#10910]]
        // with root cause
        //	java.lang.IllegalArgumentException: HOUR_OF_DAY: 2 -> 3
        return AirportNameList;
    }


   /* @RequestMapping(value = "searchAutoCompleteDeparture")
    @ResponseBody
    public Set<String> searchAutoCompleteDeparture(@RequestParam(value = "term", defaultValue = "") String term) {

        //todo change it so only departure locations and shown from the database

        //todo
        // currently the database query is returning 10 items that are the same, and currently casting that/changing ti to a set only
        // makes it contain one type, since the previous list only has one item

        List<FlightEntity> flightEntityList = flightEntityDAO.searchAirportVia(term);
        List<String> airportNameList = new LinkedList<>();

        for (FlightEntity flightEntity : flightEntityList) {
            airportNameList.add(flightEntity.getDepartureCode().getAirport());
        }


        return new LinkedHashSet<>(airportNameList);
    }

    @GetMapping
    @RequestMapping(value = "searchAutoCompleteDestination")
    @ResponseBody
    public List<String> searchAutoCompleteDestination(@RequestParam(value = "term", defaultValue = "") String term) {

        //todo needs to be changed so the database only returns destinations

        List<FlightEntity> flightEntityList = flightEntityDAO.searchAirportDestination(term);
        List<String> airportNameList = new ArrayList<>();

        for (FlightEntity flightEntity : flightEntityList) {
            airportNameList.add(flightEntity.getDestinationCode().getAirport());
        }

        return airportNameList;
    }

    @GetMapping
    @RequestMapping(value = "searchAutoCompleteVia")
    @ResponseBody
    public Set<String> searchAutoCompleteVia(@RequestParam(value = "term", defaultValue = "") String term) {

        List<FlightEntity> flightEntityList = flightEntityDAO.searchAirportVia(term);
        //List<FlightEntity> flightEntityList = flightEntityDAO.searchAirportVia(term);
        Set<String> airportNameList = new HashSet<>();

        //todo
        // needs to be a list of all of the different airports, not just solely the ones that are stopovers

        for (FlightEntity flightEntity : flightEntityList) {
            airportNameList.add(flightEntity.getStopOverCode().getAirport());
        }

        return airportNameList;
    }*/

    public JSONObject generateJourneysJSON(LinkedList<JourneyEntity> inputList) {

        //generates a JSON object of 3 layers

        //STRUCTURE::

        /*
        parentObject{
            [
            journeyList[
                legList[ leg [departure:, stopover:, destination:], leg2[]... ]
                "price",
                "departureTime",
                "arrivalTime"
                "totalDuration",
                "jetLagIndicator"
                "flightURL"
                "review"
            ]
        }
        */

//        int sponsoredCount = 0;
        //journeys: [journey, thing in the legs[a,b,c]], []

        //function to get the list and turn it into a JSON object
        JSONObject journeyObject = new JSONObject();
        JSONObject parentObject = new JSONObject();
        JSONArray legList;
        JSONArray journeyList = new JSONArray();

        for (JourneyEntity journeyEntity : inputList
        ) {

            //get the sublist of all of the leg entities
            LinkedList<LegEntity> legEntitySubList = new LinkedList<>(journeyEntity.getLegsOfJourney());

            //clear the legList for each loop/recursion
            legList = new JSONArray();
            journeyObject = new JSONObject();
            for (LegEntity legEntity : legEntitySubList
            ) {

                //creates a new destination JSON object, clears the object from the previous call
                JSONObject destinationEntityObject = new JSONObject();

                //if the stopover code is not null, add the stopover code to the json object
                if (legEntity.getFlightID().getStopOverCode() != null) {
                    destinationEntityObject.put("stopover", legEntity.getFlightID().getStopOverCode().getAirport());
                }

                //add the destination/departure codes to the destination object
                destinationEntityObject.put("departure", legEntity.getFlightID().getDepartureCode().getAirport());
                destinationEntityObject.put("destination", legEntity.getFlightID().getDestinationCode().getAirport());

                //append the destinationEntityObject to the legList
                legList.put(destinationEntityObject);
            }

            //set all of the variables needed to be computer by from within the JSPs
            journeyObject.put("legs", legList);
            journeyObject.put("price", journeyEntity.getPrice().doubleValue());
            journeyObject.put("departureTime", journeyEntity.getDepartureTime());
            journeyObject.put("arrivalTime", journeyEntity.getArrivalTime());
            journeyObject.put("totalDuration", journeyEntity.getTotalDuration());
            journeyObject.put("jetLagIndicator", journeyEntity.calculateJetLag());
            journeyObject.put("flightURL", journeyEntity.getFlightIDs());
            journeyObject.put("review", journeyEntity.getRandom());
            journeyObject.put("restricted",journeyEntity.getRestricted());

//            if(sponsoredCount < 3){
//                journeyObject.put("isSponsored",true);
//            }else{
//                journeyObject.put("isSponsored",journeyEntity.isSponsoredAirline());
//            }
//
//            sponsoredCount++;

            journeyObject.put("isSponsored",journeyEntity.isSponsoredAirline());

            //put the journeyObject onto the journeyList, for it to hold all of the journey objects
            journeyList.put(journeyObject);
        }

        //put the entire journeyList sublist into a parent list, into journeyList object
        parentObject.put("journeyList", journeyList);

        return parentObject;
    }
}
