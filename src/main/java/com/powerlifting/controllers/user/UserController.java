package com.powerlifting.controllers.user;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.mail.ApplicationMailer;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;
    @Autowired private ApplicationMailer mailer;

    @RequestMapping("/")
    public ModelAndView homePage(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("CommonUser/mainPage");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        List<Competition> competitions = competitionDao.getAllCompetitions();
        modelAndView.addObject("competitions", competitions);

        return modelAndView;
    }

    @RequestMapping("/sign-in")
    public ModelAndView signIn() {
        ModelAndView modelAndView = new ModelAndView("CommonUser/sign-in");

        modelAndView.addObject("background", CommonUtils.getRandomBackground());

        return modelAndView;
    }

    @RequestMapping("/sign-up")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView("CommonUser/sign-up");

        return modelAndView;
    }

    @RequestMapping(value = "/new-user", method = RequestMethod.POST)
    @ResponseBody
    public String newUser(@RequestParam String studentJson/*, @RequestParam String birthday*/)
    {
        final User user = serializer.fromJson(studentJson, User.class);
        user.setPassword(CommonUtils.md5Hex(user.getPassword()));

        userDao.createUser(user);

        final Map messageParams = new HashMap<>();
        messageParams.put("user", userDao.getUserByCredentials(user).get());

//        mailer.sendSimpleMessage(user.getEmail(), "POWERLIFTING FUCK YEAH!", "FUCK YEAH!");
        mailer.sendMail(user.getEmail(), "Welcome in POWERLIFTING!", "/registerMessage.ftl", messageParams);

        return "success";
    }

    @RequestMapping(value = "/sign-in-request", method = RequestMethod.POST)
    @ResponseBody
    public String signInRequest(@RequestParam String requestJson, HttpServletRequest httpServletRequest)
    {
        final User requestUser = serializer.fromJson(requestJson, User.class);
        requestUser.setPassword(CommonUtils.md5Hex(requestUser.getPassword()));

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

    @RequestMapping("/profile")
    public ModelAndView profile(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("user/profile");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping("/edit-profile")
    public ModelAndView editProfile(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("user/editProfile");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping("/competitions/all")
    public ModelAndView allCompetitions(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("CommonUser/competitions");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        List<Competition> competitions = competitionDao.getAllCompetitions();
        modelAndView.addObject("competitions", competitions);

        return modelAndView;
    }

    @RequestMapping("/competitions/current")
    public ModelAndView currentCompetitions(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("CommonUser/competitions");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        List<Competition> competitions = competitionDao.getCurrentCompetitions();
        modelAndView.addObject("competitions", competitions);

        return modelAndView;
    }

    @RequestMapping("/competitions/past")
    public ModelAndView pastCompetitions(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("CommonUser/competitions");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        List<Competition> competitions = competitionDao.getPastCompetitions();
        modelAndView.addObject("competitions", competitions);

        return modelAndView;
    }

    @RequestMapping("/competition/{competitionId}")
    public ModelAndView competition(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest,
                                    HttpServletResponse response) throws Exception
    {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("CommonUser/competition");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            modelAndView.addObject("competition", competition.get());
            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

}
