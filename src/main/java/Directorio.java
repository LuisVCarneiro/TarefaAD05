
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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
                        + "idDirectorio integer primary key not null, "
                        + "nomeDirectorio text"
                        + ");");

            CallableStatement createFunction = con.prepareCall(sqlDirectorio);
            createFunction.execute();
            createFunction.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void insertDirectorio(String ruta, Connection conn){
        File file = new File (ruta);
        try{
        String sqlInsertDirectorio = new String (
                "INSERT INTO Directorio VALUES (?,?)");
        PreparedStatement psd = conn.prepareStatement(sqlInsertDirectorio);
        
        int id = 1;
        if (file.exists()){
            String [] lectura = file.list();
            for (int i = 0; i < lectura.length; i++){
                if (file.isDirectory()){
                psd.setInt(1, id);
                id++;
                psd.setString(2,lectura[i]);
                psd.executeUpdate();
                }   
            }
        }
        psd.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void insertarDirectorio(File carpetaRaiz, Connection conn ){
        String raiz = carpetaRaiz.getPath();
        String ruta = carpetaRaiz.getAbsolutePath();
        ruta = "." + ruta.substring(raiz.length(),ruta.length());
        try{
             String sqlInsert = new String(
                        "INSERT INTO Directorio VALUES (?,?);");
                PreparedStatement ps = conn.prepareStatement(sqlInsert);
                int id = 1;
                ps.setInt(1,id);
                id++;
                ps.setString(2,ruta);
                ps.executeUpdate();

                System.out.println("directorio insertado -> " + ruta);

                ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ó insertar o directorio");
        }
    }
            
}
