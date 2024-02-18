package personas.jdbc;

import java.sql.SQLException;
import java.util.List;
import personas.dto.PersonaDTO;

public interface PersonaDao {
    int insert(PersonaDTO persona) throws SQLException;
    
    int update(PersonaDTO persona) throws SQLException;
    
    int delete(PersonaDTO persona) throws SQLException;
    
    List<PersonaDTO> select() throws SQLException;
    
}
