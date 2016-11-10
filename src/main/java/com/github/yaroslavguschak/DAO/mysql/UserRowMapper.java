package com.github.yaroslavguschak.DAO.mysql;

import com.github.yaroslavguschak.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
@Profile("mysql")
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setFullName(rs.getString("fullName"));
        user.setPasswordhash(rs.getString("passwordhash"));
        return user;
    }
}
