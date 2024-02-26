package Models;

import java.util.Date;

public class Animal {
    int IDA;
    int Age;
    String Nom,Categorie,Type,Details;
    float Poids;

    public Animal(){}

    public Animal(int IDA, int age, String nom, String categorie,String type, String details, float poids) {
        this.IDA = IDA;
        Age = age;
        Nom = nom;
        Categorie = categorie;
        this.Type = type;
        Details = details;
        Poids = poids;
    }

    //Constructeur sans IDA(ID auto increment)

    public Animal(String nom,int age, String categorie, String type, String details,float poids) {
        Nom = nom;
        Age=age;
        Categorie = categorie;
        Type= type;
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

    public String getNom() {
        return Nom;
    }

    public String getCategorie() {
        return Categorie;
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

    public void setCategorie(String categorie) {
        Categorie = categorie;
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
                ", Age=" + Age +
                ", Nom='" + Nom + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", Type='" + Type + '\'' +
                ", Details='" + Details + '\'' +
                ", Poids=" + Poids +
                '}';
    }
}
