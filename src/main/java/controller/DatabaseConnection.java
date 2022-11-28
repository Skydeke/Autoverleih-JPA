package controller;

import javafx.application.Platform;
import model.Ausleihvorgang;
import model.Autoexemplar;
import model.Rechnung;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.List;

import static constants.ErrorMsgs.*;

public class DatabaseConnection {

    //Singleton
    private static DatabaseConnection instance;

    private EntityManagerFactory emf;
    private EntityManager em;

    private static boolean enabledGUI = true;

    private DatabaseConnection() {
        try {
            emf = Persistence.createEntityManagerFactory("AutoverleihJPA");
            em = emf.createEntityManager();
            if (enabledGUI)
                InputController.getInstance().printMsg(DBCON_CRE_SUC);
        } catch (Exception e) {
            if (enabledGUI) {
                InputController.getInstance().printError(e, DBCON_CRE_FAILED);
                Platform.exit();
            } else {
                System.out.println(DBCON_CRE_FAILED);
                e.printStackTrace();
            }
            System.exit(-1);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    public Autoexemplar getCarToReturn(String kenz) {
        try {
            TypedQuery<Autoexemplar> q = em.createQuery("SELECT a FROM Autoexemplar a WHERE a.kennzeichen = :kenz", Autoexemplar.class);
            q.setParameter("kenz", kenz);
            List<Autoexemplar> results = q.getResultList();
            if (results.size() != 1)
                return null;
            else
                return results.get(0);
        } catch (Exception e) {
            if (enabledGUI) {
                InputController.getInstance().printError(e, DBCON_ACC_FAILED);
                Platform.exit();
            }else {
                System.out.println(DBCON_ACC_FAILED);
            }
            System.exit(-1);
        }
        return null;
    }

    public void closeConnection() {
        try {
            em.close();
            emf.close();
        } catch (NullPointerException ex) {
            if (enabledGUI) {
                InputController.getInstance().printError(ex, DBCON_CLO_FAILED);
                Platform.exit();
            }else {
                System.out.println(DBCON_CLO_FAILED);
            }
            System.exit(-1);
        }
    }

    public void updateAvAndCar(Ausleihvorgang ausleihvorgang, Autoexemplar auto) {
        try {
            em.getTransaction().begin();
            em.merge(ausleihvorgang);
            em.merge(auto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (enabledGUI) {
                InputController.getInstance().printError(e, DBCON_UPD_FAILED_CAR_AV);
                Platform.exit();
            }else {
                System.out.println(DBCON_UPD_FAILED_CAR_AV);
            }
            System.exit(-1);
        }
    }

    public Rechnung addRechnungZuAv(Ausleihvorgang ausleihvorgang, double summe, boolean beglichen) {
        Rechnung rechnung = new Rechnung();
        rechnung.setAusleihvorgang(ausleihvorgang);
        rechnung.setSumme(summe);
        if (beglichen)
            rechnung.setBeglichen("t");
        else
            rechnung.setBeglichen("f");
        ausleihvorgang.setRechnung(rechnung);

        try {
            em.getTransaction().begin();
            em.merge(ausleihvorgang);
            em.persist(rechnung);
            em.getTransaction().commit();
            return rechnung;
        } catch (Exception e) {
            if (enabledGUI) {
                InputController.getInstance().printError(e, DBCON_UPD_FAILED_RECH_AV);
                Platform.exit();
            }else {
                System.out.println(DBCON_UPD_FAILED_RECH_AV);
            }
            System.exit(-1);
        }
        return null;
    }

    public static void disableGUIErrors() {
        enabledGUI = false;
    }

    public List<Autoexemplar> getLoanedCars() {
        try {
            TypedQuery<Autoexemplar> q = em.createQuery("SELECT DISTINCT a FROM Autoexemplar a, Ausleihvorgang v WHERE " +
                    "v.autoexemplar = a AND v.endekm = null AND v.endezeit = null AND v.rechnung not in (SELECT r from Rechnung r) ", Autoexemplar.class);
            return q.getResultList();
        } catch (Exception e) {
            if (enabledGUI) {
                InputController.getInstance().printError(e, DBCON_QUE_FAILED);
                Platform.exit();
            }else {
                System.out.println(DBCON_QUE_FAILED);
            }
            System.exit(-1);
        }
        return null;
    }

    public List<Autoexemplar> getAllCars() {
        try {
            TypedQuery<Autoexemplar> q = em.createQuery("SELECT a FROM Autoexemplar a", Autoexemplar.class);
            return q.getResultList();
        } catch (Exception e) {
            if (enabledGUI) {
                InputController.getInstance().printError(e, DBCON_QUE_FAILED);
                Platform.exit();
            }else {
                System.out.println(DBCON_QUE_FAILED);
            }
            System.exit(-1);
        }
        return null;
    }
}
