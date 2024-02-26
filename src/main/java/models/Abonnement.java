package models;
import models.Salle_de_sport;
import java.util.Date;

public class Abonnement {
    private int id_abonnement ;
    private int id_salle ;

    private String duree_abonnement ;
    private float prix_abonnement ;
    private Date date_deb_abonnement ;
    private Date date_fin_abonnement ;


    public Abonnement() {
    }
    public Abonnement(int id_abonnement, int id_salle, String duree_abonnement, float prix_abonnement, Date date_deb_abonnement, Date date_fin_abonnement) {
        this.id_abonnement = id_abonnement;
        this.id_salle = id_salle;
        this.duree_abonnement = duree_abonnement;
        this.prix_abonnement = prix_abonnement;
        this.date_deb_abonnement = date_deb_abonnement;
        this.date_fin_abonnement = date_fin_abonnement;
    }

    public Abonnement(int id_salle, String duree_abonnement, float prix_abonnement, Date date_deb_abonnement, Date date_fin_abonnement) {
        this.id_salle = id_salle;
        this.duree_abonnement = duree_abonnement;
        this.prix_abonnement = prix_abonnement;
        this.date_deb_abonnement = date_deb_abonnement;
        this.date_fin_abonnement = date_fin_abonnement;
    }


    public int getId_abonnement() {
        return id_abonnement;
    }

    public void setId_abonnement(int id_abonnement) {
        this.id_abonnement = id_abonnement;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public String getDuree_abonnement() {
        return duree_abonnement;
    }

    public void setDuree_abonnement(String duree_abonnement) {
        this.duree_abonnement = duree_abonnement;
    }

    public float getPrix_abonnement() {
        return prix_abonnement;
    }

    public void setPrix_abonnement(float prix_abonnement) {
        this.prix_abonnement = prix_abonnement;
    }

    public Date getDate_deb_abonnement() {
        return date_deb_abonnement;
    }

    public void setDate_deb_abonnement(Date date_deb_abonnement) {
        this.date_deb_abonnement = date_deb_abonnement;
    }

    public Date getDate_fin_abonnement() {
        return date_fin_abonnement;
    }

    public void setDate_fin_abonnement(Date date_fin_abonnement) {
        this.date_fin_abonnement = date_fin_abonnement;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id_abonnement=" + id_abonnement +
                ", id_salle=" + id_salle +
                ", duree_abonnement='" + duree_abonnement + '\'' +
                ", prix_abonnement=" + prix_abonnement +
                ", date_deb_abonnement=" + date_deb_abonnement +
                ", date_fin_abonnement=" + date_fin_abonnement +
                '}';
    }
}
