package com.kicks.inventory.dao;

import com.kicks.inventory.Shoe;
import com.kicks.inventory.ShoeSale;
import com.kicks.inventory.Vendor;
import com.kicks.inventory.config.DBConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoesDAO {
    private ObservableList<Shoe> shoes = FXCollections.observableArrayList();

    private static ShoesDAO instance = null;
    private Connection connection;

    private ShoesDAO() {
        connection = DBConfiguration.configure();
    }

    public static ShoesDAO getInstance() {
        if (instance == null) {
            instance = new ShoesDAO();
        }
        return instance;
    }

    public void loadShoes() {
        List<Shoe> shoeList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT brand, model, colorway, size, price, est_sale_price, quantity, style_code, sku FROM shoe_inventory WHERE quantity <> 0");
            while(resultSet.next()){
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String colorway = resultSet.getString("colorway");
                double size = resultSet.getDouble("size");
                double price = resultSet.getDouble("price");
                double estSalePrice = resultSet.getDouble("est_sale_price");
                int quantity = resultSet.getInt("quantity");
                String styleCode = resultSet.getString("style_code");
                String sku = resultSet.getString("sku");
                shoeList.add(new Shoe(estSalePrice, colorway, size, model, brand, price, quantity, styleCode, sku));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        shoes.setAll(shoeList);
    }

    public void addShoe(Shoe shoe) {
        String sql = "INSERT INTO shoe_inventory (brand, model, colorway, size, price, est_sale_price, quantity, style_code, sku) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, shoe.getBrand());
            stmt.setString(2, shoe.getModel());
            stmt.setString(3, shoe.getColorway());
            stmt.setDouble(4, shoe.getSize());
            stmt.setDouble(5, shoe.getPrice());
            stmt.setDouble(6, shoe.getEstSalePrice());
            stmt.setInt(7, shoe.getQuantity());
            stmt.setString(8, shoe.getStyleCode());
            stmt.setString(9, shoe.getSku());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new shoe was inserted successfully!");
            }

            // Add the shoe to the list of shoes
            shoes.add(shoe);
            stmt.close();
        } catch (SQLException e){
            System.out.println("Could not insert to database.");
            e.printStackTrace();
        }
    }
    public void addShoeSale(ShoeSale sale) {
        String sql = "INSERT INTO shoe_sale (sku, sale_price, sale_date, vendor_id, total_payout) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, sale.getSku());
            stmt.setDouble(2, sale.getPrice());
            stmt.setDate(3, Date.valueOf(sale.getSaleDate()));
            stmt.setInt(4, sale.getVendorId());
            stmt.setDouble(5, sale.getTotalPayout());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateShoe(Shoe shoe) {
        String sql = "UPDATE shoe_inventory SET brand=?, model=?, colorway=?, size=?, price=?, est_sale_price=?, quantity=?, style_code=? WHERE sku=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, shoe.getBrand());
            stmt.setString(2, shoe.getModel());
            stmt.setString(3, shoe.getColorway());
            stmt.setDouble(4, shoe.getSize());
            stmt.setDouble(5, shoe.getPrice());
            stmt.setDouble(6, shoe.getEstSalePrice());
            stmt.setInt(7, shoe.getQuantity());
            stmt.setString(8, shoe.getStyleCode());
            stmt.setString(9, shoe.getSku());
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Shoe getShoe(String shoeSku) {
        Optional<Shoe> optShoe = shoes.stream().filter(x -> x.getSku().equalsIgnoreCase(shoeSku)).findFirst();
        if(optShoe.isPresent()){
            return optShoe.get();
        } else {
            String sql = "SELECT brand, model, colorway, size, price, est_sale_price, quantity, style_code, sku FROM shoe_inventory WHERE sku=?";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, shoeSku);

                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    String colorway = resultSet.getString("colorway");
                    double size = resultSet.getDouble("size");
                    double price = resultSet.getDouble("price");
                    double estSalePrice = resultSet.getDouble("est_sale_price");
                    int quantity = resultSet.getInt("quantity");
                    String styleCode = resultSet.getString("style_code");
                    String sku = resultSet.getString("sku");

                    Shoe shoe = new Shoe(estSalePrice, colorway, size, model, brand, price, quantity, styleCode, sku);
                    if(quantity > 0) shoes.add(shoe);
                    return shoe;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public List<Vendor> getVendors() {
        List<Vendor> result = new ArrayList<>();

            String sql = "SELECT id, vendor_name, vendor_fee FROM sale_vendor";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String vendorName = resultSet.getString("vendor_name");
                    double vendorFee = resultSet.getDouble("vendor_fee");

                    result.add(new Vendor(id, vendorName, vendorFee));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return result;
    }

    public ObservableList<Shoe> getShoes(){
        return shoes;
    }

}
