package com.bitespeed.identity.entity;


import com.bitespeed.identity.enums.LinkPrecedence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Contact")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = true)
    private String phoneNumber;
    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private Integer linkedId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkPrecedence linkPrecedence;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime deletedAt;
}
