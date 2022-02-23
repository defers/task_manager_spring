package com.defers.taskmanager.entity;

import com.defers.taskmanager.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Enumerated
    @Column(unique = true, nullable = false)
    private ERole name;

    @ManyToMany(
            mappedBy = "roles",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<User> users;
}
