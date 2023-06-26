package com.example.GymTrackingApp;

import org.apache.catalina.User;
import org.jboss.jandex.Main;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;

@Controller
public class MainController {

    public MainController(ExercisesRepository e, CategoriesRepository c, EcRepository ec, UsersRepository u) {
        repositoryExercises = e;
        repositoryCategories = c;
        repositoryEcMapping = ec;
        repositoryUsers = u;
    }

    private ExercisesRepository repositoryExercises;
    private CategoriesRepository repositoryCategories;
    private EcRepository repositoryEcMapping;

    private UsersRepository repositoryUsers;

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

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping(path = "/getAllUsers")
    public String getAllUsers() {
        System.out.println("Hit getAllUsers endpoint");

        List<Users> allDbUsers = repositoryUsers.findAll();

        String result = "----> ";

        for (int i = 0; i < allDbUsers.stream().count(); i++) {
            Users a = allDbUsers.get(i);
            if( a != null) {
                result = result + a.getUsername() + ", ";
            }
        }

        return result;

    }

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/addUser")
    public UsersVm addUser(@RequestBody UsersVm submittedUser) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("Hit addUser endpoint");

        Users newUser = new Users();
        newUser.setUsername(submittedUser.username);
        newUser.setPassword(submittedUser.password);

        System.out.println("Submitted Username: ]" + submittedUser.username + "[");
        System.out.println("Submitted Password: ]" + submittedUser.password + "[");

        repositoryUsers.save(newUser);

        return submittedUser;

    }




    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public boolean logIn(@RequestBody LogInVm submittedEntry) throws GeneralSecurityException, UnsupportedEncodingException {

        System.out.println("Hit /login endpoint");

        LogInVm newEntry = new LogInVm(submittedEntry.username, submittedEntry.password);

        List<Users> allUserEntries = repositoryUsers.findAll();

        for(int i = 0; i < allUserEntries.stream().count(); i++) {
            Users a = allUserEntries.get(i);

            System.out.println("database.username >>> " + a.username);
            System.out.println("database.password >>> " + a.password);

            System.out.println("inputted.username >>> " + newEntry.username);
            System.out.println("inputted.password >>> " + newEntry.password);


            if(a != null) {
                System.out.println(a.username.equals(newEntry.username));
                System.out.println(a.password.equals(newEntry.password));

                if((a.username.equals(newEntry.username)) && (a.password.equals(newEntry.password))) {
                    System.out.println("Successfully logged in");
                    newEntry.succeeded = true;
                    break;
                } else {
                    System.out.println("No Match");
                    newEntry.succeeded = false;
                }

            }

        }

        System.out.println(java.time.LocalDate.now() + " " + newEntry.username + " " + newEntry.password + " " + newEntry.succeeded);

        return newEntry.succeeded;
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/signUp", consumes = "application/json", produces = "application/json")
    public boolean signUp(@RequestBody LogInVm submittedEntry) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("Hit /signUp endpoint");

        LogInVm newEntry = new LogInVm(submittedEntry.username, submittedEntry.password);
        List<Users> allUserEntries = repositoryUsers.findAll();

        boolean usernameExists = false;
        for (int i = 0; i < allUserEntries.size(); i++) {
            Users a = allUserEntries.get(i);

            System.out.println("database.username >>> " + a.getUsername());
            System.out.println("inputted.username >>> " + newEntry.username);

            if (a.getUsername().equals(newEntry.username)) {
                System.out.println("Another user already has this username");
                usernameExists = true;
                break;
            }
        }

        if (!usernameExists) {
            System.out.println("Username is fine to use");
            Users newUser = new Users();
            newUser.setUsername(newEntry.username);
            newUser.setPassword(newEntry.password);
            repositoryUsers.save(newUser);
            newEntry.succeeded = true;
        } else {
            newEntry.succeeded = false;
        }

        return newEntry.succeeded;
    }






}


