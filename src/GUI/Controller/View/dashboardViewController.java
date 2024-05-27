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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    @FXML
    private DatePicker dateFrom, dateTo;
    private List<String> dateCategories;
    private List<Profile> selectedProfiles;
    private DateTimeFormatter formatter;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateCategories = new ArrayList<>();
        selectedProfiles = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        listAvailableItems.getItems().addAll(profileModel.getObservableProfiles());

        comboSortType.setItems(FXCollections.observableArrayList("Annual Salary", "Annual Amount", "Overhead (%)", "Utilization (%)"));
        comboSortType.getSelectionModel().selectFirst();

    }

    @FXML
    private void clickComboSortType(ActionEvent actionEvent) {
        refreshGraph();
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
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        Profile profile = (Profile) listAvailableItems.getSelectionModel().getSelectedItem();
        List<Profile> historicProfiles = profileModel.getHistoricProfile(profile);

        //Sorter profil dataen efter datoen.
        historicProfiles.sort((profile1, profile2) -> {
            HistoricProfile historicProfile1 = (HistoricProfile) profile1;
            HistoricProfile historicProfile2 = (HistoricProfile) profile2;
            return historicProfile1.getDate().compareTo(historicProfile2.getDate());
        });

        //Looper gennem hver historic profile og tilføjer dem til datasættet
        for (Profile p : historicProfiles) {
            HistoricProfile hProfile = (HistoricProfile) p;
            String formattedDate = hProfile.getDate().format(formatter);
            XYChart.Data<String, Number> newData = new XYChart.Data<>(formattedDate, getSortType(hProfile, comboSortType.getSelectionModel().getSelectedItem()));
            newData.setExtraValue(hProfile);
            data.getData().add(newData);
            data.setName(profile.getName());

            if(!dateCategories.contains(formattedDate)) {
                dateCategories.add(formattedDate);
            }
        }
        if(!data.getData().isEmpty()){
            sortDates(dateCategories);
            dateAxis.setCategories(FXCollections.observableArrayList(dateCategories));
            chart.getData().add(data);
            selectedProfiles.add(profile);
        }
    }

    @FXML
    private void clickClearGraph(ActionEvent actionEvent) {
        clearGraph();
    }

    private void clearGraph() {
        dateCategories.clear();
        chart.getData().clear();
        selectedProfiles.clear();
    }

    private void refreshGraph() {
        List<Profile> profiles = new ArrayList<>(selectedProfiles);
        clearGraph();

        for(Profile profile : profiles) {
            XYChart.Series<String, Number> data = new XYChart.Series<>();
            List<Profile> historicProfiles = profileModel.getHistoricProfile(profile);

            //Sorter profil dataen efter datoen.
            historicProfiles.sort((profile1, profile2) -> {
                HistoricProfile historicProfile1 = (HistoricProfile) profile1;
                HistoricProfile historicProfile2 = (HistoricProfile) profile2;
                return historicProfile1.getDate().compareTo(historicProfile2.getDate());
            });

            //Looper gennem hver historic profile og tilføjer dem til datasættet
            for (Profile p : historicProfiles) {
                HistoricProfile hProfile = (HistoricProfile) p;
                String formattedDate = hProfile.getDate().format(formatter);
                XYChart.Data<String, Number> newData = new XYChart.Data<>(formattedDate, getSortType(hProfile, comboSortType.getSelectionModel().getSelectedItem()));
                newData.setExtraValue(hProfile);
                data.getData().add(newData);
                data.setName(profile.getName());

                if (!dateCategories.contains(formattedDate)) {
                    dateCategories.add(formattedDate);
                }
            }
            if (!data.getData().isEmpty()) {
                sortDates(dateCategories);
                dateAxis.setCategories(FXCollections.observableArrayList(dateCategories));
                chart.getData().add(data);
                selectedProfiles.add(profile);
            }
        }
    }

    @FXML
    private void pickedDateFrom(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate selectedDate = dateFrom.getValue();
        String formattedDate = formatter.format(selectedDate);
        System.out.println(formattedDate);

        List<String> dates = new ArrayList<>(dateAxis.getCategories());
        dates.sort(String::compareTo);

        //Parser datoerne og sletter alt før den valgte dato.
        List<String> datesToRemove = new ArrayList<>();
        for (String date : dates) {
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            if (parsedDate.isBefore(selectedDate)) {
                datesToRemove.add(date);
            }
        }
        dateAxis.getCategories().removeAll(datesToRemove);
    }

    @FXML
    private void pickedDateTo(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate selectedDate = dateTo.getValue();
        String formattedDate = formatter.format(selectedDate);
        System.out.println(formattedDate);

        List<String> dates = new ArrayList<>(dateAxis.getCategories());
        dates.sort(String::compareTo);

        //Parser datoerne og sletter alt efter den valgte dato.
        List<String> datesToRemove = new ArrayList<>();
        for (String date : dates) {
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            if (parsedDate.isAfter(selectedDate)) {
                datesToRemove.add(date);
            }
        }
        dateAxis.getCategories().removeAll(datesToRemove);
    }

    public  List<String> sortDates(List<String> dateList) {
        /*
         For at datoerne sortede ordentlig var det nødvendigt at konvetere dem fra string til LocalDate
         da string sortering ikke tog højde for årstal osv. Det var noget bøvl
         */
        // Parser date strings til LocalDates og sorterer dem.
        List<LocalDate> localDates = dateList.stream()
                .map(date -> LocalDate.parse(date, formatter))
                .sorted()
                .toList();

        // Konvereter localdates tilbage til strings og returner dem så de kan bruges i grafen igen.
        return localDates.stream()
                .map(date -> date.format(formatter))
                .collect(Collectors.toList());
    }
}
