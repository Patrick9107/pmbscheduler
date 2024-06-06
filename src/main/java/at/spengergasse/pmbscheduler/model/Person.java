package at.spengergasse.pmbscheduler.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String firstName;
     private String lastName;
     private LocalDate birthDate;
     private String email;
     private String password;
     private Boolean isVerfied;
     @CreationTimestamp
     private LocalDate createdOn;
}
