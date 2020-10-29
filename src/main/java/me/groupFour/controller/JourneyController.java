package me.groupFour.controller;

import me.groupFour.dao.*;
import me.groupFour.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller

@SessionAttributes({
        "currentJourney", "proposedFlights", "ticketType",
        "classCode", "timestamp", "price",
        "Users","isLast","isFirst",
        "bookedJourney", "booking", "activeUser",
        "reserved", "bookedJourneys", "GroupBooking",
        "flightReviews", "airportReviews", "lastFlight","HotelBookingNew"
})

@RequestMapping("/Journey")
public class JourneyController {
    private final IDestinationEntityDAO destinationEntityDAO;
    private final ITicketEntityDAO ticketEntityDAO;
    private final IClassEntityDAO classEntityDAO;
    private final IFlightEntityDAO flightEntityDAO;
    private final IPriceEntityDAO priceEntityDAO;
    private final IAccountEntityDAO accountEntityDAO;
    private final IBookingEntityDAO bookingEntityDAO;
    private final IPlaneConfigDAO planeConfigDAO;
    private final BookedJourneyDAO bookingDAO;
    private final LegEntityDAO legDAO;
    private final GroupBookingEntityDAO groupDAO;
    private final IReviewEntityDAO reviewEntityDAO;
    private PlaneSeatSetter planeSetter = new PlaneSeatSetter();


    public JourneyController(
            IPlaneConfigDAO iPlaneConfigDAO, IBookingEntityDAO iBookingEntityDAO,
            IAccountEntityDAO iAccountEntityDAO, IDestinationEntityDAO iDestinationEntityDAO,
            ITicketEntityDAO iTicketEntityDAO, IClassEntityDAO iClassEntityDAO,
            IFlightEntityDAO iFlightEntityDAO, IPriceEntityDAO iPriceEntityDAO, BookedJourneyDAO bookingDAO, LegEntityDAO legDAO, GroupBookingEntityDAO groupDAO, ReviewEntityDAO reviewEntityDAO) {
        this.bookingDAO = bookingDAO;
        this.destinationEntityDAO = iDestinationEntityDAO;
        this.planeConfigDAO = iPlaneConfigDAO;
        this.ticketEntityDAO = iTicketEntityDAO;
        this.accountEntityDAO = iAccountEntityDAO;
        this.classEntityDAO = iClassEntityDAO;
        this.bookingEntityDAO = iBookingEntityDAO;
        this.flightEntityDAO = iFlightEntityDAO;
        this.priceEntityDAO = iPriceEntityDAO;
        this.legDAO = legDAO;
        this.groupDAO = groupDAO;
        this.reviewEntityDAO = reviewEntityDAO;
    }

    @GetMapping("confirmIndividualSeat")
    public ModelAndView confirmIndividualSeat() {
        return new ModelAndView("IndividualSeatBooking");
    }

    @GetMapping("bookPage")
    public ModelAndView search() {
        return new ModelAndView("BookingPage");
    }

    @GetMapping("viewJourney")
    public ModelAndView viewJourney(
            @RequestParam List<Integer> flightIds,
            @RequestParam String ticketType,
            @RequestParam String classCode, ModelMap model) {
        model.remove("bookedFlights"); //Remove any existing booked flights
        List<FlightEntity> flights = new LinkedList<>();

        for (Integer id : flightIds) {
            flights.add(flightEntityDAO.findById(id));
        }
        List<ReviewEntity> flightReviews = new LinkedList<>();
        List<ReviewEntity> airportReviews = new LinkedList<>();
        for (FlightEntity flight : flights) {
            //get list of reviews that match the rating
            List<ReviewEntity> listReview = reviewEntityDAO.searchReviews(flight.getAirlineCode().getAirlineRating(), "Airline");
            List<ReviewEntity> listReview1 = reviewEntityDAO.searchReviews(flight.getDestinationCode().getAirportRating(), "Airport");
            //get random review from list
            Random random = new Random();
            int index = random.nextInt(listReview.size());
            int index1 = random.nextInt(listReview1.size());
            flightReviews.add(listReview.get(index));
            airportReviews.add(listReview1.get(index1));
        }



        Date date = new Date();


        //redundant?
        BookedJourneyEntity journey = new BookedJourneyEntity();
        Timestamp searchTime = new Timestamp(date.getTime());

        model.remove("bookedJourney");
        FlightEntity temp = flights.get(0);



        ModelAndView view = new ModelAndView("JourneyViewPage");
        TicketEntity ticket = ticketEntityDAO.findById(ticketType);
        ClassEntity classType = classEntityDAO.findById(classCode);
        PriceEntity price = priceEntityDAO.findPrice(searchTime, temp, classType, ticket, temp.getAirlineCode());
        String fixedArray = fixingString(flightIds);
        view.addObject("FlightID", fixedArray);
        view.addObject("Flights", flights);
        view.addObject("Dep", flights.get(0));
        view.addObject("Des", flights.get(flights.size() - 1));
        view.addObject("price", price);
        view.addObject("ticket", ticket);
        view.addObject("classType", classType);
        view.addObject("flightReviews", flightReviews);
        view.addObject("airportReviews", airportReviews);
        return view;
    }


