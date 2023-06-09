package com.example.GymTrackingApp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Exercises")
@Entity
public class Exercises {

    private int id;
    private String exerciseName;

    public Exercises() {

    }

    public Exercises(int id, String exerciseName) {
        this.id = id;
        this.exerciseName = exerciseName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "ExerciseName", nullable = false)
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }





}
