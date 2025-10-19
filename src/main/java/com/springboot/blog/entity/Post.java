package com.springboot.blog.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

// user the { @Getter & @Setter } or { @Data with @ToString.Exclude & @EqualsAndHashCode.Exclude }
// to not cause a stack overflow or mapping error when using ModelMapper
//
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = "title")}
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    //make content column to be TEXT instead of just VARCHAR(255) to be able to have big content
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    //@ToString.Exclude
    //@EqualsAndHashCode.Exclude
    private Set<Comment> comments = new HashSet<>();
}