    @GetMapping("searchAirport")
    public ModelAndView searchAirport(
            @RequestParam Map<String, String> allParams) {

        ModelAndView view = new ModelAndView("Flight/flight");

        DestinationEntity entity = new DestinationEntity();

        String searchString = allParams.get("search");

        List<DestinationEntity> testSearch = destinationEntityDAO.searchByAirportName(searchString);


        //view.addObject("results",testSearch);
        //Search airport by name using search criteria
        //Pass results to jsp

        return view;
    }
    //individual booking code
    @GetMapping("newIndividualBooking")
    @ResponseBody
    public ModelAndView newIndividualBooking(
            @RequestParam List<Integer> flightIds,
            @RequestParam String ticketType,
            @RequestParam String classCode, ModelMap model) {
        List<FlightEntity> flights = new LinkedList<>();

        for (Integer id : flightIds) {
            flights.add(flightEntityDAO.findById(id));
        }

        //Insert booking details into session
        model.remove("bookedFlights"); //Remove any existing booked flights
        Date date = new Date();
        BookedJourneyEntity journey = new BookedJourneyEntity();

        //if the user has already attempted to book a hotel and not completed the transaction
        //remove
        model.remove("HotelBookingNew");

        boolean isFirst = true;
        boolean isLast = false;
        if(flights.size()==1){
            isLast = true;
            model.put("lastFlight", flights.get(0));
        }
        model.put("isLast", isLast);
        model.put("isFirst", isFirst);

        Timestamp searchTime = new Timestamp(date.getTime());

        model.put("timestamp", searchTime);
        model.put("proposedFlights", flights);   //Insert journey flights
        model.put("classCode", classCode);
        model.put("ticketType", ticketType);
        model.remove("bookedJourney");
        model.put("bookedJourney", journey);

        return new ModelAndView("forward:./SeatSelectionIndividual");
    }
    //group booking code
    @GetMapping("newGroupBooking")
    @ResponseBody
    public ModelAndView newGroupBooking(
            @RequestParam List<Integer> flightIds,
            @RequestParam String ticketType,
            @RequestParam String classCode, ModelMap model) {
        List<FlightEntity> flights = new LinkedList<>();

        for (Integer id : flightIds) {
            flights.add(flightEntityDAO.findById(id));
        }

        //if the user has already attempted to book a hotel and not completed the transaction
        //remove
        model.remove("HotelBookingNew");

        //Insert booking details into session
        model.remove("bookedFlights"); //Remove any existing booked flights
        Date date = new Date();
        boolean isFirst = true;
        boolean isLast = false;
        if(flights.size()==1){
             isLast = true;
              model.put("lastFlight", flights.get(0));

        }
        model.put("isFirst",isFirst);
        model.put("isLast",isLast);

        List<BookedJourneyEntity> journeys = new LinkedList<>();
        Timestamp searchTime = new Timestamp(date.getTime());
        model.put("timestamp", searchTime);
        model.put("proposedFlights", flights);   //Insert journey flights
        model.put("classCode", classCode);
        model.put("ticketType", ticketType);
        model.remove("bookedJourney");
        List<String> users = new LinkedList<>();
        model.put("bookedJourney", journeys);
        model.put("Users",users);

        return new ModelAndView("forward:./SeatSelectionGroup");
    }



