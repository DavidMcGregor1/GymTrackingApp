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

    public MainController(ExercisesRepository e, CategoriesRepository c, EcRepository ec) {
        repositoryExercises = e;
        repositoryCategories = c;
        repositoryEcMapping = ec;
    }

    private ExercisesRepository repositoryExercises;
    private CategoriesRepository repositoryCategories;
    private EcRepository repositoryEcMapping;

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




    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "addEcMapping", consumes = "application/json", produces = "application/json")
    public EcVm addEcMapping(@RequestBody EcVm submittedEcMap) {
        System.out.println("Hit addEcMapping endpoint");

        ECs newDataBaseMap = new ECs();
        newDataBaseMap.setCategoryId(submittedEcMap.categoryId);
        newDataBaseMap.setExerciseId(submittedEcMap.exerciseId);

        repositoryEcMapping.save(newDataBaseMap);

        return submittedEcMap;

    }



    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping(path = "/getEcMapping", consumes = "application/json", produces = "application/json")
    public String getEcMapping() {
        System.out.println("Hit getEcMapping endpoint");

        List<ECs> allDbMappings = repositoryEcMapping.findAll();

        String result = "All Exercises ---> ";

        for (int i = 0; i < allDbMappings.stream().count(); i++) {
            ECs a = allDbMappings.get(i);

            if (a != null) {
                result = result + "ExerciseID = " +  a.getExerciseId() + " CategoryID = " + a.getCategoryId() + ", ";
            }
        }

        return result;

    }



    @GetMapping("/exercisesByCategory/{categoryId}")
    public String logExercisesByCategory(@PathVariable Integer categoryId, Model model) {

        System.out.println("Hit exercisesByCategory endpoint");
        System.out.println("Exercises in Category " + categoryId + ":");


        List<ECs> mappings = repositoryEcMapping.findByCategoryId(categoryId);
        List<Exercises> exercises = new ArrayList<>();


        System.out.println("New Logging:");
        System.out.println("______________");

        for (ECs mapping : mappings) {

            int exerciseId = mapping.getExerciseId();
            int elCategoryId = mapping.getCategoryId();
            System.out.println("exerciseId" + exerciseId);
            System.out.println("elCategoryId" + elCategoryId);
            Exercises exercise = repositoryExercises.findById(exerciseId).orElse(null);
            System.out.println("exercise: " + exercise);
            Categories aCategory = repositoryCategories.findById(elCategoryId).orElse(null);
            System.out.println("aCategory " + aCategory);
            if (exercise != null) {
                System.out.println(exercise.getExerciseName());
                exercises.add(exercise);

            }
        }


        System.out.println("Exercises: " + exercises);


        model.addAttribute("exercises", exercises);
        model.addAttribute("categoryName", categoryId);

        return "exercisesByCategory";
    }


    @GetMapping(path = "/exercises/{categoryId}")
    @ResponseBody
    public List<Exercises> getExercisesByCategory(@PathVariable Integer categoryId) {
        List<ECs> mappings = repositoryEcMapping.findByCategoryId(categoryId);
        List<Exercises> exercises = new ArrayList<>();

        for (ECs mapping : mappings) {
            int exerciseId = mapping.getExerciseId();
            Exercises exercise = repositoryExercises.findById(exerciseId).orElse(null);
            if (exercise != null) {
                exercises.add(exercise);
            }
        }

        return exercises;
    }


    @GetMapping(path = "/mainPage")
    public String mainPage() {
        System.out.println("Hit MainPage endpoint");
        return "index.html";
    }

    @GetMapping(path = "/index.html")
    public String mainPage2() {
        System.out.println("Hit MainPage2 endpoint");
        return "index.html";
    }

    @GetMapping(path = "/newWorkout.html")
    public String newWorkout() {
        System.out.println("Hit newWorkout endpoint");
        return "newWorkout.html";
    }

    @GetMapping(path = "/login.html")
    public String login() {
        System.out.println("Hit login endpoint");
        return "login.html";
    }

    @GetMapping(path = "/signup.html")
    public String signup() {
        System.out.println("Hit signup endpoint");
        return "signup.html";
    }





}


