package com.github.yaroslavguschak.DAO;

import com.github.yaroslavguschak.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserDAO {
    @Transactional(readOnly=true)
    List<User> findAllUsers();

    @Transactional(readOnly=true)
    User findUserById(int id);

    @Transactional(readOnly=true)
    User findUserByLogin(String login);

    @Transactional(readOnly=true)
    String getPassHashByLogin(String login);

    @Transactional(readOnly=true)
    Integer getUserCountByLogin(String login);

    boolean isUserExistByLogin(String userLogin);

    User createUser(User user);
}
