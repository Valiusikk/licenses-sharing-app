package com.endava.license.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "roles")
@RequiredArgsConstructor
public class RoleEntity {

    @Id
    @Max(3)
    @Column
    private String id;

    @Column(name = "code")
    private String code;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> userEntities = new ArrayList<>();
}
