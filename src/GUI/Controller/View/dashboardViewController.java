package GUI.Controller.View;

import BE.HistoricProfile;
import BE.Profile;
import GUI.Model.ProfileModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class dashboardViewController implements Initializable {
    private ProfileModel profileModel = ProfileModel.getInstance();
    @FXML
    private LineChart<String, Number> chart;
    @FXML
    private NumberAxis numAxis;
    @FXML
    private CategoryAxis dateAxis;
    @FXML
    private ComboBox<String> comboSortType;
    @FXML
    private ListView<Object> listAvailableItems;
    private List<String> dateCategories;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateCategories = new ArrayList<>();

        listAvailableItems.getItems().addAll(profileModel.getObservableProfiles());

        comboSortType.setItems(FXCollections.observableArrayList("Annual Salary", "Annual Amount", "Overhead (%)", "Utilization (%)"));
        comboSortType.getSelectionModel().selectFirst();

    }

    @FXML
    private void clickComboSortType(ActionEvent actionEvent) {
        //initData((Profile) listAvailableItems.getSelectionModel().getSelectedItem());
    }

    private Number getSortType(HistoricProfile profile, String type) {
        switch (type) {
            case "Annual Amount":
                return profile.getAnnualAmount();
            case "Overhead (%)":
                return profile.getOverheadPercent();
            case "Utilization (%)":
                return profile.getUtilizationPercent();
            default:
                return profile.getAnnualSalary();
        }
    }

    @FXML
    private void clickAdd(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        Profile profile = (Profile) listAvailableItems.getSelectionModel().getSelectedItem();
        List<Profile> historicProfiles = profileModel.getHistoricProfile(profile);

        historicProfiles.sort((profile1, profile2) -> {
            HistoricProfile historicProfile1 = (HistoricProfile) profile1;
            HistoricProfile historicProfile2 = (HistoricProfile) profile2;
            return historicProfile1.getDate().compareTo(historicProfile2.getDate());
        });

        for (Profile p : historicProfiles) {
            HistoricProfile hProfile = (HistoricProfile) p;
            String formattedDate = hProfile.getDate().format(formatter);
            XYChart.Data<String, Number> newData = new XYChart.Data<>(formattedDate, getSortType(hProfile, comboSortType.getSelectionModel().getSelectedItem()));
            if(!chart.getData().contains(newData)) {
                data.getData().add(newData);
                data.setName(profile.getName());
            }
            if(!dateCategories.contains(formattedDate)) {
                dateCategories.add(formattedDate);
            }
        }
        if(!data.getData().isEmpty()){
            dateCategories.sort(String::compareTo);
            dateAxis.setCategories(FXCollections.observableArrayList(dateCategories));
            chart.getData().add(data);
        }
    }

    @FXML
    private void clickRemove(ActionEvent actionEvent) {
    }

    @FXML
    private void clickRadioProfile(ActionEvent actionEvent) {
    }

    @FXML
    private void clickRadioCountry(ActionEvent actionEvent) {
    }

    @FXML
    private void clickClearGraph(ActionEvent actionEvent) {
        clearGraph();
    }

    private void clearGraph() {
        dateCategories.clear();
        chart.getData().clear();
    }
}
