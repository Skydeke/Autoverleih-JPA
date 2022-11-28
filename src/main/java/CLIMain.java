import controller.DatabaseConnection;
import model.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class CLIMain {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AutoverleihJPA");
    private EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        new CLIMain();
    }

    public CLIMain() {
        DatabaseConnection.disableGUIErrors();
        pollMenu();
    }

    private void pollMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bitte gib das Kennzeichen des zurückzugebenen Autos ein (oder e für exit): ");
        String kennzeichen = scanner.nextLine();
        Autoexemplar retCar = DatabaseConnection.getInstance().getCarToReturn(kennzeichen);
        if (retCar == null) {
            System.out.println("Das Auto mit Kennzeichen " + kennzeichen + " kann nicht gefunden werden.");
            return;
        }
        Collection<Ausleihvorgang> jpaWorkingList = retCar.getAusleihvorgaenge();
        List<Ausleihvorgang> ausleihvorgange = new ArrayList<>(jpaWorkingList)
                .stream()
                .filter(a -> a.getEndezeit() == null && a.getRechnung() == null && a.getEndekm() == 0.0)
                .collect(Collectors.toList());

        if (ausleihvorgange.size() != 1) {
            System.out.println("Es gibt mehrere oder keinen offenen Ausleihvorgang. Size: " + ausleihvorgange.size());
            return;
        }
        Ausleihvorgang ausleihvorgang = ausleihvorgange.get(0);
        System.out.println("Was ist der momentane Kilometerstand des Autos: ");
        double kmStand = scanner.nextDouble();
        Timestamp timeNow = Timestamp.from(Instant.now());
        ausleihvorgang.setEndezeit(timeNow);
        ausleihvorgang.setEndekm(kmStand);
        double oldKmStand = retCar.getKilometerstand();
        retCar.setKilometerstand(kmStand);

        DatabaseConnection.getInstance().updateAvAndCar(ausleihvorgang, retCar);

        double anzKm = retCar.getKilometerstand() - oldKmStand;
        long anzTage = Duration.between(ausleihvorgang.getBeginnzeit().toLocalDateTime(), ausleihvorgang.getEndezeit().toLocalDateTime()).toDays();
        System.out.println("Es wurden " + anzKm + " Km in " + anzTage + " Tagen gefahren.");

        double summe = retCar.getAutomodell().getPreisprokm() * anzKm;
        summe += retCar.getAutomodell().getPreisprotag() * anzTage;
        System.out.println("Die Gesammtkosten belaufen sich auf " + summe + " Euro");

        Rechnung r = DatabaseConnection.getInstance().addRechnungZuAv(ausleihvorgang, summe, false);

        DatabaseConnection.getInstance().closeConnection();
        scanner.close();
    }
}