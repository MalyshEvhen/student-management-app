package ua.com.foxstudent102052.model.entity;

import lombok.Builder;

@Builder
public record Student(int studentId, int groupId, String firstName, String lastName) {
}
