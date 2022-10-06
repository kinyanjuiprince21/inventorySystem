package com.controllers;

import com.model.Inventory;
import com.model.Part;
import com.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type Main form controller.
 */
public class MainFormController implements Initializable {

    @FXML
    private Button btnAddPart;

    @FXML
    private Button btnAddProd;

    @FXML
    private Button btnDeletePart;

    @FXML
    private Button btnDeleteProd;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnModifyPart;

    @FXML
    private Button btnModifyProd;

    @FXML
    private TableColumn<Part, String> colPartId;

    @FXML
    private TableColumn<Part, String> colPartLevel;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, String> colPartPrice;

    @FXML
    private TableColumn<Product, String> colProdId;

    @FXML
    private TableColumn<Product, String> colProdLevel;

    @FXML
    private TableColumn<Product, String> colProdName;

    @FXML
    private TableColumn<Product, String> colProdPrice;

    @FXML
    private TableView<Part> tblParts;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TextField txtSearchPart;

    @FXML
    private TextField txtSearchProduct;

    /**
     * The Parts.
     */
    ObservableList<Part> parts = FXCollections.observableArrayList();
    /**
     * The Products.
     */
    ObservableList<Product> products = FXCollections.observableArrayList();

    /**
     * The Part.
     */
    Part part = null;
    /**
     * The Product.
     */
    Product product = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWidgets();
        setEventListeners();
    }

    /**
     * This method is used to initialize the table fields
     */
    public void initWidgets() {

        parts = Inventory.getInstance().getAllParts();
        products = Inventory.getInstance().getAllProducts();

        colPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblParts.setItems(parts);
        tblProducts.setItems(products);

        tblParts.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                modifyPart();
            } else if(e.getClickCount() == 1) {
                part  = tblParts.getSelectionModel().getSelectedItem();
            }
        });

        tblProducts.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                modifyProduct();
            } else if(e.getClickCount() == 1) {
                product = tblProducts.getSelectionModel().getSelectedItem();
            }
        });
    }

    /**
     * Sets event listeners.
     */
    public void setEventListeners() {
        txtSearchPart.setOnAction(e -> {

            if(txtSearchPart.getText().isBlank()) {
                tblParts.setItems(parts);
                tblParts.refresh();
                return;
            }
            ObservableList<Part> parts1 = Inventory.getInstance().lookupPart(txtSearchPart.getText());
            if(parts1.isEmpty()) {
                Part part = Inventory.getInstance().lookupPart(Integer.parseInt(txtSearchPart.getText()));
                parts1.add(part);
            }
            tblParts.setItems(parts1);
            tblParts.refresh();

        });

        txtSearchProduct.setOnAction(e -> {
            if(txtSearchProduct.getText().isBlank()) {
                tblProducts.setItems(products);
                tblProducts.refresh();
                return;
            }
            ObservableList<Product> products1 = Inventory.getInstance().lookupProduct(txtSearchProduct.getText());
            if(products1.isEmpty()) {
                Product product = Inventory.getInstance().lookupProduct(Integer.parseInt(txtSearchProduct.getText()));
                products1.add(product);
            }
            tblProducts.setItems(products1);
            tblProducts.refresh();
        });

        btnAddPart.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/add-part.fxml"));

            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            AddPartController controller = loader.getController();
            Scene scene = new Scene(parent);

            controller.setPart(null);
            Stage stage = new Stage();
            stage.setScene(scene);
            controller.initialize(test -> {
                if(test) {
                    parts = Inventory.getInstance().getAllParts();
                    tblParts.setItems(parts);
                    tblParts.refresh();
                }

                stage.close();
            });

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        });

        btnModifyPart.setOnAction(e -> {
            if(part != null) {
                modifyPart();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not selected any part to modify");
                alert.show();
            }

        });

        btnModifyProd.setOnAction(e -> {
            if(product != null) {
                modifyProduct();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not selected any product to modify");
                alert.show();
            }
        });

        btnDeletePart.setOnAction(e -> {
            if(part == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not selected any part to delete!");
                alert.show();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete part " + part.getName() + " ?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    Inventory.getInstance().deletePart(part);
                    parts = Inventory.getInstance().getAllParts();
                    tblParts.setItems(parts);
                    alert = new Alert(Alert.AlertType.CONFIRMATION, part.getName() + " was deleted successfully!");
                    alert.show();
                    tblParts.refresh();
                    part = null;
                }
            }

        });

        btnDeleteProd.setOnAction(e -> {
            if(product == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not selected any product to delete!");
                alert.show();
                return;
            } else {

                if(!product.getAssociatedParts().isEmpty()) {
                    Alert alert1 = new Alert(Alert.AlertType.WARNING, "You cannot delete " + product.getName() + " because it contains associated parts");
                    alert1.show();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete product " + product.getName() + "?");
                alert.showAndWait();

                if(alert.getResult() == ButtonType.OK) {
                    Inventory.getInstance().deleteProduct(product);
                    products = Inventory.getInstance().getAllProducts();
                    tblProducts.setItems(products);
                    alert = new Alert(Alert.AlertType.CONFIRMATION, product.getName() + " was successfully deleted!");
                    alert.show();
                    tblProducts.refresh();

                    product = null;
                }
            }

        });

        btnAddProd.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/add-product.fxml"));

            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            AddProductController controller = loader.getController();
            Scene scene = new Scene(parent);

            controller.setProduct(null);
            Stage stage = new Stage();
            stage.setScene(scene);
            controller.initialize(test -> {
                if(test) {
                    products = Inventory.getInstance().getAllProducts();
                    tblProducts.setItems(products);
                    tblProducts.refresh();
                }

                stage.close();
            });

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
        });

        btnExit.setOnAction(e -> {
            ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
        });
    }


    /**
     * Modify part.
     * This method is used to open modify part pop up controller
     */
    public void modifyPart() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/add-part.fxml"));

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        AddPartController controller = loader.getController();
        Scene scene = new Scene(parent);

        controller.setPart(part);
        Stage stage = new Stage();
        stage.setScene(scene);

        controller.initialize(test -> {
            if(test) {
                parts = Inventory.getInstance().getAllParts();
                tblParts.setItems(parts);
                tblParts.refresh();
            }

            stage.close();
        });

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();
    }


    /**
     * Modify product.
     */
    public void modifyProduct() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/add-product.fxml"));

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        AddProductController controller = loader.getController();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        controller.setProduct(product);

        controller.initialize(test -> {
            if(test) {
                products = Inventory.getInstance().getAllProducts();
                tblProducts.setItems(products);
                tblProducts.refresh();
            }
            stage.close();
        });

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();
    }
}
