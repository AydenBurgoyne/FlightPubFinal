package me.groupFour.controller;


import me.groupFour.dao.IDestinationEntityDAO;
import me.groupFour.dao.IHotelEntityDAO;
import me.groupFour.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({
        "HotelBookingService", "hotelBooking",
        "lastFlight", "hotelDestination","HotelBookingNew"
})
@RequestMapping("/hotel-addon")
public class BookingController {
    private final IHotelEntityDAO hotelEntityDAO;
    private final IDestinationEntityDAO destinationEntityDAO;

    BookingController(
            IHotelEntityDAO hotelEntityDAO,
            IDestinationEntityDAO destinationEntityDAO
    ) {
        this.hotelEntityDAO = hotelEntityDAO;
        this.destinationEntityDAO = destinationEntityDAO;
    }


    @GetMapping
    public ModelAndView hotelAddon(
            @ModelAttribute("lastFlight") FlightEntity flight,
            BindingResult bindingResult,
            ModelMap model
    ) {

        //redirects the user if they have already booked a hotel
        if(model.containsKey("HotelBookingNew")){
            model.put("hotelBooked", true);
            return new ModelAndView("HotelAddon");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //estimate the check in date
        String checkinTime = formatter.format(flight.getArrivalTime());

        //estimate the check out date (add 1)
        Calendar cal = Calendar.getInstance();
        cal.setTime(flight.getArrivalTime());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String checkoutTime = formatter.format(cal.getTime());


        model.put("checkinTime", checkinTime);
        model.put("checkoutTime", checkoutTime);
        model.put("hotelBooked", false);
        model.put("lastFlight", flight);
        model.put("HotelBookingService", new HotelBookingService());

        //need to get the user
        return new ModelAndView("HotelAddon");
    }


    @GetMapping
    @RequestMapping("show")
    public ModelAndView showHotels(
            ModelMap model,
            @Valid @ModelAttribute("HotelBookingService") HotelBookingService hotelBookingService,
            BindingResult bindingResult
    ) {
        if (!model.containsKey("HotelBookingService")) {
            //redirect to main
            return new ModelAndView("HotelAddon");
        } else if (bindingResult.hasErrors()){
            return new ModelAndView("HotelAddon");
        } else if(hotelBookingService == null){
            return new ModelAndView("HotelAddon");
        }


        DestinationEntity destinationEntity = destinationEntityDAO.searchByAirportNameSingle(hotelBookingService.getLocation());
        List<HotelEntity> hotelEntityList = hotelEntityDAO.findAllByLocation(destinationEntity.getDestinationCode());

        model.put("hotelDestination", destinationEntity);
        model.put("HotelBookingService", hotelBookingService);
        model.put("hotelEntityList", hotelEntityList);
        return new ModelAndView("HotelAddon");
    }


    @GetMapping
    @RequestMapping("confirm")
    // /book/confirm
    public RedirectView confirmBooking(
            @RequestParam(name = "hotelID") int hotelID,
            ModelMap model
    ) {

        if(hotelID == 0){
            return new RedirectView("/");
        }

        HotelBookingService hbs = (HotelBookingService) model.get("HotelBookingService");

        if (hbs == null || hbs.getStartDate() == null || hbs.getEndDate() == null) {
            //return to main page if the page has been gone to accidentally
            return new RedirectView("/");
        } else {

            HotelEntity currentHotel = hotelEntityDAO.findById(hotelID);

            HotelBookingEntity newBooking = new HotelBookingEntity(
                    hbs.getPeople(),
                    hbs.getStartDate(),
                    hbs.getEndDate(),
                    currentHotel
            );

            model.put("HotelBookingNew", newBooking);


        }

        return new RedirectView("/");
    }

}
