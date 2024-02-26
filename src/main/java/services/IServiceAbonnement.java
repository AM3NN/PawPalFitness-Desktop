package services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceAbonnement<T>{

    void ajouter_abonnement(T t) throws SQLException;
    void modifier_abonnement(T t) throws SQLException;

    void supprimer_abonnement(int id_abonnement) throws SQLException;
    List<T> recuperer_abonnements() throws SQLException;
}
