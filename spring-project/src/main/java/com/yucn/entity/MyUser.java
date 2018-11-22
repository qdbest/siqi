package com.yucn.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by yucn on 2018/11/22.
 */
@Entity
@Data
public class MyUser {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
