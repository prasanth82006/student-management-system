package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.Admin;
import com.example.studentmanagement.entity.Teacher;
import com.example.studentmanagement.repository.AdminRepository;
import com.example.studentmanagement.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Creates the initial staff accounts. Students create their own records through registration. */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.findByEmail("admin@gmail.com").isEmpty()) {
            adminRepository.save(Admin.builder().firstName("System").lastName("Admin")
                    .email("admin@gmail.com").password(passwordEncoder.encode("admin123")).build());
        }
        if (teacherRepository.findByEmail("teacher@gmail.com").isEmpty()) {
            teacherRepository.save(Teacher.builder().firstName("Default").lastName("Teacher")
                    .email("teacher@gmail.com").password(passwordEncoder.encode("teacher123")).build());
        }
    }
}
