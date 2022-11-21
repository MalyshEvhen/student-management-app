package ua.com.foxstudent102052.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxstudent102052.mapper.GroupMapper;
import ua.com.foxstudent102052.mapper.StudentMapper;
import ua.com.foxstudent102052.model.GroupDto;
import ua.com.foxstudent102052.model.StudentDto;
import ua.com.foxstudent102052.repository.GroupRepository;
import ua.com.foxstudent102052.repository.RepositoryException;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private static final String GROUP_WITH_ID_NOT_EXIST = "Group with id %d doesn't exist";
    private static final String GROUP_WITH_ID_EXISTS = "Group with id %d already exists";
    private final GroupRepository groupRepository;

    @Override
    public void addGroup(GroupDto groupDto) throws ServiceException {
        var newGroup = GroupMapper.toGroup(groupDto);

        try {
            groupRepository.addGroup(newGroup);
            var lastGroupFromDB = groupRepository.getLastGroup();

            if (newGroup.equals(lastGroupFromDB)) {
                log.info("Group with id {} was added", lastGroupFromDB.getGroupId());
            } else {
                log.info("Group with id {} was added", lastGroupFromDB.getGroupId());
            }

        } catch (RepositoryException e) {
            String msg = String.format(GROUP_WITH_ID_EXISTS, groupDto.getId());
            log.error(msg, e);

            throw new ServiceException(msg, e);
        }
    }

    @Override
    public List<GroupDto> getAllGroups() throws ServiceException {

        try {
            return groupRepository.getAllGroups()
                .stream()
                .map(GroupMapper::toDto)
                .toList();

        } catch (RepositoryException e) {
            String msg = "There are no groups in the database";
            log.error(msg, e);

            throw new ServiceException(msg, e);
        }
    }

    @Override
    public GroupDto getGroupById(int groupId) throws ServiceException {

        try {
            return GroupMapper.toDto(groupRepository.getGroupById(groupId));

        } catch (RepositoryException e) {
            String msg = String.format(GROUP_WITH_ID_NOT_EXIST, groupId);
            log.error(msg, e);

            throw new ServiceException(msg, e);
        }
    }

    @Override
    public List<GroupDto> getGroupsSmallerThen(int numberOfStudents) throws ServiceException {

        try {
            return groupRepository.getGroupsSmallerThen(numberOfStudents)
                .stream()
                .map(GroupMapper::toDto)
                .toList();

        } catch (RepositoryException e) {
            String msg = String.format("There are no groups with number of students less then %d",
                numberOfStudents);
            log.error(msg, e);

            throw new ServiceException(msg, e);
        }
    }

    @Override
    public GroupDto getGroupByName(String groupName) throws ServiceException {

        try {
            return GroupMapper.toDto(groupRepository.getGroupByName(groupName));

        } catch (RepositoryException e) {
            String msg = String.format("Group with name %s doesn't exist", groupName);
            log.error(msg, e);

            throw new ServiceException(msg, e);
        }
    }

    @Override
    public List<StudentDto> getStudentsByGroup(int groupId) throws ServiceException {

        try {
            return groupRepository.getStudentsByGroup(groupId)
                .stream()
                .map(StudentMapper::toDto)
                .toList();

        } catch (RepositoryException e) {
            String msg = String.format("There are no students in group with id %d", groupId);
            log.error(msg, e);

            throw new ServiceException(msg, e);
        }
    }
}
