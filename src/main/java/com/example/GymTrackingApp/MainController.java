package com.example.GymTrackingApp;

import org.jboss.jandex.Main;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.*;

@Controller
public class MainController {

    public MainController(ExercisesRepository e) {
        repositoryExercises = e;
    }

    private ExercisesRepository repositoryExercises;

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("arrived at hello page");
        return "index";
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping(path = "/getAllExercises", consumes = "application/json", produces = "application/json")
    public String getAllExercises() {
        System.out.println("Hit getAllExercises endpoint");

        List<Exercises> allDbExercises = repositoryExercises.findAll();

        String result = "All Exercises ---> ";

        for (int i = 0; i < allDbExercises.stream().count(); i++) {
            Exercises a = allDbExercises.get(i);

            if (a != null) {
                result = result + a.getExerciseName() + ", ";
            }
        }

        return result;

    }




    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/addExercise", consumes = "application/json", produces = "application/json")
    public ExercisesVm addExercises(@RequestBody ExercisesVm submittedExercise) {

        System.out.println("Hit addExercise endpoint");
        Exercises newDataBaseExercise = new Exercises();

        newDataBaseExercise.setExerciseName(submittedExercise.exerciseName);

        repositoryExercises.save(newDataBaseExercise);

        return submittedExercise;
        
    }



}
