package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShoeStoreUI extends Application {


    private static final int BUTTON_WIDTH = 100;
    private static final int ITEMS_PER_PAGE = 10;
    private TableView<Shoe> table;
    private ObservableList<Shoe> shoes;

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadShoes();

        table = createTable();
        Pagination pagination = createPagination();
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
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Shattered Backboard'", "Nike", 160.0, 7, "555088-005", "510815 026"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Black Toe'", "Nike", 160.0, 10, "555088-125", "575441 125"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Bred Toe'", "Nike", 160.0, 8, "555088-610", "575441 610"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Royal Blue'", "Nike", 160.0, 12, "555088-007", "575441 007"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Chicago'", "Nike", 160.0, 11, "555088-101", "575441 101"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Shadow'", "Nike", 160.0, 9, "555088-013", "575441 013"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Court Purple'", "Nike", 160.0, 6, "555088-500", "575441 500"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Storm Blue'", "Nike", 160.0, 10, "555088-127", "575441 127"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Metallic Red'", "Nike", 160.0, 8, "555088-103", "575441 103"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Yin Yang'", "Nike", 160.0, 7, "555088-011", "575441 011"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'All Star'", "Nike", 160.0, 9, "907958-015", "575441 015"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Game Royal'", "Nike", 160.0, 12, "555088-403", "575441 403"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Pine Green'", "Nike", 160.0, 8, "555088-302", "575441 302"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Rookie of the Year'", "Nike", 160.0, 6, "555088-700", "575441 700"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Bred'", "Nike", 160.0, 11, "555088-001", "575441 001"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Top 3'", "Nike", 160.0, 10, "555088-026", "575441 026"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Shadow 2.0'", "Nike", 170.0, 9, "555088-035", "555088-035"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Hyper Royal'", "Nike", 170.0, 8, "555088-402", "555088-402"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Court Purple' 2020", "Nike", 170, 6, "555088 500", "W1"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Metallic Red' 2017", "Nike", 160, 8, "555088 103", "W2"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Metallic Navy' 2016", "Nike", 160, 7, "555088 106", "W3"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Metallic Gold' 2018", "Nike", 160, 12, "555088 031", "W4"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Game Royal' 2018", "Nike", 160, 9, "555088 403", "W5"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Royal Toe' 2020", "Nike", 170, 11, "555088 041", "W6"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Shadow' 2018", "Nike", 160, 10, "555088 013", "W7"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Satin Black Toe' 2019", "Nike", 160, 7, "555088 125", "W8"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Obsidian' 2019", "Nike", 160, 8, "555088 140", "W9"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Black Toe' 2016", "Nike", 160, 11, "555088 125", "W10"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Bred Toe' 2018", "Nike", 160, 9, "555088 610", "W11"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Hyper Royal' 2018", "Nike", 160, 6, "555088 401", "W12"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Shattered Backboard 3.0' 2019", "Nike", 160, 12, "555088 028", "W13"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Black Satin' 2019", "Nike", 160, 8, "555088 060", "W14"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Bio Hack' 2020", "Nike", 170, 7, "555088 201", "W15"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Shadow 2.0' 2021", "Nike", 170, 10, "555088 035", "W16"));
        shoeList.add(new Shoe(10,"Air Jordan 1 Retro High OG 'Panda' 2019", "Nike", 160, 9, "555088 101", "W17"));

        shoes = FXCollections.observableList(shoeList);
    }

    private TableView<Shoe> createTable() {
        TableView<Shoe> table = new TableView<>();
        TableColumn<Shoe, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
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
        table.getColumns().addAll(nameCol, brandCol, priceCol, sizeCol, quantityCol, styleCodeCol, skuCol);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(Region.USE_COMPUTED_SIZE);
        table.setPrefHeight(Region.USE_COMPUTED_SIZE);
        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return table;
    }


    private Pagination createPagination() {
        Pagination pagination = new Pagination();
        pagination.setPageCount((int) Math.ceil((double) shoes.size() / ITEMS_PER_PAGE));
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
            table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
            return table;
        });
        pagination.setPrefHeight(350);
        return pagination;
    }
    private VBox createButtonBox(Pagination pagination) {
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        Button addButton = new Button("Add shoe");
        addButton.setPrefWidth(BUTTON_WIDTH);
        addButton.setOnAction(e -> addShoe(pagination));

        Button sellButton = new Button("Sell shoe");
        sellButton.setPrefWidth(BUTTON_WIDTH);
        sellButton.setOnAction(e -> sellShoe());

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(BUTTON_WIDTH);
        exitButton.setOnAction(e -> Platform.exit());

        buttonBox.getChildren().addAll(addButton, sellButton, exitButton);
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
            pagination.setPageFactory(this.createPageFactory(pagination));
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                clearButton.setVisible(false);
                pagination.setPageFactory(this.createPageFactory(pagination));
            } else {
                clearButton.setVisible(true);
                pagination.setPageFactory(this.createPageFactory(pagination, newValue));
            }
        });

        Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent -> {
            String searchTerm = searchField.getText();
            if (searchTerm == null || searchTerm.isEmpty()) {
                pagination.setPageFactory(this.createPageFactory(pagination));
            } else {
                pagination.setPageFactory(this.createPageFactory(pagination, searchTerm));
            }
        });

        HBox searchBox = new HBox(searchField, searchButton, clearButton);
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setPadding(new Insets(10));
        searchBox.setSpacing(10);
        searchBox.setStyle("-fx-background-color: #f2f2f2;");

        return searchBox;
    }

    private Callback<Integer, Node> createPageFactory(Pagination pagination) {
        return pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
            table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
            return table;
        };
    }

    private Callback<Integer, Node> createPageFactory(Pagination pagination, String searchTerm) {
        return pageIndex -> {
            Predicate<Shoe> nameBrandPredicate = shoe ->
                    shoe.getName().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getBrand().toLowerCase().contains(searchTerm.toLowerCase());
            List<Shoe> filteredShoes = shoes.stream()
                    .filter(nameBrandPredicate)
                    .collect(Collectors.toList());
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, filteredShoes.size());
            table.setItems(FXCollections.observableList(filteredShoes.subList(fromIndex, toIndex)));
            return table;
        };
    }


    private void addShoe(Pagination pagination) {
        // Create a new window for adding a shoe
        Stage addShoeStage = createPopupStage(primaryStage, "Add Shoe");

        // Create input fields for the shoe
        TextField brandField = new TextField();
        brandField.setPromptText("Brand");
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
        skuField.setPromptText("SKU");

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
            Shoe shoe = new Shoe(size, name, brand, price, quantity, styleCode, sku);

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
                nameField, brandField, priceField, sizeField, quantityField,
                styleCodeField, skuField, buttonBox);

        // Create a scene and set it to the add shoe window
        Scene scene = new Scene(layout);
        addShoeStage.setScene(scene);
        addShoeStage.setOnCloseRequest(e -> addShoeStage.close());

        // Show the add shoe window
        addShoeStage.showAndWait();
    }

