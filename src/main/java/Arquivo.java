
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Arquivo {
    int idArquivo;
    int idDirectorio;
    String nomeArquivo;
    
    Operacions operacion = new Operacions();

    public Arquivo() {
    }

    public Arquivo(int idArquivo, int idDirectorio, String nomeArquivo) {
        this.idArquivo = idArquivo;
        this.idDirectorio = idDirectorio;
        this.nomeArquivo = nomeArquivo;
    }
    
    //Crea a táboa Arquivo se non existe
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
    
    //Inserta un arquivo na táboa Arquivo
    public  void insertArquivo(File arquivo, Connection con) {

        try {
           // if (comprobarArquivo(arquivo, con) != true) {
                
                String path = operacion.getRuta(arquivo);
                System.out.println(path);
                
                int idPath = operacion.getIdByNome(path, con);
                System.out.println(idPath);

                String sqlInsert
                        = "INSERT INTO Arquivo (nomeArquivo, idDirectorio) VALUES (?,?);";
                PreparedStatement ps = con.prepareStatement(sqlInsert);

                ps.setString(1, arquivo.getName());
                ps.setInt(2, idPath);

                ps.executeUpdate();
                System.out.println("Arquivo insertado: " + arquivo.getName());

                ps.close();

           // }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //Comproba que na táboa non existe un arquivo igual para insertar o arquivo
   /* public boolean comprobarArquivo(File ruta, Connection con){
        boolean existe = false;
        int id = operacion.selectIdDirectorio(operacion.getRuta(ruta), con);
       try {
            String sql = "SELECT * FROM Arquivos WHERE nomeArquivo = ? AND idDirectorio = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ruta.getName());
            ps.setInt(2, id);
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
    }*/
    
    //Recorre o directorio raíz en busca de directorios e arquivos
    public void recorrerArquivos(String ruta, Connection con){
        File fileRaiz = new File(ruta);
        String[] arquivos = fileRaiz.list();

        for (int i = 0; i < arquivos.length; i++) {
            File f = new File(fileRaiz + File.separator + arquivos[i]);
            if (f.isDirectory()){
                String [] subcarpeta = f.list();
                for (int j = 0; j<subcarpeta.length; j++){
                    File f2 = new File (f + File.separator + subcarpeta[j]);
                    if (f2.isFile()){
                    String [] subarquivos = f2.list();
                    insertArquivo(f2,con);
                    }
                }
            }
        }
    }

}
