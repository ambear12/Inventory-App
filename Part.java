package model;

/**
 * Supplied class Part.java
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Amber Lanier
 * ID: #001313050
 */

 /** The Part class contains methods for creating an object of the part class. */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Lists what variables make up a part.
     * @param id The part id.
     * @param name The product name.
     * @param stock The stock amount of the part.
     * @param price The part price.
     * @param min The min stock amount of the part.
     * @param max The max stock amount of the part.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }



    /**
     * Gets the id for a part.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id for a part.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of a part.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a part.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of a part.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of a part.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the stock of a part.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock of a part.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the min stock of a part.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the min stock for a part.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the max stock for a part.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the max stock for a part.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}
