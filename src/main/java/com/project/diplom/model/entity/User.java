package com.project.diplom.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false)
    private Boolean verificationStatus;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Board> boardsOwned;

    @OneToMany(mappedBy = "assigneeId", fetch = FetchType.EAGER)
    private Set<Task> tasks;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private Set<Message> messagesIn;

    @OneToMany(mappedBy = "destUserId", fetch = FetchType.EAGER)
    private Set<Message> messagesOut;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "board_user",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "boardId", referencedColumnName = "id")})
    private Set<Board> boards;


    public enum UserRole {
        USER;
    }
}
