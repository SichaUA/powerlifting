package com.powerlifting.mail;

import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;

import java.util.HashMap;
import java.util.Map;

public class Email {
    @Autowired private UserDao userDao;
    @Autowired private ApplicationMailer mailer;
    @Autowired private CompetitionDao competitionDao;

    public void sendRegisterEmail(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map messageParams = new HashMap<>();
                messageParams.put("user", userDao.getUserByCredentials(user).get());

                mailer.sendMail(user.getEmail(), "Welcome in POWERLIFTING!", "/registerMessage.ftl", messageParams);
            }
        }).start();
    }

    public void moderRegisterUserEmail(User user, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map messageParams = new HashMap<>();
                messageParams.put("user", userDao.getUserByCredentials(user).get());
                messageParams.put("password",password);

                mailer.sendMail(user.getEmail(), "Welcome in POWERLIFTING!", "/moderRegisterUserMessage.ftl", messageParams);
            }
        }).start();
    }

    public void appointJudgeToCompetitionMessage(User judge, Competition competition, User author) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map messageParams = new HashMap<>();
                messageParams.put("user", userDao.getUserByCredentials(judge).get());
                messageParams.put("competition", competitionDao.getCompetition(competition.getId()).get());
                messageParams.put("author", userDao.getUserByCredentials(author).get());

                mailer.sendMail(judge.getEmail(), "Congratulation, you was appointed as a judge!", "/appointJudgeToCompetitionMessage.ftl", messageParams);
            }
        }).start();
    }
}
