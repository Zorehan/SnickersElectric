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

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class dashboardViewController implements Initializable {
    private ProfileModel profileModel = ProfileModel.getInstance();
    @FXML
    private AreaChart<String, Number> chart;
    @FXML
    private NumberAxis numAxis;
    @FXML
    private CategoryAxis dateAxis;
    @FXML
    private ComboBox<Profile> comboProfile;
    @FXML
    private ComboBox<String> comboSortType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboProfile.getItems().addAll(profileModel.getObservableProfiles());
        comboProfile.getSelectionModel().selectFirst();

        comboSortType.setItems(FXCollections.observableArrayList("Annual Salary", "Annual Amount", "Overhead (%)", "Utilization (%)"));
        comboSortType.getSelectionModel().selectFirst();

        initData(comboProfile.getSelectionModel().getSelectedItem());

    }

    public void initData(Profile profile) {
        chart.getData().clear();
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Historical Data");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Profile> historicProfiles = profileModel.getHistoricProfile(profile);
        historicProfiles.sort((profile1, profile2) ->  {
            HistoricProfile historicProfile1 = (HistoricProfile) profile1;
            HistoricProfile historicProfile2 = (HistoricProfile) profile2;
            return historicProfile1.getDate().compareTo(historicProfile2.getDate());
        });

        List<String> dateCategories = new ArrayList<>();

        // Init Data in chart
        for(Profile p : historicProfiles) {
            HistoricProfile hProfile = (HistoricProfile) p;
            String formattedDate = hProfile.getDate().format(formatter);
            data.getData().add(new XYChart.Data<>(formattedDate, getSortType(hProfile, comboSortType.getSelectionModel().getSelectedItem())));
            dateCategories.add(formattedDate);
        }

        numAxis.setLabel(comboSortType.getSelectionModel().getSelectedItem());
        dateAxis.setCategories(FXCollections.observableArrayList(dateCategories));

        chart.getData().add(data);
    }

    @FXML
    private void clickComboProfile(ActionEvent actionEvent) {
        initData(comboProfile.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void clickComboSortType(ActionEvent actionEvent) {
        initData(comboProfile.getSelectionModel().getSelectedItem());
    }

    private Number getSortType(HistoricProfile profile, String type) {
        switch(type) {
            case "Annual Salary":
                return profile.getAnnualSalary();
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
}
