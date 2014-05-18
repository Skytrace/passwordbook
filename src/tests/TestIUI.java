package tests;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mac on 4/6/14.
 */
public class TestIUI {


    @Ignore
    public void checkOnFileExist() throws Exception {
        String filename = "psswd.xml";
        String finalfile = "";
        String workingDir = System.getProperty("user.dir");
        finalfile = workingDir + "/resources" + File.separator + filename;
        System.out.println("Final filepath : " + finalfile);
        File file = new File(finalfile);

        if (file.createNewFile()) {
            System.out.println("Done");
        } else {
            System.out.println("File already exists!");
        }
    }

    @Test
    public void testOfGetResourceAsStream() {
        Properties prop = new Properties();
        String filePath = "";

        try {

            InputStream inputStream =
                    getClass().getClassLoader().getResourceAsStream("config.properties");
            prop.load(inputStream);
            filePath = prop.getProperty("json.filepath");
            System.err.println(filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
