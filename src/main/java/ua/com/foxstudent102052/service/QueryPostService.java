package ua.com.foxstudent102052.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxstudent102052.dao.exceptions.DAOException;
import ua.com.foxstudent102052.dao.interfaces.PostDAO;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryPostService {
    private final PostDAO postDAO;

    public void executeQuery(String query) throws DAOException {
        postDAO.doPost(query);
    }
}
