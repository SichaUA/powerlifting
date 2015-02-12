package com.powerlifting.config;

import com.powerlifting.dao.AdminDao;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.UserDao;
import com.powerlifting.dao.rowMappers.JudgeDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean public DataSource dataSource() {
        final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/powerlifting");
        driverManagerDataSource.setUsername("Ivan");
        driverManagerDataSource.setPassword("password");

        return driverManagerDataSource;
    }

    @Bean public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean public UserDao userDao(JdbcTemplate jdbcTemplate) {
        return new UserDao(jdbcTemplate);
    }

    @Bean public AdminDao adminDao(JdbcTemplate jdbcTemplate) {
        return new AdminDao(jdbcTemplate);
    }

    @Bean public CompetitionDao competitionDao(JdbcTemplate jdbcTemplate) {
        return new CompetitionDao(jdbcTemplate);
    }

    @Bean public JudgeDao judgeDao(JdbcTemplate jdbcTemplate) {
        return new JudgeDao(jdbcTemplate);
    }
}
