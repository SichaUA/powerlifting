package com.powerlifting.controllers.admin;

import com.google.gson.Gson;
import com.powerlifting.dao.AdminDao;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired private AdminDao adminDao;

    @RequestMapping("/moderRequests")
    public ModelAndView moderRequests(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("admin/modersRequests");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        modelAndView.addObject("moders", adminDao.getModerRequests());

        return modelAndView;
    }

    @RequestMapping(value = "/addModer", method = RequestMethod.POST)
    public String addModer(@RequestParam String studentJson) {
        ModelAndView modelAndView = new ModelAndView("admin/modersRequests");

        modelAndView.addObject("moders", adminDao.getModerRequests());

        return "success";
    }
}
