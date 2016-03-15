package gui;

import entities.Visit;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIControllerChateauVisitFrom implements Initializable{

    private GUIWindow guiWindow;

    public boolean newVisit;
    public TextField visitName;
    public TextArea visitPresTextFR;
    public TextArea visitPresTextEN;
    public TextArea visitLengthFR;
    public TextArea visitLengthEN;
    public Button addImages;
    public Button saveChanges;




    public GUIControllerChateauVisitFrom(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }

    public void fillInputs(Visit v) {
        visitName.setText(v.getName());
        visitPresTextFR.setText(v.getOverview().readPresentation_FR());
        visitPresTextEN.setText(v.getOverview().readPresentation_EN());
        visitLengthFR.setText(v.getOverview().readLength_FR());
        visitLengthEN.setText(v.getOverview().readLength_EN());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newVisit = true;
    }
}
