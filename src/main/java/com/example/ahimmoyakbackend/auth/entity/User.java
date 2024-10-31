package com.example.ahimmoyakbackend.auth.entity;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.dto.UserInformationRequestDTO;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import com.example.ahimmoyakbackend.auth.common.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String email;

    @Column
    private boolean tutorState;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
//    @ColumnDefault("NONE")
    private Gender gender;

    public void patch(UserInformationRequestDTO requestDTO, String passwordEncoder) {
        if(passwordEncoder != null){
            this.password = passwordEncoder;
        }
        if(requestDTO.getPhone() != null){
            this.phone = requestDTO.getPhone();
        }
        if(requestDTO.getEmail() != null){
            this.email = requestDTO.getEmail();
        }
    }

    public User setTutorState(boolean tutorState) {
        this.tutorState = tutorState;
        return this;
    }
}
