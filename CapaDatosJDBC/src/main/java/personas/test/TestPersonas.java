package personas.test;

import java.sql.Connection;
import java.sql.SQLException;
import personas.dto.PersonaDTO;
import personas.jdbc.Conexion;
import personas.jdbc.PersonaDao;
import personas.jdbc.PersonaDaoJDBC;

public class TestPersonas {

    public static void main(String[] args) {

        Connection conexionTransaccional = null;

        try {
            conexionTransaccional = Conexion.getConnection();

            //quitar el Autocommit
            if (conexionTransaccional.getAutoCommit()) {
                conexionTransaccional.setAutoCommit(false);
            }

            PersonaDao personaDao = new PersonaDaoJDBC(conexionTransaccional);

            //insertar campos
            //PersonaDTO personaDto = new PersonaDTO("Maria Isabell del perpetuo socorro", "Urrutia");
            PersonaDTO personaDto = new PersonaDTO("Maria Isabell", "Urrutia");
            personaDao.insert(personaDto);

            //actualizar campos
            PersonaDTO personaDto2 = new PersonaDTO(2, "Clara", "Maldonado");
            personaDao.update(personaDto2);

            conexionTransaccional.commit();
            System.out.println("Se realizó el commit");

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                conexionTransaccional.rollback();
                System.out.println("Se realizó Rollback");
            } catch (SQLException ex1) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
