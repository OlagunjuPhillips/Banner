package com.company;
import java.util.*;
import java.io.*;

public class Main{

    // *Phillips*
    // static final String[] NAME = new String[]{"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
    static final String[] NAMES = {"John", "Kate", "Sam", "Kevin", "Paul", "Maddison", "Cooper", "Smith", "Chris", "Tom"};
    static final String FILENAME = "generatedData.csv";
    static final int NUM_OF_ROWS  = 3325;
    static Scanner console = new Scanner(System.in);
    static Random rand = new Random();

    public static void main(String[] args) throws IOException {

        // Generate a file of 3325 Student data
        createfile();

        // *Phillips* After you make whatever changes to createFile(), push your code to your repo
        // Put every name in the file created in an Array
        String[] names = nameArray(); // function now takes no parameter.

        // put the number of each name in an Array using only Arrays
        int[] numberOfNames = nameNumber(names);

        // find the number of times each name appears
        Map<String, Integer> nameFrequency = numberOfDifferentNames(numberOfNames);
        System.out.print("Number of times each name appears is: "+ nameFrequency);

        //Transfer the names from the Array to an ArrayList
        ArrayList<String> studentNames = arrayToList(names);

        //Sort the names in ascending order
        Collections.sort(studentNames);

        //function check if an id has a duplicate
        int id = 0;
        checkDuplicateID(id); // This fuction checks if a particular id has a duplicate.

        //Create an ArrayList that contains Students created from the generated file
        ArrayList<Student> studentList = createStudentList();

        //Remove every other duplicate IDs from the ArrayList of Students
        ArrayList<Student> newStudentList = removeDuplicate(studentList);

        //Map of Student names to their frequencies
        System.out.print("The number of different names are: ");
        System.out.println(countDifferentNames(studentNames));


        // Remove duplicate IDs and create an ArrayList of students directly from the generated file
        ArrayList<Student> uniqueStudentList = removeDuplicateID();

        //Sort the ArrayList of Students in ascending order of the student IDs
        uniqueStudentList.sort(Student::compareTo);

        // reverse the ordered student ArrayList into a stack
        Stack<Student> reversedStudent = reverseStudentList(newStudentList);

        // reverse the ordered student ArrayList using a deque
        Deque<Student> dequeStudent = reverseStudentWithDeque(newStudentList);

        //reverse the ordered student Arraylist using a deque initialized with a stack
        System.out.println(reverseDequeWithStack(uniqueStudentList));
        // I am still not sure how to use a stack to make a deque.


        try {
            numberOfLinesToOpen(uniqueStudentList); // The cafeteria simulation.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(checkIdSet(uniqueStudentList));

    }

    // *Phillips* I'm confused by this method signature (the header of your method/function).
    // To create a file, shouldn't you use the user's filename rather than your favorite filename?

    static void createfile(){
        System.out.print("Enter set seed: ");
        int seed = console.nextInt();

        // *Phillips* Maybe your user doesn't want to use 5 as the seed.
        rand.setSeed(seed);

        // *Phillips* You need documentation on this method because you're creating a CSV
        // but the specs don't say to do this.
//        File file = new File("generatedData.csv");

        // *Phillips*  This is terrible code.  Here's how it should be written.
        try(PrintWriter writer = new PrintWriter(new File(FILENAME))) {
            writer.write("id,name,gpa\n");
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
                writer.write(id+","+name+","+gpa+"\n");
            }
        } catch(FileNotFoundException e) {
            e.getStackTrace();
        }
    }
    static String[] nameArray() throws FileNotFoundException {

        File file = new File(FILENAME);
        String[] bannerNames = new String[3325];
        Scanner fileScan = new Scanner(file);
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

    static int[] nameNumber(String[] nameArray){
        int[] numberNames = new int[NAMES.length];  // *Phillips* how do you know this should be 10?
        for(int i = 0; i < NAMES.length; i++){
            int total = 0;
            for(int j = 0; j < 3325; j++){
                if(NAMES[i].equals(nameArray[j])){
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
        File file = new File(FILENAME);
        Scanner fileScan = new Scanner(file);
        ArrayList<String> idArray = new ArrayList<>();
        ArrayList<Integer> duplicateIdsArr = new ArrayList<>();
        String headers = fileScan.nextLine();
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            String[] parts = line.split(",");
            if(idArray.contains(parts[0])){
                duplicateIdsArr.add(Integer.parseInt(parts[0]));
            }
            idArray.add(parts[0]);
        }
        return duplicateIdsArr.contains(id);
    }

    static ArrayList<Student> createStudentList() throws FileNotFoundException {
        ArrayList<Student> studentList = new ArrayList<>();
        File file = new File(FILENAME);
        Scanner fileScan = new Scanner(file);
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
    static ArrayList<Student> removeDuplicateID() throws FileNotFoundException {
        File file = new File(FILENAME);
        Scanner fileScan = new Scanner(file);
        ArrayList<String> id = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();
        String headers = fileScan.nextLine();
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            String[] parts = line.split(",");
            if(!id.contains(parts[0])){
                id.add(parts[0]);
                Student student = new Student(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
                studentList.add(student);
            }
        }

        return studentList;
    }


    static Map<String, Integer> numberOfDifferentNames(int[] numberOfNames){
        Map<String, Integer> namesMapToFrequency= new HashMap<>();
        for(int i = 0; i < numberOfNames.length; i++){
            namesMapToFrequency.put(NAMES[i], numberOfNames[i]);
        }
        return namesMapToFrequency;
    }

    static ArrayList<String> arrayToList(String[] names){
        ArrayList<String> studentNames = new ArrayList<>();
        Collections.addAll(studentNames, names);

        return studentNames;
    }

    static Stack<Student> reverseStudentList(ArrayList<Student> students){
        Stack<Student> studentStack = new Stack<>();
        for(Student student : students){
            studentStack.push(student);
        }

        return studentStack;
    }


    static Deque<Student> reverseStudentWithDeque(ArrayList<Student> students){
        Deque<Student> studentDeque = new LinkedList<>();
        for(Student student : students){
            studentDeque.push(student);
        }
        return studentDeque;
    }

    static Deque<Student> reverseDequeWithStack(ArrayList<Student>  students){
        Deque<Student> studentDeque = new MyStack();
        studentDeque.addAll(students);
        return studentDeque;
    }

    static void numberOfLinesToOpen(ArrayList<Student> studentList) throws InterruptedException {
        Random rand = new Random();
        Queue<Student> studentQueue = new ArrayDeque<>();
        MyTime startTime = new MyTime(7);

        int cafeteriaCapacity = 0;

        int numberOfHours = 12;

        for(int time = 0; time < numberOfHours; time++) {

            int studentOnLine = rand.nextInt(50);
            int waitedOn = rand.nextInt(40);
            int numberOfLines = 1;


            for (int i = 0; i < studentOnLine; i++) {
                int pickStudent = rand.nextInt(studentList.size());
                Student student = studentList.get(pickStudent);
                studentQueue.add(student);
            }

            numberOfLines += studentQueue.size()/40;
            MyTime newTime = new MyTime(startTime.getHour()+time);
            System.out.printf("Number of lines to open at %s is %d\n", newTime, numberOfLines);
            System.out.println();

            //I'm sorry, I took the easy way out.


            if (studentOnLine >= waitedOn) {
                for (int i = 0; i < waitedOn; i++) {
                    studentQueue.remove();
                    cafeteriaCapacity++;
                }
            } else {
                for (int i = 0; i < studentOnLine; i++) {
                    studentQueue.remove();
                    cafeteriaCapacity++;
                }
            }
            if(cafeteriaCapacity >= 200){
                break;
            }
            Thread.sleep(2000);
        }
    }

    static boolean checkIdSet(ArrayList<Student> studentList){
        int[] idList = new int[studentList.size()];
        for(int i = 0;i < studentList.size();i++){
            Student s =  studentList.get(i);
            idList[i] = (s.getId());
        }
        Set<Integer> idSet = new HashSet<>();
        for (int value : idList) idSet.add(value);

        return idSet.size() == idList.length;
    }

}