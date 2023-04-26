package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.example.ShoeStoreUI.ITEMS_PER_PAGE;

public class AddShoe {
    private Pagination pagination;
    private Stage primaryStage;

    public AddShoe(Stage primaryStage, Pagination pagination){
        this.pagination = pagination;
        this.primaryStage = primaryStage;
    }

    public void addShoe(TableView<Shoe> table, ObservableList<Shoe> shoes, String skuStr) {
        // Create a new window for adding a shoe
        Stage addShoeStage = PopupStage.createPopupStage(primaryStage, "Add Shoe");

        // Create input fields for the shoe
        TextField brandField = new TextField();
        brandField.setPromptText("Brand");
        TextField cwField = new TextField();
        cwField.setPromptText("Colorway");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField sizeField = new TextField();
        sizeField.setPromptText("Size");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        TextField styleCodeField = new TextField();
        styleCodeField.setPromptText("Style Code");
        TextField skuField = new TextField();
        if(skuStr.isEmpty())
            skuField.setPromptText("SKU");
        else
            skuField.setText(skuStr);

        // Create a button for adding the shoe
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            // Create a new shoe object
            double size = Double.parseDouble(sizeField.getText());
            String name = nameField.getText();
            String brand = brandField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String styleCode = styleCodeField.getText();
            String sku = skuField.getText();
            String cw = cwField.getText();
            Shoe shoe = new Shoe(cw, size, name, brand, price, quantity, styleCode, sku);

            // Add the shoe to the list of shoes
            shoes.add(shoe);

            // Update the pagination to display the new shoe
            int pageIndex = pagination.getCurrentPageIndex();
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
            table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));

            // Close the add shoe window
            addShoeStage.close();
        });

        // Create a button for canceling the add shoe operation
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> addShoeStage.close());

        // Create a layout for the input fields and buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton, cancelButton);

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                nameField, cwField, brandField, priceField, sizeField, quantityField,
                styleCodeField, skuField, buttonBox);

        // Create a scene and set it to the add shoe window
        Scene scene = new Scene(layout);
        addShoeStage.setScene(scene);
        addShoeStage.setOnCloseRequest(e -> addShoeStage.close());

        // Show the add shoe window
        addShoeStage.showAndWait();
    }
}
