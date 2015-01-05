package com.powerlifting.controllers.user;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;

    @RequestMapping("/")
    public ModelAndView homePage(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("mainPage");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        List<Competition> competitions = competitionDao.getAllCompetitions();
        modelAndView.addObject("competitions", competitions);

        return modelAndView;
    }

    @RequestMapping("/sign-in")
    public ModelAndView signIn() {
        ModelAndView modelAndView = new ModelAndView("unregistered/sign-in");

        modelAndView.addObject("background", CommonUtils.getRandomBackground());

        return modelAndView;
    }

    @RequestMapping("/sign-up")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView("unregistered/sign-up");

        return modelAndView;
    }

    @RequestMapping(value = "/new-user", method = RequestMethod.POST)
    @ResponseBody
    public String newUser(@RequestParam String studentJson/*, @RequestParam String birthday*/)
    {
        final User user = serializer.fromJson(studentJson, User.class);

        userDao.createUser(user);

        return "success";
    }

    @RequestMapping(value = "/sign-in-request", method = RequestMethod.POST)
    @ResponseBody
    public String signInRequest(@RequestParam String requestJson, HttpServletRequest httpServletRequest)
    {
        final User requestUser = serializer.fromJson(requestJson, User.class);

        Optional<User> user = userDao.getUserByCredentials(requestUser);
        if(user.isPresent()) {
            final HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user", user.get());

            return "success";
        }

        return "error";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout(HttpServletRequest httpServletRequest)
    {
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();

        return "success";
    }

}
