
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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
                        + "idArquivo integer primary key not null,"
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
    
    public void insertArquivo(String ruta, Connection conn){
        File file = new File (ruta);
        try{
            String sqlInsertArquivo = new String (
                "INSERT INTO Arquivo VALUES (?,?,?)");
        PreparedStatement psa = conn.prepareStatement(sqlInsertArquivo);
        
        int id = 1;
        if (file.exists()){
            String [] lectura = file.list();
            for (int i = 0; i < lectura.length; i++){
               if (file.isDirectory()){
                   File arquivo = new File(file.getAbsolutePath(),lectura[i]);
                   for (int j=0; j < lectura.length; j++){
                       if(file.isFile()){
                           psa.setInt(1,id);
                           psa.setString(2, lectura[i]);
                           //psa.setInt(3,);
                       }
                   }
                }   
            }
        }
        psa.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
