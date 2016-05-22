package gui;

import javafx.fxml.FXMLLoader;

import java.io.IOException;


/**
 * Classe utilitaire pour le GUI du backend
 */
public class GUIUtilities {

    /**
     * Charge la vue du FXML
     *
     * @param resourcePath le chemin du FXML à charger
     * @param Controller   le controller associé à la vue
     * @return l'objet contenant les objets chargés
     * @throws IOException en cas d'erreur de chargement
     */
    public static Object loadLayout(String resourcePath, Object Controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GUIWindow.class.getResource(resourcePath));
        fxmlLoader.setController(Controller);
        return fxmlLoader.load();
    }
}
