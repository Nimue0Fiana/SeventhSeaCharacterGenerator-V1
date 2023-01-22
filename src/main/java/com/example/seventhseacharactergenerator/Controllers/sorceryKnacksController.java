package com.example.seventhseacharactergenerator.Controllers;

/**
 * Sample Skeleton for 'sorceryKnacksPage-view.fxml' Controller Class
 */

import com.example.seventhseacharactergenerator.DBAccess.DBSorceryKnack;
import com.example.seventhseacharactergenerator.Models.Sorcery;
import com.example.seventhseacharactergenerator.Models.SorceryKnack;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.seventhseacharactergenerator.Controllers.confirmSorceryController.tempSorcerer;

public class sorceryKnacksController implements Initializable {
        protected static ObservableList<SorceryKnack> sorceryKnacks = tempSorcerer.getSorceryKnacks1();
        @FXML // fx:id="continueButton"
        private Button continueButton; // Value injected by FXMLLoader

        @FXML // fx:id="sorceryPointsTotal"
        private Label sorceryPointsTotal; // Value injected by FXMLLoader
        @FXML // fx:id="knackName"
        private Label knackName; // Value injected by FXMLLoader
        @FXML // fx:id="knackDescription"
        private Label knackDescription; // Value injected by FXMLLoader

        @FXML // fx:id="knackNameCol"
        private TableColumn<SorceryKnack, String> knackNameCol; // Value injected by FXMLLoader

        @FXML // fx:id="knackTable"
        private TableView<SorceryKnack> knackTable; // Value injected by FXMLLoader

        @FXML // fx:id="rankValue"
        private Spinner<Integer> rankValue = new Spinner<>(0, 3, 0); // Value injected by FXMLLoader

        @FXML // fx:id="knackRankCol"
        private TableColumn<SorceryKnack, Integer> knackRankCol; // Value injected by FXMLLoader

        @FXML // fx:id="updateButton"
        private Button updateButton; // Value injected by FXMLLoader

        //TODO:set value of rankValue spinner to the rank of the selected object in the tableView. Outside the scope of this project.
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                sorceryPointsTotal.setText((String.valueOf(tempSorcerer.getSorceryPoints1())));
                knackTable.setItems(sorceryKnacks);

                knackNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                knackRankCol.setCellValueFactory(new PropertyValueFactory<>("knackLevel"));
                knackTable.setOnMouseClicked((MouseEvent event) -> {
                        tempSorcerer.getSorceryKnacks1();
                        if(event.getButton().equals(MouseButton.PRIMARY)){
                                knackName.setVisible(true);
                                knackName.setText(knackTable.getSelectionModel().getSelectedItem().getName());
                                knackDescription.setVisible(true);
                                knackDescription.setText(knackTable.getSelectionModel().getSelectedItem().getDescription());
                        }
                });


        }

        @FXML
        void onContinueButton(ActionEvent event) {
                if(tempSorcerer.getSorceryPoints1() < 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "You've spent too many points.\n Decrease some Knacks and try again");
                        alert.showAndWait();
                } else {
                        try {
                                Parent root = FXMLLoader.load(getClass().getResource("/com/example/seventhseacharactergenerator/confirmSwordSchoolPage-view.fxml"));
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }

        }

        @FXML
        void onUpdateButton(ActionEvent event) {
                knackTable.getSelectionModel().getSelectedItem().setKnackLevel(rankValue.getValue());
                knackTable.refresh();
                int total = 7;
                for(SorceryKnack sc : sorceryKnacks) {
                        int next = sc.getKnackLevel();
                        total -= next;
                }
                sorceryPointsTotal.setText(String.valueOf(total));
                tempSorcerer.setSorceryPoints1(total);
        }

}
