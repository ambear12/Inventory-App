package model;

/** InHouse class populates the part table for a part that is made in house. */
public class InHouse extends Part {

    private int machineId;

    /** Used to create an in house part.
     * @param id The part id.
     * @param name The part name.
     * @param price The part price.
     * @param stock The amount of stock of the part.
     * @param min  The min stock amount of the part.
     * @param max The max stock amount of the part.
     * @param machineId The machine id where part is from.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Gets the machine id of a part.
     * @return Returns the machine id of a part.
     */
    public int getMachineId() {
        return machineId;
    }

    /** Sets the machine id for a part.
     * @param machineId The machine ID of an in house part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
