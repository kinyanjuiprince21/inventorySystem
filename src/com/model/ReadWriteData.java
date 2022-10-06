package com.model;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;


/**
 * The type Read write data.
 *
 * @param <T> the type parameter
 */
public class ReadWriteData <T> {

    /**
     * The constant readWriteData.
     */
    public static ReadWriteData readWriteData;

    private static final String BASE_DIR = "database";

    /**
     * Instantiates a new Read write data.
     */
    public ReadWriteData(){}


    /**
     * Gets instance.
     * This constructor is used in creating the ReadWriteData Generic class
     * @return the instance
     */
    public static ReadWriteData getInstance() {
        if(readWriteData == null) readWriteData = new ReadWriteData();

        return readWriteData;
    }

    /**
     * Write boolean.
     *
     * @param fName the f name
     * @param obj   the obj
     * @return the boolean
     */
    public boolean write(String fName, ObservableList<T> obj) {

        File file = new File(BASE_DIR);
        String filename = "";

        file.mkdir();

        filename = file.getAbsolutePath() + "/" + fName;
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<T>(obj));
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Read object.
     *
     * @param fName the f name
     * @return the object
     */
    public static Object read(String fName) {
        File file = new File(BASE_DIR);
        String filename = "";

        filename = file.getAbsolutePath() + "/" + fName;

        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return  ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

}