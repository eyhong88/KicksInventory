package com.kicks.inventory.function;

import com.kicks.inventory.dao.ShoesDAO;
import com.kicks.inventory.dto.Shoe;
import com.kicks.inventory.dto.ShoeSale;
import com.kicks.inventory.dto.Vendor;
import com.kicks.inventory.factory.PayOutFactory;
import com.kicks.inventory.service.KicksClientService;
import com.kicks.inventory.service.PayOut;
import com.kicks.inventory.util.PopupStage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SellShoe {
    private ShoesDAO dao;
    private Vendor vendor;
    private double payOut;
    private KicksClientService service;
    public VBox sellShoe(Stage modifyStage, TableView<Shoe> table, Shoe shoe, TextField quantityTextField) {
        service = KicksClientService.getInstance();

        // Create TextFields for the ShoeSale fields
        TextField priceTextField = new TextField();
        TextField taxTextField = new TextField();
        TextField saleDateTextField = new TextField();
        // Set the prompt text for the sale date field and add a listener to select all text when focused
        saleDateTextField.setPromptText("MM/dd/yyyy");
        saleDateTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(() -> saleDateTextField.selectAll());
            }
        });

        // Create a ComboBox for the vendor selection
        ComboBox<String> vendorComboBox = new ComboBox<>();
        List<Vendor> vendors = service.getVendors();
        List<String> vendorNames = vendors.stream().map(Vendor::getVendorName).collect(Collectors.toList());
        vendorComboBox.getItems().addAll(vendorNames);
        // Create a label to display the vendor fee
        Label vendorFeeLabel = new Label();

        // Set the current date as the default sale date
        saleDateTextField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // Pre-populate the SKU TextField with the shoe's SKU
        TextField skuTextField = new TextField(shoe.getSku());
        skuTextField.setEditable(false);

        TextField totalPayoutTextField = new TextField();
        totalPayoutTextField.setEditable(false);
        totalPayoutTextField.setVisible(false);

        Button sellButton = sellButton(vendorComboBox, priceTextField, saleDateTextField, quantityTextField, totalPayoutTextField, taxTextField,
                vendors, vendorFeeLabel, shoe, modifyStage, table);

        // Create a GridPane to hold the TextFields, ComboBox, and the Sell button
        GridPane gridPane = setupGridPane(priceTextField, saleDateTextField, totalPayoutTextField, vendorComboBox, vendorFeeLabel, skuTextField, taxTextField, sellButton);

        return new VBox(gridPane);
    }

    private Button sellButton(ComboBox<String> vendorComboBox,
                                     TextField priceTextField,
                                     TextField saleDateTextField,
                                     TextField quantityTextField,
                                     TextField totalPayoutTextField,
                                     TextField taxTextField,
                                     List<Vendor> vendors,
                                     Label vendorFeeLabel,
                                     Shoe shoe,
                                     Stage modifyStage,
                                     TableView<Shoe> table) {

        Button sellButton = new Button("Sell");
        // Create a BooleanBinding that checks if both vendor and price are entered
        BooleanBinding isSellButtonDisabled = Bindings.createBooleanBinding(
                () -> vendorComboBox.getValue() == null || priceTextField.getText().isEmpty(),
                vendorComboBox.valueProperty(),
                priceTextField.textProperty(),
                taxTextField.textProperty()
        );

        sellButton.disableProperty().bind(isSellButtonDisabled); // Disable the Sell button when the BooleanBinding is true

        vendorComboBox.setOnAction(event -> {
            // Get the selected vendor from the ComboBox
            String selectedVendor = vendorComboBox.getValue();
            if (selectedVendor != null) {
                Optional<Vendor> vendOpt = vendors.stream().filter(v -> v.getVendorName().equalsIgnoreCase(selectedVendor))
                        .findFirst();

                if (vendOpt.isPresent()) {
                    vendor = vendOpt.get();
                    vendorFeeLabel.setText("Vendor Fee: " + (100 * vendor.getVendorFee()) + "%");
                    totalPayoutTextField.setVisible(true); // Show the total payout text field
                    payOut = updateTotalPayout(totalPayoutTextField, priceTextField.getText(), taxTextField.getText());

                }
            } else {
                vendor = null;
                vendorFeeLabel.setText(""); // Clear the vendor fee label if no vendor is selected
                totalPayoutTextField.setVisible(false); // Hide the total payout text field
            }
        });


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
                ShoeSale sale = new ShoeSale(shoe.getSku(), price, saleDate.toString(), vendor.getId(), payOut);
                service.addShoeSale(sale);

                // Refresh the shoe list
                table.setItems(FXCollections.observableArrayList(service.getShoes()));
            }

            totalPayoutTextField.clear();
            priceTextField.clear();
            vendorFeeLabel.setVisible(true);

            table.refresh();
        });

        return sellButton;
    }
    private double updateTotalPayout(TextField totalPayoutTextField, String priceText, String taxText) {
        double payOut = 0.0;
        if (priceText.isEmpty()) {
            totalPayoutTextField.clear();
        } else {
            PayOut po = PayOutFactory.getInstance(vendor);

            double price = Double.parseDouble(priceText);
            double tax = Double.parseDouble(taxText);
            payOut = po.calculate(price, vendor.getVendorFee(), tax);
            totalPayoutTextField.setText(String.valueOf((new DecimalFormat("#.00")).format(payOut)));
        }

        return payOut;
    }
    private GridPane setupGridPane(TextField priceTextField, TextField saleDateTextField,
                                          TextField totalPayoutTextField, ComboBox<String> vendorComboBox,
                                          Label vendorFeeLabel, TextField skuTextField, TextField taxTextField,
                                          Button sellButton) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(new Label("SKU:"), 0, 0);
        gridPane.add(skuTextField, 1, 0);
        gridPane.add(new Label("Price:"), 0, 1);
        gridPane.add(priceTextField, 1, 1);
        gridPane.add(new Label("Tax:"), 0, 2);
        gridPane.add(taxTextField, 1, 2);
        gridPane.add(new Label("Sale Date:"), 0, 3);
        gridPane.add(saleDateTextField, 1, 3);
        gridPane.add(new Label("Vendor:"), 0, 4);
        gridPane.add(vendorComboBox, 1, 4);
        gridPane.add(vendorFeeLabel, 1, 5);
        gridPane.add(new Label("Total Payout:"), 0, 6);
        gridPane.add(totalPayoutTextField, 1, 6);
        // Create a label and text field for the total payout
        GridPane.setHalignment(sellButton, HPos.CENTER);
        gridPane.add(sellButton, 1, 20);
        return gridPane;
    }




}
