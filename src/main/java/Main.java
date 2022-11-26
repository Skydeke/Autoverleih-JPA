import model.Ausleihvorgang;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AutoverleihJPA");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Ausleihvorgang.class)));
        long count = em.createQuery(cq).getSingleResult();

        System.out.println("Es gibt " + count + " Ausleihvorgänge.");
        for (int i = 0; i < count; i++) {

            Ausleihvorgang sg = em.find(Ausleihvorgang.class, BigInteger.valueOf(i));
            if (sg == null) {
                System.out.println("Kein Ausleihvorgang mit dieser Nummer " + i);
                em.close();
                emf.close();
                return;
            }
            System.out.println("Ausleihvorgang " + sg.getAusleihvorgangnr() + " hat das Auto: ");
            System.out.println(" Kennzeichen: " + sg.getAutoexemplar().getKennzeichen());
        }

//        if (args.length < 3) {
//            em.close();
//            emf.close();
//            return;
//        }
//        em.getTransaction().begin();
//        Studenten neuS = new Studenten();
//        neuS.setMatrnr(Long.parseLong(args[1]));
//        neuS.setName(args[2]);
//        neuS.setStudiengang(sg);
//        em.persist(neuS);
//        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}