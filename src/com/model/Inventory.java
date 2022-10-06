package com.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Inventory.
 * This is a service class.Contains business logic of the system.
 */
public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    private static Inventory inventory;

    /**
     * Instantiates a new Inventory.
     */
    public Inventory() {
    }

    /**
     * Gets instance.
     *
     * @return <p>     This constructor is used to make the Inventory class to be a singleton class. </p>
     */
    public static Inventory getInstance() {
        if(inventory == null) inventory = new Inventory();

        return inventory;
    }

    /**
     * Add parts.
     *
     * @param obj the obj
     */
    public void addParts(Object obj) {
        List<Part> parts1 = (List<Part>) ReadWriteData.read("parts.dat");
        ObservableList<Part> parts = FXCollections.observableArrayList();

        if(parts1 != null) {
            parts = FXCollections.observableList(parts1);
        }

        if(obj instanceof InHouse inHouse) {
            List<InHouse> inHouse1 = (List<InHouse>) ReadWriteData.read("in-house.dat");
            ObservableList<InHouse> inHouses = FXCollections.observableArrayList();

            InHouse inHouse2 = new InHouse();
            if(inHouse1 != null) {
                inHouses = FXCollections.observableList(inHouse1);
            }
            if(parts.size() > 0) {
                int lastId = parts.get(parts.size() - 1).getId();
                inHouse2.setId(lastId + 1);
            } else {
                inHouse2.setId(0);
            }

            if(inHouses.size() > 0) {
                int lastId = inHouses.get(inHouses.size() - 1).getId();
                inHouse.setInHouse_id(lastId + 1);
            } else {
                inHouse.setInHouse_id(0);
            }

            inHouse2.setName(inHouse.getName());
            inHouse2.setPrice(inHouse.getPrice());
            inHouse2.setStock(inHouse.getStock());
            inHouse2.setMax(inHouse.getMax());
            inHouse2.setMin(inHouse.getMin());
            parts.add(inHouse2);
            ReadWriteData.getInstance().write("parts.dat", parts);

            inHouse.setMachineId(inHouse.getMachineId());
            inHouse.setPart_id(inHouse2.getId());

            inHouses.add(inHouse);
            ReadWriteData.getInstance().write("in-house.dat", inHouses);

        } else if(obj instanceof  OutSourced outSourced) {
            List<OutSourced> outSourced1 = (List<OutSourced>) ReadWriteData.read("outsourced.dat");
            ObservableList<OutSourced> outSourceds = FXCollections.observableArrayList();

            OutSourced outSourced2 = new OutSourced();
            if(outSourced1 != null) {
                outSourceds = FXCollections.observableList(outSourced1);
            }
            if(parts.size() > 0) {
                int lastId = parts.get(parts.size() - 1).getId();
                outSourced2.setId(lastId + 1);
            } else {
                outSourced2.setId(0);
            }

            if(outSourceds.size() > 0) {
                int lastId = outSourceds.get(outSourceds.size() - 1).getId();
                outSourced.setOutsourced_id(lastId + 1);
            } else {
                outSourced.setOutsourced_id(0);
            }

            outSourced2.setName(outSourced.getName());
            outSourced2.setPrice(outSourced.getPrice());
            outSourced2.setStock(outSourced.getStock());
            outSourced2.setMax(outSourced.getMax());
            outSourced2.setMin(outSourced.getMin());
            parts.add(outSourced2);
            ReadWriteData.getInstance().write("parts.dat", parts);

            outSourced.setOutsourced_id(outSourced.getOutsourced_id());
            outSourced.setCompanyName(outSourced.getCompanyName());
            outSourced.setPart_id(outSourced2.getId());

            outSourceds.add(outSourced);
            ReadWriteData.getInstance().write("outsourced.dat", outSourceds);
        }

    }

    /**
     * Add product.
     *
     * @param product the product
     */
    public void addProduct(Product product) {
        List<Product> list = (List<Product>) ReadWriteData.read("products.dat");
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(list != null) {
            products = FXCollections.observableList(list);
        }

        if(products.size() > 0) {
            int lastId = products.get(products.size() - 1).getId();
            product.setId(lastId + 1);
        } else {
            product.setId(0);
        }

        products.add(product);

        ReadWriteData.getInstance().write("products.dat", products);
    }

    /**
     * Lookup part part.
     *
     * @param partId the part id
     * @return the part
     */
    public Part lookupPart(int partId) {
        List<Part> parts1 = (List<Part>) ReadWriteData.read("parts.dat");
        ObservableList<Part> parts = FXCollections.observableArrayList();

        if(parts1 != null) {
            parts = FXCollections.observableList(parts1);
        }

        return parts.stream().filter(part -> part.getId() == partId).findAny().orElse(null);
    }

    /**
     * Lookup product product.
     *
     * @param productId the product id
     * @return the product
     */
    public Product lookupProduct(int productId) {
        List<Product> list = (List<Product>) ReadWriteData.read("products.dat");
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(list != null) {
            products = FXCollections.observableList(list);
        }

        return products.stream().filter(product -> product.getId() == productId).findAny().orElse(null);
    }

    /**
     * Lookup part observable list.
     *
     * @param name the name
     * @return the observable list
     */
    public ObservableList<Part> lookupPart(String name) {
        List<Part> parts1 = (List<Part>) ReadWriteData.read("parts.dat");
        ObservableList<Part> parts = FXCollections.observableArrayList();

        if(parts1 != null) {
            parts = FXCollections.observableList(parts1);
        }

        List<Part> list = parts.stream().filter(part -> part.getName().contains(name)).toList();
        ObservableList<Part> newList = FXCollections.observableArrayList();
        newList.addAll(list);
        return newList;
    }

    /**
     * Lookup product observable list.
     *
     * @param name the name
     * @return the observable list
     */
    public ObservableList<Product> lookupProduct(String name) {
        List<Product> list1 = (List<Product>) ReadWriteData.read("products.dat");
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(list1 != null) {
            products = FXCollections.observableList(list1);
        }

        List<Product> list = products.stream().filter(product -> product.getName().contains(name)).toList();
        ObservableList<Product> newList = FXCollections.observableArrayList();
        newList.addAll(list);
        return newList;
    }

    /**
     * Update part.
     *
     * @param index        the index
     * @param selectedPart the selected part
     */
    public void updatePart(int index, Part selectedPart) {
        List<Part> parts1 = (List<Part>) ReadWriteData.read("parts.dat");
        ObservableList<Part> parts = FXCollections.observableArrayList();

        if(parts1 != null) {
            parts = FXCollections.observableList(parts1);
        }

        if(index == selectedPart.getId()) {
            for (int i = 0; i < parts.size(); i++) {
                if(parts.get(i).getId() == index) {
                    parts.get(i).setPrice(selectedPart.getPrice());
                    parts.get(i).setMax(selectedPart.getMax());
                    parts.get(i).setStock(selectedPart.getStock());
                    parts.get(i).setMin(selectedPart.getMin());
                    parts.get(i).setName(selectedPart.getName());
                }
            }
        }


        ReadWriteData.getInstance().write("parts.dat", parts);
    }

    /**
     * Update product.
     *
     * @param index   the index
     * @param product the product
     */
    public void updateProduct(int index, Product product) {
        List<Product> list = (List<Product>) ReadWriteData.read("products.dat");
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(list != null) {
            products = FXCollections.observableList(list);
        }

        if(index == product.getId()) {

            products.forEach(product1 -> {
                if(product1.getId() == index) {
                    System.out.println(product1.getName());
                    product1.setPrice(product.getPrice());
                    product1.setMax(product.getMax());
                    product1.setStock(product.getStock());
                    product1.setMin(product.getMin());
                    product1.setName(product.getName());
                    product1.setAssociatedParts(product.getAssociatedParts());
                }
            });

        }

        ReadWriteData.getInstance().write("products.dat", products);
    }

    /**
     * Delete part boolean.
     *
     * @param selectedPart the selected part
     * @return the boolean
     */
    public boolean deletePart(Part selectedPart) {
        List<Part> parts1 = (List<Part>) ReadWriteData.read("parts.dat");
        ObservableList<Part> parts = FXCollections.observableArrayList();

        if(parts1 != null) {
            parts = FXCollections.observableList(parts1);
        }
        parts.removeIf(part -> part.getId() == selectedPart.getId());
        return ReadWriteData.getInstance().write("parts.dat", parts);
    }

    /**
     * Delete product boolean.
     *
     * @param selectedProduct the selected product
     * @return the boolean
     */
    public boolean deleteProduct(Product selectedProduct) {
        List<Product> list = (List<Product>) ReadWriteData.read("products.dat");
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(list != null) {
            products = FXCollections.observableList(list);
        }
        products.removeIf(product -> product.getId() == selectedProduct.getId());
        return ReadWriteData.getInstance().write("products.dat", products);
    }

    /**
     * Gets in.
     *
     * @param partId the part id
     * @return the in
     */
    public InHouse getIn(int partId) {

        List<InHouse> inHouse1 = (List<InHouse>) ReadWriteData.read("in-house.dat");
        ObservableList<InHouse> inHouses = FXCollections.observableArrayList();

        if(inHouse1 != null) {
            inHouses = FXCollections.observableList(inHouse1);
        }
        InHouse inHouse = new InHouse();

            for (int i = 0; i < inHouses.size(); i++) {
                if(inHouses.get(i).getPart_id() == partId) {
                    inHouse.setInHouse_id(inHouses.get(i).getInHouse_id());
                    inHouse.setMachineId(inHouses.get(i).getMachineId());
                }
            }

        return inHouse;
    }

    /**
     * Gets out sourced.
     *
     * @param partId the part id
     * @return the out sourced
     */
    public OutSourced getOutSourced(int partId) {

        List<OutSourced> outSourced1 = (List<OutSourced>) ReadWriteData.read("outsourced.dat");
        ObservableList<OutSourced> outSourceds = FXCollections.observableArrayList();

        if(outSourced1 != null) {
            outSourceds = FXCollections.observableList(outSourced1);
        }
        OutSourced outSourced = new OutSourced();

        for (int i = 0; i < outSourceds.size(); i++) {
            if(outSourceds.get(i).getPart_id() == partId) {
                outSourced.setOutsourced_id(outSourceds.get(i).getOutsourced_id());
                outSourced.setCompanyName(outSourceds.get(i).getCompanyName());
            }
        }

        return outSourced;
    }

    /**
     * Gets all parts.
     *
     * @return the all parts
     */
    public ObservableList<Part> getAllParts() {
        List<Part> parts1 = (List<Part>) ReadWriteData.read("parts.dat");
        ObservableList<Part> parts = FXCollections.observableArrayList();

        if(parts1 != null) {
            parts = FXCollections.observableList(parts1);
        }
        return parts;
    }

    /**
     * Sets all parts.
     *
     * @param allParts the all parts
     */
    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public ObservableList<Product> getAllProducts() {
        List<Product> list = (List<Product>) ReadWriteData.read("products.dat");
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(list != null) {
            products = FXCollections.observableList(list);
        }

        return products;
    }

    /**
     * Sets all products.
     *
     * @param allProducts the all products
     */
    public void setAllProducts(ObservableList<Product> allProducts) {
        this.allProducts = allProducts;
    }

}
