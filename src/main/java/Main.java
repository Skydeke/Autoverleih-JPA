import model.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AutoverleihJPA");
    private EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        pollMenu();
    }

    private void pollMenu() {
        Scanner scanner = new Scanner(System.in);
        do {

            System.out.println("Bitte gib das Kennzeichen des zurückzugebenen Autos ein (oder e für exit): ");
            String kennzeichen = scanner.nextLine();
            if (kennzeichen.equals("e")) {
                break;
            }
            Autoexemplar retCar = getCarToReturn(kennzeichen);
            if (retCar == null) {
                System.out.println("Das Auto mit Kennzeichen " + kennzeichen + " kann nicht gefunden werden.");
                break;
            }
            Collection<Ausleihvorgang> jpaWorkingList = retCar.getAusleihvorgaenge();
            List<Ausleihvorgang> ausleihvorgange = new ArrayList<>(jpaWorkingList)
                    .stream()
                    .filter(a -> a.getEndezeit() == null && a.getRechnung() == null && a.getEndekm() == 0.0)
                    .collect(Collectors.toList());

            if (ausleihvorgange.size() != 1) {
                System.out.println("Es gibt mehrere oder keinen offenen Ausleihvorgang. Size: " + ausleihvorgange.size());
                break;
            }
            Ausleihvorgang ausleihvorgang = ausleihvorgange.get(0);
            System.out.println("Was ist der momentane Kilometerstand des Autos: ");
            double kmStand = scanner.nextDouble();
            Timestamp timeNow = Timestamp.from(Instant.now());
            ausleihvorgang.setEndezeit(timeNow);
            ausleihvorgang.setEndekm(kmStand);
            double oldKmStand = retCar.getKilometerstand();
            retCar.setKilometerstand(kmStand);

            em.getTransaction().begin();
            em.merge(ausleihvorgang);
            em.merge(retCar);
            em.getTransaction().commit();

            double anzKm = retCar.getKilometerstand() - oldKmStand;
            long anzTage = Duration.between(ausleihvorgang.getBeginnzeit().toLocalDateTime(), ausleihvorgang.getEndezeit().toLocalDateTime()).toDays();
            System.out.println("Es wurden " + anzKm + " Km in " + anzTage + " Tagen gefahren.");

            Rechnung rechnung = new Rechnung();
            double summe = retCar.getAutomodell().getPreisprokm() * anzKm;
            summe += retCar.getAutomodell().getPreisprotag() * anzTage;
            rechnung.setAusleihvorgang(ausleihvorgang);
            rechnung.setSumme(summe);
            rechnung.setBeglichen("f");
            ausleihvorgang.setRechnung(rechnung);
            System.out.println("Die Gesammtkosten belaufen sich auf " + summe + " Euro");

            em.getTransaction().begin();
            em.merge(ausleihvorgang);
            em.persist(rechnung);
            em.getTransaction().commit();//TODO
        } while (true);
        cleanUp();
    }

    private Autoexemplar getCarToReturn(String kenz) {
        TypedQuery<Autoexemplar> q = em.createQuery("SELECT a FROM Autoexemplar a WHERE a.kennzeichen = :kenz", Autoexemplar.class);
        q.setParameter("kenz", kenz);
        List<Autoexemplar> results = q.getResultList();
        if (results.size() != 1)
            return null;
        else
            return results.get(0);
    }

    private void cleanUp() {
        em.close();
        emf.close();
    }
}