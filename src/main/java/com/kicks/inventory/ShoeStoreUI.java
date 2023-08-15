package com.kicks.inventory;

import com.kicks.inventory.dto.Shoe;
import com.kicks.inventory.dto.ShoeSale;
import com.kicks.inventory.pagination.ShoeTablePagination;
import com.kicks.inventory.util.PopupStage;
import com.kicks.inventory.function.AddShoe;
import com.kicks.inventory.function.ModifyShoe;
import com.kicks.inventory.service.KicksClientService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShoeStoreUI extends Application {
    public static final int BUTTON_WIDTH = 100;
    public static final int ITEMS_PER_PAGE = 20;
    private TableView<Shoe> table;
    private Stage primaryStage;
    private KicksClientService service;
    private String currentBrand = "Show All";

    private static final DecimalFormat DF = new DecimalFormat("#.00");

    @Override
    public void start(Stage primaryStage) {
        service = KicksClientService.getInstance();

        this.primaryStage = primaryStage;
        loadShoes();

        table = createTable();
        addRowDoubleClickHandler(table);

        ShoeTablePagination tablePagination = new ShoeTablePagination();
        Pagination pagination = tablePagination.createPagination(service.getShoes(), table);
        VBox buttonBox = createButtonBox(pagination);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(table);
        borderPane.setCenter(pagination);
        borderPane.setRight(buttonBox);

        HBox searchBar = createSearchBar(pagination);

        Button refreshButton = new Button("Refresh");
        HBox refreshBox = new HBox(refreshButton);
        refreshBox.setPadding(new Insets(10));
        refreshBox.setSpacing(10);

        refreshButton.setOnAction(event -> {
            loadShoes();
            table.setItems(FXCollections.observableArrayList(service.getShoes()));
            table.refresh();
        });

        HBox topBar = new HBox(refreshBox, searchBar, createFilterBar(pagination));
        borderPane.setTop(topBar);

        Scene scene = new Scene(borderPane, 900, 600);
        scene.getStylesheets().add("project.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadShoes() {
        service.loadShoes();
    }

    private TableView<Shoe> createTable() {
        TableView<Shoe> table = new TableView<>();
        TableColumn<Shoe, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<Shoe, String> cwCol = new TableColumn<>("Colorway");
        cwCol.setCellValueFactory(new PropertyValueFactory<>("colorway"));
        TableColumn<Shoe, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        TableColumn<Shoe, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        TableColumn<Shoe, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Shoe, Double> estSalePriceCol = new TableColumn<>("Est Sale Price");
        estSalePriceCol.setCellValueFactory(new PropertyValueFactory<>("estSalePrice"));
        estSalePriceCol.setCellFactory(column -> new TableCell<Shoe, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    Shoe shoe = getTableView().getItems().get(getIndex());
                    double difference = shoe.getEstSalePrice() - shoe.getPrice();
                    Text text = new Text(String.format("%.2f", item));
                    text.setFill(Color.BLACK);
                    Text diffText = new Text(String.format(" (%+.2f)", difference));
                    diffText.setFill(difference < 0 ? Color.RED : Color.GREEN);
                    HBox hbox = new HBox(text, diffText);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hbox);
                }
            }


        });

        TableColumn<Shoe, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Shoe, String> styleCodeCol = new TableColumn<>("Style Code");
        styleCodeCol.setCellValueFactory(new PropertyValueFactory<>("styleCode"));
        TableColumn<Shoe, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>("sku"));
        table.getColumns().addAll(modelCol, cwCol, brandCol, priceCol, estSalePriceCol, sizeCol, quantityCol, styleCodeCol, skuCol);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(Region.USE_COMPUTED_SIZE);
        table.setPrefHeight(Region.USE_COMPUTED_SIZE);
        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null) {
                Shoe selectedShoe = table.getSelectionModel().getSelectedItem();

                //TODO Make this singleton
                ModifyShoe modify = new ModifyShoe();
                modify.modifyShoe(primaryStage, selectedShoe, table, "");
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
                    popupContent.getChildren().add(new Label("Model: " + shoe.getModel()));
                    popupContent.getChildren().add(new Label("Size: " + shoe.getSize()));
                    popupContent.getChildren().add(new Label("Price: " + DF.format(shoe.getPrice())));
                    popupContent.getChildren().add(new Label("Est. Sale Price: " + DF.format(shoe.getPrice())));
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

        //ADD SHOE
        Button addButton = new Button("Add Shoe");
        addButton.setPrefWidth(BUTTON_WIDTH);
        addButton.setOnAction(e -> {
            AddShoe addShoe = new AddShoe(primaryStage, pagination);
            addShoe.addShoe(table, service.getShoes(), "");

        });

        //SKU SCAN
        Button skuScanButton = new Button("SKU Scan");
        skuScanButton.setPrefWidth(BUTTON_WIDTH);
        skuScanButton.setOnAction(e -> skuScan(pagination));

        //Stats
        Button statsButton = new Button("Stats");
        statsButton.setPrefWidth(BUTTON_WIDTH);
        statsButton.setOnAction(e -> statsScreen());

        //EXIT
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(BUTTON_WIDTH);
        exitButton.setOnAction(e -> Platform.exit());

        buttonBox.getChildren().addAll(addButton, skuScanButton, statsButton, exitButton);
        return buttonBox;
    }
    private void statsScreen() {
        Stage statsStage = PopupStage.createPopupStage(primaryStage, "Stats");

        int totCount;
        double priceSum;
        double estSalePriceDiffSum;
        String displayBrand;
        int saleCount;
        double saleTot;
        if(currentBrand.equalsIgnoreCase("Show All")){
            displayBrand = "All";
            totCount = service.getShoes().stream()
                    .mapToInt(Shoe::getQuantity)
                    .sum();

            priceSum = service.getShoes().stream()
                    .mapToDouble(shoe -> shoe.getPrice() * shoe.getQuantity())
                    .sum();

            estSalePriceDiffSum = service.getShoes().stream()
                    .mapToDouble(shoe -> (shoe.getEstSalePrice() - shoe.getPrice()) * shoe.getQuantity())
                    .sum();


            List<ShoeSale> shoeSales = service.getShoeSales();
            saleCount = shoeSales.size();
            saleTot = shoeSales.stream().mapToDouble(ShoeSale::getTotalPayout).sum();
        }
        else {
            displayBrand = currentBrand;
            totCount = service.getShoes().stream().filter(shoe -> shoe.getBrand().equalsIgnoreCase(currentBrand))
                    .mapToInt(Shoe::getQuantity)
                    .sum();

            priceSum = service.getShoes().stream().filter(shoe -> shoe.getBrand().equalsIgnoreCase(currentBrand))
                    .mapToDouble(shoe -> shoe.getPrice() * shoe.getQuantity())
                    .sum();

            estSalePriceDiffSum = service.getShoes().stream().filter(shoe -> shoe.getBrand().equalsIgnoreCase(currentBrand))
                    .mapToDouble(shoe -> (shoe.getEstSalePrice() - shoe.getPrice()) * shoe.getQuantity())
                    .sum();

            List<ShoeSale> shoeSales = service.getShoeSales();
            saleCount = (int) shoeSales.stream()
                    .filter(sale -> sale.getBrand().equalsIgnoreCase(currentBrand))
                    .count();
            saleTot = shoeSales.stream()
                    .filter(sale -> sale.getBrand().equalsIgnoreCase(currentBrand))
                    .mapToDouble(ShoeSale::getTotalPayout)
                    .sum();
        }
        // create labels for the shoe count, total price, and estSalePrice - Price sum
        Label brandLabel = new Label("Stats for " + displayBrand);
        Label countLabel = new Label("Total Shoe Count: " + totCount);
        Label priceLabel = new Label("Total Price: $" + DF.format(priceSum));
        Label estSalePriceDiffLabel = new Label("Total Difference: $" + DF.format(estSalePriceDiffSum));
        Label gainsPercentLabel = new Label("% Difference: " + DF.format((estSalePriceDiffSum / priceSum) * 100)  + "%");
        Label salesTexTLabel = new Label();
        Label saleCountLabel = new Label();
        Label saleTotalLabel = new Label();
        if(saleCount > 0){
            salesTexTLabel = new Label("SALES");
            saleCountLabel = new Label("Shoes Sold: " +  saleCount);
            saleTotalLabel = new Label("Earned: $" + DF.format(saleTot));
            saleCountLabel.setStyle(getStyle());
            saleTotalLabel.setStyle(getStyle());
        }

        // set the style of the labels using CSS
        brandLabel.setStyle(getStyle());
        countLabel.setStyle(getStyle());
        priceLabel.setStyle(getStyle());
        estSalePriceDiffLabel.setStyle(getStyle());
        gainsPercentLabel.setStyle(getStyle());
        saleCountLabel.setStyle(getStyle());
        saleTotalLabel.setStyle(getStyle());
        salesTexTLabel.setStyle("-fx-underline: true;"+getStyle());

        // create a VBox to hold the labels
        VBox vbox = new VBox(brandLabel, countLabel, priceLabel, estSalePriceDiffLabel, gainsPercentLabel, salesTexTLabel, saleCountLabel, saleTotalLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // set the background color of the VBox using CSS
        vbox.setStyle("-fx-background-color: #212121;");

        // create a Scene for the VBox
        Scene scene = new Scene(vbox, 300, 350);

        // set the Scene of the statsStage and show it
        statsStage.setScene(scene);
        statsStage.show();
    }

    private static String getStyle() {
        return "-fx-font-size: 24px; -fx-text-fill: white;";
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
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, service.getShoes().size());
            table.setItems(FXCollections.observableList(service.getShoes().subList(fromIndex, toIndex)));
            return table;
        };
    }

    private Callback<Integer, Node> createSearchPageFactory(String searchTerm) {
        return pageIndex -> {
            Predicate<Shoe> searchPredicate = shoe ->
                    shoe.getModel().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getBrand().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getSku().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getStyleCode().toLowerCase().contains(searchTerm.toLowerCase())
                            || shoe.getColorway().toLowerCase().contains(searchTerm.toLowerCase());
            List<Shoe> filteredShoes = service.getShoes().stream()
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
                pagination.setPageCount((int) Math.ceil((double) service.getShoes().size() / ITEMS_PER_PAGE));
                pagination.setPageFactory(ShoeTablePagination.pageFactory(service.getShoes(), table));
            } else {
                Predicate<Shoe> brandPredicate = shoe -> shoe.getBrand().equals(selectedBrand);
                List<Shoe> filteredShoes = service.getShoes().stream()
                        .filter(brandPredicate)
                        .collect(Collectors.toList());
                pagination.setPageCount((int) Math.ceil((double) filteredShoes.size() / ITEMS_PER_PAGE));
                pagination.setPageFactory(ShoeTablePagination.pageFactory(filteredShoes, table));
                showAllCheckBox.setSelected(false);
            }

            currentBrand = selectedBrand;
        });

        // create a listener to update the brand options when the shoes list is modified
        ListChangeListener<Shoe> shoesListener = c -> {
            if (c.next()) {
                if (c.wasAdded() || c.wasRemoved()) {
                    brandOptions.clear();
                    brandOptions.addAll(createBrandOptionsList());
                    Platform.runLater(() -> {
                        brandFilter.setItems(FXCollections.observableArrayList(brandOptions));
                        brandFilter.getSelectionModel().select(0); // select the "Show All" option by default
                    });
                }
            }
        };

        // add the listener to the shoes list
        service.getShoes().addListener(shoesListener);
        HBox filterBox = new HBox(new Label("Filter by Brand:"), brandFilter, showAllCheckBox);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(10));
        filterBox.setSpacing(10);
        filterBox.setStyle("-fx-background-color: #f2f2f2;");

        return filterBox;
    }


    // helper method to create the brand options list
    private List<String> createBrandOptionsList() {
        List<String> brandOptions = service.getShoes().stream()
                .map(Shoe::getBrand)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        brandOptions.add(0, "Show All"); // add a "Show All" option to the beginning of the list
        return brandOptions;
    }

    private void skuScan(Pagination pagination) {
        Stage popupStage = PopupStage.createPopupStage(primaryStage, "SKU Scan");
        popupStage.initOwner(primaryStage);

        TextField skuTextField = new TextField();
        skuTextField.setPromptText("Enter SKU");
        skuTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String sku = skuTextField.getText();
                Shoe shoe = service.getShoe(sku);

                if (shoe != null) {
                    // SKU exists, call modifyShoe method
                    ModifyShoe modify = new ModifyShoe();
                    modify.modifyShoe(primaryStage, shoe, table, sku);

                } else {
                    // SKU does not exist, call addShoe method
                    AddShoe addShoe = new AddShoe(primaryStage, pagination);
                    addShoe.addShoe(table, service.getShoes(), sku);

                }
                Platform.runLater(skuTextField::requestFocus);
                skuTextField.clear();
            }
        });

        skuTextField.requestFocus();

        // Create an HBox to hold the text field and the exit button
        HBox hbox = new HBox(skuTextField);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);

        // Create the root VBox and add the HBox and any other content you need
        VBox root = new VBox(hbox);
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        // Add styling to the VBox
        root.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
