package models;

public class Travailleur extends Personne {
    private int id;
    private int personneId; // New property to hold the ID from the Personne table
    private String diplome;
    private String categorie;
    private String langue;
    private String experience;

    public Travailleur(int age, String nom, String prenom, String region, String email, String password, int roleId, String diplome, String categorie, String langue, String experience) {
        super(age, nom, prenom, region, email, password, roleId);
        this.diplome = diplome;
        this.categorie = categorie;
        this.langue = langue;
        this.experience = experience;
    }
    public Travailleur(int personneId, String diplome, String categorie, String langue, String experience) {
        this.personneId = personneId;
        this.diplome = diplome;
        this.categorie = categorie;
        this.langue = langue;
        this.experience = experience;
    }
    public Travailleur(int id, int age, String nom, String prenom, String region, String email, String password, int roleId, int personneId, String diplome, String categorie, String langue, String experience) {
        super(id, age, nom, prenom, region, email, password, roleId);
        this.personneId = personneId;
        this.diplome = diplome;
        this.categorie = categorie;
        this.langue = langue;
        this.experience = experience;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getPersonneId() {
        return personneId;
    }

    public void setPersonneId(int personneId) {
        this.personneId = personneId;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Travailleur{" +
                "id=" + getId() +
                ", age=" + getAge() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", region='" + getRegion() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", diplome='" + diplome + '\'' +
                ", categorie='" + categorie + '\'' +
                ", langue='" + langue + '\'' +
                ", experience='" + experience + '\'' +
                '}';
    }
}
