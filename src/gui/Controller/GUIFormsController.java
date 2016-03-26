package gui.Controller;

import entities.InterestPoint;
import entities.Visit;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.GUIUtilities;
import gui.GUIWindow;

public class GUIFormsController {

    private GUIWindow guiWindow;
    private GUIUtilities utilities;
    private GUIControllerChateauVisitForm chateauVisitForm;
    private GUIControllerChateauIPForm chateauIPForm;



    public GUIFormsController(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
        this.utilities = new GUIUtilities();
    }

    public GUIFormsController() {
    }


    public void displayChateauVisitForm(boolean isNewVisit, Visit selectedVisit) {

        chateauVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
    }

    public void displayChateauIPForm(boolean isNewVisit, InterestPoint selectedPoint) {

        chateauIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
    }

}
