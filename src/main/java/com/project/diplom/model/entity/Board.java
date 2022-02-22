package com.project.diplom.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; //fk

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @OneToMany(mappedBy = "boardId", fetch = FetchType.EAGER)
    private Set<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "board_user",
            joinColumns = {@JoinColumn(name = "boardId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")})
    private Set<User> users;


}