    @GetMapping("SeatSelectionGroup")
    @ResponseBody
    public ModelAndView SeatSelectionGroup(
            @SessionAttribute("Users") String[] emails,
            @SessionAttribute("bookedJourney") List<BookedJourneyEntity> journeys, ModelMap model,
            @SessionAttribute("proposedFlights") List<FlightEntity> proposedFlights,
            @SessionAttribute("ticketType") String ticketType,
            @SessionAttribute("classCode") String classCode,
            @SessionAttribute("timestamp") Timestamp timestamp) throws ParseException {
        ModelAndView view = new ModelAndView("GroupBookingPage");
        view.addObject("isLast",model.getAttribute("isLast"));
        view.addObject("isFirst",model.getAttribute("isFirst"));
            if (proposedFlights.isEmpty()) {
                //We need to create accounts for all these people so that they can Might be worth just giving them default values.
                //adding it to the database.
                HotelBookingEntity hotel = (HotelBookingEntity)model.get("HotelBookingNew");
                GroupBookingEntity GroupBooking = new GroupBookingEntity();
                if(hotel!=null) {
                    GroupBooking.setHotelBooking(hotel);
                    hotel.addGroupBooking(GroupBooking);
                }
                //setting account leader
                for (int i = 0; i < emails.length; i++) {
                    AccountEntity tempA = accountEntityDAO.findById(emails[i]);
                    if(i==0&&tempA==null){ //if the accont leader is null.
                        view = new ModelAndView("CreateAccountGuest");
                        model.put("GroupBooking",true);
                        view.addObject("AccountEntity", new AccountEntity());

                        return view;
                    }
                    if (tempA == null) {
                        tempA = new AccountEntity();
                        tempA.setEmail(emails[i]);
                        tempA.setFirstName("Guest");
                        tempA.setLastName("Guest");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                        tempA.setDateOfBirth(myFormat.parse("02 01 1999"));
                        tempA.setPword("Guest1");
                        tempA.setStatus("active");
                        tempA.setAddress("Address street");
                        tempA.setType(true);
                    }
                    BookedJourneyEntity tempJ = journeys.get(i);
                    List<LegEntity> leglist = tempJ.getLegsOfJourney();
                    Iterator<LegEntity> it = leglist.iterator();
                    while(it.hasNext()){
                        it.next().setJourneyID(tempJ);
                    }
                    if(i==0){
                        GroupBooking.setAccountLeader(tempA);
                    }
                    BookingEntity tempB = new BookingEntity();
                    tempB.setAccountID(tempA);
                    tempB.setBookingID(null);
                    tempB.setJourneyID(tempJ);
                    tempJ.setLegTimestamp((Timestamp) model.get("timestamp"));
                    tempJ.setJourneyID(null);
                    tempJ.setBooking(tempB);
                    tempB.setGroupBookingID(GroupBooking);
                    GroupBooking.setBooking(tempB);
                }
                DestinationEntity start = GroupBooking.getBookingsList().get(0).getJourneyID().getLegsOfJourney().get(0).getFlightID().getDepartureCode();
                DestinationEntity end = GroupBooking.getBookingsList().get(0).getJourneyID().getLegsOfJourney().get(GroupBooking.getBookingsList().get(0).getJourneyID().getLegsOfJourney().size()-1).getFlightID().getDestinationCode();
                GroupBooking.setDestinationEnd(end);
                GroupBooking.setDestinationStart(start);
                groupDAO.create(GroupBooking);
                view = new ModelAndView("ConfirmationPageGroup");
                view.addObject("GroupBooking",GroupBooking);
                return view;

            }

        return getSeatingMapObj(model, proposedFlights, ticketType, classCode, timestamp, view);
    }

