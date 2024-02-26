package services;

import java.sql.SQLException;
import java.util.List;

public interface IService <S>{
    void ajouter_salle(S s) throws SQLException;
    void modifier_salle(S s) throws SQLException;

    void supprimer_salle(int id_salle) throws SQLException;
    List<S> recuperer_salles() throws SQLException;
















































}
