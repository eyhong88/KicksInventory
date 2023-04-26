package org.example;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.List;

import static org.example.ShoeStoreUI.ITEMS_PER_PAGE;

public class ShoeTablePagination {
    public Pagination createPagination(List<Shoe> shoes, TableView<Shoe> table) {
        Pagination pagination = new Pagination();
        pagination.setPageCount((int) Math.ceil((double) shoes.size() / ITEMS_PER_PAGE));
        pagination.setPageFactory(pageFactory(shoes, table));
        pagination.setPrefHeight(350);
        return pagination;
    }
    public static Callback<Integer, Node> pageFactory(List<Shoe> shoes, TableView<Shoe> table){
        return pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, shoes.size());
            table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
            return table;
        };
    }}
