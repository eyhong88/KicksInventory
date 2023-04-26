package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.example.ShoeTablePagination.pageFactory;

public class ShoeStoreUI extends Application {
    public static final int BUTTON_WIDTH = 100;
    public static final int ITEMS_PER_PAGE = 20;
    private TableView<Shoe> table;
    private ObservableList<Shoe> shoes;

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadShoes();

        table = createTable();
        addRowDoubleClickHandler(table);

        ShoeTablePagination tablePagination = new ShoeTablePagination();
        Pagination pagination = tablePagination.createPagination(shoes, table);
        VBox buttonBox = createButtonBox(pagination);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(table);
        borderPane.setCenter(pagination);
        borderPane.setRight(buttonBox);

        HBox searchBar = createSearchBar(pagination);

        searchBar.getChildren().add(createFilterBar(pagination));
        borderPane.setTop(searchBar);


        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add("project.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadShoes() {
        List<Shoe> shoeList = new ArrayList<>();
        shoeList.add(new Shoe("Shattered Backboard", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 7, "555088-005", "510815 026"));
        shoeList.add(new Shoe("Black Toe", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 10, "555088-125", "575441 125"));
        shoeList.add(new Shoe("Bred Toe", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 8, "555088-610", "575441 610"));
        shoeList.add(new Shoe("Royal Blue", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 12, "555088-007", "575441 007"));
        shoeList.add(new Shoe("Chicago", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 11, "555088-101", "575441 101"));
        shoeList.add(new Shoe("Shadow", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 9, "555088-013", "575441 013"));
        shoeList.add(new Shoe("Court Purple", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 6, "555088-500", "575441 500"));
        shoeList.add(new Shoe("Storm Blue", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 10, "555088-127", "575441 127"));
        shoeList.add(new Shoe("Metallic Red", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 8, "555088-103", "575441 103"));
        shoeList.add(new Shoe("Yin Yang", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 7, "555088-011", "575441 011"));
        shoeList.add(new Shoe("All Star", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 9, "907958-015", "575441 015"));
        shoeList.add(new Shoe("Game Royal", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 12, "555088-403", "575441 403"));
        shoeList.add(new Shoe("Pine Green", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 8, "555088-302", "575441 302"));
        shoeList.add(new Shoe("Rookie of the Year", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 6, "555088-700", "575441 700"));
        shoeList.add(new Shoe("Bred", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 11, "555088-001", "575441 001"));
        shoeList.add(new Shoe("Top 3", 10,"Air Jordan 1 Retro High OG", "Nike", 160.0, 10, "555088-026", "575441 026"));
        shoeList.add(new Shoe("Shadow 2.0", 10,"Air Jordan 1 Retro High OG", "Nike", 170.0, 9, "555088-035", "555088-035"));
        shoeList.add(new Shoe("Hyper Royal", 10,"Air Jordan 1 Retro High OG", "Nike", 170.0, 8, "555088-402", "555088-402"));
        shoeList.add(new Shoe("Court Purple", 10,"Air Jordan 1 Retro High OG", "Nike", 170, 6, "555088 500", "W1"));
        shoeList.add(new Shoe("Metallic Red", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 8, "555088 103", "W2"));
        shoeList.add(new Shoe("Metallic Navy", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 7, "555088 106", "W3"));
        shoeList.add(new Shoe("Metallic Gold", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 12, "555088 031", "W4"));
        shoeList.add(new Shoe("Game Royal", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 9, "555088 403", "W5"));
        shoeList.add(new Shoe("Royal Toe", 10,"Air Jordan 1 Retro High OG", "Nike", 170, 11, "555088 041", "W6"));
        shoeList.add(new Shoe("Shadow", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 10, "555088 013", "W7"));
        shoeList.add(new Shoe("Satin Black Toe", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 7, "555088 125", "W8"));
        shoeList.add(new Shoe("Obsidian", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 8, "555088 140", "W9"));
        shoeList.add(new Shoe("Black Toe", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 11, "555088 125", "W10"));
        shoeList.add(new Shoe("Bred Toe", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 9, "555088 610", "W11"));
        shoeList.add(new Shoe("Hyper Royal", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 6, "555088 401", "W12"));
        shoeList.add(new Shoe("Shattered Backboard 3.0", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 12, "555088 028", "W13"));
        shoeList.add(new Shoe("Black Satin", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 8, "555088 060", "W14"));
        shoeList.add(new Shoe("Bio Hack", 10,"Air Jordan 1 Retro High OG", "Nike", 170, 7, "555088 201", "W15"));
        shoeList.add(new Shoe("Shadow 2.0", 10,"Air Jordan 1 Retro High OG", "Nike", 170, 10, "555088 035", "W16"));
        shoeList.add(new Shoe("Panda", 10,"Air Jordan 1 Retro High OG", "Nike", 160, 9, "555088 101", "W17"));
        shoeList.add(new Shoe("Dame 2", 10,"Dame 2", "Adidas", 160, 9, "ad 101", "W18"));

        shoes = FXCollections.observableList(shoeList);
    }

