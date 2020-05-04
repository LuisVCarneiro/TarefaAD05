
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Directorio{
    int idDirectorio;
    String nomeDirectorio;

    public Directorio(){
    }
    
    public Directorio (int idDirectorio, String nomeDirectorio){
        this.idDirectorio = idDirectorio;
        this.nomeDirectorio = nomeDirectorio;
    }

    public int getIdDirectorio() {
        return idDirectorio;
    }

    public String getNomeDirectorio() {
        return nomeDirectorio;
    }
    
    public void createTableDirectorio(Connection con){
        try{
            String sqlDirectorio = new String(
                "CREATE TABLE IF NOT EXISTS Directorio ("
                        + "idDirectorio serial primary key, "
                        + "nomeDirectorio text"
                        + ");");

            CallableStatement createFunction = con.prepareCall(sqlDirectorio);
            createFunction.execute();
            createFunction.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void insertarDirectorio(File carpeta, Connection conn ){

        String raiz = "." + carpeta.getAbsolutePath();
        System.out.println("Raíz: " + raiz);
        if (comprobarDirectorio (raiz,conn) != true){
            try{ 
                 String sqlInsert = new String(
                            "INSERT INTO Directorio (nomeDirectorio) VALUES (?);");
                    PreparedStatement ps = conn.prepareStatement(sqlInsert);
                    ps.setString(1,raiz);
                    ps.executeUpdate();
                    System.out.println("directorio insertado -> " + raiz);

                    ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erro ó insertar o directorio");
            }  
        }
    }
    
    public void recorrerDirectorios(String path,Connection con) {

        File fileRaiz = new File(path);
        String[] directorios = fileRaiz.list();

        for (int i = 0; i < directorios.length; i++) {
            File archivo = new File(fileRaiz + File.separator + directorios[i]);

            //operaciones en cada directorio
            if (archivo.isDirectory()) {

                insertarDirectorio(archivo,con);

                // llamamos a la recursiva para comprobar subcarpetas
                recorrerDirectorios(fileRaiz + File.separator + directorios[i],con);
            }
        }
    }
    
    public boolean comprobarDirectorio(String nombre, Connection con) {
        boolean existe = false;
        try {
            String sql = "SELECT * FROM Directorio WHERE nomeDirectorio = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                existe = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return existe;
    }            
}
