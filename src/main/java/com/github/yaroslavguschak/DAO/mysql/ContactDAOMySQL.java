package com.github.yaroslavguschak.DAO.mysql;
import com.github.yaroslavguschak.DAO.ContactDAO;
import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.User;
import com.github.yaroslavguschak.entityrequest.ContactRequest;
import com.github.yaroslavguschak.entityrequest.SearchRequest;
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
public class ContactDAOMySQL implements ContactDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly=true)
    public List<Contact> findAllContacts() {
        return jdbcTemplate.query("select * from contacts",
                new ContactRowMapper());
    }


    @Transactional(readOnly=true)
    private List<Contact> findByFirstName(final String searchInput, final Integer userId) {
        final String param = searchInput + "%";
        final String paramPhone = "%" + searchInput + "%";

        return jdbcTemplate.query("select * from contacts WHERE ( firstName  LIKE ? " +
                        "OR lastName LIKE ? " +
                        "OR patronymicName LIKE ? " +
                        "OR address LIKE ? " +
                        "OR email LIKE ? " +
                        "OR cellPhone LIKE ? " +
                        "OR homeNumber LIKE ? )" +
                        "AND userId = ?",
                new Object[] {param, param, param, param, param, paramPhone, paramPhone, userId},
                new ContactRowMapper());
    }


    @Override
    public List<Contact> findContactsByAllFieds(SearchRequest searchRequest, User user) {
        List<Contact> searchResult = findByFirstName(searchRequest.getSearchTextInput(), user.getId());


        return searchResult;
    }



    @Override
    @Transactional(readOnly=true)
    public List<Contact> findContactsByUserId(int id) {
        return jdbcTemplate.query("select * from contacts WHERE userId = ?",
                new Object[] {id}, new ContactRowMapper());
    }

    @Override
    @Transactional(readOnly=true)
    public Contact findContactById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from contacts where id=?",
                new Object[]{id}, new ContactRowMapper());
    }

    @Override
    public void updateContact(final ContactRequest contact, User user){
        final String sql =
                "update contacts SET firstName = ?, " +
                                    "lastName = ?, " +
                                    "patronymicName = ?, " +
                                    "cellPhone = ?, " +
                                    "homeNumber = ?, " +
                                    "address = ?, " +
                                    "email = ? " +
                                "where id = ?";

        jdbcTemplate.update(sql, new Object[]{
                                    contact.getFirstName(),
                                    contact.getLastName(),
                                    contact.getPatronymicName(),
                                    contact.getCellPhone(),
                                    contact.getHomeNumber(),
                                    contact.getAddress(),
                                    contact.getEmail(),
                                 contact.getId()
        });

        user.setContacts(this.findContactsByUserId(user.getId()));
    }



    @Override
    public void deleteContactById(Integer contactId, User user){
        final String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, contactId);
        user.setContacts(this.findContactsByUserId(user.getId()));
    }





    @Override
    public Contact createContact(final Contact contact, User user) {

        final String sql = "insert into contacts(firstName, lastName, patronymicName, cellPhone, homeNumber," +
                "address, email, userId) values(?,?,?,?,?,?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getLastName());
                ps.setString(3, contact.getPatronymicName());
                ps.setString(4, contact.getCellPhone());
                ps.setString(5, contact.getHomeNumber());
                ps.setString(6, contact.getAddress());
                ps.setString(7, contact.getEmail());
                ps.setInt(8, user.getId());

                return ps;
            }
        }, holder);

        int newContactId = holder.getKey().intValue();
        contact.setId(newContactId);
        user.getContacts().add(contact);
        return contact;
    }

}
