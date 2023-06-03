package com.kicks.inventory;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.List;

public class ShoeTablePagination {
    public Pagination createPagination(List<Shoe> shoes, TableView<Shoe> table) {
        Pagination pagination = new Pagination();
        pagination.setPageCount((int) Math.ceil((double) shoes.size() / ShoeStoreUI.ITEMS_PER_PAGE));
        pagination.setPageFactory(pageFactory(shoes, table));
        pagination.setPrefHeight(350);
        return pagination;
    }
    public static Callback<Integer, Node> pageFactory(List<Shoe> shoes, TableView<Shoe> table){
        return pageIndex -> {
            int fromIndex = pageIndex * ShoeStoreUI.ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ShoeStoreUI.ITEMS_PER_PAGE, shoes.size());
            table.setItems(FXCollections.observableList(shoes.subList(fromIndex, toIndex)));
            return table;
        };
    }}