    private ModelAndView getSeatingMapObj(
            ModelMap model,
            @SessionAttribute("proposedFlights") List<FlightEntity> proposedFlights,
            @SessionAttribute("ticketType") String ticketType,
            @SessionAttribute("classCode") String classCode,
            @SessionAttribute("timestamp") Timestamp timestamp,
            ModelAndView view
    ) {
        SeatingMapObj seatingMapObj = new SeatingMapObj();
        TicketEntity ticket = ticketEntityDAO.findById(ticketType);
        ClassEntity classType = classEntityDAO.findById(classCode);
        seatingMapObj.setProposedFlights(proposedFlights);
        PriceEntity price = priceEntityDAO.findPrice(timestamp, seatingMapObj.getFlight(), classType, ticket,seatingMapObj.getFlight().getAirlineCode());
        seatingMapObj.setClasscode(classType);
        seatingMapObj.setLegEntityDAO(legDAO);
        seatingMapObj.setTickettype(ticket);
        seatingMapObj.setPrice(price);
        view = planeSetter.ConfiguringMap(view,seatingMapObj,model);
        return view;
    }

    @GetMapping("SeatSelectionIndividual")
    @ResponseBody
    public ModelAndView SeatSelectionIndividual(
            @SessionAttribute("bookedJourney") BookedJourneyEntity journey, ModelMap model,
            @SessionAttribute("proposedFlights") List<FlightEntity> proposedFlights,
            @SessionAttribute("ticketType") String ticketType,
            @SessionAttribute("classCode") String classCode,
            @SessionAttribute("timestamp") Timestamp timestamp) {

        ModelAndView view = new ModelAndView("BookingPage");

        view.addObject("isLast",model.getAttribute("isLast"));
        view.addObject("isFirst",model.getAttribute("isFirst"));

        //if the flights are empty, then the booking has been "confirmed"
        // as the user has exhausted all of the previous booking pages
        if (proposedFlights.isEmpty()) {
            BookingEntity booking = new BookingEntity();
            HotelBookingEntity hotel = (HotelBookingEntity)model.get("HotelBookingNew");
            if(hotel!=null) {
                booking.setHotelBooking(hotel);
            }
            //get the booking, and set the journey ID to this journey
            booking.setJourneyID(journey);

            //when the user is logged in, check whether the current session is logged in
            if (!model.containsKey("activeUser")) {
                //if they're not logged in, refer them to the create create guest account page
                model.put("booking", booking);
                view = new ModelAndView("CreateAccountGuest");
                view.addObject("AccountEntity", new AccountEntity());

                return view;
            }

            //if the model contains the active user
            AccountEntity user = (AccountEntity) model.get("activeUser");
            user = accountEntityDAO.findById(user.getEmail());
            booking.setAccountID(user);
            booking.setBookingID(null);
            journey.setLegsOfJourney(journey.getLegsOfJourney());
            journey.setLegTimestamp((Timestamp)model.get("timestamp"));
            journey.setBooking(booking);
            journey.setJourneyID(null);
            bookingDAO.create(journey);

            model.put("booking", booking);
            view = new ModelAndView("ConfirmationPage");
            view.addObject("booking", booking);
            view.addObject("reserved", model.get("reserved"));
            return view;
        }


        boolean isFirst = false;
        boolean isLast = false;
        if(proposedFlights.size()==1){
            isLast = true;
            model.put("lastFlight", proposedFlights.get(0));
        }
        model.put("isLast", isLast);
        model.put("isFirst", isFirst);

        //determines which seat arrangement gets sent
        return getSeatingMapObj(model, proposedFlights, ticketType, classCode, timestamp, view);
    }


    public List<String> SeatArray(List<LegEntity> list) {
        Iterator<LegEntity> it = list.iterator();
        List<String> newList = new LinkedList<String>();
        while (it.hasNext()) {
            newList.add(it.next().getSeat());
        }
        return newList;
    }

