package com.model;

import java.io.Serializable;

/**
 * The type Out sourced.
 */
public class OutSourced extends Part {

    /**
     * The Outsourced id.
     */
    private int outsourced_id;
    /**
     * The Company name.
     */
    private String companyName;
    /**
     * The Part id.
     */
    private int part_id;

    /**
     * Instantiates a new Out sourced.
     */
    public OutSourced() {
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets part id.
     *
     * @return the part id
     */
    public int getPart_id() {
        return part_id;
    }

    /**
     * Sets part id.
     *
     * @param part_id the part id
     */
    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    /**
     * Gets outsourced id.
     *
     * @return the outsourced id
     */
    public int getOutsourced_id() {
        return outsourced_id;
    }

    /**
     * Sets outsourced id.
     *
     * @param outsourced_id the outsourced id
     */
    public void setOutsourced_id(int outsourced_id) {
        this.outsourced_id = outsourced_id;
    }

}
