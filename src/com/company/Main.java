package com.company;
import java.util.*;
import java.io.*;

public class Main{

    // *Phillips*
    // static final String[] NAME = new String[]{"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
    static final String[] NAMES = {"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
    static final String FILENAME = "generatedData.csv";
    static final int NUM_OF_ROWS  = 3325;

    public static void main(String[] args) throws IOException {

        // Generate a file of 3325 Student data
        File generatedData = createfile(NAMES);
        // *Phillips* After you make whatever changes to createFile(), push your code to your repo

        // Put every name in the file created in an Array
        String[] names = nameArray(generatedData);

        // put the number of each name in an Array using only Arrays
        int[] numberOfNames = nameNumber(NAMES, names);

        // find the number of times each name appears
        Map<String, Integer> nameFrequency = numberOfDifferentNames(NAMES, numberOfNames);

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

    // *Phillips* I'm confused by this method signature (the header of your method/function).
    // To create a file, shouldn't you use the user's filename rather than your favorite filename?
    static File createfile(String[] names) throws IOException {

        Random rand = new Random();

        // *Phillips* Maybe your user doesn't want to use 5 as the seed.
        rand.setSeed(5);

        // *Phillips* You need documentation on this method because you're creating a CSV
        // but the specs don't say to do this.
        File file = new File("generatedData.csv");

        // *Phillips*  This is terrible code.  Here's how it should be written.
        try(PrintWriter writer = new PrintWriter(new File(FILENAME))) {
            for (int i = 0; i < NUM_OF_ROWS; i++) {
                // randomly generate an id in 90_000..99_999
                int id = rand.nextInt(10_000) + 90_000;

                // randomly generate a first name
                String name = NAMES[rand.nextInt(NAMES.length)];

                // randomly generate a gpa
                // it's not so obvious how to do this since rand.nextDouble gives values in [0..1]
                int lowerBound = 0;
                int upperBound = 4;
                double gpa = lowerBound + rand.nextDouble() * (upperBound - lowerBound);  // think about why this works

                // *Phillips* Now you write to the file
            }
        } catch(FileNotFoundException e) {
            e.getStackTrace();
        }
//        PrintWriter writer = null;
//        writer = new PrintWriter(file);
//        writer.write("id,name,gpa\n");
//
//        for(int i = 0; i < 3325; i++) {
//
//
//            if (i < 3324) {
//                int index = rand.nextInt(names.length);
//                String name = names[index];
//
//
//                int id;
//                do {
//                    id = rand.nextInt(99999 + 1);
//                } while (id < 90000);
//
//
//                double gpa = 0;
//                do {
//                    gpa = rand.nextDouble();
//                } while (gpa > 4);
//
//
//                writer.write(String.format("%s,%s,%s\n", Integer.toString(id), name, gpa));
//
//
//            }else{
//                int index = rand.nextInt(names.length);
//                String name = names[index];
//
//
//                int id;
//                do {
//                    id = rand.nextInt(99999 + 1);
//                } while (id < 90000);
//                double gpa = 0;
//
//
//                do {
//                    gpa = rand.nextDouble();
//                } while (gpa > 4);
//
//
//                writer.write(String.format("%s,%s,%s", Integer.toString(id), name, gpa));
//            }
//        }
//        writer.close();

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
        int[] numberNames = new int[10];  // *Phillips* how do you know this should be 10?
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
            return studentDeque;
        }

        static Deque<Student> reverseStudentWithDeque(ArrayList<Student> students){
            Deque<Student> studentDeque = new LinkedList<>();
            for(Student student : students){
                studentDeque.push(student);
            }
            return studentDeque;
        }
} // Class Main
