package javagraph.fileManager;

public class savePopUp extends PopUp {
    private String saveInfo = null;
    private String popUpTitle = "Save File";
    private String popUpButton = "Save";

    public savePopUp() {
        super.setPopUpButton(popUpButton);
        super.setPopUpTitle(popUpTitle);
    }

    public String getFileName() {
        return saveInfo;
    }
}
