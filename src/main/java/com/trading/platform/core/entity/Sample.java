package com.trading.platform.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sample")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
}
