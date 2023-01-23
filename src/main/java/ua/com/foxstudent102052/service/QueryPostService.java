package ua.com.foxstudent102052.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.com.foxstudent102052.dao.interfaces.PostDao;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryPostService {
    private final PostDao postDAO;

    public void executeQuery(String query) throws DataAccessException {
        postDAO.doPost(query);
    }
}
