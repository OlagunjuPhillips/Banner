package com.company;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main{
    static final String[] name = new String[]{"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
    public static void main(String[] args) throws IOException {


        File generatedData = createfile(name);

        String[] names = nameArray(generatedData);

        int[] numberOfNames = nameNumber(name, names);

        numberOfDifferentNames(name, numberOfNames);

        ArrayList<String> studentNames = new ArrayList<>();

        Collections.sort(studentNames);

        checkDuplicateID(generatedData);

        ArrayList<Student> studentlist = createStudentList(generatedData);

        ArrayList<Student> newStudentList = removeDuplicate(studentlist);

        countDifferentNames(studentNames);

        ArrayList<Student> uniqueStudentList = removeDuplicateID(generatedData);

        Collections.sort(uniqueStudentList, Student::compareTo);


        System.out.println(uniqueStudentList);

    }

    static File createfile(String[] names) throws IOException {

        Random rand = new Random();
        rand.setSeed(5);
        File file = new File("generatedData.csv");
        PrintWriter writer = null;

        writer = new PrintWriter(file);
        writer.write("id,name,gpa\n");

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
                writer.write(String.format("%s,%s,%s\n", Integer.toString(id), name, gpa));
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
                writer.write(String.format("%s,%s,%s", Integer.toString(id), name, gpa));
            }
        }
        writer.close();
    // Generate File method
        return file;
    }
    static String[] nameArray(File file) throws FileNotFoundException {
        String[] bannerNames = new String[3325];
        Scanner fileScan = null;
        fileScan = new Scanner(file);
        String headers = fileScan.nextLine();
        int i = 0;
        while(fileScan.hasNext()){
            String data = fileScan.nextLine();
            String[] parts = data.split(",");
            bannerNames[i] = parts[1];
            i++;
        }
    return bannerNames;
    }
    static int[] nameNumber(String[] actualNames, String[] nameArray){
        int[] numberNames = new int[10];
        for(int i = 0; i < actualNames.length; i++){
            int total = 0;
            for(int j = 0; j < 3325; j++){
                if(actualNames[i].equals(nameArray[j])){
                    total++;
                }
            }
            numberNames[i] = total;
        }
        return numberNames;
    }
    static void countDifferentNames(ArrayList<String> studentNames){
        int total = 1;
        for(int i = 0; i < studentNames.size(); i++){
            if(i != studentNames.size() - 1){
                if(studentNames.get(i).equals(studentNames.get(i + 1))){
                    total++;
                }
                else {
                    System.out.printf("The number of student with name \"%s\" is %d\n", studentNames.get(i), total);
                    total = 1;
                }
            } else{
                System.out.printf("The number of student with name \"%s\" is %d\n", studentNames.get(i), total);
            }
        }
    }

    static void checkDuplicateID(File file) throws FileNotFoundException {
        Scanner fileScan = null;
        fileScan = new Scanner(file);
        ArrayList<String> idArray = new ArrayList<>();
        String headers = fileScan.nextLine();
        int row = 1;
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            String[] parts = line.split(",");
            row++;
            if(idArray.contains(parts[0])){
                System.out.printf("Row %d is a duplicate.\n",row);
            }
            idArray.add(parts[0]);

            }

        }
        static ArrayList<Student> createStudentList(File file) throws FileNotFoundException {
        ArrayList<Student> studentList = new ArrayList<>();
        Scanner fileScan = null;
        fileScan = new Scanner(file);
        String headers = fileScan.nextLine();
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            String[] parts = line.split(",");
            Student student = new Student(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
            studentList.add(student);
        }
        return studentList;
        }
        static ArrayList<Student> removeDuplicate(ArrayList<Student> studentList){
        ArrayList<Integer> checkDuplicate = new ArrayList<>();
        for(int i = 0; i < studentList.size(); i++){
            Student student = studentList.get(i);
            if(checkDuplicate.contains(student.getId())){
                studentList.remove(student);
            }else{
                checkDuplicate.add(student.getId());
            }
        }
        return studentList;
        }
        static ArrayList<Student> removeDuplicateID(File file) throws FileNotFoundException {
        Scanner fileScan = null;
        fileScan = new Scanner(file);
        ArrayList<String> id = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();
        String headers = fileScan.nextLine();
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            String[] parts = line.split(",");
            if(id.contains(parts[0]) == false){
                id.add(parts[0]);
                Student student = new Student(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
                studentList.add(student);
            }
        }
        return studentList;
        }
        static void numberOfDifferentNames(String[] nameArray, int[] numberOfNames){
            int total = 0;
            for(int i = 0; i < numberOfNames.length; i++){
                total += numberOfNames[i];
                System.out.printf("The name \"%s\" occurs %d times\n",nameArray[i],numberOfNames[i]);
            }
            System.out.printf("Total number of names is %d\n",total);
        }
        static ArrayList<String> arrayToList(String[] names){
            ArrayList<String> studentNames = new ArrayList<>();
            for(int i = 0; i < names.length; i++){
                studentNames.add(names[i]);
            }
            return studentNames;
        }


} // Class Main
