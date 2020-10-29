package me.groupFour.controller;


import me.groupFour.dao.*;
import me.groupFour.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"activeUser", "booking", "reserved","bookedJourney","timestamp","GroupBooking"})
@RequestMapping("Account")
public class AccountController {
    private final IAccountEntityDAO accountEntityDAO;
    private final IBookingEntityDAO bookingEntityDAO;
    private final BookedJourneyDAO bookedJourneyDAO;
    private final IDestinationEntityDAO destinationEntityDAO;
    private final GroupBookingEntityDAO groupBookingEntityDAO;

    @Autowired
    public AccountController(IAccountEntityDAO iAccountEntityDAO, IBookingEntityDAO iBookingEntityDAO, BookedJourneyDAO bookedJourneyDAO,IDestinationEntityDAO iDestinationEntityDAO,GroupBookingEntityDAO groupBookingEntityDAO) {
        this.accountEntityDAO = iAccountEntityDAO;
        this.bookingEntityDAO = iBookingEntityDAO;
        this.bookedJourneyDAO = bookedJourneyDAO;
        this.destinationEntityDAO = iDestinationEntityDAO;
        this.groupBookingEntityDAO = groupBookingEntityDAO;

    }
    @GetMapping
    @RequestMapping("ViewJourney")
    public ModelAndView viewJourney(@RequestParam("JourneyID") Integer JourneyID
                                    ){
        BookedJourneyEntity temp = bookedJourneyDAO.findById(JourneyID);
        ModelAndView view = new ModelAndView("JourneyView");
        view.addObject("Journey",temp);
        return view;

    }

    @GetMapping
    @RequestMapping("CreateAccount")
    public ModelAndView createAccount() {
        ModelAndView view = new ModelAndView("CreateAccount");
        view.addObject("AccountEntity", new AccountEntity());
        return view;
    }

    @GetMapping
    @RequestMapping("ViewAccount")
    public ModelAndView viewAccount(ModelMap model) {
        ModelAndView view = new ModelAndView("Account");
        view.addObject("account", model.get("activeUser"));
        view.addObject("AccountEntity",new AccountEntity());
        //Create a list of Journeys that are in the history and pass them through.
        List<BookingEntity> History = bookingEntityDAO.searchByAccount((AccountEntity)model.get("activeUser"));
        view.addObject("history",History);
        List<GroupBookingEntity> GroupBookingList = groupBookingEntityDAO.searchByAccount((AccountEntity)model.get("activeUser"));
        view.addObject("GroupBookingList",GroupBookingList);
        return view;
    }
    @PostMapping
    @RequestMapping("EditAccount")
    public ModelAndView editAccount(ModelMap model,@Valid @ModelAttribute("AccountEntity") AccountEntity account, BindingResult bindingResult, HttpServletRequest request){
        AccountEntity temp = accountEntityDAO.findById(account.getEmail());
        //setting all the details
        temp.setAddress(account.getAddress());
        temp.setDateOfBirth(account.getDateOfBirth());
        temp.setLastName(account.getLastName());
        temp.setFirstName(account.getFirstName());
        accountEntityDAO.update(temp);
        temp = accountEntityDAO.findById(account.getEmail());
        model.put("activeUser",temp);
        return new ModelAndView("redirect:./ViewAccount");
    }
    @GetMapping
    @RequestMapping("ViewGroupBooking")
    public ModelAndView viewGroupBooking(ModelMap model,@RequestParam int GroupBookingID){
        ModelAndView view = new ModelAndView("GroupBookingView");
        GroupBookingEntity temp = groupBookingEntityDAO.findById(GroupBookingID);
        view.addObject("GroupBooking",temp);
        view.addObject("Journey",temp.getBookingsList().get(0).getJourneyID());
        return view;
    }
    @PostMapping
    @RequestMapping("LogOut")
    public ModelAndView logOut(ModelMap model, HttpSession session) {
        session.invalidate();
        ModelAndView view = new ModelAndView("HomePage");
        view.addObject("SearchQueryEntity", new SearchQueryEntity());
        model.put("activeUser", null);

        model.remove("activeUser");
        //view.addObject("account", null);
        return view;
    }

    @PostMapping
    @RequestMapping("ValidateAccCreation")
    public ModelAndView validateAccount(@Valid @ModelAttribute("AccountEntity") AccountEntity account, BindingResult bindingResult, HttpServletRequest request)                                                   // indicating that a request will be passed in via url as "name"
    {
        String[] wishlistString = request.getParameterValues("wishList");
        account.setStatus("active");
        if (bindingResult.hasErrors()) {
            return new ModelAndView("CreateAccount");
        } else if (accountEntityDAO.findById(account.getEmail()) != null) {
            return new ModelAndView("CreateAccount");
        } else {
            accountEntityDAO.create(account);
            ModelAndView view = new ModelAndView("Login");
            view.addObject("LoginQueryEntity", new LoginQueryEntity());
            return view;
        }
    }

