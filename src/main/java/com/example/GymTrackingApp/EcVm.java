package com.example.GymTrackingApp;

public class EcVm {
    public int id;
    public int categoryId;
    public int exerciseId;

    EcVm(int aId, int aExerciseId, int aCategoryId) {
        id = aId;
        categoryId = aCategoryId;
        exerciseId = aExerciseId;
    }

    public EcVm() {

    }

}
