package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Builder;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
@SQLDelete(sql = "UPDATE students SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Student extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private String studentId; // Custom ID like STU20260001

    @Column(nullable = false)
    private boolean deleted = false; // Soft delete flag

    private String profilePictureUrl;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    /** Set for students who can sign in to the portal. */
    @JsonIgnore
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Department is required")
    @Column(nullable = false)
    private String department;

    @NotBlank(message = "Course is required")
    @Column(nullable = false)
    private String course;

    @Column(name = "enrollment_year", nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Double cgpa;

    @PrePersist
    public void prePersist() {
        if (this.studentId == null) {
            // Generate a basic custom ID. E.g., STU + Year + random 4 digits
            int randomNum = (int) (Math.random() * 9000) + 1000;
            this.studentId = "STU" + java.time.Year.now().getValue() + randomNum;
        }
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(new SimpleGrantedAuthority("ROLE_STUDENT")); }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return !deleted; }
}
