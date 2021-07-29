package com.scala.controlfinan.repository.data;

import com.scala.controlfinan.domain.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "categories")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private CategoryType type;

}
