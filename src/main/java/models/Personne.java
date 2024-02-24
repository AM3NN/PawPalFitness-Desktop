package models;

public class Personne {
    private int id, age;
    private String nom, prenom, region, email, password;
    private int roleId;

    public Personne(int id, int age, String nom, String prenom, String region, String email, String password, int roleId) {
        this.id = id;
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        this.region = region;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public Personne(int age, String nom, String prenom, String region, String email, String password, int roleId) {
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        this.region = region;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public Personne() {

    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getRegion() {
        return region;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", age=" + age +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
