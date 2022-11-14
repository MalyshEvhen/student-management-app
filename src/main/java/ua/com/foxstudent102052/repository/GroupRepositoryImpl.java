package ua.com.foxstudent102052.repository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxstudent102052.model.Group;
import ua.com.foxstudent102052.model.Student;

import java.util.List;

@Slf4j
public class GroupRepositoryImpl implements GroupRepository {
    private static DAOFactory daoFactory;
    
    public GroupRepositoryImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public void addGroup(@NonNull Group group) {
        daoFactory.doPost(String.format("""
                INSERT
                INTO groups (group_name) values ('%s');""",
            group.getGroupName()));
        log.info("Group: '{}' added successfully", group.getGroupName());
    }

    @Override
    public void removeGroupById(int groupId) {
        daoFactory.doPost(String.format("""
                DELETE
                FROM groups
                WHERE group_id = '%d';""",
            groupId));
        log.info("Group with id: '{}' removed successfully", groupId);
    }

    @Override
    public void updateGroupById(@NonNull Group group) {
        daoFactory.doPost(String.format("""
                UPDATE groups
                SET group_name = '%s'
                WHERE group_id = '%d';""",
            group.getGroupName(), group.getGroupId()));
        log.info("Group with id: '{}' updated successfully", group.getGroupId());
    }

    @Override
    public Group getGroupById(int groupId) {
        String query = String.format("""
                SELECT *
                FROM groups
                WHERE group_id = '%d';""",
            groupId);

        return daoFactory.getGroup(query);
    }

    @Override
    public Group getGroupByName(String groupName) {
        String query = String.format("""
                SELECT *
                FROM groups
                WHERE group_name = '%s';""",
            groupName);

        return daoFactory.getGroup(query);
    }

    @Override
    public List<Group> getAllGroups() {
        String query = "SELECT * FROM groups;";

        return daoFactory.getGroups(query);
    }

    @Override
    public List<Group> getGroupsSmallerThen(int numberOfStudents) {
        String query = String.format("""
                SELECT *
                FROM groups
                WHERE group_id
                NOT IN(
                    SELECT group_id
                    FROM students
                    WHERE group_id
                    IS NOT NULL)
                OR group_id IN(
                    SELECT group_id
                    FROM students
                    WHERE group_id
                    IS NOT NULL
                    GROUP BY group_id
                    HAVING COUNT(group_id) <= %d);""",
            numberOfStudents);
        
        return daoFactory.getGroups(query);
    }

    @Override
    public List<Student> getStudentsByGroup(int groupId) {
        String query = String.format("""
                SELECT *
                FROM students
                WHERE group_id = %d;""",
            groupId);
        
        return daoFactory.getStudents(query);
    }
}
