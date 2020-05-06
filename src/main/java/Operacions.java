
import java.io.File;
import java.sql.CallableStatement;
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
    
    public int numeroDirectorios(Connection con) {
        int numDIrectorios = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Directorio";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numDIrectorios = rs.getInt(1);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numDIrectorios;
    }
     

    public int numeroArquivos(Connection con) {
        int numArchivos = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Arquivo";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numArchivos = rs.getInt(1);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numArchivos;
    }
    
    //Creo o triguer que se executa ó engadir unha nova mensaxe
    public void Trigger(Connection con) {
        
        String sql = new String(
                "DROP TRIGGER IF EXISTS notificacion ON Arquivo; "
                + "CREATE TRIGGER notificacion "
                + "AFTER INSERT "
                + "ON Arquivo "
                + "FOR EACH ROW "
                + "EXECUTE PROCEDURE mensaje(); ");
        try {
            CallableStatement createTrigger = con.prepareCall(sql);
            createTrigger.execute();
            createTrigger.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Creo a función que notifica que se engadiu un novo arquivo
    public void Funcion(Connection con) {   
        try {
            String sql = new String(
                    "CREATE OR REPLACE FUNCTION mensaje() "
                    + "RETURNS trigger AS $$ "
                    + "BEGIN "
                    + "PERFORM pg_notify('nuevo_mensaje',NEW.id::text); "
                    + "RETURN NEW; "
                    + "END; "
                    + "$$ LANGUAGE plpgsql; ");

            CallableStatement createFunction = con.prepareCall(sql);
            createFunction.execute();
            createFunction.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
