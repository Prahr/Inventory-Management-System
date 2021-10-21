package S1.model;

/**
 * Model of an outsourced part object
 */

public class Outsourced extends Part{
    /**
     * Company name of part
     */
    private String companyName;

    /**
     * Constructor of outsourced part object
     * @param id ID of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Stock level of part
     * @param min Minimum stock of part
     * @param max Maximum stock of part
     * @param companyName Company name of part
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name of part
     * @param companyName Company name of part
     */

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Returns the company name of part
     * @return Returns the company name of part
     */

    public String getCompanyName(){
        return companyName;
    }
}
