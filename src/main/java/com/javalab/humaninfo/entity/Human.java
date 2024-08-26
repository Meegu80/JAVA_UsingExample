package com.javalab.humaninfo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Human {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String gender;
        private String address;
        private String phoneNumber;
        private String job;
        private String company;
        private String education;
        private String hobby;
        private String mbti;
        private String bloodType;
        private int age;
        private int salary;
        private long wealth;


    }

