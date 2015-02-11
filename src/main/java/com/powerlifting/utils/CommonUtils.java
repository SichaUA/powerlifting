package com.powerlifting.utils;

import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CommonUtils {

    private static List<String> getAllFilesInDirectory(String path) {
        List<String> files = new ArrayList<>();
        File[] realFiles;
        final File directory = new File(path);

        realFiles = directory.listFiles();

        for(int i = 0; i < realFiles.length; i++) {
            if(realFiles[i].isFile()) {
                files.add(realFiles[i].getName());
            }
        }

        return files;
    }

    public static String getRandomBackground() {
        final List<String> files = CommonUtils.getAllFilesInDirectory("C:\\powerlifting\\powerlifting\\src\\" +
                                                                        "main\\resources\\public\\img\\backgrounds");

        Random random = new Random();

        return files.get(random.nextInt(files.size()));
    }

    public static <T> Optional<T> selectOne(JdbcTemplate jdbc, String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            return Optional.of(jdbc.queryForObject(sql, rowMapper, params));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public static void addUserToModel(HttpServletRequest httpServletRequest, ModelAndView modelAndView) {
        HttpSession session = httpServletRequest.getSession();

        final User user = (User)session.getAttribute("user");
        if(user != null) {
            modelAndView.addObject("user", user);
        }
    }

    public static String md5Hex(String st) {
        return DigestUtils.md5Hex(st);
    }

    public static void updateUserInSession(HttpServletRequest httpServletRequest, User user) {
        final HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", user);
    }

    public String generateRandomPassword() {
        SecureRandom secureRandom = new SecureRandom();

        String password = new BigInteger(130, secureRandom).toString();
        return password;
    }
}