    @PostMapping
    @RequestMapping("ValidateAccCreationGuest")
    public ModelAndView validateAccountGuest(@Valid @ModelAttribute("AccountEntity") AccountEntity account, BindingResult bindingResult, ModelMap model)                                                   // indicating that a request will be passed in via url as "name"
    {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("CreateAccountGuest");

        }

        if(account.getEmail() != null){
            if (accountEntityDAO.findById(account.getEmail()) != null) {
                return new ModelAndView("CreateAccountGuest");
            }
        }
        account.setStatus("active");

            /*String password = account.getPword();
            password = account.encryptPassword(password);
            account.setPword(password);
            ac.update(account);*/
            Boolean GroupBooking = (Boolean)model.getAttribute("GroupBooking");
            if(GroupBooking!=null){
                accountEntityDAO.create(account);
                return new ModelAndView("redirect:/Journey/SeatSelectionGroup");
            }
            ModelAndView view = new ModelAndView("ConfirmationPage");
            BookingEntity book = SaveBooking(account,model);

            view.addObject("booking", book);
            view.addObject("reserved", model.getAttribute("reserved"));

            return view;
    }
    public BookingEntity SaveBooking(AccountEntity account, ModelMap model){
        BookingEntity book = (BookingEntity) model.get("booking");
        BookedJourneyEntity JourneyBooked = (BookedJourneyEntity) model.get("bookedJourney");
        book.setAccountID(account);
        book.setJourneyID(JourneyBooked);
        book.setBookingID(null);
        JourneyBooked.setLegsOfJourney(JourneyBooked.getLegsOfJourney());
        JourneyBooked.setLegTimestamp((Timestamp)model.get("timestamp"));
        JourneyBooked.setBooking(book);
        JourneyBooked.setJourneyID(null);
        bookedJourneyDAO.create(JourneyBooked);
        return book;
    }

    @GetMapping
    @RequestMapping("Login")        // 8080::localhost/Account/login
    public ModelAndView login() {
        ModelAndView view = new ModelAndView("Login");
        view.addObject("LoginQueryEntity", new LoginQueryEntity());
         return view;
    }

    @PostMapping
    @RequestMapping("ValidateLogin")
    // to enable validation, we pass in an arg to the create method that is bindingresult. this will tell us if there are any errors in view model. the view model is specified as a param. valid, makes it validate teh beans passed in the form. we also need to specifiy that it is a modelattribute and give it the name
    public ModelAndView validateLogin(
            @Valid @ModelAttribute("LoginQueryEntity") LoginQueryEntity query, BindingResult bindingResult, ModelMap model
    ) {
        if (bindingResult.hasErrors())       // if this happens there are errors in the bean validation, return them back to the form page
        {
            return new ModelAndView("Login");

        } else if (checkLoginCredentials(query)) {
            AccountEntity account = accountEntityDAO.findById(query.getEmailAddress());
            if(account.getStatus().equalsIgnoreCase("active")){
                model.put("activeUser", account);
            }
            if(account.getStatus().equalsIgnoreCase("inactive")){
                ModelAndView view = new ModelAndView("LoginError");
                view.addObject("account",account);
                return view;
            }
            return new ModelAndView("redirect:/");

        } else {

            return new ModelAndView("Login");
        }
    }

    private boolean checkLoginCredentials(LoginQueryEntity query) {
        AccountEntity account = accountEntityDAO.findById(query.getEmailAddress());
        return account.getPword().equals(query.getPassword());
    }


    //Method returns json with City names that are similar to those inputted by the user. This enables the autocompete destination fields.
    //todo
    // ensure that any city that is only a stopover location is not shown, as they cannot be flown from
    @GetMapping
    @RequestMapping(value = "WishListAutoComplete")
    @ResponseBody
    public List<String> wishListAutoComplete(@RequestParam(value = "term", defaultValue = "") String term) {
        List<DestinationEntity> list = destinationEntityDAO.searchByAirportName(term);
        List<String> AirportNameList = new ArrayList<>();
        for (DestinationEntity destinationEntity : list) {
            if(!destinationEntity.getFlightDestinationList().isEmpty()&&!destinationEntity.getFlightDepartureList().isEmpty()) {
                AirportNameList.add(destinationEntity.getAirport());
            }
        }
        return AirportNameList;
    }

}
