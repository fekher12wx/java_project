/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.planmanagmentsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author WINDOWS 10
 */
public class planController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label username;

    @FXML
    private Label page_label;

    @FXML
    private Button home_btn;

    @FXML
    private Button myPlans_btn;

    @FXML
    private Button finishedPlans_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_username;

    @FXML
    private Label home_dateRegistered;

    @FXML
    private Label home_NAP;

    @FXML
    private Label home_FP;

    @FXML
    private AnchorPane myPlans_form;

    @FXML
    private TextArea myPlans_plan;

    @FXML
    private DatePicker myPlans_startDate;

    @FXML
    private DatePicker myPlans_endDate;

    @FXML
    private Button myPlans_addBtn;

    @FXML
    private Button myPlans_updateBtn;

    @FXML
    private Button myPlans_clearBtn;

    @FXML
    private Button myPlans_deleteBtn;

    @FXML
    private TableView<plandata> myPlans_tableView;

    @FXML
    private TableColumn<plandata, String> myPlans_col_plan;

    @FXML
    private TableColumn<plandata, String> myPlans_col_startDate;

    @FXML
    private TableColumn<plandata, String> myPlans_col_endDate;

    @FXML
    private TableColumn<plandata, String> myPlans_col_dateCreated;

    @FXML
    private TableColumn<plandata, String> myPlans_col_status;

    @FXML
    private AnchorPane finishedPlans_form;

    @FXML
    private TableView<plandata> finishedPlans_tableView;

    @FXML
    private TableColumn<plandata, String> finishedPlans_col_planID;

    @FXML
    private TableColumn<plandata, String> finishedPlans_col_plan;

    @FXML
    private TableColumn<plandata, String> finishedPlans_col_startDate;

    @FXML
    private TableColumn<plandata, String> finishedPlans_col_endDate;

    @FXML
    private TableColumn<plandata, String> finishedPlans_col_status;

    @FXML
    private ComboBox<?> finishedPlans_status;

    @FXML
    private TextField finishedPlans_planID;

    @FXML
    private Button finishedPlans_updateBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Alert alert;

    public void homeDisplayUsername() {
        home_username.setText(username.getText());
    }

    public void homeDisplayDateRegistered() {

        String selectDate = "SELECT date FROM user WHERE username = '"
                + data.username + "'";

        connect = database.getConnection();

        try {

            prepare = connect.prepareStatement(selectDate);
            result = prepare.executeQuery();

            if (result.next()) {
                home_dateRegistered.setText(result.getString("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayNAP() {
        String sql = "SELECT COUNT(planID) FROM myplans WHERE planner = '"
                + username.getText() + "'";

        connect = database.getConnection();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                home_NAP.setText(result.getString("COUNT(planID)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayFP() {
        String sql = "SELECT COUNT(planID) FROM myplans WHERE planner = '"
                + username.getText() + "' AND status = 'Finished'";

        connect = database.getConnection();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                home_FP.setText(result.getString("COUNT(planID)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void myPlansAddBtn() {

        connect = database.getConnection();


        try {
            String username = data.getUsername();

            if (myPlans_plan.getText().isEmpty() || myPlans_startDate.getValue() == null
                    || myPlans_endDate.getValue() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (myPlans_endDate.getValue().isBefore(myPlans_startDate.getValue())) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Date :3");
                    alert.showAndWait();
                } else {

                    String checkPlan = "SELECT plan FROM myplans WHERE plan = ? AND planner = ?";
                    prepare = connect.prepareStatement(checkPlan);
                    prepare.setString(1, myPlans_plan.getText());
                    prepare.setString(2, username);

                    result = prepare.executeQuery();

                    if (result.next()) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Plan " + myPlans_plan.getText() + " is already recorded");
                        alert.showAndWait();
                    } else {
                        String insertData = "INSERT INTO myplans (plan, startDate, endDate, dateCreated, status, planner) "
                                + "VALUES(?,?,?,?,?,?)";

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, myPlans_plan.getText());
                        prepare.setString(2, String.valueOf(myPlans_startDate.getValue()));
                        prepare.setString(3, String.valueOf(myPlans_endDate.getValue()));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(4, String.valueOf(sqlDate));
                        prepare.setString(5, "Not Finish");
                        prepare.setString(6, username);
                        prepare.executeUpdate();

                        myPlansShowData();
                        myPlansClearBtn();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void myPlansUpdateBtn() {

        connect = database.getConnection();

        try {

            if (planID == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Plan: "
                        + myPlans_plan.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String checkData = " SELECT * FROM myplans WHERE planID = " + planID;

                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);
                    String currentD;
                    if (result.next()) {
                        currentD = result.getString("dateCreated");

                        String updateData = "UPDATE myplans SET plan = '"
                                + myPlans_plan.getText() + "', startDate = '"
                                + myPlans_startDate.getValue() + "', endDate = '"
                                + myPlans_endDate.getValue() + "', dateCreated = '"
                                + currentD + "' WHERE planID = " + planID;

                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();

                        myPlansShowData();
                        myPlansClearBtn();

                    }
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void myPlansDeleteBtn() {

        connect = database.getConnection();

        try {

            if (planID == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Plan: "
                        + myPlans_plan.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String checkData = "SELECT * FROM myplans WHERE planID = " + planID;

                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);
                    if (result.next()) {
                        result.getString("dateCreated");

                        String deleteData = "DELETE FROM myplans WHERE planID = " + planID;

                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();

                        myPlansShowData();
                        myPlansClearBtn();

                    }
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void myPlansClearBtn() {
        myPlans_plan.setText("");
        myPlans_startDate.setValue(null);
        myPlans_endDate.setValue(null);
        planID = 0;
    }

    public ObservableList<plandata> myPlansDataList() {

        ObservableList<plandata> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM myplans WHERE planner = '"
                + username.getText() + "'";

        connect = database.getConnection();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            plandata pData;
            while (result.next()) {
                pData = new plandata(result.getInt("planID"),
                        result.getString("plan"), result.getDate("startDate"),
                        result.getDate("endDate"), result.getDate("dateCreated"),
                        result.getString("status"), result.getString("planner"));

                listData.add(pData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<plandata> myPlansListData;

    public void myPlansShowData() {
        myPlansListData = myPlansDataList();

        myPlans_col_plan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        myPlans_col_startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        myPlans_col_endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        myPlans_col_dateCreated.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        myPlans_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        myPlans_tableView.setItems(myPlansListData);
    }

    private int planID;

    public void myPlansSelectData() {

        plandata pData = myPlans_tableView.getSelectionModel().getSelectedItem();
        int num = myPlans_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        planID = pData.getPlanID();

        myPlans_plan.setText(pData.getPlan());
        myPlans_startDate.setValue(LocalDate.parse(String.valueOf(pData.getStartDate())));
        myPlans_endDate.setValue(LocalDate.parse(String.valueOf(pData.getEndDate())));

    }

    public void finishedPlansUpdateBtn() {
        connect = database.getConnection();

        try {
            if (finishedPlans_planID.getText().isEmpty() || finishedPlans_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select an item first");
                alert.showAndWait();
            } else {
                // Get the plan ID from the text field
                int planID = Integer.parseInt(finishedPlans_planID.getText());

                // Get the selected status from the combo box
                String selectedStatus = finishedPlans_status.getSelectionModel().getSelectedItem().toString();

                // Update the plan's status in the database
                String updateData = "UPDATE myplans SET status = ? WHERE planID = ?";
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, selectedStatus);
                prepare.setInt(2, planID);
                prepare.executeUpdate();

                // Show a confirmation message
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                // Refresh the table view to reflect the changes
                finishedPlansShowData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] listStatus = {"Finished", "Not Finish"};

    public void finishedPlansListStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        finishedPlans_status.setItems(listData);
    }

    public ObservableList<plandata> finishedPlansDataList() {

        ObservableList<plandata> listData = FXCollections.observableArrayList();
        String selectData = "SELECT * FROM myplans WHERE planner = '"
                + username.getText() + "'";
        connect = database.getConnection();

        try {

            assert connect != null;
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            plandata pData;
            while (result.next()) {

                pData = new plandata(result.getInt("planID"), result.getString("plan"),
                        result.getDate("startDate"), result.getDate("endDate"),
                        result.getDate("dateCreated"), result.getString("status"),
                        result.getString("planner"));
                listData.add(pData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<plandata> finishedPlansListData;

    public void finishedPlansShowData() {

        finishedPlansListData = finishedPlansDataList();

        finishedPlans_col_planID.setCellValueFactory(new PropertyValueFactory<>("planID"));
        finishedPlans_col_plan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        finishedPlans_col_startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        finishedPlans_col_endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        finishedPlans_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        finishedPlans_tableView.setItems(finishedPlansListData);

    }

    public void finishedPlansSelectData() {
        plandata pData = finishedPlans_tableView.getSelectionModel().getSelectedItem();
        int num = finishedPlans_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        finishedPlans_planID.setText(String.valueOf(pData.getPlanID()));
    }

    public void displayUsername() {

        String user = data.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            myPlans_form.setVisible(false);
            finishedPlans_form.setVisible(false);

            page_label.setText("HOME");

            homeDisplayUsername();
            homeDisplayDateRegistered();
            homeDisplayNAP();
            homeDisplayFP();

        } else if (event.getSource() == myPlans_btn) {
            home_form.setVisible(false);
            myPlans_form.setVisible(true);
            finishedPlans_form.setVisible(false);

            myPlansShowData();

            page_label.setText("MY PLANS");
        } else if (event.getSource() == finishedPlans_btn) {
            home_form.setVisible(false);
            myPlans_form.setVisible(false);
            finishedPlans_form.setVisible(true);

            page_label.setText("FINISHED PLANS");

            finishedPlansShowData();
        }

    }

    public void logout() {

        try {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // HIDE MAIN FORM
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        homeDisplayUsername();
        homeDisplayDateRegistered();
        homeDisplayNAP();
        homeDisplayFP();

        myPlansShowData();

        finishedPlansListStatus();

        finishedPlansShowData();
    }

}
