package com.project.diplom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(nullable = false, length = 250)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId; //fk

    @ManyToOne
    @JoinColumn(name = "dest_user_id")
    private User destUserId; //fk

}
