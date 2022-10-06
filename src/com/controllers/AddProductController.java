package com.controllers;

import com.model.Inventory;
import com.model.Part;
import com.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Products Controller
 * This is the product's controller , It is very important
 * It's used as controller for both Add and Modify product window
 */
public class AddProductController {

    /**
     * The Txt search part.
     */
    public TextField txtSearchPart;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel1;

    @FXML
    private Button btnRemovePart;

    @FXML
    private Button btnSave1;

    @FXML
    private TableColumn<Part, String> colId;

    @FXML
    private TableColumn<Part, String> colId1;

    @FXML
    private TableColumn<Part, String> colLevel;

    @FXML
    private TableColumn<Part, String> colLevel1;

    @FXML
    private TableColumn<Part, String> colName;

    @FXML
    private TableColumn<Part, String> colName1;

    @FXML
    private TableColumn<Part, String> colPrice;

    @FXML
    private TableColumn<Part, String> colPrice1;

    @FXML
    private Label lblWindowTitle;

    @FXML
    private TableView<Part> tblPart;

    @FXML
    private TableView<Part> tblPart1;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLevel;

    @FXML
    private TextField txtMax;

    @FXML
    private TextField txtMin;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;
    /**
     * The Product.
     */
    Product product;
    /**
     * The Part.
     */
    Part part = null;

    /**
     * The Parts.
     */
    ObservableList<Part> parts = FXCollections.observableArrayList();
    /**
     * The Parts 1.
     */
    ObservableList<Part> parts1 = FXCollections.observableArrayList();

    /**
     * The Part to remove.
     */
    Part partToRemove = null;

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }


    /***
     * This method is called on MainFormController and it bootstraps the add and modify
     * product window.
     * @param callback the callback to be executed when the product is initialized
     */
    public void initialize(Callback callback) {
        try {
            if(product != null) {
                lblWindowTitle.setText("Modify Product");


                txtId.setText(product.getId() + "");
                txtLevel.setText(product.getStock() + "");
                txtMax.setText(product.getMax() + "");
                txtMin.setText(product.getMin() + "");
                txtName.setText(product.getName());
                txtPrice.setText(product.getPrice() + "");

                List<Part> list = product.getAssociatedParts();
                parts1 = FXCollections.observableList(list);
            }
            initWidgets();
            setListeners(callback);

        }catch (Exception e) {
            alert("Error initializing product: ", Alert.AlertType.ERROR);
        }

    }

    /**
     * Init widgets.
     */
    public void initWidgets() {
        try {
            parts =  Inventory.getInstance().getAllParts();

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            colId1.setCellValueFactory(new PropertyValueFactory<>("id"));
            colLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
            colName1.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));

            tblPart.setItems(parts);
            tblPart1.setItems(parts1);

            tblPart.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1) {
                    part = tblPart.getSelectionModel().getSelectedItem();
                }
            });

            tblPart1.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1) {
                    partToRemove = tblPart1.getSelectionModel().getSelectedItem();
                } else if(e.getClickCount() == 2) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this associated part?");

                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK) {
                        removeAssociated();
                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "The associated part was deleted successfully!");
                        alert2.show();
                    }
                }
            });
        }catch (Exception e) {
            alert("Error initializing widgets ", Alert.AlertType.ERROR);
        }
    }


    /**
     * Sets listeners.
     * This method is used to set the event listeners on the both the add and modify product window
     *
     * @param callback the callback
     */
    public void setListeners(Callback callback) {
        try {
            txtSearchPart.setOnAction(e -> {
                if(txtSearchPart.getText().isBlank()) {
                    tblPart.setItems(parts);
                    tblPart.refresh();
                    return;
                }

                ObservableList<Part> parts2 = Inventory.getInstance().lookupPart(txtSearchPart.getText());
                if(parts2.isEmpty()) {
                    Part part = Inventory.getInstance().lookupPart(Integer.parseInt(txtSearchPart.getText()));
                    parts2.add(part);
                }

                tblPart.setItems(parts2);
                tblPart.refresh();
            });

            btnAdd.setOnAction(e -> {
                part = tblPart.getSelectionModel().getSelectedItem();
                if(parts1.contains(part)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, part.getName() + " already exists in the associated table!");
                    alert.show();
                    return;
                }
                parts1.add(part);
            });

            btnCancel1.setOnAction(e -> {
                ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
            });

            btnSave1.setOnAction(e -> {
                if(product == null){
                    save(callback);
                } else {
                    update(callback);
                }
            });

            btnRemovePart.setOnAction(e -> {
                if(partToRemove == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not selected any associated part to remove!");
                    alert.show();
                    return;
                }

                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + partToRemove.getName() + " associated part?");
                alert3.showAndWait();
                if(alert3.getResult() == ButtonType.OK) {
                    removeAssociated();
                    alert3 = new Alert(Alert.AlertType.CONFIRMATION, partToRemove.getName() + " was deleted successfully!");
                    alert3.show();
                }

                partToRemove = null;
            });

        }catch (Exception e) {
            alert("Something went wrong: " + e.getMessage() , Alert.AlertType.ERROR);
        }
    }

    /**
     * This method is used to remove the associated parts of a product in add and modify product window
     */
    public void removeAssociated() {
        parts1.removeIf(part1 -> part1.getId() == partToRemove.getId());
        tblPart1.refresh();
    }

    /**
     * Save.
     * public interface
     * This method is used to save the fields in add product window in database
     * and the modified fields in the database.
     *
     * @param callback the callback
     */
    public void save(Callback callback) {
        try {
            Product product = new Product();

            product.setName(txtName.getText());
            product.setStock(Integer.parseInt(txtLevel.getText()));
            product.setPrice(Double.parseDouble(txtPrice.getText()));

            if(Integer.parseInt(txtMax.getText()) < Integer.parseInt(txtMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The max value cannot be less than the min value");
                alert.show();

                return;
            }

            product.setMax(Integer.parseInt(txtMax.getText()));
            product.setMin(Integer.parseInt(txtMin.getText()));
            product.setAssociatedParts(new ArrayList<Part>(parts1));

            Inventory.getInstance().addProduct(product);

            callback.onResponse(true);
        }catch (Exception e) {
            alert("Error saving product", Alert.AlertType.ERROR);
        }
    }


    /**
     * Update.
     * This method updates the database when the modify product window loads.
     * @param callback the callback
     */
    public void update(Callback callback) {
        try {
            Product product1 = new Product();

            product1.setId(product.getId());
            product1.setName(txtName.getText());
            product1.setStock(Integer.parseInt(txtLevel.getText()));
            product1.setPrice(Double.parseDouble(txtPrice.getText()));

            if(Integer.parseInt(txtMax.getText()) < Integer.parseInt(txtMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The max value cannot be less than the min value");
                alert.show();

                return;
            }

            product1.setMax(Integer.parseInt(txtMax.getText()));
            product1.setMin(Integer.parseInt(txtMin.getText()));
            product1.setAssociatedParts(new ArrayList<Part>(parts1));

            Inventory.getInstance().updateProduct(product.getId(), product1);
            callback.onResponse(true);

        }catch (Exception e) {
            alert("Error updating", Alert.AlertType.ERROR);
        }
    }

    private static void alert(String message, Alert.AlertType type){
        Alert alert = new Alert(type, message);
        alert.show();
    }

    /**
     * The interface Callback.
     */
    public interface Callback {


        /**
         * On response.
         *
         * @param test the test
         */
        void onResponse(boolean test);
    }

}
