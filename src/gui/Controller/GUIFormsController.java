package gui.Controller;

import entities.InterestPoint;
import entities.Visit;
import gui.Controller.chateau.GUIControllerChateauIPForm;
import gui.Controller.chateau.GUIControllerChateauVisitForm;
import gui.Controller.village.GUIControllerVillageIPForm;
import gui.Controller.village.GUIControllerVillageVisitForm;
import gui.GUIUtilities;
import gui.GUIWindow;

public class GUIFormsController {

    private GUIWindow guiWindow;
    private GUIUtilities utilities;
    private GUIControllerChateauVisitForm chateauVisitForm;
    private GUIControllerChateauIPForm chateauIPForm;
    private GUIControllerVillageVisitForm villageVisitForm;
    private GUIControllerVillageIPForm villageIPForm;



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


    public void displayVillageVisitForm(boolean isNewVisit, Visit selectedVisit) {

        villageVisitForm.getInstance().displayForm(isNewVisit, selectedVisit);
    }

    public void displayVillageIPForm(boolean isNewVisit, InterestPoint selectedPoint) {

        villageIPForm.getInstance().displayForm(isNewVisit, selectedPoint);
    }

}
