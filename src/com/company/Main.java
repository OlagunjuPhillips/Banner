package com.company;
import java.util.*;
import java.io.*;

public class Main{
//    private static Random rand;
    public static void main(String[] args) throws IOException {
        String[] name = new String[]{"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
        createfile(name);
    } // main

    static void createfile(String[] names) throws IOException {

        Random rand = new Random();
        File file = new File("generatedData.csv");
        PrintWriter writer = null;

        writer = new PrintWriter(file);
        writer.write("name,id,gpa\n");

        for(int i = 0; i < 3325; i++) {
            if (i < 3324) {
                int index = rand.nextInt(names.length);
                String name = names[index];
                int id;
                do {
                    id = rand.nextInt(99999 + 1);
                } while (id < 90000);
                double gpa = 0;
                do {
                    gpa = rand.nextDouble();
                } while (gpa > 4);
                writer.write(name + "," + id + "," + gpa + "\n");
            }else{
                int index = rand.nextInt(names.length);
                String name = names[index];
                int id;
                do {
                    id = rand.nextInt(99999 + 1);
                } while (id < 90000);
                double gpa = 0;
                do {
                    gpa = rand.nextDouble();
                } while (gpa > 4);
                writer.write(name + "," + id + "," + gpa);
            }
        }
        writer.close();
    // Generate File method
    }

} // Class Main
