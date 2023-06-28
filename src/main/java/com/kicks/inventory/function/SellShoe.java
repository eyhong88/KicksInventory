package com.kicks.inventory.function;

import com.kicks.inventory.PopupStage;
import com.kicks.inventory.service.KicksClientService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.kicks.inventory.Shoe;
import com.kicks.inventory.ShoeSale;
import com.kicks.inventory.dao.ShoesDAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SellShoe {
    private static KicksClientService service;
    public static VBox sellShoe(Stage modifyStage, TableView<Shoe> table, Shoe shoe, TextField quantityTextField) {
        service = KicksClientService.getInstance();

        // Create TextFields for the ShoeSale fields
        TextField priceTextField = new TextField();
        TextField saleDateTextField = new TextField();
        // Set the prompt text for the sale date field and add a listener to select all text when focused
        saleDateTextField.setPromptText("MM/dd/yyyy");
        saleDateTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(() -> saleDateTextField.selectAll());
            }
        });

        // Set the current date as the default sale date
        saleDateTextField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // Pre-populate the SKU TextField with the shoe's SKU
        TextField skuTextField = new TextField(shoe.getSku());
        skuTextField.setEditable(false);

        Button sellButton = new Button("Sell");
        sellButton.setOnAction(event -> {
            // Get the values from the TextFields
            double price = Double.parseDouble(priceTextField.getText());
            // Get the sale date from the sale date field
            LocalDate saleDate = LocalDate.parse(saleDateTextField.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));


            // Decrement the quantity of the Shoe
            int quantity = shoe.getQuantity();
            if (quantity == 0) {
                service.getShoes().remove(shoe);
                // Show a popup if the quantity is 0
                Stage popupStage = PopupStage.createPopupStage(modifyStage, "Error");
                Label label = new Label("You don't own any more pairs of this shoe");
                Button okButton = new Button("OK");
                okButton.setOnAction(e -> {
                    popupStage.close();
                    modifyStage.close();
                });
                VBox vbox = new VBox(label, okButton);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);
                Scene popupScene = new Scene(vbox, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            } else {
                shoe.setQuantity(quantity - 1);
                service.updateShoe(shoe);

                quantityTextField.setText(String.valueOf(shoe.getQuantity()));

                // Insert a new ShoeSale record
                ShoeSale sale = new ShoeSale(shoe.getSku(), price, saleDate);
                service.addShoeSale(sale);

                // Refresh the shoe list
                table.setItems(FXCollections.observableArrayList(service.getShoes()));
            }

            table.refresh();
        });

        // Create a GridPane to hold the TextFields and the Sell button
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(new Label("SKU:"), 0, 0);
        gridPane.add(skuTextField, 1, 0);
        gridPane.add(new Label("Price:"), 0, 1);
        gridPane.add(priceTextField, 1, 1);
        gridPane.add(new Label("Sale Date:"), 0, 2);
        gridPane.add(saleDateTextField, 1, 2);
        GridPane.setHalignment(sellButton, HPos.CENTER);
        gridPane.add(sellButton, 1, 3);

        return new VBox(gridPane);
    }


}
