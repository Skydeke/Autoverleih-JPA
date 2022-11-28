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

    private DatabaseConnection(){
        try {
            emf = Persistence.createEntityManagerFactory("AutoverleihJPA");
            em = emf.createEntityManager();
            InputController.getInstance().printMsg(DBCON_CRE_SUC);
        }catch (Exception e){
            InputController.getInstance().printError(e, DBCON_CRE_FAILED);
            Platform.exit();
            System.exit(-1);
        }
    }

    public static DatabaseConnection getInstance(){
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
        }catch (Exception e){
            InputController.getInstance().printError(e, DBCON_ACC_FAILED);
            Platform.exit();
            System.exit(-1);
        }
        return null;
    }

    public void closeConnection() {
        try {
            em.close();
            emf.close();
        }catch (NullPointerException ex){
            InputController.getInstance().printError(ex, DBCON_CLO_FAILED);
            Platform.exit();
            System.exit(-1);
        }
    }

    public void updateAvAndCar(Ausleihvorgang ausleihvorgang, Autoexemplar auto) {
        try {
            em.getTransaction().begin();
            em.merge(ausleihvorgang);
            em.merge(auto);
            em.getTransaction().commit();
        }catch (Exception e){
            InputController.getInstance().printError(e, DBCON_UPD_FAILED_CAR_AV);
            Platform.exit();
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

        try{
            em.getTransaction().begin();
            em.merge(ausleihvorgang);
            em.persist(rechnung);
            em.getTransaction().commit();
            return rechnung;
        }catch (Exception e){
            InputController.getInstance().printError(e, DBCON_UPD_FAILED_RECH_AV);
            Platform.exit();
            System.exit(-1);
        }
        return null;
    }
}
