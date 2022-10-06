package com.controllers;

import com.model.InHouse;
import com.model.Inventory;
import com.model.OutSourced;
import com.model.Part;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;


/**
 * The type Add part controller.
 * This class is used as a controller class for the both the add and modify part windows.
 */
public class AddPartController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblSource;

    @FXML
    private Label lblWindowTitle;

    @FXML
    private RadioButton rbInHouse;

    @FXML
    private RadioButton rbOutSourced;

    @FXML
    private ToggleGroup source;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLevel;

    @FXML
    private TextField txtMachineId;

    @FXML
    private TextField txtMax;

    @FXML
    private TextField txtMin;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;
    /**
     * The Part.
     */
    Part part;


    /**
     * Sets part.
     *
     * @param part the part
     */
    public void setPart(Part part) {
        this.part = part;
    }


    /**
     * Initialize.
     * This method is called on MainFormController and it bootstraps the add and modify
     * part window.
     *
     * @param callback the callback
     */
    public void initialize(Callback callback) {
        try {
            if(part != null) {
                lblWindowTitle.setText("Modify Part");

                InHouse inHouse = Inventory.getInstance().getIn(part.getId());
                OutSourced outSourced = Inventory.getInstance().getOutSourced(part.getId());

                if(inHouse == null) {
                    rbOutSourced.setSelected(true);
                    lblSource.setText("Company Name");
                    txtMachineId.setText(outSourced.getCompanyName());
                    if(rbInHouse.isSelected()) {
                        inHouse.setMachineId(0);
                    }
                } else {
                    rbInHouse.setSelected(true);
                    lblSource.setText("Machine ID");
                    txtMachineId.setText(inHouse.getMachineId() + "");
                    if(rbOutSourced.isSelected()) {
                        outSourced.setCompanyName(" ");
                    }
                }

                txtId.setText(part.getId() + "");
                txtLevel.setText(part.getStock() + "");
                txtMax.setText(part.getMax() + "");
                txtMin.setText(part.getMin() + "");
                txtName.setText(part.getName());
                txtPrice.setText(part.getPrice() + "");
            }

            setListeners(callback);
        }catch (Exception e) {
            alert("Error initializing application", Alert.AlertType.ERROR);
        }
    }

    /**
     * Sets listeners.
     * This method is used to set the event listeners on both the add and modify part window.
     *
     * @param callback :public interface
     */
    public void setListeners(Callback callback) {

        try {
            btnSave.setOnAction(e -> {

                if(part == null) {
                    save(callback);
                }else{
                    update(callback);
                }

            });

            btnCancel.setOnAction(e -> {
                ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
            });
            rbInHouse.setOnAction(e -> {

                lblSource.setText("Machine ID");
                if(part != null) {
                    InHouse inHouse1 = Inventory.getInstance().getIn(part.getId());
                    OutSourced outSourced1 = Inventory.getInstance().getOutSourced(part.getId());
                    txtMachineId.setText(inHouse1.getMachineId() + "");
                    if(rbOutSourced.isSelected()) {
                        outSourced1.setCompanyName(" ");
                    }
                }

            });

            rbOutSourced.setOnAction(e -> {

                lblSource.setText("Company Name");
                if(part != null) {
                    OutSourced outSourced1 = Inventory.getInstance().getOutSourced(part.getId());
                    InHouse inHouse1 = Inventory.getInstance().getIn(part.getId());
                    txtMachineId.setText(outSourced1.getCompanyName());
                    if(rbInHouse.isSelected()) {
                        inHouse1.setMachineId(0);
                    }
                }

            });
        }catch (Exception e) {
            alert("Error: Something went wrong", Alert.AlertType.ERROR);
        }

    }


    /**
     * Save.
     * This method is used to save the fields in add part window in database
     * and the modified fields in the database.
     *
     * @param callback the callback
     */
    public void save(Callback callback) {

        try {
            if(rbInHouse.isSelected()){
                InHouse inHouse = new InHouse();
                inHouse.setPrice(Double.parseDouble(txtPrice.getText()));

                if(Integer.parseInt(txtLevel.getText()) > Integer.parseInt(txtMax.getText()) && Integer.parseInt(txtLevel.getText()) > Integer.parseInt(txtMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory value should not exceed the maximum value!");
                    alert.show();
                    return;
                }

                if(Integer.parseInt(txtLevel.getText()) < Integer.parseInt(txtMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory value should not be less than the minimum value!");
                    alert.show();
                    return;
                }

                inHouse.setStock(Integer.parseInt(txtLevel.getText()));
                inHouse.setName(txtName.getText());

                if(Integer.parseInt(txtMax.getText()) < Integer.parseInt(txtMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The max value cannot be less than the min value");
                    alert.show();
                    return;
                }

                inHouse.setMax(Integer.parseInt(txtMax.getText()));
                inHouse.setMachineId(Integer.parseInt(txtMachineId.getText()));
                inHouse.setMin(Integer.parseInt(txtMin.getText()));
                Inventory.getInstance().addParts(inHouse);
            }

            if(rbOutSourced.isSelected()) {
                OutSourced outSourced = new OutSourced();
                outSourced.setPrice(Double.parseDouble(txtPrice.getText()));

                if(Integer.parseInt(txtLevel.getText()) > Integer.parseInt(txtMax.getText()) && Integer.parseInt(txtLevel.getText()) > Integer.parseInt(txtMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory value should not exceed the maximum value!");
                    alert.show();
                    return;
                }

                if(Integer.parseInt(txtLevel.getText()) < Integer.parseInt(txtMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory value should not be less than the minimum value!");
                    alert.show();
                    return;
                }

                outSourced.setStock(Integer.parseInt(txtLevel.getText()));
                outSourced.setName(txtName.getText());

                if(Integer.parseInt(txtMax.getText()) < Integer.parseInt(txtMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The max value cannot be less than the min value");
                    alert.show();
                    return;
                }

                outSourced.setMax(Integer.parseInt(txtMax.getText()));
                outSourced.setCompanyName(txtMachineId.getText());
                outSourced.setMin(Integer.parseInt(txtMin.getText()));
                Inventory.getInstance().addParts(outSourced);
            }

            callback.onResponse(true);

        }catch (Exception e) {
            alert("Error while saving: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    /**
     * Update.
     * This method updates the database when the modify part window loads.
     * @param callback the callback
     */
    public void update(Callback callback) {
        try {
            part.setId(part.getId());
            part.setPrice(Double.parseDouble(txtPrice.getText()));

            if(Integer.parseInt(txtLevel.getText()) > Integer.parseInt(txtMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory value should not exceed the maximum value!");
                alert.show();
                return;
            }

            if(Integer.parseInt(txtLevel.getText()) < Integer.parseInt(txtMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory value should not be less than the minimum value!");
                alert.show();
                return;
            }

            part.setStock(Integer.parseInt(txtLevel.getText()));
            part.setName(txtName.getText());

            if(Integer.parseInt(txtMax.getText()) < Integer.parseInt(txtMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The max value cannot be less than the min value");
                alert.show();
                return;
            }

            part.setMax(Integer.parseInt(txtMax.getText()));
            part.setMin(Integer.parseInt(txtMin.getText()));

            Inventory.getInstance().updatePart(part.getId(), part);

            callback.onResponse(true);

        }catch (Exception e) {
            alert("Error Updating: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     *  Called when an error is encountered
     * @param message message to be displayed
     * @param type Alert type
     */
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