    private TableView<Shoe> createTable() {
        TableView<Shoe> table = new TableView<>();
        TableColumn<Shoe, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<Shoe, String> cwCol = new TableColumn<>("Colorway");
        cwCol.setCellValueFactory(new PropertyValueFactory<>("colorway"));
        TableColumn<Shoe, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Shoe, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        TableColumn<Shoe, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Shoe, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Shoe, String> styleCodeCol = new TableColumn<>("Style Code");
        styleCodeCol.setCellValueFactory(new PropertyValueFactory<>("styleCode"));
        TableColumn<Shoe, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>("sku"));
        table.getColumns().addAll(nameCol, cwCol, brandCol, priceCol, sizeCol, quantityCol, styleCodeCol, skuCol);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(Region.USE_COMPUTED_SIZE);
        table.setPrefHeight(Region.USE_COMPUTED_SIZE);
        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null) {
                Shoe selectedShoe = table.getSelectionModel().getSelectedItem();
                ModifyShoe.modifyShoe(primaryStage, selectedShoe, table, "");
            }
        });
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return table;
    }

    private void addRowDoubleClickHandler(TableView<Shoe> table) {
        table.setRowFactory(tv -> {
            TableRow<Shoe> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Shoe shoe = row.getItem();
                    // create popup and populate fields
                    Popup popup = new Popup();
                    popup.setAutoHide(true);
                    VBox popupContent = new VBox();
                    popupContent.getChildren().add(new Label("Brand: " + shoe.getBrand()));
                    popupContent.getChildren().add(new Label("Colorway: " + shoe.getColorway()));
                    popupContent.getChildren().add(new Label("Name: " + shoe.getName()));
                    popupContent.getChildren().add(new Label("Size: " + shoe.getSize()));
                    popupContent.getChildren().add(new Label("Price: " + shoe.getPrice()));
                    popupContent.getChildren().add(new Label("Quantity: " + shoe.getQuantity()));
                    popupContent.getChildren().add(new Label("Style Code: " + shoe.getStyleCode()));
                    popupContent.getChildren().add(new Label("SKU: " + shoe.getSku()));
                    popup.getContent().add(popupContent);
                    popup.show(table.getScene().getWindow());
                }
            });
            return row;
        });
    }


    private VBox createButtonBox(Pagination pagination) {
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        Button addButton = new Button("Add Shoe");
        addButton.setPrefWidth(BUTTON_WIDTH);
        addButton.setOnAction(e -> {
            AddShoe addShoe = new AddShoe(primaryStage, pagination);
            addShoe.addShoe(table, shoes, "");

        });

        Button sellButton = new Button("Sell Shoe");
        sellButton.setPrefWidth(BUTTON_WIDTH);
        sellButton.setOnAction(e -> sellShoe());

        Button skuScanButton = new Button("SKU Scan");
        skuScanButton.setPrefWidth(BUTTON_WIDTH);
        skuScanButton.setOnAction(e -> skuScan(pagination));

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(BUTTON_WIDTH);
        exitButton.setOnAction(e -> Platform.exit());

        buttonBox.getChildren().addAll(addButton, sellButton, skuScanButton, exitButton);
        return buttonBox;
    }

    private HBox createSearchBar(Pagination pagination) {
        TextField searchField = new TextField();
        searchField.setPromptText("Search shoes");

        Button clearButton = new Button("X");
        clearButton.setVisible(false);
        clearButton.setOnAction(event -> {
            searchField.clear();
            clearButton.setVisible(false);
            pagination.setPageFactory(this.createPageFactory());
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                clearButton.setVisible(false);
                pagination.setPageFactory(this.createPageFactory());
            } else {
                clearButton.setVisible(true);
                pagination.setPageFactory(this.createSearchPageFactory(newValue));
            }
        });

        Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent -> {
            String searchTerm = searchField.getText();
            if (searchTerm == null || searchTerm.isEmpty()) {
                pagination.setPageFactory(this.createPageFactory());
            } else {
                pagination.setPageFactory(this.createSearchPageFactory(searchTerm));
            }
        });

        HBox searchBox = new HBox(searchField, searchButton, clearButton);
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setPadding(new Insets(10));
        searchBox.setSpacing(10);
        searchBox.setStyle("-fx-background-color: #f2f2f2;");

        // Add a delay to give the search bar enough time to be added to the scene
        Platform.runLater(searchField::requestFocus);

        return searchBox;
    }

    private Callback<Integer, Node> createPageFactory() {
        return pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
            table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
            return table;
        };
    }

    private Callback<Integer, Node> createSearchPageFactory(String searchTerm) {
        return pageIndex -> {
            Predicate<Shoe> searchPredicate = shoe ->
                    shoe.getName().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getBrand().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getSku().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getStyleCode().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getColorway().toLowerCase().contains(searchTerm.toLowerCase());
            List<Shoe> filteredShoes = shoes.stream()
                    .filter(searchPredicate)
                    .collect(Collectors.toList());
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, filteredShoes.size());
            table.setItems(FXCollections.observableList(filteredShoes.subList(fromIndex, toIndex)));
            return table;
        };
    }

    private HBox createFilterBar(Pagination pagination) {
        // create a list of brand options for the filter
        List<String> brandOptions = createBrandOptionsList();

        // create a combo box for the brand filter
        ComboBox<String> brandFilter = new ComboBox<>(FXCollections.observableArrayList(brandOptions));

        // create a checkbox for the "Show All" option
        CheckBox showAllCheckBox = new CheckBox("Show All");
        showAllCheckBox.setSelected(true); // check the checkbox by default
        showAllCheckBox.setOnAction(actionEvent -> {
            if (showAllCheckBox.isSelected()) {
                brandFilter.getSelectionModel().select(0); // select the "Show All" option
            }
        });

        brandFilter.getSelectionModel().select(0); // select the "Show All" option by default
        brandFilter.setOnAction(actionEvent -> {
            String selectedBrand = brandFilter.getSelectionModel().getSelectedItem();
            if (selectedBrand != null && selectedBrand.equals("Show All")) {
                // reset the pagination to its original state
                pagination.setPageCount((int) Math.ceil((double) shoes.size() / ITEMS_PER_PAGE));
                pagination.setPageFactory(pageFactory(shoes, table));
            } else {
                Predicate<Shoe> brandPredicate = shoe -> shoe.getBrand().equals(selectedBrand);
                List<Shoe> filteredShoes = shoes.stream()
                        .filter(brandPredicate)
                        .collect(Collectors.toList());
                pagination.setPageCount((int) Math.ceil((double) filteredShoes.size() / ITEMS_PER_PAGE));
                pagination.setPageFactory(pageFactory(filteredShoes, table));
                showAllCheckBox.setSelected(false);
            }
        });

        // create a listener to update the brand options when the shoes list is modified
        ListChangeListener<Shoe> shoesListener = c -> {
            if (c.next()) {
                if (c.wasAdded() || c.wasRemoved()) {
                    brandOptions.clear();
                    brandOptions.addAll(createBrandOptionsList());
                    brandFilter.setItems(FXCollections.observableArrayList(brandOptions));
                }
            }
        };

        // add the listener to the shoes list
        shoes.addListener(shoesListener);

        HBox filterBox = new HBox(new Label("Filter by Brand:"), brandFilter, showAllCheckBox);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(10));
        filterBox.setSpacing(10);
        filterBox.setStyle("-fx-background-color: #f2f2f2;");

        return filterBox;
    }

    // helper method to create the brand options list
    private List<String> createBrandOptionsList() {
        List<String> brandOptions = shoes.stream()
                .map(Shoe::getBrand)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        brandOptions.add(0, "Show All"); // add a "Show All" option to the beginning of the list
        return brandOptions;
    }

    private void sellShoe() {
        // TODO: Implement sellShoe method
    }

    private void skuScan(Pagination pagination) {
        Stage popupStage = PopupStage.createPopupStage(primaryStage, "SKU Scan");
        popupStage.initOwner(primaryStage);

        TextField skuTextField = new TextField();
        skuTextField.setPromptText("Enter SKU");
        skuTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String sku = skuTextField.getText();
                Optional<Shoe> optionalShoe = shoes.stream().filter(shoe -> shoe.getSku().equals(sku)).findFirst();
                if (optionalShoe.isPresent()) {
                    // SKU exists, call modifyShoe method
                    Shoe shoe = optionalShoe.get();
                    ModifyShoe.modifyShoe(primaryStage, shoe, table, sku);
                    Platform.runLater(skuTextField::requestFocus);

                    skuTextField.clear();
                } else {
                    // SKU does not exist, call addShoe method
                    AddShoe addShoe = new AddShoe(primaryStage, pagination);
                    addShoe.addShoe(table, shoes, sku);
                    Platform.runLater(skuTextField::requestFocus);

                    skuTextField.clear();
                }
            }
        });

        skuTextField.requestFocus();

        Button exitButton = new Button("Exit");
        // Create an HBox to hold the text field and the exit button
        HBox hbox = new HBox(skuTextField, exitButton);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);

        // Create the root VBox and add the HBox and any other content you need
        VBox root = new VBox(hbox);
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        // Add styling to the VBox
        root.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Set up the event handler for the exit button
        exitButton.setOnAction(e -> popupStage.close());

        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void createModifyPopup(Stage primaryStage, Shoe shoe) {
        Stage popupStage = PopupStage.createPopupStage(primaryStage, "Modify Shoe");
        popupStage.initOwner(primaryStage);
        VBox popupVBox = new VBox(10);
        popupVBox.setPadding(new Insets(10));
        popupVBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Name: ");
        TextField nameField = new TextField(shoe.getName());
        HBox nameBox = new HBox(nameLabel, nameField);
        nameBox.setAlignment(Pos.CENTER_LEFT);

        Label brandLabel = new Label("Brand: ");
        TextField brandField = new TextField(shoe.getBrand());
        HBox brandBox = new HBox(brandLabel, brandField);
        brandBox.setAlignment(Pos.CENTER_LEFT);

        Label cwLabel = new Label("Colorway: ");
        TextField cwField = new TextField(shoe.getColorway());
        HBox cwBox = new HBox(cwLabel, cwField);
        cwBox.setAlignment(Pos.CENTER_LEFT);

        Label sizeLabel = new Label("Size: ");
        TextField sizeField = new TextField(Double.toString(shoe.getSize()));
        HBox sizeBox = new HBox(sizeLabel, sizeField);
        sizeBox.setAlignment(Pos.CENTER_LEFT);

        Label priceLabel = new Label("Price: ");
        TextField priceField = new TextField(Double.toString(shoe.getPrice()));
        HBox priceBox = new HBox(priceLabel, priceField);
        priceBox.setAlignment(Pos.CENTER_LEFT);

        Label quantityLabel = new Label("Quantity: ");
        TextField quantityField = new TextField(Integer.toString(shoe.getQuantity()));
        HBox quantityBox = new HBox(quantityLabel, quantityField);
        quantityBox.setAlignment(Pos.CENTER_LEFT);

        Label styleCodeLabel = new Label("Style Code: ");
        TextField styleCodeField = new TextField(shoe.getStyleCode());
        HBox styleCodeBox = new HBox(styleCodeLabel, styleCodeField);
        styleCodeBox.setAlignment(Pos.CENTER_LEFT);

        Label skuLabel = new Label("SKU: ");
        TextField skuField = new TextField(shoe.getSku());
        HBox skuBox = new HBox(skuLabel, skuField);
        skuBox.setAlignment(Pos.CENTER_LEFT);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(submitEvent -> {
            shoe.setName(nameField.getText());
            shoe.setBrand(brandField.getText());
            shoe.setColorway(cwField.getText());
            shoe.setSize(Double.parseDouble(sizeField.getText()));
            shoe.setPrice(Double.parseDouble(priceField.getText()));
            shoe.setQuantity(Integer.parseInt(quantityField.getText()));
            shoe.setStyleCode(styleCodeField.getText());
            shoe.setSku(skuField.getText());
            popupStage.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> popupStage.close());
        buttonBox.getChildren().addAll(submitButton, cancelButton);

        popupVBox.getChildren().addAll(nameBox, brandBox, cwBox, sizeBox, priceBox, quantityBox, styleCodeBox, skuBox, buttonBox);
        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
