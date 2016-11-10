package com.github.yaroslavguschak.DAO.mysql;
import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.entity.User;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.jdbc.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Profile("mysql")
@Repository
@Component
public class UserDAOMySQL implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ContactDAOMySQL contactDAOMySQL;

    @Override
    @Transactional(readOnly=true)
    public List<User> findAllUsers() {
        List<User> users = jdbcTemplate.query("select * from users",
                new UserRowMapper());
        for (User u: users) {
            u.setContacts(contactDAOMySQL.findContactsByUserId(u.getId()));
        }
        return users;
    }

    @Override
    @Transactional(readOnly=true)
    public User findUserById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id=?",
                new Object[]{id}, new UserRowMapper());
    }

    @Override
    @Transactional(readOnly=true)
    public User findUserByLogin(String login) {
        User user = jdbcTemplate.queryForObject(
                "select * from users where login=?",
                new Object[]{login}, new UserRowMapper());
        user.setContacts(contactDAOMySQL.findContactsByUserId(user.getId()));
        return user;
    }


    @Override
    @Transactional(readOnly=true)
    public String getPassHashByLogin(String login) {
        return jdbcTemplate.queryForObject("select passwordhash from USERS where login = ?", String.class, login);
    }

    @Override
    @Transactional(readOnly=true)
    public Integer getUserCountByLogin(String login) {
        return jdbcTemplate.queryForObject("select count(*) from USERS where login = ?", Integer.class, login);
    }

    @Override
    public boolean isUserExistByLogin(String userLogin) {
        boolean isExist = false;
        isExist = getUserCountByLogin(userLogin) > 0;
        return isExist;
    }


    @Override
    public User createUser(final User user)
    {
        if(isUserExistByLogin(user.getLogin())){
            return null;
        }

        final String sql = "insert into users(login, fullName, passwordhash) values(?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getPasswordhash());
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        user.setId(newUserId);
        return user;
    }

}
