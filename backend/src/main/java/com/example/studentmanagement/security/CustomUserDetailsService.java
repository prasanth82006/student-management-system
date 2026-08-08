package com.example.studentmanagement.security;

import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.AdminRepository;
import com.example.studentmanagement.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByEmail(username)
                .<UserDetails>map(student -> student)
                .or(() -> adminRepository.findByEmail(username).map(admin -> (UserDetails) admin))
                .or(() -> teacherRepository.findByEmail(username).map(teacher -> (UserDetails) teacher))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
