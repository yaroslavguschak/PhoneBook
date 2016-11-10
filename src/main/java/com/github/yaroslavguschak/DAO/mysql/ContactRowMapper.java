package com.github.yaroslavguschak.DAO.mysql;

import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
@Profile("mysql")
public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setFirstName(rs.getString("firstName"));
        contact.setLastName(rs.getString("lastName"));
        contact.setPatronymicName(rs.getString("patronymicName"));
        contact.setCellPhone(rs.getString("cellPhone"));
        contact.setHomeNumber(rs.getString("homeNumber"));
        contact.setAddress(rs.getString("address"));
        contact.setEmail(rs.getString("email"));
        return contact;
    }
}
