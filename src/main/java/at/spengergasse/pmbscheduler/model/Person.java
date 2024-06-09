package at.spengergasse.pmbscheduler.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Value("${props.boolean.isFPL:#{false}}")
    private Boolean isVerfied = false;
    @CreationTimestamp
    private LocalDate createdOn;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    List<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_appointments",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    )
    private List<Appointment> appointments = new ArrayList<>();
}
