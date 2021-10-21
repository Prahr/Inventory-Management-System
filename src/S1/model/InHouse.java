package S1.model;

/**
 * Model of inhouse part object
 */

public class InHouse extends Part {
    /**
     * Machine ID of part
     */
    private int machineId;

    /**
     * Constructor of inhouse part object
     * @param id ID of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Stock level of part
     * @param min Minimum stock of part
     * @param max Maximum stock of part
     * @param machineId Machine ID of part
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets machine ID of part
     * @param machineId Machine ID of part
     */

    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Returns the machine ID of part
     * @return Returns the machine ID of part
     */

    public int getMachineId(){
        return machineId;
    }
}
