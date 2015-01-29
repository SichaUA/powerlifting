package com.powerlifting.controllers.registered;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class RegisteredController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;

    @RequestMapping("/my-competitions")
    public ModelAndView competitions(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("user/userCompetitions");

        HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");
        if(user != null) {
            modelAndView.addObject("user", user);

            List<Competition> competitions = competitionDao.getCompetitionsUserParticipate(user.getUserId());

//          split all competitions into participated and impending
            List<Competition> pCompetitions = new ArrayList<>();
            List<Competition> iCompetitions = new ArrayList<>();
            final java.sql.Date currentDate = new java.sql.Date(new java.util.Date().getTime());
            for(Iterator<Competition> i = competitions.iterator(); i.hasNext(); ) {
                Competition competition = i.next();
                if(competition.getStartDate().before(currentDate) || competition.getStartDate().equals(currentDate)) {
                    pCompetitions.add(competition);
                }else{
                    iCompetitions.add(competition);
                }
            }

            modelAndView.addObject("pCompetitions", pCompetitions);
            modelAndView.addObject("iCompetitions", iCompetitions);

            if(user.getRole() == 2) {
                modelAndView.addObject("createdCompetitions", competitionDao.getCompetitionsCreatedByUser(user.getUserId()));
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = "/participate-competition", method = RequestMethod.POST)
    @ResponseBody
    public String participateCompetition(@RequestParam Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");

        userDao.participateTheCompetition(competitionId, user.getUserId());

        return "success";
    }
}
