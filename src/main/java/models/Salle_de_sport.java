package models;

public class Salle_de_sport {
    private int id_salle ;
    private String nom_salle ;
    private String description_salle ;
    private EnumRegion region_salle ;
    private String image_salle ;
    private String adresse_salle ;




    public enum EnumRegion{
        Ariana,
        Ben_Arous,
        Monastir,
        Sfax,
        Tozeur,
        Tunis,
    }
    public static EnumRegion convertToEnumRegion(String regionString) {
        return EnumRegion.valueOf(regionString);
    }
    public Salle_de_sport() {
    }

    public Salle_de_sport(int id_salle, String nom_salle, String description_salle, EnumRegion region_salle, String image_salle, String adresse_salle) {
        this.id_salle = id_salle;
        this.nom_salle = nom_salle;
        this.description_salle = description_salle;
        this.region_salle = region_salle;
        this.image_salle = image_salle;
        this.adresse_salle = adresse_salle;
    }

    public Salle_de_sport(String nom_salle, String description_salle, EnumRegion region_salle, String image_salle, String adresse_salle) {
        this.nom_salle = nom_salle;
        this.description_salle = description_salle;
        this.region_salle = region_salle;
        this.image_salle = image_salle;
        this.adresse_salle = adresse_salle;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public String getNom_salle() {
        return nom_salle;
    }

    public void setNom_salle(String nom_salle) {
        this.nom_salle = nom_salle;
    }

    public String getDescription_salle() {
        return description_salle;
    }

    public void setDescription_salle(String description_salle) {
        this.description_salle = description_salle;
    }

    public EnumRegion getRegion_salle() {
        return region_salle;
    }

    public void setRegion_salle(EnumRegion region_salle) {
        this.region_salle = region_salle;
    }

    public String getImage_salle() {
        return image_salle;
    }

    public void setImage_salle(String image_salle) {
        this.image_salle = image_salle;
    }

    public String getAdresse_salle() {
        return adresse_salle;
    }

    public void setAdresse_salle(String adresse_salle) {
        this.adresse_salle = adresse_salle;
    }

    @Override
    public String toString() {
        return "Salle_de_sport{" +
                "id_salle=" + id_salle +
                ", nom_salle='" + nom_salle + '\'' +
                ", description_salle='" + description_salle + '\'' +
                ", region_salle=" + region_salle +
                ", image_salle='" + image_salle + '\'' +
                ", adresse_salle='" + adresse_salle + '\'' +
                '}';
    }
}
