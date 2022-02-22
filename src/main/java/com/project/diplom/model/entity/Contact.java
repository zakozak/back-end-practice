package com.project.diplom.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@IdClass(ContactId.class)
@Getter
@Setter
@NoArgsConstructor
public class Contact {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "dest_user_id", nullable = false)
    private Long destId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "dest_user_id", nullable = false, insertable = false, updatable = false)
    private User destUser;
}
