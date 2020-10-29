package me.groupFour.controller;

import me.groupFour.data.AccountEntity;
import me.groupFour.data.SearchQueryEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping
    //@PutMapping    ....other http verbs
    //@PostMapping
    public ModelAndView homePageActive(ModelMap model)                                                   // indicating that a request will be passed in via url as "name"
    {
        ModelAndView view = new ModelAndView("HomePage");
        view.addObject("SearchQueryEntity", new SearchQueryEntity());
        if(model.containsKey("activeUser")) {
            AccountEntity temp = (AccountEntity) model.get("activeUser");
            view.addObject("activeUser", temp);
        } else {
            AccountEntity temp = null;
            view.addObject("activeUser", temp);
        }
        return view;
    }//data model for the view. not like a regular business model

    @GetMapping
    @RequestMapping("login")
    public ModelAndView Login() {
        return new ModelAndView("Login");
    }
    @GetMapping
    @RequestMapping("ContactUs")
    public ModelAndView Contactus(){
        return new ModelAndView("ContactUS");
    }
    @GetMapping
    @RequestMapping("AboutUs")
    public ModelAndView AboutUs(){
        return new ModelAndView("AboutUs");
    }
}
