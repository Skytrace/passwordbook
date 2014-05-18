package generate.password;

import generate.backend.Backend;
import generate.ui.Front;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private Backend core;
    private Front front;
    private TextField servicePasswordField;
    private TextField serviceNameField;
    private Label label;
    private Button generatePasswordButton;
    private Button save;
    private String pass;
    private String service;
    private Pane root;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        root = new Pane();
        core = new Backend();
        front = new Front();

        List<String> list = front.getListOfServices();
        ObservableList<String> options = FXCollections.observableArrayList(
                list
        );

        ComboBox comboBox = new ComboBox(options);
        comboBox.setLayoutX(230);
        comboBox.setLayoutY(250);
        Button button = new Button("x");
        button.setLayoutX(330);
        button.setLayoutY(250);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String service = comboBox.getSelectionModel().getSelectedItem().toString();
                    core.removeService(service);
                    options.remove(service);
                    root.getChildren().clear();
                    root.getChildren().addAll(servicePasswordField, generatePasswordButton, label, serviceNameField, save, comboBox, button);
                    root.getChildren().addAll(front.getListFields());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        generatePasswordButton = new Button("generate password");
        serviceNameField = new TextField();
        serviceNameField.setLayoutX(165);
        serviceNameField.setLayoutY(50);
        label = new Label("for: ");
        label.setLayoutX(125);
        label.setLayoutY(50);
        servicePasswordField = new TextField("<= click");
        servicePasswordField.setLayoutX(165);
        servicePasswordField.setLayoutY(10);
        generatePasswordButton.setLayoutX(10);
        generatePasswordButton.setLayoutY(10);
        save = new Button("save");
        save.setLayoutX(10);
        save.setLayoutY(80);

        generatePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                servicePasswordField.setText(front.generatePassowrd());
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pass = servicePasswordField.getText();
                service = serviceNameField.getText();
                System.out.println(String.format("%s %s", pass, service));
                options.add(service);
                try {
                    core.addService(service, pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                root.getChildren().addAll(front.getListFields());
            }
        });

        root.getChildren().addAll(servicePasswordField, generatePasswordButton, label, serviceNameField, save, comboBox, button);
        root.getChildren().addAll(front.getListFields());
        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

