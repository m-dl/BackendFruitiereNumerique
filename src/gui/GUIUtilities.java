package gui;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by adriansalas on 26/03/2016.
 */
public class GUIUtilities {

    public static Object loadLayout(String resourcePath, Object Controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GUIWindow.class.getResource(resourcePath));
        fxmlLoader.setController(Controller);
        return fxmlLoader.load();
    }
}
