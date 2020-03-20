package com.company;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main{
    static final String[] name = new String[]{"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
    public static void main(String[] args) throws IOException {

        // Generate a file of 3325 Student data
        File generatedData = createfile(name);

        // Put every name in the file created in an Array
        String[] names = nameArray(generatedData);

        // put the number of each name in an Array using only Arrays
        int[] numberOfNames = nameNumber(name, names);

        // find the number of times each name appears
        Map<String, Integer> nameFrequency = numberOfDifferentNames(name, numberOfNames);

        //Transfer the names from the Array to an ArrayList
        ArrayList<String> studentNames = arrayToList(names);

        //Sort the names in ascending order
        Collections.sort(studentNames);

        //function check if an id has a duplicate
        System.out.println(checkDuplicateID(99890));

        //Create an ArrayList that contains Students creates from the generated file
        ArrayList<Student> studentlist = createStudentList(generatedData);

        //Remove every other duplicate IDs from the ArrayList of Students
        ArrayList<Student> newStudentList = removeDuplicate(studentlist);

        //Map of Student names to their frequencies
        countDifferentNames(studentNames) ;

        // Remove duplicate IDs and create an ArrayList of students directly from the generated file
        ArrayList<Student> uniqueStudentList = removeDuplicateID(generatedData);

        //Sort the ArrayList of Students in ascending order of the student IDs
        Collections.sort(uniqueStudentList, Student::compareTo);

        // reverse the ordered student ArrayList into a stack
        Stack<Student> reversedStudent = reverseStudentList(newStudentList);

        // reverse the ordered student ArrayList using a deque
        Deque<Student> dequeStudent = reverseStudentWithDeque(newStudentList);



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
    static Map<String, Integer> countDifferentNames(ArrayList<String> studentNames){
        Map<String, Integer> nameMap = new HashMap<>();
        int total = 1;
        for(int i = 0; i < studentNames.size(); i++){
            if(i != studentNames.size() - 1){
                if(studentNames.get(i).equals(studentNames.get(i + 1))){
                    total++;
                }
                else {
                    nameMap.put(studentNames.get(i), total);
                    total = 1;
                }
            } else{
                nameMap.put(studentNames.get(i), total);
            }
        }

        return nameMap;
    }

    static boolean checkDuplicateID(int id) throws FileNotFoundException {
        Scanner fileScan = null;
        File file = new File("generatedData.csv");
        fileScan = new Scanner(file);
        ArrayList<String> idArray = new ArrayList<>();
        ArrayList<Integer> duplicateIdsArr = new ArrayList<>();
        String headers = fileScan.nextLine();
        int row = 1;
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            String[] parts = line.split(",");
            row++;
            if(idArray.contains(parts[0])){
                duplicateIdsArr.add(Integer.parseInt(parts[0]));
            }
            idArray.add(parts[0]);
        }
        if(duplicateIdsArr.contains(id)){

            return true;
        }

        return false;
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


        static Map<String, Integer> numberOfDifferentNames(String[] nameArray, int[] numberOfNames){
            Map<String, Integer> namesMapToFrequency= new HashMap<>();
            for(int i = 0; i < numberOfNames.length; i++){
                namesMapToFrequency.put(nameArray[i], numberOfNames[i]);
            }
            return namesMapToFrequency;
        }

        static ArrayList<String> arrayToList(String[] names){
            ArrayList<String> studentNames = new ArrayList<>();
            for(int i = 0; i < names.length; i++){
                studentNames.add(names[i]);
            }

            return studentNames;
        }

        static Stack<Student> reverseStudentList(ArrayList<Student> students){
            Stack<Student> studentStack = new Stack<>();
            for(Student student : students){
               studentStack.push(student);
            }

            return studentStack;
        }

        static Deque<Student> reverseStudentListUsingStack(ArrayList<Student> students){
            Deque<Student> studentDeque = new MyStack();
        }

        static Deque<Student> reverseStudentWithDeque(ArrayList<Student> students){
            Deque<Student> studentDeque = new LinkedList<>();
            for(Student student : students){
                studentDeque.push(student);
            }
            return studentDeque;
        }
} // Class Main
