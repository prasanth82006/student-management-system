package com.example.studentmanagement.security;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.AdminRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.TeacherRepository;
import com.example.studentmanagement.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    /** Public registration creates a student record and its portal login together. */
    public AuthenticationResponse register(RegisterRequest request) {
        if (emailExists(request.getEmail())) throw new IllegalArgumentException("An account with this email already exists");
        if (request.getPassword() == null || request.getPassword().length() < 6) throw new IllegalArgumentException("Password must contain at least 6 characters");

        Student student = Student.builder()
                .firstName(request.getFirstname()).lastName(request.getLastname())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone()).department(request.getDepartment()).course(request.getCourse())
                .year(request.getYear()).cgpa(request.getCgpa()).build();
        String token = jwtService.generateToken(student);
        studentRepository.save(student);
        emailService.sendWelcomeEmail(student.getEmail(), student.getFirstName());
        return AuthenticationResponse.builder().token(token).role("STUDENT").build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails account = findAccount(request.getEmail());
        String role = account.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        if (request.getAccountType() != null && !role.equalsIgnoreCase(request.getAccountType())) {
            throw new IllegalArgumentException("This account must be signed in from the " + role.toLowerCase() + " login page");
        }
        return AuthenticationResponse.builder().token(jwtService.generateToken(account)).role(role).build();
    }

    private boolean emailExists(String email) {
        return studentRepository.findByEmail(email).isPresent() || adminRepository.findByEmail(email).isPresent() || teacherRepository.findByEmail(email).isPresent();
    }

    private UserDetails findAccount(String email) {
        return studentRepository.findByEmail(email).<UserDetails>map(s -> s)
                .or(() -> adminRepository.findByEmail(email).map(a -> (UserDetails) a))
                .or(() -> teacherRepository.findByEmail(email).map(t -> (UserDetails) t))
                .orElseThrow();
    }
}
