package personas.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import personas.dto.PersonaDTO;

public class PersonaDaoJDBC implements PersonaDao {

    private Connection userConn; //para conexion transaccional
    private static final String SQL_INSERT = "INSERT INTO udemy.datos (nombre, apellido) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE udemy.datos SET nombre = ?, apellido = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM udemy.datos WHERE id = ?";
    private static final String SQL_SELECT = "SELECT id, nombre, apellido FROM udemy.datos";

    public PersonaDaoJDBC() {
    }

    public PersonaDaoJDBC(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public int insert(PersonaDTO persona) throws SQLException {
        PreparedStatement pstm = null;
        int camposInsertados = 0;
        try {
            userConn = this.userConn != null ? this.userConn : Conexion.getConnection();
            pstm = userConn.prepareStatement(SQL_INSERT);
            pstm.setString(1, persona.getNombre());
            pstm.setString(2, persona.getApellido());
            camposInsertados = pstm.executeUpdate();
        } finally {
            Conexion.close(pstm);
            if (this.userConn == null) {
                Conexion.close(userConn);
            }
        }
        return camposInsertados;
    }

    @Override
    public int update(PersonaDTO persona) throws SQLException {
        PreparedStatement pstm = null;
        int camposActualizados = 0;
        try {
            userConn = this.userConn != null ? this.userConn : Conexion.getConnection();
            pstm = userConn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, persona.getNombre());
            pstm.setString(2, persona.getApellido());
            pstm.setInt(3, persona.getIdPersona());
            camposActualizados = pstm.executeUpdate();
        } finally {
            Conexion.close(pstm);
            if (this.userConn == null) {
                Conexion.close(userConn);
            }
        }
        return camposActualizados;
    }

    @Override
    public int delete(PersonaDTO persona) throws SQLException {
        PreparedStatement pstm = null;
        int camposEliminados = 0;
        try {
            userConn = this.userConn != null ? this.userConn : Conexion.getConnection();
            pstm = userConn.prepareStatement(SQL_DELETE);
            pstm.setInt(1, persona.getIdPersona());
            camposEliminados = pstm.executeUpdate();
        } finally {
            Conexion.close(pstm);
            if (this.userConn == null) {
                Conexion.close(userConn);
            }
        }
        return camposEliminados;
    }

    @Override
    public List<PersonaDTO> select() throws SQLException {
        PersonaDTO persona = null;
        List<PersonaDTO> personasDTO = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            userConn = this.userConn != null ? this.userConn : Conexion.getConnection();
            pstm = userConn.prepareStatement(SQL_SELECT);
            rs = pstm.executeQuery(); //tiene la info de la BD se debe sacar para poder mostrarla

            //se recorren todas las filas de la BD con datos
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                //se construye un objeto persona con los campos obtenidos de la BD
                persona = new PersonaDTO(id, nombre, apellido);

                //se adiciona a la lista de personas
                personasDTO.add(persona);
            }

        } finally {
            Conexion.close(rs);
            Conexion.close(pstm);
            if (this.userConn == null) {
                Conexion.close(userConn);
            }
        }

        return personasDTO;
    }

}
