
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Arquivo {
    int idArquivo;
    int idDirectorio;
    String nomeArquivo;

    public Arquivo() {
    }

    public Arquivo(int idArquivo, int idDirectorio, String nomeArquivo) {
        this.idArquivo = idArquivo;
        this.idDirectorio = idDirectorio;
        this.nomeArquivo = nomeArquivo;
    }

    public int getIdArquivo() {
        return idArquivo;
    }

    public int getIdDirectorio() {
        return idDirectorio;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public void createTableArquivo(Connection con){
        try{
            String sqlArquivo = new String(
                "CREATE TABLE IF NOT EXISTS Arquivo ("
                        + "idArquivo serial primary key,"
                        + " nomeArquivo text, "
                        + "idDirectorio integer, "
                        + "FOREIGN KEY (idDirectorio) REFERENCES Directorio (idDirectorio)"
                        + ");");
            
            CallableStatement createFunction = con.prepareCall(sqlArquivo);
            createFunction.execute();
            createFunction.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
    }
    
    /*public  void insertArchivo(File archivo, Connection con) {

        try {
            if (existeArcEnBD(archivo, con) != true) {

                String path = getRuta(archivo);

                // consultamos el id de esa ruta en tabla directorios
                int idPath = selectIdPorPath(path, con);

                FileInputStream fiStream = new FileInputStream(archivo);

                //Creamos a consulta que inserta a imaxe na base de datos
                String sqlInsert
                        = "INSERT INTO archivos (nombre,directorioid,archivo) VALUES (?,?,?);";
                PreparedStatement ps = con.prepareStatement(sqlInsert);

                ps.setString(1, archivo.getName());
                ps.setInt(2, idPath);
                ps.setBinaryStream(3, fiStream, (int) archivo.length());

                //Executamos a consulta
                ps.executeUpdate();

                System.out.println("archivo insertado -> " + archivo.getName());

                //Cerrramos a consulta e o arquivo aberto
                ps.close();

            }
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
