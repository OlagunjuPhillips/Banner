package com.company;

public class Student {
    private int id;
    private String name;
    private double gpa;
    public Student(int idIn, String nameIn, double gpaIn){
        id = idIn;
        name = nameIn;
        gpa = gpaIn;
    }
    public String toString(){
        return String.format("Student ID: %d, Student name: %s, Student gpa: %f\n",id,name,gpa);
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getGpa(){
        return gpa;
    }
    public int compareTo(Student s){
        return id - s.getId();
    }
}
