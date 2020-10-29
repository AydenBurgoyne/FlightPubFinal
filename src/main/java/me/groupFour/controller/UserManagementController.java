package me.groupFour.controller;

import me.groupFour.dao.AccountEntityDAO;
import me.groupFour.dao.BookingEntityDAO;
import me.groupFour.dao.GroupBookingEntityDAO;
import me.groupFour.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

@Controller
@SessionAttributes({
        "currentJourney", "proposedFlights", "ticketType",
        "classCode", "timestamp", "price",
        "Users","isLast","isFirst",
        "bookedJourney", "booking", "activeUser",
        "reserved", "bookedJourneys", "GroupBooking",
        "flightReviews", "airportReviews", "lastFlight","HotelBookingNew"
})
@RequestMapping("/UserManagement")
public class UserManagementController {
    //daos
    private final AccountEntityDAO accountEntityDAO;
    private final BookingEntityDAO bookingEntityDAO;
    private final GroupBookingEntityDAO groupBookingEntityDAO;

    public UserManagementController(AccountEntityDAO accountEntityDAO,BookingEntityDAO bookingEntityDAO,GroupBookingEntityDAO groupBookingEntityDAO){
        this.accountEntityDAO = accountEntityDAO;
        this.bookingEntityDAO = bookingEntityDAO;
        this.groupBookingEntityDAO = groupBookingEntityDAO;
    }
    @GetMapping
    @RequestMapping("activeAccounts")
    public ModelAndView UserList(){
        ModelAndView view = new ModelAndView("UserManagement");
        //Get all the users
        view.addObject("ActiveAccounts",gettingActiveAccounts());

        return view;
    }
    public List<AccountEntity> gettingActiveAccounts(){
        List<AccountEntity> list = accountEntityDAO.findAll(0,10000);
        return list;
    }
    @GetMapping
    @RequestMapping("DeleteUser")
    public ModelAndView deleteRestricted(
            @RequestParam(name = "AccountID") String AccountID
    ){
        AccountEntity temp = accountEntityDAO.findById(AccountID);
        temp.setStatus("inactive");
        accountEntityDAO.update(temp);
        ModelAndView view = new ModelAndView("redirect:./activeAccounts");
        return view;

    }
    @GetMapping
    @RequestMapping("UserCreation")
    public ModelAndView UserCreation(){
        ModelAndView view = new ModelAndView("AdminUserCreation");
        AccountEntity temp = new AccountEntity();
        view.addObject("AccountEntity",temp);
        return view;
    }
    @PostMapping
    @RequestMapping("ValidateAccountFromAdmin")
    public ModelAndView validateAccountFromAdmin(@Valid @ModelAttribute("AccountEntity") AccountEntity account, BindingResult bindingResult, ModelMap model){
      if(bindingResult.hasErrors()){
          return new ModelAndView("AdminUserCreation");
      }
        if(account.getEmail() != null){
            if (accountEntityDAO.findById(account.getEmail()) != null) {
                return new ModelAndView("AdminUserCreation");
            }
        }
        account.setStatus("active");
        if(account.getAccountRole().equalsIgnoreCase("Admin")) {
            AdminEntity temp = new AdminEntity();
            temp.setAccountID(account);
            account.setAdmin(temp);
        }
        accountEntityDAO.create(account);
        return new ModelAndView("redirect:./activeAccounts");

    }
    @GetMapping
    @RequestMapping("CancelBooking")
    public ModelAndView cancelBooking(RedirectAttributes redirectAttributes, @RequestParam Integer BookingID,ModelMap model){

       ModelAndView view = new ModelAndView("redirect:/UserManagement/ViewAccountAdmin");
       BookingEntity temp = bookingEntityDAO.findById(BookingID);
       AccountEntity account = temp.getAccountID();
       redirectAttributes.addAttribute("AccountID",account.getEmail());
       AccountEntity check = (AccountEntity)model.get("activeUser");
       if(account.getEmail().equals(check.getEmail())){
           view = new ModelAndView("redirect:/Account/ViewAccount");
       }
       //e.g if not part of group booking.
        if(temp.getGroupBookingID()==null){
            bookingEntityDAO.delete(temp);
        }
        else if(temp.getGroupBookingID()!=null){
            view = new ModelAndView("ErrorPageForBooking");
        }
        return view;
    }
    @PostMapping
    @RequestMapping("EditAccountAdmin")
    public ModelAndView editAccount(RedirectAttributes redirectAttributes, ModelMap model, @Valid @ModelAttribute("AccountEntity") AccountEntity account, BindingResult bindingResult, HttpServletRequest request){
        AccountEntity temp = accountEntityDAO.findById(account.getEmail());
        //setting all the details
        temp.setAddress(account.getAddress());
        temp.setDateOfBirth(account.getDateOfBirth());
        temp.setLastName(account.getLastName());
        temp.setFirstName(account.getFirstName());
        accountEntityDAO.update(temp);
        temp = accountEntityDAO.findById(account.getEmail());
        redirectAttributes.addAttribute("AccountID",temp.getEmail());

        return new ModelAndView("redirect:./ViewAccountAdmin");
    }

    @GetMapping
    @RequestMapping("ViewAccountAdmin")
    public ModelAndView viewAccountAdmin(ModelMap model,@RequestParam String AccountID) {
        ModelAndView view = new ModelAndView("AccountEditAdmin");
        AccountEntity temp = accountEntityDAO.findById(AccountID);
        view.addObject("AccountEntity",new AccountEntity());
        view.addObject("account",temp);
        //Create a list of Journeys that are in the history and pass them through.
        List<BookingEntity> History = bookingEntityDAO.searchByAccount(temp);
        view.addObject("history",History);
        List<GroupBookingEntity> GroupBookingList = groupBookingEntityDAO.searchByAccount(temp);
        view.addObject("GroupBookingList",GroupBookingList);
        return view;
    }
    @GetMapping
    @RequestMapping("CancelGroupBooking")
    public ModelAndView CancelGroupBooking(RedirectAttributes redirectAttributes,@RequestParam int GroupBookingID,ModelMap model){
        GroupBookingEntity temp = groupBookingEntityDAO.findById(GroupBookingID);
        ModelAndView view = new ModelAndView("redirect:/UserManagement/ViewAccountAdmin");
        AccountEntity tempAccount = temp.getAccountLeader();
        redirectAttributes.addAttribute("AccountID",tempAccount.getEmail());
        //checking if the person signed in is the same person who's deleting the account.
        AccountEntity check = (AccountEntity)model.get("activeUser");
        if(check!=null) {
            if (tempAccount.getEmail().equals(check.getEmail())) {
                view = new ModelAndView("redirect:/Account/ViewAccount");
            }
        }
            groupBookingEntityDAO.delete(temp);
            return view;
    }
    @GetMapping
    @RequestMapping("Activate")
    public ModelAndView activateUser(
            @RequestParam(name = "AccountID") String AccountID
    ){
        AccountEntity temp = accountEntityDAO.findById(AccountID);
        temp.setStatus("active");
        accountEntityDAO.update(temp);
        ModelAndView view = new ModelAndView("redirect:./activeAccounts");
        return view;

    }

}
