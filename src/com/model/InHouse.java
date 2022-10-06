package com.model;

/**
 * The type In house.
 */
public class InHouse extends Part {

    /**
     * The In house id.
     */
    private int inHouse_id;
    /**
     * The Machine id.
     */
    private int machineId;
    /**
     * The Part id.
     */
    private int part_id;

    /**
     * Instantiates a new In house.
     */
    public InHouse() {
    }

    /**
     * Gets machine id.
     *
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets machine id.
     *
     * @param machineId the machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
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
     * Gets in house id.
     *
     * @return the in house id
     */
    public int getInHouse_id() {
        return inHouse_id;
    }

    /**
     * Sets in house id.
     *
     * @param inHouse_id the in house id
     */
    public void setInHouse_id(int inHouse_id) {
        this.inHouse_id = inHouse_id;
    }

}
