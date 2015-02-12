package com.powerlifting.controllers.moder;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.Category;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.Judge;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.dao.rowMappers.JudgeDao;
import com.powerlifting.mail.ApplicationMailer;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = "/moder")
public class ModerController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;
    @Autowired private JudgeDao judgeDao;
    @Autowired private ApplicationMailer mailer;

    @RequestMapping(value = "/createCompetition")
    public ModelAndView createCompetition(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/competitionCreateForm");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("ageGroups", competitionDao.getAllAgeGroups());

        return modelAndView;
    }

    @RequestMapping(value = "/new-competition", method = RequestMethod.POST)
    public @ResponseBody String newUser(@RequestParam String competitionJson, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        final Competition competition = serializer.fromJson(competitionJson, Competition.class);
        HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");

        competitionDao.createNewCompetition(competition, user.getUserId());

        return "success";
    }

    @RequestMapping(value = "/delete-competition", method = RequestMethod.POST)
    public @ResponseBody String deleteCompetition(@RequestParam String competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(Integer.parseInt(competitionId));
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                competitionDao.deleteCompetition(competition.get().getId());
                return "success";
            }
        }

        return "error";
    }

    @RequestMapping(value = "/add-judges/{competitionId}")
    public ModelAndView addJudges(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception{
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/addJudgesToCompetition");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("judges", judgeDao.getAllJudgeOfCompetition(competitionId));

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            modelAndView.addObject("competition", competition.get());
            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/deleteJudgeFromCompetition/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String deleteJudgeFromCompetition(@PathVariable Integer competitionId, @RequestParam Integer judgeId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                judgeDao.deleteJudgeFromCompetition(competitionId, judgeId);
                return "success";
            }
        }
        return "error";
    }

    @RequestMapping(value = "/getJudgesLike/{competitionId}", method = RequestMethod.GET)
    public @ResponseBody List<User> getJudgesLike(@PathVariable Integer competitionId, @RequestParam String term, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        return judgeDao.getJudgesLikeWhichNotJudgeInCompetition(term, competitionId);
    }

    @RequestMapping(value = "/AddJudgeToCompetition/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String addJudgeToCompetition(@PathVariable Integer competitionId, @RequestParam String judgeEmail, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                judgeDao.addJudgeToCompetition(judgeEmail, competitionId);
                return "success";
            }
        }
        return "error";
    }

    @RequestMapping(value = "/assignJudge/{competitionId}")
    public ModelAndView assignJudge(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/assignJudge");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("competition", competitionDao.getCompetition(competitionId));
        modelAndView.addObject("categories", judgeDao.getAllCategory());

        return modelAndView;
    }

    @RequestMapping(value = "/getUsersLike/{competitionId}", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsersLike(@PathVariable Integer competitionId, @RequestParam String term, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        return userDao.getUsersLike(term);
    }

    @RequestMapping(value = "/assignUserToJudge/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String assignUserToJudge(@PathVariable Integer competitionId, @RequestParam String userEmail,
                                                  @RequestParam Integer categoryId, @RequestParam Date categoryDate,
                                                  @RequestParam Boolean addJudgeToCompetition,
                                                  HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                judgeDao.assignUserToJudge(userEmail, categoryId, categoryDate);

                if(addJudgeToCompetition) {
                    judgeDao.addJudgeToCompetition(userEmail, competitionId);
                }

                return "success";
            }
        }
        return "error";
    }

    @RequestMapping(value = "/createNewJudge/{competitionId}")
    public ModelAndView createNewJudge(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/createNewJudge");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("categories", judgeDao.getAllCategory());

        return modelAndView;
    }

    @RequestMapping(value = "/new-judge/{competitionId}", method = RequestMethod.POST)
    @ResponseBody
    public String newUser(@PathVariable Integer competitionId, @RequestParam String judgeJson, @RequestParam Boolean addJudgeToCompetition, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final Judge judge = serializer.fromJson(judgeJson, Judge.class);
        String judgePassword = CommonUtils.generateRandomPassword();
        judge.setPassword(CommonUtils.md5Hex(judgePassword));

        judgeDao.createNewJudgeAsUser(judge);
        judgeDao.assignUserToJudge(judge.getEmail(), judge.getCategory(), judge.getAssignmentDate());

        if(addJudgeToCompetition) {
            judgeDao.addJudgeToCompetition(judge.getEmail(), competitionId);
        }

        final Map messageParams = new HashMap<>();
        User user = new User();
        user.setEmail(judge.getEmail());
        user.setPassword(judge.getPassword());
        messageParams.put("user", userDao.getUserByCredentials(user).get());

        mailer.sendMail(user.getEmail(), "Welcome in POWERLIFTING!", "/registerMessage.ftl", messageParams);

        return "success";
    }

    @RequestMapping(value = "/createAndAddParticipantToCompetition/{id}")
    public ModelAndView CreateAndAddParticipant(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/AddParticipantToCompetition");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = "/add-participant")
    public String addParticipant(@RequestParam String participantJson, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        final User user = serializer.fromJson(participantJson, User.class);


        return "success";
    }
}
