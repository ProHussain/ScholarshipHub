package com.hashmac.scholarshiphub.dto;

import androidx.annotation.Keep;

import java.io.Serializable;

public class Scholarship implements Serializable {
    private String id;
    private String name;
    private String country;
    private String continent;
    private String university;
    private String subjects;
    private String degreeLevel;
    private String funds;
    private String students;
    private String description;
    private String imageUrl;
    private String scholarshipUrl;
    private String createdAt;
    private String updatedAt;

    @Keep
    public Scholarship() {
    }

    public Scholarship(String id, String name, String country, String continent, String university, String subjects, String degreeLevel, String funds, String students, String description, String imageUrl, String scholarshipUrl, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.continent = continent;
        this.university = university;
        this.subjects = subjects;
        this.degreeLevel = degreeLevel;
        this.funds = funds;
        this.students = students;
        this.description = description;
        this.imageUrl = imageUrl;
        this.scholarshipUrl = scholarshipUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScholarshipUrl() {
        return scholarshipUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
