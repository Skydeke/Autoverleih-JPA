package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.Ausleihvorgang;
import model.Autoexemplar;
import model.Rechnung;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InputController {

    @FXML TextArea errorText;


    @FXML TextField kenzTxtField;
    @FXML TextField kmTxtField;
    @FXML TextField summeTxtField;

    @FXML CheckBox beglichenCheckBox;

    @FXML Button retBtn;

    @FXML ListView<String> carListView;
    @FXML CheckBox onlyLoanedCars;

    private static final InputController instance = new InputController();

    public static InputController getInstance() {
        return instance;
    }

    private InputController(){}

    public void initialize(){
        //Called after @FXML is initialized
        DatabaseConnection.getInstance(); //To create the connection
        onlyLoanedCars.setSelected(true);
        onlyLoanedCars.selectedProperty().addListener((observable, oldValue, onlyLoanedC) -> {
            if (onlyLoanedC){
                //Show Only Loaned Cars
                carListView.getItems().clear();
                carListView.getItems().addAll(DatabaseConnection.getInstance().getLoanedCars().stream().map(Autoexemplar::getKennzeichen).collect(Collectors.toList()));
            }else {
                //Show all Cars
                carListView.getItems().clear();
                carListView.getItems().addAll(DatabaseConnection.getInstance().getAllCars().stream().map(Autoexemplar::getKennzeichen).collect(Collectors.toList()));
            }
        });

        //Populate List View
        carListView.getItems().clear();
        List<Autoexemplar> lCars = DatabaseConnection.getInstance().getLoanedCars();
        lCars.forEach( a -> System.out.println(a.getKennzeichen()));
        carListView.getItems().addAll(lCars.stream().map(Autoexemplar::getKennzeichen).collect(Collectors.toList()));
    }

    @FXML
    public void returnCar(){
        clearFields();
        retBtn.setDisable(true);
        String kenz = kenzTxtField.getText();
        Autoexemplar auto = DatabaseConnection.getInstance().getCarToReturn(kenz);
        if (auto == null){
            printMsg("Das Auto mit Kennzeichen " + kenz + " konnte nicht gefunden werden.");
            return;
        }
        Collection<Ausleihvorgang> jpaWorkingList = auto.getAusleihvorgaenge();
        List<Ausleihvorgang> ausleihvorgange = new ArrayList<>(jpaWorkingList)
                .stream()
                .filter(a -> a.getEndezeit() == null && a.getRechnung() == null && a.getEndekm() == 0.0)    //könnte man auch als SQL-Abfrage machen
                .collect(Collectors.toList());

        if (ausleihvorgange.size() != 1) {
            printMsg("Es gibt mehrere oder keinen offenen Ausleihvorgänge für dieses Auto. Anz Ausleihvorgänge: " + ausleihvorgange.size());
            return;
        }
        Ausleihvorgang ausleihvorgang = ausleihvorgange.get(0);
        double kmStand;
        try{
            kmStand = Integer.parseInt(kmTxtField.getText());
        }catch (Exception e){
            printError(e,"Der Kilometerstand ist keine Zahl. Englische Schreibweiße mit . anstelle von ,");
            retBtn.setDisable(false);
            return;
        }
        if (kmStand < auto.getKilometerstand()){
            printMsg("Der Kilometerstand ist kleiner als der in der Datenbank: " + auto.getKilometerstand());
            retBtn.setDisable(false);
            return;
        }

        Timestamp timeNow = Timestamp.from(Instant.now());
        ausleihvorgang.setEndezeit(timeNow);
        ausleihvorgang.setEndekm(kmStand);
        double oldKmStand = auto.getKilometerstand();
        auto.setKilometerstand(kmStand);

        DatabaseConnection.getInstance().updateAvAndCar(ausleihvorgang, auto);

        double anzKm = auto.getKilometerstand() - oldKmStand;
        long anzTage = Duration.between(ausleihvorgang.getBeginnzeit().toLocalDateTime(), ausleihvorgang.getEndezeit().toLocalDateTime()).toDays();
        printMsg("Es wurden " + anzKm + " Km in " + anzTage + " Tagen gefahren.");

        double summe = auto.getAutomodell().getPreisprokm() * anzKm;
        summe += auto.getAutomodell().getPreisprotag() * anzTage;

        Rechnung r = DatabaseConnection.getInstance().addRechnungZuAv(ausleihvorgang, summe, false);

        summeTxtField.setText(r.getSumme() + "");
        beglichenCheckBox.setSelected(r.getBeglichen());
        onlyLoanedCars.fire();//Toggle Selection to update CarListView
        onlyLoanedCars.fire();//Trigger it again that it has the state from before update
    }

    public void informationDialog(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void clearFields() {
        beglichenCheckBox.setSelected(false);
        summeTxtField.setText("");
        errorText.setText("");
    }

    public void printError(Exception ex, String fehler ) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText("Ein Fehler ist aufgetreten.");
        alert.setContentText(fehler);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    public void printMsg(String msg) {
        errorText.setText(msg);
        errorText.setStyle("");
    }

    @FXML
    public void kenzEdited(){
        retBtn.setDisable(false);
    }
}
