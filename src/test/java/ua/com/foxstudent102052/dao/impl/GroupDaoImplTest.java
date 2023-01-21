package ua.com.foxstudent102052.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxstudent102052.dao.exceptions.DAOException;
import ua.com.foxstudent102052.dao.interfaces.GroupDao;
import ua.com.foxstudent102052.model.entity.Group;

@JdbcTest
@Sql({ "/scripts/ddl/Table_creation.sql", "/scripts/dml/testDB_Data.sql" })
class GroupDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Group> groupRowMapper = BeanPropertyRowMapper.newInstance(Group.class);
    private GroupDao groupDao;

    @BeforeEach
    void setUp() {
        groupDao = new GroupDaoImpl(jdbcTemplate, groupRowMapper);
    }

    @Test
    void MethodAddGroup_ShouldAddGroupToDb() throws DAOException {
        // given
        var group = Group.builder().groupName("New Group").build();
        groupDao.addGroup(group);

        // when
        var allGroups = groupDao.getAll();

        // then
        assertEquals(4, allGroups.size());
    }

    @Test
    void MethodGetGroups_ShouldReturnAllGroupsFromDb() throws DAOException {
        // when
        var allGroups = groupDao.getAll();

        // then
        assertEquals(3, allGroups.size());
    }

    @Test
    void MethodGetGroup_ById_ShouldReturnGroupById() throws DAOException {
        // given
        var expected = Group.builder().groupId(1).groupName("Group 1").build();

        // when
        var actual = groupDao.getGroupById(1).get();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void MethodGetGroup_ByName_ShouldReturnGroupByName() throws DAOException {
        // given
        var expected = Group.builder().groupId(1).groupName("Group 1").build();

        // when
        var actual = groupDao.getGroupByName("Group 1").get();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void MethodGetGroupsLessThen_ShouldReturnGroupLessCount() throws DAOException {
        var actual = groupDao.getGroupsLessThen(2);

        assertEquals(1, actual.size());
    }
}
