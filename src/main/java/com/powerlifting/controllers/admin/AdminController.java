package com.powerlifting.controllers.admin;

import com.google.gson.Gson;
import com.powerlifting.controllers.registered.model.AgeGroupWithWeightCategory;
import com.powerlifting.controllers.registered.model.Region;
import com.powerlifting.dao.AdminDao;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired private AdminDao adminDao;
    @Autowired private Gson serializer;

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

    @RequestMapping("/editRegions")
    public ModelAndView editRegions(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("admin/editRegions");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = "/getRegionsLike", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getRegionsLike(@RequestParam String term, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final List<Region> regionsLike = adminDao.getRegionsLike(term, 10);

        return serializer.toJson(regionsLike);
    }

    @RequestMapping(value = "/deleteRegion", method = RequestMethod.POST)
    @ResponseBody
    public String deleteRegion(@RequestParam String regionJson, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        final Region region = serializer.fromJson(regionJson, Region.class);
        try {
            adminDao.deleteRegion(region);
        }catch (Exception e) {
            return "error";
        }

        return "success";
    }

    @RequestMapping("/addNewRegion")
    public ModelAndView addNewRegion(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView("admin/addNewRegion");
        CommonUtils.addUserToModel(httpServletRequest, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = "/addRegion", method = RequestMethod.POST)
    @ResponseBody
    public String addRegion(@RequestParam String regionName, HttpServletRequest httpServletRequest, HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");

        try {
            adminDao.addNewRegion(regionName);
        }catch (Exception e) {
            return "error";
        }

        return "success";
    }
}
