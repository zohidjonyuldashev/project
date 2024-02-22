package dev.zohidjon.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private String id;
    private String fullName;
    private LocalDateTime createdAt;
    private String groupID;
    private int age;
}
