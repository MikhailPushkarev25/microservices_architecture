package com.programmingtechie.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String name;

    private String description;

    private BigDecimal price;
}
