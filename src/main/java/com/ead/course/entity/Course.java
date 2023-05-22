package com.ead.course.entity;


import com.ead.course.entity.enumerated.CourseLevel;
import com.ead.course.entity.enumerated.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Course implements Serializable {

    private static final long serialVersionUID = -2778793552214178453L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @Column(nullable = false)
    private UUID courseInstructor;

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private final Set<Module> modules = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now(ZoneId.of("UTC"));
        updatedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return courseId.equals(course.courseId);
    }

    @Override
    public int hashCode() {
        return courseId.hashCode();
    }
}
