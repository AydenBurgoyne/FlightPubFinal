package me.groupFour.controller;

import me.groupFour.dao.DestinationEntityDAO;
import me.groupFour.dao.RestrictedAirportDAO;
import me.groupFour.data.AccountEntity;
import me.groupFour.data.DestinationEntity;
import me.groupFour.data.RestrictedAirportsEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.Transient;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("RestrictedAirports")
public class RestrictedAirportController {
    private final RestrictedAirportDAO restrictedAirportDAO;
    private final DestinationEntityDAO destinationEntityDAO;
    public RestrictedAirportController(RestrictedAirportDAO restrictedAirportDAO, DestinationEntityDAO destinationEntityDAO){
        this.restrictedAirportDAO = restrictedAirportDAO;
        this.destinationEntityDAO = destinationEntityDAO;
    }
    @GetMapping
    @RequestMapping("addRestrictedAirports")
    public ModelAndView addRestrictedAirport(){
        ModelAndView view = new ModelAndView("AirportRestriction");
        view.addObject("RestrictedAirport", new RestrictedAirportsEntity());

        return view;
    }
    @PostMapping
    @RequestMapping("ValidateRestrictedAirports")
    public ModelAndView ValidateRestrictedAirport(@Valid @ModelAttribute("RestrictedAirport") RestrictedAirportsEntity restrictedAirportsEntity, BindingResult bindingResult, HttpServletRequest request){
        ModelAndView view = new ModelAndView("HomePage");
        //Setting the destination Entity
        restrictedAirportsEntity.setDestinationCode(destinationEntityDAO.searchByAirportNameSingle(restrictedAirportsEntity.getAirport()));
        //Changing the Timestamps
        restrictedAirportsEntity.setTimefromTimestamp(new Timestamp(restrictedAirportsEntity.getTimeFrom().getTime()));
        restrictedAirportsEntity.setTimetoTimestamp(new Timestamp(restrictedAirportsEntity.getTimeTo().getTime()));
        if(bindingResult.hasErrors()){
            return new ModelAndView("AirportRestriction");
        }
        restrictedAirportDAO.create(restrictedAirportsEntity);
        return view;
    }
    @GetMapping
    @RequestMapping("getRestrictedList")
    public ModelAndView getRestrictedList(){
        ModelAndView view = new ModelAndView("RestrictedAirportsList");
        List<RestrictedAirportsEntity> list = restrictedAirportDAO.findAll(0,1000);
        view.addObject("RestrictedList",list);
        return view;
    }
    @GetMapping
    @RequestMapping("deleteRestricted")
    public ModelAndView deleteRestricted(
        @RequestParam(name = "RestrictionID") Integer RestrictionID
        ){
        RestrictedAirportsEntity temp = restrictedAirportDAO.findById(RestrictionID);
        restrictedAirportDAO.delete(temp);
        ModelAndView view = new ModelAndView("redirect:./getRestrictedList");
        return view;

    }

}