//    private HBox createFilterBar(Pagination pagination) {
//        ComboBox<String> brandComboBox = new ComboBox<>();
//        brandComboBox.setPromptText("Filter by brand");
//        brandComboBox.getItems().addAll("Show All", "Nike", "Adidas", "Puma", "Reebok", "Under Armour");
//        brandComboBox.setOnAction(actionEvent -> {
//            String selectedBrand = brandComboBox.getValue();
//            if (selectedBrand == null || selectedBrand.isEmpty()) {
//                // reset the pagination to its original state
//                pagination.setPageCount((int) Math.ceil((double) shoes.size() / ITEMS_PER_PAGE));
//                pagination.setPageFactory(pageIndex -> {
//                    int fromIndex = pageIndex * ITEMS_PER_PAGE;
//                    int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
//                    table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
//                    return table;
//                });
//            } else {
//                // filter the list of shoes based on the selected brand
//                List<Shoe> filteredShoes = shoes.stream()
//                        .filter(shoe -> shoe.getBrand().equalsIgnoreCase(selectedBrand))
//                        .collect(Collectors.toList());
//                // update the pagination with the filtered list of shoes
//                pagination.setPageCount((int) Math.ceil((double) filteredShoes.size() / ITEMS_PER_PAGE));
//                pagination.setPageFactory(pageIndex -> {
//                    int fromIndex = pageIndex * ITEMS_PER_PAGE;
//                    int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, filteredShoes.size());
//                    table.setItems(FXCollections.observableList(filteredShoes.subList(fromIndex, toIndex)));
//                    return table;
//                });
//            }
//        });
//
//        HBox filterBox = new HBox(brandComboBox);
//        filterBox.setAlignment(Pos.CENTER_RIGHT);
//        filterBox.setPadding(new Insets(10));
//        filterBox.setSpacing(10);
//        filterBox.setStyle("-fx-background-color: #f2f2f2;");
//
//        return filterBox;
//    }
    private HBox createFilterBar(Pagination pagination) {
        // create a list of brand options for the filter
        List<String> brandOptions = shoes.stream()
                .map(Shoe::getBrand)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        brandOptions.add(0, "Show All"); // add a "Show All" option to the beginning of the list

        // create a combo box for the brand filter
        ComboBox<String> brandFilter = new ComboBox<>(FXCollections.observableArrayList(brandOptions));
        brandFilter.getSelectionModel().select(0); // select the "Show All" option by default
        brandFilter.setOnAction(actionEvent -> {
            String selectedBrand = brandFilter.getSelectionModel().getSelectedItem();
            if (selectedBrand.equals("Show All")) {
                // reset the pagination to its original state
                pagination.setPageCount((int) Math.ceil((double) shoes.size() / ITEMS_PER_PAGE));
                pagination.setPageFactory(pageIndex -> {
                    int fromIndex = pageIndex * ITEMS_PER_PAGE;
                    int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
                    table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
                    return table;
                });
            } else {
                Predicate<Shoe> brandPredicate = shoe ->
                        shoe.getBrand().equals(selectedBrand);
                table.setItems(FXCollections.observableList(shoes.stream()
                        .filter(brandPredicate)
                        .collect(Collectors.toList())));
                pagination.setPageCount((int) Math.ceil((double) table.getItems().size() / ITEMS_PER_PAGE));
                pagination.setCurrentPageIndex(0);
            }
        });

        // create a checkbox for the "Show All" option
        CheckBox showAllCheckBox = new CheckBox("Show All");
        showAllCheckBox.setSelected(true); // check the checkbox by default
        showAllCheckBox.setOnAction(actionEvent -> {
            if (showAllCheckBox.isSelected()) {
                brandFilter.getSelectionModel().select(0); // select the "Show All" option
            }
        });

        HBox filterBox = new HBox(new Label("Filter by Brand:"), brandFilter, showAllCheckBox);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(10));
        filterBox.setSpacing(10);
        filterBox.setStyle("-fx-background-color: #f2f2f2;");

        return filterBox;
    }


    private Stage createPopupStage(Stage primaryStage, String title) {
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);
        popupStage.setResizable(false);
        return popupStage;
    }

    private void sellShoe() {
        // TODO: Implement sellShoe method
    }

    public static void main(String[] args) {
        launch(args);
    }
}
