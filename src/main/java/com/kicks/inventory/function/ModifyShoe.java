package com.kicks.inventory.function;

import com.kicks.inventory.PopupStage;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.kicks.inventory.Shoe;
import com.kicks.inventory.dao.ShoesDAO;

public class ModifyShoe {

    private ShoesDAO dao;

    public void modifyShoe(Stage primaryStage, Shoe shoe, TableView<Shoe> table, String sku){
        dao = ShoesDAO.getInstance();

        Stage popupStage = PopupStage.createPopupStage(primaryStage, "Modify/Sell Shoe");
        popupStage.initOwner(table.getScene().getWindow());

        GridPane popupGrid = new GridPane();
        popupGrid.setHgap(10);
        popupGrid.setVgap(10);
        popupGrid.setPadding(new Insets(10));

        Label modelLabel = new Label("Model:");
        TextField modelTextField = new TextField(shoe.getModel());
        popupGrid.addRow(0, modelLabel, modelTextField);

        Label brandLabel = new Label("Brand:");
        TextField brandTextField = new TextField(shoe.getBrand());
        popupGrid.addRow(1, brandLabel, brandTextField);

        Label colorwayLabel = new Label("Colorway:");
        TextField colorwayTextField = new TextField(shoe.getColorway());
        popupGrid.addRow(2, colorwayLabel, colorwayTextField);

        Label sizeLabel = new Label("Size:");
        TextField sizeTextField = new TextField(String.valueOf(shoe.getSize()));
        popupGrid.addRow(3, sizeLabel, sizeTextField);

        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField(String.valueOf(shoe.getPrice()));
        popupGrid.addRow(4, priceLabel, priceTextField);

        Label estSalePriceLabel = new Label("Est Sale Price:");
        TextField estSalePriceTextField = new TextField(String.valueOf(shoe.getEstSalePrice()));
        popupGrid.addRow(5, estSalePriceLabel, estSalePriceTextField);

        Label quantityLabel = new Label("Quantity:");
        TextField quantityTextField = new TextField(String.valueOf(shoe.getQuantity()));
        popupGrid.addRow(6, quantityLabel, quantityTextField);

        Label styleCodeLabel = new Label("Style Code:");
        TextField styleCodeTextField = new TextField(shoe.getStyleCode());
        popupGrid.addRow(7, styleCodeLabel, styleCodeTextField);

        Label skuLabel = new Label("SKU:");
        TextField skuTextField = new TextField(sku.isEmpty() ? shoe.getSku() : sku);
        popupGrid.addRow(8, skuLabel, skuTextField);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            // Close the popup window without making any changes
            popupStage.close();
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {

            // Update the shoe object with the new values from the text fields
            shoe.setModel(modelTextField.getText());
            shoe.setBrand(brandTextField.getText());
            shoe.setColorway(colorwayTextField.getText());
            shoe.setSize(Double.parseDouble(sizeTextField.getText()));
            shoe.setPrice(Double.parseDouble(priceTextField.getText()));
            shoe.setEstSalePrice(Double.parseDouble(estSalePriceTextField.getText()));
            shoe.setQuantity(Integer.parseInt(quantityTextField.getText()));
            shoe.setStyleCode(styleCodeTextField.getText());
            shoe.setSku(skuTextField.getText());

            dao.updateShoe(shoe);

            // Refresh the table view to reflect the changes
            table.refresh();

            // Close the popup window
            popupStage.close();
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        popupGrid.add(buttonBox, 1, 9);

        VBox sellShoeVBox = SellShoe.sellShoe(popupStage, table, shoe, quantityTextField);
        HBox hBox = new HBox(popupGrid, new Separator(Orientation.VERTICAL), sellShoeVBox);

        Scene popupScene = new Scene(hBox);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

}
