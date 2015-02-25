package com.powerlifting.controllers.user;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.*;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.ParticipantDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.mail.ApplicationMailer;
import com.powerlifting.mail.Email;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@Controller
public class UserController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;
    @Autowired private ParticipantDao participantDao;
    @Autowired private Email email;

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

        email.sendRegisterEmail(user);

        return "success";
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
            modelAndView.addObject("ageGroup", competitionDao.getCompetitionAgeGroupById(competition.get().getAgeGroup()));
            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

    @RequestMapping("/participantsStandings/{competitionId}")
    public ModelAndView participantsStandings(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest,
                                              HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("CommonUser/participantsStandings");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        final List<ParticipantAllInf> participants = participantDao.getAllParticipantOfCompetitionWithAllInf(competitionId);
        Collections.sort(participants, new Comparator<ParticipantAllInf>() {
            @Override
            public int compare(ParticipantAllInf o1, ParticipantAllInf o2) {
                return o1.getTotal() < o2.getTotal()? 1 : -1;
            }
        });
        modelAndView.addObject("participants", participants);

        Set<Integer> weightCategoriesOfParticipants = new HashSet<>();
        for(Iterator<ParticipantAllInf> i = participants.iterator(); i.hasNext(); ) {
            ParticipantAllInf participantAllInf = i.next();
            weightCategoriesOfParticipants.add(participantAllInf.getCategory());
        }

        final List<WeightCategory> weightCategories = participantDao.getAllWeightCategories();
        List<WeightCategory> resultWeightCategories = new ArrayList<>();
        for(Iterator<WeightCategory> i = weightCategories.iterator(); i.hasNext(); ) {
            WeightCategory weightCategory = i.next();
            if(weightCategoriesOfParticipants.contains(weightCategory.getCategoryId())) {
                resultWeightCategories.add(weightCategory);
            }
        }

        modelAndView.addObject("categories", resultWeightCategories);


        return modelAndView;
    }

}