    @RequestMapping(value = "BookingHandlingIndividual", method = RequestMethod.GET)
    public ModelAndView bookingHandlingIndividual(
            @RequestParam String hasReserved,
            ModelMap model,
            @RequestParam String seatId,
            @SessionAttribute("proposedFlights") List<FlightEntity> proposedFlights,
            @SessionAttribute("price") PriceEntity price,
            @SessionAttribute("bookedJourney") BookedJourneyEntity journey,
            @SessionAttribute("ticketType") String ticketType,
            @SessionAttribute("classCode") String classCode,
            @SessionAttribute("timestamp") Timestamp timestamp) {

        if (journey == null) {
            journey = new BookedJourneyEntity();
        }



        model.put("reserved", hasReserved);
        TicketEntity ticket = ticketEntityDAO.findById(ticketType);
        ClassEntity classType = classEntityDAO.findById(classCode);
        LegEntity newLeg = new LegEntity();
        newLeg.setFlightID((proposedFlights.remove(0)));

        boolean isFirst = false;
        boolean isLast = false;
        if(proposedFlights.size()==1){
            isLast = true;
            model.put("lastFlight", proposedFlights.get(0));
        }
        model.put("isLast", isLast);
        model.put("isFirst", isFirst);

        newLeg.setTicketType(ticket);
        newLeg.setTicketClass(classType);
        newLeg.setPriceEntity(priceEntityDAO.findPrice(timestamp, newLeg.getFlightID(), classType, ticket, newLeg.getFlightID().getAirlineCode()));
        newLeg.setSeat(seatId);
        journey.addLeg(newLeg);
        model.put("bookedJourney", journey);
        model.put("proposedFlights", proposedFlights);


        return new ModelAndView("forward:./SeatSelectionIndividual");
    }
    @RequestMapping(value = "BookingHandlingGroup", method = RequestMethod.GET)
    public ModelAndView bookingHandlingGroup(
            ModelMap model,
            @RequestParam String[] seatID,
            @RequestParam String[] UserEmails,
            @SessionAttribute("proposedFlights") List<FlightEntity> proposedFlights,
            @SessionAttribute("price") PriceEntity price,
            @SessionAttribute("bookedJourney") List<BookedJourneyEntity> journeys,
            @SessionAttribute("ticketType") String ticketType,
            @SessionAttribute("classCode") String classCode,
            @SessionAttribute("timestamp") Timestamp timestamp) {
        //There are no BookedJourneys being created yet.
        if (journeys.isEmpty()) {
            journeys = new LinkedList<BookedJourneyEntity>();
            for (int i = 0; i < UserEmails.length; i++) {
                BookedJourneyEntity temp = new BookedJourneyEntity();
                journeys.add(temp);
            }
            model.put("bookedJourneys", journeys);
        }
        //Creating a list of leg entities that will be added.
        List<String> Seats = Arrays.asList(seatID.clone());
        List<LegEntity> legEntities = new LinkedList<>();
        Iterator<BookedJourneyEntity> it = journeys.listIterator();
        int count =0;

        while(it.hasNext()){
            TicketEntity ticket = ticketEntityDAO.findById(ticketType);
            ClassEntity classType = classEntityDAO.findById(classCode);
            LegEntity newLeg = new LegEntity();
            newLeg.setFlightID((proposedFlights.get(0)));
            newLeg.setTicketType(ticket);
            newLeg.setTicketClass(classType);
            newLeg.setPriceEntity(priceEntityDAO.findPrice(timestamp, newLeg.getFlightID(), classType, ticket, newLeg.getFlightID().getAirlineCode()));
            newLeg.setSeat(seatID[count]);
            it.next().addLeg(newLeg);
            count++;
        }
        proposedFlights.remove(0);
        boolean isLast = false;
        boolean isFirst = false;
        if(proposedFlights.size()==1){
            isLast = true;
            model.put("lastFlight", proposedFlights.get(0));
        }

        model.put("isLast",isLast);
        model.put("isFirst",isFirst);

        model.put("Users",UserEmails);
        model.put("bookedJourney", journeys);
        model.put("proposedFlights", proposedFlights);
        return new ModelAndView("forward:./SeatSelectionGroup");
    }

    private String fixingString(List<Integer> array) {
        StringBuilder output = new StringBuilder();
        Iterator<Integer> it = array.iterator();
        while (it.hasNext()) {
            output.append(it.next());
            if (it.hasNext()) {
                output.append(",");
            }
        }

        return output.toString();
    }
}
