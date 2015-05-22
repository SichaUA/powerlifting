package com.powerlifting.controllers.moder;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.*;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.ParticipantDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.dao.JudgeDao;
import com.powerlifting.mail.Email;
import com.powerlifting.reports.ReportsGenerating;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
    @Autowired private ReportsGenerating reportsGenerating;

    @RequestMapping(value = "/createCompetition")
    public ModelAndView createCompetition(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/competitionCreateForm");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("ageGroups", competitionDao.getAllAgeGroups());

        return modelAndView;
    }

    @RequestMapping(value = "/new-competition", method = RequestMethod.POST)
    public @ResponseBody String newUser(@RequestParam String competitionJson,@RequestParam String ageGroups,
                                        HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        final Competition competition = serializer.fromJson(competitionJson, Competition.class);
        final List<Boolean> ageGroupsList = serializer.fromJson(ageGroups, ArrayList.class);
        HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");

        Integer competitionId = competitionDao.createNewCompetitionReturningId(competition, user.getUserId());

        List<Integer> checkedAgeGroups = new ArrayList<>();
        for(int i = 0; i < ageGroupsList.size(); i++) {
            Boolean currAgeGroup = ageGroupsList.get(i);
            if(currAgeGroup) {
                checkedAgeGroups.add(i+1);
            }
        }

        competitionDao.addAgeGroupsToCompetition(competitionId, checkedAgeGroups);

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
    public @ResponseBody String addJudgeToCompetition(@PathVariable Integer competitionId, @RequestParam Integer judgeId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        if(competition.isPresent()) {
            HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            if(competition.get().getAuthor() == user.getUserId()) {
                judgeDao.addJudgeToCompetition(judgeId, competitionId);
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
    public @ResponseBody String assignUserToJudge(@PathVariable Integer competitionId, @RequestParam Integer userId,
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
                judgeDao.assignUserToJudge(userId, categoryId, categoryDate);

                if(addJudgeToCompetition) {
                    judgeDao.addJudgeToCompetition(userId, competitionId);
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

        final Integer userId = judgeDao.createNewJudgeAsUserReturningId(judge);

        User user = new User();
        user.setEmail(judge.getEmail());
        user.setPassword(judge.getPassword());
        email.moderRegisterUserEmail(user, judgePassword);

        judgeDao.assignUserToJudge(userId, judge.getCategory(), judge.getAssignmentDate());

        if(addJudgeToCompetition) {
            judgeDao.addJudgeToCompetition(userId, competitionId);
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
            modelAndView.addObject("ageGroups", competitionDao.getAgeGroupsOfCompetition(competitionId));
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
    public @ResponseBody String addParticipantToCompetition(@PathVariable Integer competitionId, @RequestParam Integer participantId,
                                                            HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        Optional<User> participant = userDao.getUserById(participantId);

        if(competition.isPresent() && participant.isPresent()) {
            return "/moder/add-participant-info/" + participant.get().getUserId() + "/" + competition.get().getId();
        }

        throw new Exception("Resources not found");
    }

    @RequestMapping(value = "/insertParticipantToCompetition/{participantId}/{competitionId}", method = RequestMethod.POST)
    public @ResponseBody String insertParticipantToCompetition(@PathVariable Integer participantId, @PathVariable Integer competitionId,
                                                               @RequestParam String participantAddingInfoJson,
                                                               HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        final ParticipantAddingInfo participantAddingInfo = serializer.fromJson(participantAddingInfoJson, ParticipantAddingInfo.class);
        response.setContentType("text/html; charset=UTF-8");

        Optional<Competition> competition = competitionDao.getCompetition(competitionId);
        Optional<User> participant = userDao.getUserById(participantId);
        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute("user");

        if(competition.isPresent() && participant.isPresent() && competition.get().getAuthor() == user.getUserId()) {
            participantDao.addParticipantToCompetition(participantId, competitionId, participantAddingInfo);

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

        final Integer userId = userDao.createUserReturningId(user);
        email.sendRegisterEmail(user);

        if(addParticipantToCompetition) {
            final Optional<Competition> competition = competitionDao.getCompetition(competitionId);
            if(competition.isPresent()) {
                return "/moder/add-participant-info/" + userId + "/" + competition.get().getId();
            }
        }

        return "success";
    }

    @RequestMapping(value = "/splitParticipantsIntoSequences/{competitionId}")
    public ModelAndView splitParticipantsIntoSequences(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/participantSequences");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        Optional<Competition> competition = competitionDao.getCompetition(competitionId);

        if(competition.isPresent()) {
            modelAndView.addObject("notUsedWeightGroups", competitionDao.getAllAgeGroupAndWeightCategoriesNotUsedInSequences(competitionId).size());

            return modelAndView;
        }
        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/getSequences/{competitionId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSequences(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        return serializer.toJson(competitionDao.getCompetitionSequences(competitionId));
    }

    @RequestMapping(value = "/deleteSequence/{sequenceId}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSequence(@PathVariable Integer sequenceId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        competitionDao.deleteSequence(sequenceId);

        return "success";
    }

    @RequestMapping(value = "/addNewSequence/{competitionId}")
    public ModelAndView addNewSequence(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/addNewSequence");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        Optional<Competition> competition = competitionDao.getCompetition(competitionId);

        if(competition.isPresent()) {
            modelAndView.addObject("competition", competition.get());
            return modelAndView;
        }
        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/getAgeGroupsAndWeightCategories/{competitionId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAgeGroups(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        List<AgeGroupWithWeightCategory> allAgeGroupAndWeightCategoriesNotUsedInSequences = competitionDao.getAllAgeGroupAndWeightCategoriesNotUsedInSequences(competitionId);
        int number = 0;
        for(Iterator<AgeGroupWithWeightCategory> i = allAgeGroupAndWeightCategoriesNotUsedInSequences.iterator(); i.hasNext(); number++) {
            i.next().setId(number);
        }

        return serializer.toJson(allAgeGroupAndWeightCategoriesNotUsedInSequences);
    }

    @RequestMapping(value = "/addNewSequence/{competitionId}", method = RequestMethod.POST)
    @ResponseBody
    public String getWeightCategories(@PathVariable Integer competitionId, @RequestParam String newSequenceJSON, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        final AddSequenceRequest newSequence = serializer.fromJson(newSequenceJSON, AddSequenceRequest.class);

        Integer sequenceId = competitionDao.addNewSequenceWithCategories(newSequence, competitionId);

        Integer groupId = competitionDao.insertFirstSequenceGroup(sequenceId);
        competitionDao.insertAllSequenceParticipantToFirstGroup(sequenceId, groupId);

        return "success";
    }

    @RequestMapping(value = "/addJudgesToSequence/{sequenceId}")
    public ModelAndView addJudgesToSequence(@PathVariable Integer sequenceId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/addJudgesToSequence");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        Optional<Sequence> sequence = competitionDao.getSequenceById(sequenceId);
        if(sequence.isPresent()) {
            modelAndView.addObject("sequence", sequence.get());
            modelAndView.addObject("alreadyJudges", judgeDao.getJudgesOfSequence(sequenceId));
            modelAndView.addObject("competitionJudge", judgeDao.getCompetitionJudgeWhichNotInSequence(sequenceId));
            modelAndView.addObject("judgeCategories", judgeDao.getAllCategory());
            modelAndView.addObject("judgeTypes", judgeDao.getAllJudgeTypes());
            modelAndView.addObject("competitionId", competitionDao.getCompetitionIdBySequence(sequenceId));

            return modelAndView;
        }
        throw new Exception("Sequence not found");
    }

    @RequestMapping(value = "/addJudgeToSequence/{sequenceId}", method = RequestMethod.POST)
    @ResponseBody
    public String addJudgeToSequence(@PathVariable Integer sequenceId, @RequestParam Integer judgeId, @RequestParam Integer typeId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        judgeDao.addJudgeToSequence(sequenceId, judgeId, typeId);

        return "success";
    }

    @RequestMapping(value = "/deleteJudgeFromSequence/{sequenceId}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteJudgeFromSequence(@PathVariable Integer sequenceId, @RequestParam Integer judgeId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        judgeDao.deleteJudgeFromSequence(sequenceId, judgeId);

        return "success";
    }

    @RequestMapping(value = "/splitIntoGroups/{sequenceId}")
    public ModelAndView splitIntoGroups(@PathVariable Integer sequenceId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/splitSequenceIntoGroups");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        Optional<Sequence> sequence = competitionDao.getSequenceById(sequenceId);
        if(sequence.isPresent()) {
            /*modelAndView.addObject("sequence", sequence.get());
            modelAndView.addObject("alreadyJudges", judgeDao.getJudgesOfSequence(sequenceId));
            modelAndView.addObject("competitionJudge", judgeDao.getCompetitionJudgeWhichNotInSequence(sequenceId));
            modelAndView.addObject("judgeCategories", judgeDao.getAllCategory());
            modelAndView.addObject("judgeTypes", judgeDao.getAllJudgeTypes());*/
            Integer groupsCount = competitionDao.getSequenceGroupCount(sequenceId);

            modelAndView.addObject("groupCount", groupsCount);
            modelAndView.addObject("participants", competitionDao.getAllSequenceParticipant(sequenceId));

            return modelAndView;
        }
        throw new Exception("Sequence not found");
    }

    @RequestMapping(value = "/changeSequenceGroupCount/{sequenceId}", method = RequestMethod.POST)
    @ResponseBody
    public String changeSequenceGroupCount(@PathVariable Integer sequenceId, @RequestParam Integer groupCount, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final Integer existsGroup = competitionDao.getSequenceGroupCount(sequenceId);
        if(existsGroup > groupCount) {
            //delete groups
            List<Integer> groups = new ArrayList<>();
            for(int i = existsGroup; i > groupCount; i--) {
                groups.add(i);
            }
            competitionDao.deleteGroupFromSequence(sequenceId, groups);
        }else if(existsGroup < groupCount) {
            //add groups
            List<Integer> groups = new ArrayList<>();
            for(int i = existsGroup + 1; i <= groupCount; i++) {
                groups.add(i);
            }

            competitionDao.insertGroupsToSequence(sequenceId, groups);
        }

        return "success";
    }

    @RequestMapping(value = "/changeParticipantGroup/{sequenceId}", method = RequestMethod.POST)
    @ResponseBody
    public String changeParticipantGroup(@PathVariable Integer sequenceId, @RequestParam Integer participantId,
                                         @RequestParam Integer groupNum, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        /*competitionDao.deleteParticipantFromGroup(sequenceId, participantId);
        competitionDao.insertParticipantToGroup(sequenceId, groupNum, participantId);*/
        competitionDao.updateParticipantGroup(sequenceId, groupNum, participantId);

        return "success";
    }

    @RequestMapping(value = "/competitionControlPage/{competitionId}")
    public ModelAndView startCompetition(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/competitionControlPage");

        modelAndView.addObject("competitionId", competitionId);

        return modelAndView;
    }

    @RequestMapping(value = "/weighingParticipants/{competitionId}")
    public ModelAndView weighingParticipants(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/weighingParticipants");

        modelAndView.addObject("sequences", competitionDao.getCompetitionSequences(competitionId));

        return modelAndView;
    }

    @RequestMapping(value = "/getSequenceParticipants/{sequenceId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSequenceParticipants(@PathVariable Integer sequenceId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        List<ParticipantInfo> participants = competitionDao.getSequenceParticipant(sequenceId);

        return serializer.toJson(participants);
    }

    @RequestMapping(value = "/changeParticipantWeight", method = RequestMethod.POST)
    @ResponseBody
    public String changeParticipantWeight(@RequestParam Integer groupParticipantId, @RequestParam Float weight, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        if(weight.isNaN()) return "Not a Number";

        competitionDao.updateParticipantWeight(groupParticipantId, weight);

        ParticipantStatus groupParticipantStatus = competitionDao.getGroupParticipantStatus(groupParticipantId);

        if(groupParticipantStatus.getStatusId() == 1)
            return "Ok";

        return "Disqualified";
    }

    @RequestMapping(value = "/setFirstAttemptWeight", method = RequestMethod.POST)
    @ResponseBody
    public String setFirstAttemptWeight(@RequestParam Integer groupParticipantId, @RequestParam String type,
                                        @RequestParam Float weight, @RequestParam Integer attemptNum, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        if(weight.isNaN()) return "Not a Number";

        if(type.equals("SQ")){
            competitionDao.deleteAttempt(groupParticipantId, attemptNum, 1/*SQ*/);
            competitionDao.setAttempt(groupParticipantId, attemptNum, weight, 1/*SQ*/, 1/*firstDeclaredWeight*/);
        }else if(type.equals("BP")){
            competitionDao.deleteAttempt(groupParticipantId, attemptNum, 2/*BP*/);
            competitionDao.setAttempt(groupParticipantId, attemptNum, weight, 2/*BP*/, 1/*firstDeclaredWeight*/);
        }else{
            competitionDao.deleteAttempt(groupParticipantId, attemptNum, 3/*DL*/);
            competitionDao.setAttempt(groupParticipantId, attemptNum, weight, 3/*DL*/, 1/*firstDeclaredWeight*/);
        }

        return "Ok";
    }

    @RequestMapping(value = "/changeAttemptStatus", method = RequestMethod.POST)
    @ResponseBody
    public String changeAttemptStatus(@RequestParam Integer groupParticipantId, @RequestParam String type,
                                        @RequestParam Float weight, @RequestParam Integer attemptNum,
                                      @RequestParam Integer status, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        if(weight.isNaN()) return "Not a Number";

//        Refuse
        if(status == -1) {
            competitionDao.updateGroupParticipantStatus(groupParticipantId, 1);
            if(type.equals("SQ")){
                competitionDao.updateAttemptStatus(groupParticipantId, attemptNum, weight, 1/*SQ*/, 1);
            }else if(type.equals("BP")){
                competitionDao.updateAttemptStatus(groupParticipantId, attemptNum, weight, 2/*BP*/, 1);
            }else{
                competitionDao.updateAttemptStatus(groupParticipantId, attemptNum, weight, 3/*DL*/, 1);
            }

            return "Ok";
        }

//        Change attempt status
        if(type.equals("SQ")){
            competitionDao.updateAttemptStatus(groupParticipantId, attemptNum, weight, 1/*SQ*/, status);
        }else if(type.equals("BP")){
            competitionDao.updateAttemptStatus(groupParticipantId, attemptNum, weight, 2/*BP*/, status);
        }else{
            competitionDao.updateAttemptStatus(groupParticipantId, attemptNum, weight, 3/*DL*/, status);
        }

        return "Ok";
    }

    @RequestMapping(value = "/changeGroupParticipantStatus", method = RequestMethod.POST)
    @ResponseBody
    public String changeGroupParticipantStatus(@RequestParam Integer groupParticipantId, @RequestParam Integer status, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        competitionDao.updateGroupParticipantStatus(groupParticipantId, status);

        return "Ok";
    }

    @RequestMapping(value = "/startCompetitionPage/{competitionId}")
    public ModelAndView startCompetitionPage(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/startCompetitionPage");

        modelAndView.addObject("sequences", competitionDao.getCompetitionSequences(competitionId));
        Competition competition = competitionDao.getCompetition(competitionId).get();
        modelAndView.addObject("competition", competition);

        return modelAndView;
    }

    @RequestMapping(value = "/competitionReports/{competitionId}")
    public ModelAndView competitionReports(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("moderator/competitionReports");


        return modelAndView;
    }

    /*@RequestMapping(value = "/createMainProtocol/{competitionId}"*//*, method = RequestMethod.POST*//*)
    public class ProtocolCreateAndDownload {
        private static final int BUFFER_SIZE = 4096;



    }*/

    @RequestMapping(value = "/createMainProtocol/{competitionId}", method = RequestMethod.GET)
    public void doDownload(@PathVariable Integer competitionId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int BUFFER_SIZE = 4096;

        response.setContentType("text/html; charset=UTF-8");

        String s = reportsGenerating.generateMainProtocol(competitionId);

        final String filePath = "C:\\powerlifting\\powerlifting\\src\\main\\resources\\public\\reports\\" + competitionId + ".xls";

        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);

        // construct the complete absolute path of the file
        File downloadFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    /*public String createMainProtocol(@PathVariable Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        String s = reportsGenerating.generateMainProtocol(competitionId);

        return "success";
    }*/

    @RequestMapping(value = "/getCompetitionGroupParticipants/{groupId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getCompetitionGroupParticipants(@PathVariable Integer groupId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        List<ParticipantInfo> participants = competitionDao.getGroupParticipants(groupId);

        return serializer.toJson(participants);
    }

    @RequestMapping(value = "/getCompetitionSequenceGroups/{sequenceId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getCompetitionSequenceGroups(@PathVariable Integer sequenceId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        List<Group> groups = competitionDao.getSequenceGroups(sequenceId);

        return serializer.toJson(groups);
    }

    @RequestMapping(value = "/startBroadcasting", method = RequestMethod.POST)
    @ResponseBody
    public String startBroadcasting(@RequestParam Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        competitionDao.startBroadcasting(competitionId);

        return "success";
    }

    @RequestMapping(value = "/stopBroadcasting", method = RequestMethod.POST)
    @ResponseBody
    public String stopBroadcasting(@RequestParam Integer competitionId, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        competitionDao.stopBroadcasting(competitionId);

        return "success";
    }

    @RequestMapping(value = "/updateBroadcastingInfo", method = RequestMethod.POST)
    @ResponseBody
    public String updateBroadcastingInfo(@RequestParam Integer competitionId, @RequestParam Integer sequenceId,
                                         @RequestParam Integer groupId, @RequestParam String type,HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        if(type.equals("SQ")){
            competitionDao.updateBroadcastingInfo(competitionId, sequenceId, groupId, 1);
        }else if(type.equals("BP")){
            competitionDao.updateBroadcastingInfo(competitionId, sequenceId, groupId, 2);
        }else{
            competitionDao.updateBroadcastingInfo(competitionId, sequenceId, groupId, 3);
        }

        return "success";
    }
}
