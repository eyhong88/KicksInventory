package org.example;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupStage {
    public static Stage createPopupStage(Stage primaryStage, String title) {
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);
        popupStage.setResizable(false);
        return popupStage;
    }
}
