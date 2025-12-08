package com.trading.platform.core.entity;

import com.trading.platform.core.entity.enums.ExpiryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "option_chain")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OptionChain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;  // BANKNIFTY, NIFTY
    private String expiry;  // 2024-12-26
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private ExpiryType expiryType;

    @OneToMany(mappedBy = "optionChain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionStrike> strikes;
}
