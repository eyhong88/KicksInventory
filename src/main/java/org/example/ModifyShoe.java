package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModifyShoe {
    public static void modifyShoe(Stage primaryStage, Shoe shoe, TableView<Shoe> table, String sku){
//        Shoe selectedShoe = table.getSelectionModel().getSelectedItem();
        Stage popupStage = PopupStage.createPopupStage(primaryStage, "Modify Shoe");
        popupStage.initOwner(table.getScene().getWindow());

        VBox popupVBox = new VBox(10);
        popupVBox.setPadding(new Insets(10, 10, 10, 10));
        popupVBox.setAlignment(Pos.CENTER_LEFT);
        popupVBox.setPrefWidth(400);

        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField(shoe.getName());
        HBox nameHBox = new HBox(10, nameLabel, nameTextField);
        nameHBox.setAlignment(Pos.CENTER_LEFT);
        nameHBox.setPrefWidth(350);
        nameHBox.setPadding(new Insets(0, 0, 0, 10));

        Label brandLabel = new Label("Brand:");
        TextField brandTextField = new TextField(shoe.getBrand());
        HBox brandHBox = new HBox(10, brandLabel, brandTextField);
        brandHBox.setAlignment(Pos.CENTER_LEFT);
        brandHBox.setPrefWidth(350);
        brandHBox.setPadding(new Insets(0, 0, 0, 10));

        Label colorwayLabel = new Label("Colorway:");
        TextField colorwayTextField = new TextField(shoe.getColorway());
        HBox colorwayHBox = new HBox(10, colorwayLabel, colorwayTextField);
        colorwayHBox.setAlignment(Pos.CENTER_LEFT);
        colorwayHBox.setPrefWidth(350);
        colorwayHBox.setPadding(new Insets(0, 0, 0, 10));

        Label sizeLabel = new Label("Size:");
        TextField sizeTextField = new TextField(String.valueOf(shoe.getSize()));
        HBox sizeHBox = new HBox(10, sizeLabel, sizeTextField);
        sizeHBox.setAlignment(Pos.CENTER_LEFT);
        sizeHBox.setPrefWidth(350);
        sizeHBox.setPadding(new Insets(0, 0, 0, 10));

        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField(String.valueOf(shoe.getPrice()));
        HBox priceHBox = new HBox(10, priceLabel, priceTextField);
        priceHBox.setAlignment(Pos.CENTER_LEFT);
        priceHBox.setPrefWidth(350);
        priceHBox.setPadding(new Insets(0, 0, 0, 10));

        Label quantityLabel = new Label("Quantity:");
        TextField quantityTextField = new TextField(String.valueOf(shoe.getQuantity()));
        HBox quantityHBox = new HBox(10, quantityLabel, quantityTextField);
        quantityHBox.setAlignment(Pos.CENTER_LEFT);
        quantityHBox.setPrefWidth(350);
        quantityHBox.setPadding(new Insets(0, 0, 0, 10));

        Label styleCodeLabel = new Label("Style Code:");
        TextField styleCodeTextField = new TextField(shoe.getStyleCode());
        HBox styleCodeHBox = new HBox(10, styleCodeLabel, styleCodeTextField);
        styleCodeHBox.setAlignment(Pos.CENTER_LEFT);
        styleCodeHBox.setPrefWidth(350);
        styleCodeHBox.setPadding(new Insets(0, 0, 0, 10));

        Label skuLabel = new Label("SKU:");
        TextField skuTextField;
        if(sku.isEmpty()) {
            skuTextField = new TextField(shoe.getSku());
        } else {
            skuTextField = new TextField(sku);
        }
        HBox skuHBox = new HBox(10, skuLabel, skuTextField);
        skuHBox.setAlignment(Pos.CENTER_LEFT);
        skuHBox.setPrefWidth(350);
        skuHBox.setPadding(new Insets(0, 0, 0, 10));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            // Close the popup window without making any changes
            popupStage.close();
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Update the shoe object with the new values from the text fields
            shoe.setName(nameTextField.getText());
            shoe.setBrand(brandTextField.getText());
            shoe.setColorway(colorwayTextField.getText());
            shoe.setSize(Double.parseDouble(sizeTextField.getText()));
            shoe.setPrice(Double.parseDouble(priceTextField.getText()));
            shoe.setQuantity(Integer.parseInt(quantityTextField.getText()));
            shoe.setStyleCode(styleCodeTextField.getText());
            shoe.setSku(skuTextField.getText());

            // Refresh the table view to reflect the changes
            table.refresh();

            // Close the popup window
            popupStage.close();
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(submitButton, cancelButton);

        popupVBox.getChildren().addAll(
                nameHBox,
                brandHBox,
                colorwayHBox,
                sizeHBox,
                priceHBox,
                quantityHBox,
                styleCodeHBox,
                skuHBox,
                buttonBox
        );

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();

    }
}
