package model;

/** The outsourced class allows for an outsourced part to be added to the parts table. */
public class Outsourced extends Part {

    private String companyName;

    /** Used to create an outsourced part.
     * @param id The part id.
     * @param name The part name.
     * @param price The part price.
     * @param stock The amount of stock of the part.
     * @param min  The min stock amount of the part.
     * @param max The max stock amount of the part.
     * @param companyName The company name where part is from.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Gets the company name for an outsourced part.
     * @return Returns the company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Sets the company name for an outsoured part.
     * @param companyName The company name of the outsourced part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
