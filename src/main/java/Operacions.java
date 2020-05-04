
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Operacions {
    String SEPARADOR = File.separator;
    
    
    //Obteño a ruta dun arquivo ou directorio sen o nome
    public String getRuta(File archivo) {
        String path = archivo.getAbsolutePath();
        int pos = path.lastIndexOf(SEPARADOR);
        path = path.substring(0, pos);
        return path;
    }
    
    
    //Obteño o id dun arquivo ou directorio da base de datos a través do nome
    public int getIdByNome (String ruta, Connection con){
        int id = 0;
        ruta = "." + ruta;

        try {
            String sql = "SELECT idDirectorio FROM Directorio WHERE nomeDirectorio = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ruta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    
    
    //Obteño o nome do arquivo mediante o id que lle pase por parámetro
    public String getNomeByID (int id, Connection con){
        String ruta = null;
        try {
            String sql = "SELECT nomeDirectorio FROM Directorio WHERE idDirectorio = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ruta = rs.getString(1);
            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ruta;
    }

}
