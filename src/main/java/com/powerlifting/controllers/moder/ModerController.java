package com.powerlifting.controllers.moder;

import com.google.gson.Gson;
import com.mysql.jdbc.SQLError;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.Judge;
import com.powerlifting.controllers.registered.model.Region;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.ParticipantDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.dao.JudgeDao;
import com.powerlifting.mail.ApplicationMailer;
import com.powerlifting.mail.Email;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping(value = "/moder")
public class ModerController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;
    @Autowired private JudgeDao judgeDao;
    @Autowired private ParticipantDao participantDao;
    @Autowired private Email email;

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

        return judgeDao.getJudgesLikeWhichNotJudgeInCompetition(term, competitionId, 9);
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

        return userDao.getUsersLike(term, 9);
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
    public String newJudge(@PathVariable Integer competitionId, @RequestParam String judgeJson, @RequestParam Boolean addJudgeToCompetition, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final Judge judge = serializer.fromJson(judgeJson, Judge.class);
        String judgePassword = CommonUtils.generateRandomPassword(8);
        judge.setPassword(CommonUtils.md5Hex(judgePassword));

        judgeDao.createNewJudgeAsUser(judge);

        User user = new User();
        user.setEmail(judge.getEmail());
        user.setPassword(judge.getPassword());
        email.moderRegisterUserEmail(user, judgePassword);

        judgeDao.assignUserToJudge(judge.getEmail(), judge.getCategory(), judge.getAssignmentDate());

        if(addJudgeToCompetition) {
            judgeDao.addJudgeToCompetition(judge.getEmail(), competitionId);
            final Optional<Competition> competition = competitionDao.getCompetition(competitionId);
            if(competition.isPresent()) {
                email.appointJudgeToCompetitionMessage(user, competition.get(), userDao.getUserById(competition.get().getAuthor()).get());
            }
        }

        return "success";
    }

    @RequestMapping(value = "/add-participants/{competitionId}")
    public ModelAndView addParticipants(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception{
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/addParticipantToCompetition");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("participants", participantDao.getAllParticipantOfCompetition(competitionId));

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            modelAndView.addObject("competition", competition.get());
            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/add-participant-info/{userId}/{competitionId}")
    public ModelAndView addParticipants(@PathVariable Integer userId, @PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception{
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/addParticipantInformation");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        Optional<User> participant = userDao.getUserById(userId);
        if(competition.isPresent() && participant.isPresent()) {
            modelAndView.addObject("competition", competition.get());
            modelAndView.addObject("participant", participant.get());
            modelAndView.addObject("categories", participantDao.getAllWeightCategories());
            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/getRegionLike", method = RequestMethod.GET)
    public @ResponseBody List<Region> getRegionLike(@RequestParam String term, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        return userDao.getRegionLike(term, 9);
    }

    @RequestMapping(value = "/newRegion", method = RequestMethod.POST)
    public @ResponseBody String newRegion(@RequestParam String regionName, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        try{
            userDao.addNewRegion(regionName);
        }catch(Exception e) {
            return e.getMessage();
        }

        return "success";
    }

    @RequestMapping(value = "/deleteParticipantFromCompetition/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String deleteParticipantFromCompetition(@PathVariable Integer competitionId, @RequestParam Integer participantId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                participantDao.deleteParticipantFromCompetition(participantId, competitionId);
                return "success";
            }
        }
        return "error";
    }

    @RequestMapping(value = "/getUsersLikeWhichNotInCompetition/{competitionId}", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsersLikeWhichNotInCompetition(@PathVariable Integer competitionId, @RequestParam String term, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        return participantDao.getUsersLikeWhichNotInCompetition(term, competitionId, 9);
    }

    @RequestMapping(value = "/AddParticipantToCompetition/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String addParticipantToCompetition(@PathVariable Integer competitionId, @RequestParam String participantEmail,
                                                            HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        Optional<User> participant = userDao.getUserByEmail(participantEmail);

        if(competition.isPresent() && participant.isPresent()) {
            return "/moder/add-participant-info/" + participant.get().getUserId() + "/" + competition.get().getId();
        }

        throw new Exception("Resources not found");
    }

    @RequestMapping(value = "/insertParticipantToCompetition/{participantId}/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String insertParticipantToCompetition(@PathVariable Integer participantId, @PathVariable Integer competitionId,
                                                               @RequestParam Integer category,
                                                               @RequestParam String region,
                                                               @RequestParam Boolean ownParticipation,
                                                               @RequestParam Float sq,
                                                               @RequestParam Float bp,
                                                               @RequestParam Float dl,
                                                               HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        Optional<User> participant = userDao.getUserById(participantId);
        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute("user");

        if(competition.isPresent() && participant.isPresent() && competition.get().getAuthor() == user.getUserId()) {
            Integer own = 0;
            if(ownParticipation) {
                own = 1;
            }
            participantDao.addParticipantToCompetition(participantId, competitionId, category, region, sq, bp, dl, own);
            return "success";
        }

        return "error";
    }

    @RequestMapping(value = "/createNewUser/{competitionId}")
    public ModelAndView createNewUser(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/createNewUser");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = "/new-user/{competitionId}", method = RequestMethod.POST)
    @ResponseBody
    public String newUser(@PathVariable Integer competitionId, @RequestParam String participantJson, @RequestParam Boolean addParticipantToCompetition,
                          HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final User user = serializer.fromJson(participantJson, User.class);
        String userPassword = CommonUtils.generateRandomPassword(8);
        user.setPassword(CommonUtils.md5Hex(userPassword));

        userDao.createUser(user);

        if(addParticipantToCompetition) {
            final Optional<User> createdUser = userDao.getUserByCredentials(user);
            if(createdUser.isPresent()) {
                userDao.participateTheCompetition(competitionId, createdUser.get().getUserId());
            }
        }

        email.sendRegisterEmail(user);

        return "success";
    }
}
