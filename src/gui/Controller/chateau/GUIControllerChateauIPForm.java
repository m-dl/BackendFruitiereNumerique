package gui.Controller.chateau;

public class GUIControllerChateauIPForm {

    /*

    private GUIWindow guiWindow;
    private static GUIControllerChateauIPForm INSTANCE = new GUIControllerChateauIPForm();
    private Stage stage;

    public boolean isAlreadyDisplayed = false;
    public TextField visitName;
    public TextArea visitPresTextFR;
    public TextArea visitPresTextEN;
    public TextArea visitLengthFR;
    public TextArea visitLengthEN;


    private GUIControllerChateauIPForm()
    {}


    public static GUIControllerChateauIPForm getInstance() {
        return INSTANCE;
    }

    public void setMainClass(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }


    public void displayForm(boolean isNewVisit, InterestPoint selectedPoint) {

        if (!isAlreadyDisplayed) {

            try {


                GUIUtilities utilities = new GUIUtilities();

                ScrollPane root = (ScrollPane) utilities.loadLayout("view/chateau/visitForm.fxml", this);

                stage = new Stage();
                stage.setTitle("Formulaire de visite");
                stage.setScene(new Scene(root));

                stage.setOnCloseRequest(closeEvent -> {
                    System.out.println("Stage is closing");
                    isAlreadyDisplayed = false;
                });


                if (!isNewVisit && selectedVisit != null) {

                    this.fillInputs(selectedVisit);

                    stage.show();
                }
                else if (isNewVisit){


                    stage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void fillInputs(Visit v) {
            visitName.setText(v.getName());
            visitPresTextFR.setText(v.getOverview().readPresentation_FR());
            visitPresTextEN.setText(v.getOverview().readPresentation_EN());
            visitLengthFR.setText(v.getOverview().readLength_FR());
            visitLengthEN.setText(v.getOverview().readLength_EN());

    }


    @FXML
    public void saveChanges() {
        guiWindow.test();
        stage.close();

    }
*/

}
