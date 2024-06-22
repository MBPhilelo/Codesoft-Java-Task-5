/**
 * 
 */

/**
 * @author HP
 *
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private CourseRegistrationSystem system;
    private ListView<String> courseListView;
    private ListView<String> registeredCoursesListView;
    private Student currentStudent;

    @Override
    public void start(Stage primaryStage) {
        system = new CourseRegistrationSystem();
        loadSampleData();

        // Login Screen
        Stage loginStage = new Stage();
        loginStage.setTitle("Student Login");
        GridPane loginGrid = createLoginScreen(loginStage);
        Scene loginScene = new Scene(loginGrid, 300, 200);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void loadSampleData() {
        // Adding sample courses
    	system.addCourse(new Course("ATC101", "Intro to Airplanes", "Basic concepts of Airplanes", 30, "MWF 10-11 AM"));
        system.addCourse(new Course("MATH101", "Intro to Advanced Calculus I", "Differentiation and Integration", 40, "TTh 9-10:30 AM"));
        system.addCourse(new Course("STA101", "Intro to Advanced Probability", "Advanced Statistical Probability", 25, "MWF 2-3 PM"));
        system.addCourse(new Course("CS201", "Intro to Networking", "Basic concepts of Netwoking", 30, "MWF 10-11 AM"));
        system.addCourse(new Course("CS301", "Intro to Data Structures", "Basic concepts of Data Structures", 30, "MWF 10-11 AM"));
        system.addCourse(new Course("IFM101", "Intro to Software Engineering", "Basic concepts of Software engineering", 30, "MWF 10-11 AM"));
        system.addCourse(new Course("AI", "Intro to Artificial Intelligence", "Basic concepts of AI", 30, "MWF 10-11 AM"));
        
        // Adding sample students
        system.addStudent(new Student("S001", "John Doe"));
        system.addStudent(new Student("S002", "Jane Smith"));
        system.addStudent(new Student("S003", "Emily Davis"));
    }

    private GridPane createLoginScreen(Stage loginStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);
        loginGrid.setAlignment(Pos.CENTER);

        Label studentIDLabel = new Label("Student ID:");
        TextField studentIDField = new TextField();
        Button loginButton = new Button("LOGIN");

        loginButton.setOnAction(e -> {
            String studentID = studentIDField.getText();
            Student student = system.getStudent(studentID);
            if (student != null) {
                currentStudent = student;
                loginStage.close();
                showCourseRegistrationScreen();
            } else {
                showMessage("Invalid student ID.");
            }
        });

        loginGrid.add(studentIDLabel, 0, 0);
        loginGrid.add(studentIDField, 1, 0);
        loginGrid.add(loginButton, 1, 1);

        return loginGrid;
    }

    private void showCourseRegistrationScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Course Registration System");

        courseListView = new ListView<>();
        for (Course course : system.getCourses().values()) {
            courseListView.getItems().add(course.getCourseCode() + ": " + course.getTitle());
        }

        registeredCoursesListView = new ListView<>();
        updateRegisteredCoursesListView();

        Button registerButton = new Button("Register");
        Button dropButton = new Button("Drop");

        registerButton.setOnAction(e -> handleRegisterCourse());
        dropButton.setOnAction(e -> handleDropCourse());

        VBox layout = new VBox(10, new Label("Available Courses"), courseListView, registerButton, new Label("Registered Courses"), registeredCoursesListView, dropButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleRegisterCourse() {
        String selectedCourse = courseListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String courseCode = selectedCourse.split(":")[0];
            Course course = system.getCourse(courseCode);
            if (course.registerStudent()) {
                currentStudent.registerCourse(course);
                showMessage("Registered successfully for " + course.getTitle());
                updateRegisteredCoursesListView();
            } else {
                showMessage("Course is full.");
            }
        } else {
            showMessage("Select a course to register.");
        }
    }

    private void handleDropCourse() {
        String selectedCourse = registeredCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String courseCode = selectedCourse.split(":")[0];
            Course course = system.getCourse(courseCode);
            currentStudent.dropCourse(course);
            course.dropStudent();
            showMessage("Dropped successfully from " + course.getTitle());
            updateRegisteredCoursesListView();
        } else {
            showMessage("Select a course to drop.");
        }
    }

    private void updateRegisteredCoursesListView() {
        registeredCoursesListView.getItems().clear();
        for (Course course : currentStudent.getRegisteredCourses()) {
            registeredCoursesListView.getItems().add(course.getCourseCode() + ": " + course.getTitle());
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
