package javagraph.fileManager;

public class openPopUp extends PopUp {
    private String saveInfo = null;
    private String popUpTitle = "Open File";
    private String popUpButton = "Open";

    public openPopUp() {
        super.setPopUpButton(popUpButton);
        super.setPopUpTitle(popUpTitle);
    }

    public String getFileName() {
        return saveInfo;
    }
}
