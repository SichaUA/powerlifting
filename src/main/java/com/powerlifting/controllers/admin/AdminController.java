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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired private AdminDao adminDao;

    @RequestMapping("/assignUserToNewStatus")
    public ModelAndView assignUserToNewStatus(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("admin/assignUserToNewStatus");

        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = "/new-moder", method = RequestMethod.POST)
    @ResponseBody
    public String newModer(@RequestParam String moderEmail) {
        adminDao.assignUserToNewStatus(moderEmail, 2);

        return "success";
    }

    @RequestMapping(value = "/new-admin", method = RequestMethod.POST)
    @ResponseBody
    public String newAdmin(@RequestParam String adminEmail) {
        adminDao.assignUserToNewStatus(adminEmail, 3);

        return "success";
    }
}
