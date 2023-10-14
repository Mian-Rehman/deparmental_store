package com.rehman.newtrend.Model;

public class TasbeehModel
{
    String tasbeehKey;
    String tasbeehName;
    String tasbeehSteps;

    public TasbeehModel(String tasbeehKey, String tasbeehName, String tasbeehSteps) {
        this.tasbeehKey = tasbeehKey;
        this.tasbeehName = tasbeehName;
        this.tasbeehSteps = tasbeehSteps;
    }

    public TasbeehModel() {
    }

    public String getTasbeehKey() {
        return tasbeehKey;
    }

    public void setTasbeehKey(String tasbeehKey) {
        this.tasbeehKey = tasbeehKey;
    }

    public String getTasbeehName() {
        return tasbeehName;
    }

    public void setTasbeehName(String tasbeehName) {
        this.tasbeehName = tasbeehName;
    }

    public String getTasbeehSteps() {
        return tasbeehSteps;
    }

    public void setTasbeehSteps(String tasbeehSteps) {
        this.tasbeehSteps = tasbeehSteps;
    }
}
