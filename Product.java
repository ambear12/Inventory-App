package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The product class contains methods to create an object in the product table. */
public class Product {
    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Lists what variables make up a product.
     * @param id The ID of a product.
     * @param name The name of a product.
     * @param price The price of a product.
     * @param stock The amount of stock of a product.
     * @param min The minimum stock of a product.
     * @param max The maximum stock of a product.
      */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets the product id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     * @return The name of product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets stock for the product.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock for the product.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the min stock for the product.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the min stock for the product.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the max stock for the product.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the max stock for the product.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *  Adds an associated part to a product.
     * @param part Adds the selected part to associated parts.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Gets an associated part from a product.
     * @return Returns the associated part.
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * Removes an associated part from a product.
     * @param selectedAssociatedPart the selected associated part of a product.
     * @return If returns true will remove the selected part from associated parts.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
}
