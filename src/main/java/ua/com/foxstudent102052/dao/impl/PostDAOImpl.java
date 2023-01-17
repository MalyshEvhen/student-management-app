package ua.com.foxstudent102052.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostDAOImpl implements ua.com.foxstudent102052.dao.interfaces.PostDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void doPost(String query) throws DataAccessException {
        jdbcTemplate.execute(query);
    }
}
