/*
 * Online Examination
 * -------------------
 * This Java program implements a simple online examination system with the following features:
 * - User authentication with the ability to change usernames and passwords.
 * - Multiple-choice question (MCQ) exams with or without a timer, and automatic submission.
 * - Checking and displaying the MCQ exam marks for logged-in users.
 * - Session closure and program exit.
 *
 * How to Use:
 * 1. Run the program.
 * 2. Choose "Login" to access the system.
 * 3. After logging in, explore options to change credentials, start MCQ exams, and check MCQ marks.
 * 4. Log out to exit the system.
 * 
 * Author: [Ankan Misra]
 */

 import java.util.HashMap;
 import java.util.Map;
 import java.util.Scanner;
 import java.util.concurrent.TimeUnit;
 
 public class OnlineExamination {
     // User credentials storage
     private static final Map<String, String> userCredentials = new HashMap<>();
     private static String currentUser;
     private static boolean isLoggedIn = false;
     private static int mcqMarks = -1; // Default value indicating not attempted
 
     public static void main(String[] args) {
         initializeUsers();  // Initialize user credentials
 
         Scanner scanner = new Scanner(System.in);
 
         while (true) {
             if (!isLoggedIn) {
                 printLoginForm();  // Display login form if not logged in
             } else {
                 printWelcomeMenu();  // Display welcome menu if logged in
             }
 
             System.out.print("Enter your choice: ");
             int choice = scanner.nextInt();
             scanner.nextLine(); // Consume the newline character
 
             switch (choice) {
                 case 1:
                     if (!isLoggedIn) {
                         login(scanner);  // Perform login if not logged in
                     } else {
                         changeUserIDAndPassword(scanner);  // Change user ID and password if already logged in
                     }
                     break;
                 case 2:
                     if (!isLoggedIn) {
                         closeSessionAndExit();  // Close session and exit if not logged in
                     } else {
                         startMCQExam(scanner, false);  // Start MCQ exam if logged in
                     }
                     break;
                 case 3:
                     if (!isLoggedIn) {
                         System.out.println("Please login first.");
                     } else {
                         startMCQExamWithTimerAndAutoSubmit(scanner, 1);  // Start MCQ exam with timer if logged in
                     }
                     break;
                 case 4:
                     if (isLoggedIn) {
                         checkMCQMarks();  // Check MCQ marks if logged in
                     } else {
                         System.out.println("Invalid option. Please enter a valid choice.");
                     }
                     break;
                 case 5:
                     if (isLoggedIn) {
                         logOut();  // Log out if already logged in
                     } else {
                         System.out.println("Invalid option. Please enter a valid choice.");
                     }
                     break;
                 default:
                     System.out.println("Invalid choice. Please enter a valid option.");
             }
         }
     }
 
     private static void initializeUsers() {
         // Initialize user credentials (test cases)
         userCredentials.put("testcase01", "password1");
         userCredentials.put("testcase02", "password2");
         userCredentials.put("testcase03", "password3");
     }
 
     private static void printLoginForm() {
         // Display the login form
         System.out.println("===== Online Exam System =====");
         System.out.println("1. Login");
         System.out.println("2. Exit");
     }
 
     private static void printWelcomeMenu() {
         // Display the welcome menu for logged-in users
         System.out.println("===== Online Exam System - Welcome, " + currentUser + " =====");
         System.out.println("1. Change Username and Password");
         System.out.println("2. Start the MCQ Exam");
         System.out.println("3. Start the MCQ Exam with a 1-minute timer and auto-submit");
         System.out.println("4. Check MCQ Marks");
         System.out.println("5. Log Out");
     }
 
     private static void login(Scanner scanner) {
         // Perform user login
         System.out.println("Enter your username: ");
         String enteredUsername = scanner.nextLine();
 
         if (!userCredentials.containsKey(enteredUsername)) {
             System.out.println("Invalid username. Please try again.");
             return;
         }
 
         System.out.println("Enter your password: ");
         String enteredPassword = scanner.nextLine();
         String storedPassword = userCredentials.get(enteredUsername);
 
         if (!enteredPassword.equals(storedPassword)) {
             System.out.println("Incorrect password. Please try again.");
         } else {
             isLoggedIn = true;
             currentUser = enteredUsername;
             System.out.println("Login successful. Welcome, " + currentUser + "!");
         }
     }
 
     private static void changeUserIDAndPassword(Scanner scanner) {
         // Change user ID and password
         if (!isLoggedIn) {
             System.out.println("Please login first.");
             return;
         }
 
         System.out.println("Enter new user ID: ");
         String newUserID = scanner.nextLine();
         System.out.println("Enter new password: ");
         String newPassword = scanner.nextLine();
 
         userCredentials.remove(currentUser);
         userCredentials.put(newUserID, newPassword);
 
         currentUser = newUserID;
 
         System.out.println("User ID and password changed successfully.");
     }
 
     private static void startMCQExam(Scanner scanner, boolean withTimer) {
         // Start the MCQ exam
         if (!isLoggedIn) {
             System.out.println("Please login first.");
             return;
         }
 
         String[] questions = {
                 "What is the capital of Japan?",
                 "Which programming language is commonly used for Android app development?",
                 "What is the largest mammal in the world?",
                 "In which year did the Titanic sink?",
                 "Who wrote 'To Kill a Mockingbird'?"
         };
 
         String[][] options = {
                 {"Tokyo", "Seoul", "Beijing", "Bangkok"},
                 {"Java", "Swift", "C#", "Python"},
                 {"Elephant", "Blue Whale", "Giraffe", "Gorilla"},
                 {"1912", "1920", "1905", "1931"},
                 {"Harper Lee", "J.K. Rowling", "George Orwell", "Mark Twain"}
         };
 
         int[] correctAnswers = {1, 1, 2, 1, 1};
 
         int score = 0;
         long startTime = System.currentTimeMillis();
         long examDurationMillis = withTimer ? TimeUnit.MINUTES.toMillis(1) : Long.MAX_VALUE;
 
         System.out.println("===== MCQ Exam =====");
         if (withTimer) {
             System.out.println("You have 1 minute to complete the exam.");
         }
 
         for (int i = 0; i < questions.length; i++) {
             System.out.println("Question " + (i + 1) + ": " + questions[i]);
             for (int j = 0; j < options[i].length; j++) {
                 System.out.println((j + 1) + ". " + options[i][j]);
             }
 
             System.out.print("Enter your answer (1-4): ");
             int userAnswer = scanner.nextInt();
 
             if (userAnswer >= 1 && userAnswer <= 4) {
                 if (userAnswer == correctAnswers[i]) {
                     System.out.println("Correct!");
                     score++;
                 } else {
                     System.out.println("Incorrect. The correct answer is: " + options[i][correctAnswers[i] - 1]);
                 }
             } else {
                 System.out.println("Invalid answer. Please enter a number between 1 and 4.");
             }
 
             if (withTimer && System.currentTimeMillis() - startTime > examDurationMillis) {
                 System.out.println("Time's up! Exam auto-submitted.");
                 break;
             }
         }
 
         if (!withTimer) {
             System.out.println("Exam completed. Your final score: " + score + " out of " + questions.length);
             mcqMarks = score; // Store MCQ marks for later retrieval
         }
     }
 
     private static void startMCQExamWithTimerAndAutoSubmit(Scanner scanner, int minutes) {
         // Start MCQ exam with timer and auto-submit
         if (!isLoggedIn) {
             System.out.println("Please login first.");
             return;
         }
 
         startMCQExam(scanner, true);
     }
 
     private static void checkMCQMarks() {
         // Check MCQ marks for the logged-in user
         if (mcqMarks == -1) {
             System.out.println("MCQ not attempted.");
         } else {
             System.out.println("Your MCQ marks: " + mcqMarks);
         }
     }
 
     private static void logOut() {
         // Log out the user
         System.out.println("Logging out. Goodbye, " + currentUser + "!");
         isLoggedIn = false;
         currentUser = null;
         mcqMarks = -1; // Reset MCQ marks upon logout
     }
 
     private static void closeSessionAndExit() {
         // Close the session and exit the program
         System.out.println("Session closed. Exiting. Goodbye!");
         System.exit(0);
     }
 }
 