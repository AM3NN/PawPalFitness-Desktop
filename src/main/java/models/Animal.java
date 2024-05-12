package models;

public class Animal {
    int IDA;
    int IDC;
    int IDU;
    int Age;
    String Nom,Type,Details;
    float Poids;

    public Animal(){}

    public Animal(int IDA,int IDC,int IDU, int age, String nom,String type, String details, float poids) {
        this.IDA = IDA;
        this.IDC = IDC;
        this.IDU = IDU;
        Age = age;
        Nom = nom;
        this.Type = type;
        Details = details;
        Poids = poids;
    }

    //Constructeur sans IDA(ID auto increment)
    public Animal(int IDC,int IDU, int age, String nom,String type, String details, float poids) {
        this.IDC = IDC;
        this.IDU = IDU;
        Age = age;
        Nom = nom;
        this.Type = type;
        Details = details;
        Poids = poids;
    }

    //Constructeur sans IDA(ID auto increment)

    public Animal(String nom,int age, String type, String details,float poids) {
        Nom = nom;
        Age=age;
        Type= type;
        Details = details;
        Poids = poids;
    }

    public Animal(int age, String details, float poids) {
        Age=age;
        Details = details;
        Poids = poids;
    }

    public int getAge() {
        return Age;
    }

    public Animal(String nom) {
        this.Nom =nom;
    }

    //GETTERS
    public int getIDA() {
        return IDA;
    }

    public int getIDC() {
        return IDC;
    }

    public int getIDU() {
        return IDU;
    }

    public String getNom() {
        return Nom;
    }

    public float getPoids() {
        return Poids;
    }

    public String getType() {
        return Type;
    }

    public String getDetails() {
        return Details;
    }

    //SETTERS
    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPoids(float poids) {
        Poids = poids;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public void setAge(int age) {
        Age = age;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "IDA=" + IDA +
                "IDC=" + IDC +
                "IDU=" + IDU +
                ", Age=" + Age +
                ", Nom='" + Nom + '\'' +
                ", Type='" + Type + '\'' +
                ", Details='" + Details + '\'' +
                ", Poids=" + Poids +
                '}';
    }
}
