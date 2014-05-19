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
    private Pane root;
    private ComboBox comboBox;
    private ObservableList<String> options;
    private Button deleteServiceButton;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        root = new Pane();
        core = new Backend();
        front = new Front();

        List<String> list = front.getListOfServices();
        options = FXCollections.observableArrayList(list);

        setCombobox();
        deleteServiceButton = new Button("x");
        deleteServiceButton.setLayoutX(330);
        deleteServiceButton.setLayoutY(250);
        deleteServiceButton.setId("custom-button");
        deleteServiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String serviceName = comboBox.getSelectionModel().getSelectedItem().toString();
                    core.removeService(serviceName);
                    options.remove(serviceName);
                    root.getChildren().clear();
                    setCombobox();
                    root.getChildren().addAll(servicePasswordField, generatePasswordButton, label, serviceNameField, save, comboBox, deleteServiceButton);
                    root.getChildren().addAll(front.getListFields());
                } catch (Exception e) {
                    Front.storageNotFoundModalWindow(e);
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
                if (front.isServiceName(serviceNameField.getText(), servicePasswordField.getText())) front.serviceNameModalWindow();

                else {
                    options.add(serviceNameField.getText());
                    try {
                        core.addService(serviceNameField.getText(), servicePasswordField.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    root.getChildren().addAll(front.getListFields());
                }
            }
        });

        Scene scene = new Scene(root, 450, 450);
        scene.getStylesheets().add("style.css");
        root.getChildren().addAll(servicePasswordField, generatePasswordButton, label, serviceNameField, save, comboBox, deleteServiceButton);
        root.getChildren().addAll(front.getListFields());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setCombobox() {
        comboBox = new ComboBox(options);
        comboBox.setLayoutX(230);
        comboBox.setLayoutY(250);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

