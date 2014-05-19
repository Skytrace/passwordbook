package generate.ui;

import generate.backend.Backend;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Front {
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private List<TextField> listFields;;
    private Backend backend;
    private static Frame frame;

    public Front() throws Exception {
        backend = new Backend();
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    public List<TextField> getListFields() {
        listFields = new ArrayList<TextField>();
        List<String> list = backend.parseXML();
        int y = 130;
        for (int i = 0; i < list.size(); i++) {
            listFields.add(new TextField(list.get(i)));
            listFields.get(i).setLayoutY(y += 30);
            listFields.get(i).setLayoutX(10);
        }
        return listFields;
    }

    public List<String> getListOfServices() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < backend.parseXML().size(); i++) {
            list.add(backend.parseXML().get(i).replaceAll(",.*", ""));
        }
        return list;
    }

    public boolean isServiceName(String serviceName, String servicePassword) {
        return serviceName.equals("") || serviceName == null || servicePassword.equals("") || servicePassword == null;
    }

    public static void serviceNameModalWindow() {
        frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                "Service name should not be empty",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void storageNotFoundModalWindow(Exception e) {
        frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                e.getMessage(),
                "XML error",
                JOptionPane.ERROR_MESSAGE);
    }


}
