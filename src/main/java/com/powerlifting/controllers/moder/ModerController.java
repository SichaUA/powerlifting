package com.powerlifting.controllers.moder;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.Judge;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.ParticipantDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.dao.JudgeDao;
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
    @Autowired private ParticipantDao participantDao;
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
    public String newJudge(@PathVariable Integer competitionId, @RequestParam String judgeJson, @RequestParam Boolean addJudgeToCompetition, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final Judge judge = serializer.fromJson(judgeJson, Judge.class);
        String judgePassword = CommonUtils.generateRandomPassword(8);
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

        return participantDao.getUsersLikeWhichNotInCompetition(term, competitionId);
    }

    @RequestMapping(value = "/AddParticipantToCompetition/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String AddParticipantToCompetition(@PathVariable Integer competitionId, @RequestParam String participantEmail,
                                                            HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                participantDao.AddParticipantToCompetition(participantEmail, competitionId);
                return "success";
            }
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

        final Map messageParams = new HashMap<>();
        messageParams.put("user", user);
        mailer.sendMail(user.getEmail(), "Welcome in POWERLIFTING!", "/registerMessage.ftl", messageParams);

        return "success";
    }

    /*@RequestMapping(value = "/createAndAddParticipantToCompetition/{id}")
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
    }*/
}
