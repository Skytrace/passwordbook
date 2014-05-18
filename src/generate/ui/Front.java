package generate.ui;

import generate.backend.Backend;
import javafx.scene.control.TextField;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Front {
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private List<TextField> listFields;;
    private Backend backend;

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

    public String generatePassowrd() {
        Random randomGenerator = new Random();
        return Integer.toString(randomGenerator.nextInt(1280000));
    }

    public List<String> getListOfServices() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < backend.parseXML().size(); i++) {
            list.add(backend.parseXML().get(i).replaceAll(",.*", ""));
        }
        return list;
    }

}
