package com.powerlifting.controllers.registered;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.Title;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping(value = "/user")
public class RegisteredController {
    @Autowired private Gson serializer;
    @Autowired private UserDao userDao;
    @Autowired private CompetitionDao competitionDao;

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

            if(user.getRole() >= 2) {
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

    @RequestMapping("/profile")
    public ModelAndView profile(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("user/profile");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        final HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");
        List<Title> titles = userDao.getListOfTitles();
        for(Iterator<Title> i = titles.iterator(); i.hasNext(); ) {
            Title title = i.next();
            if(user.getTitle() == title.getTitleId()) {
                modelAndView.addObject("userTitle", title.getName());
            }
        }

        return modelAndView;
    }

    @RequestMapping("/edit-profile")
    public ModelAndView editProfile(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("user/editProfile");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);
        modelAndView.addObject("titles", userDao.getListOfTitles());

        return modelAndView;
    }

    @RequestMapping(value = "/upload-avatar", method = RequestMethod.POST)
    public String avatarUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        if (!file.isEmpty()) {
            final BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

            final HttpSession session = httpServletRequest.getSession();
            final User user = (User)session.getAttribute("user");

            final String suffix = user.getUserId() + ".png";
            final File destination = new File("C:\\powerlifting\\powerlifting\\src\\main\\resources\\public\\img\\avatars\\" + suffix);
            final File destinationArtifact = new File("C:\\powerlifting\\powerlifting\\out\\artifacts\\powerlifting\\exploded\\powerlifting-1.0.war\\WEB-INF\\classes\\public\\img\\avatars\\" + suffix);
            ImageIO.write(src, "png", destination);
            ImageIO.write(src, "png", destinationArtifact);

            userDao.updateUserImage(suffix, user.getUserId());
            user.setPhoto(suffix);

            CommonUtils.updateUserInSession(httpServletRequest, user);
        }
        return "redirect: /profile";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestParam String newPassword, HttpServletRequest httpServletRequest) {
        final HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");

        final String newPasswordMD5 = CommonUtils.md5Hex(newPassword);
        userDao.changeUserPassword(user.getUserId(), newPasswordMD5);

        return "success";
    }

    @RequestMapping(value = "/change-title", method = RequestMethod.POST)
    @ResponseBody
    public void changeTitle(@RequestParam Integer newTitle, HttpServletRequest httpServletRequest) {
        final HttpSession session = httpServletRequest.getSession();
        final User user = (User)session.getAttribute("user");

        userDao.changeUserTitle(user.getUserId(), newTitle);

        user.setTitle(newTitle);
        CommonUtils.updateUserInSession(httpServletRequest, user);
    }
}
