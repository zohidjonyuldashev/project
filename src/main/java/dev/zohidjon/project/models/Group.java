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
public class Group {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private int studentCount;
}
