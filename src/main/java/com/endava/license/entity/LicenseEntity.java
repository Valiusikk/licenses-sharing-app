package com.endava.license.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "licenses")
public class LicenseEntity {

    @Id
    @Column
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "license_name")
    private String licenseName;

    @Column
    private String description;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToMany
    @JoinColumn(name = "id")
    private List<OrderEntity> orders;
}
