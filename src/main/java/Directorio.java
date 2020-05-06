
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Directorio{
    int idDirectorio;
    String nomeDirectorio;
    Operacions operacion = new Operacions();

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
    
    
    //Creo a táboa directorio na base de datos, se non está xa creada
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
    
    //Inserto un directorio na base de datos, se non existe xa de antes
    public void insertarDirectorio(File carpeta, Connection conn ){

        String raiz = "." + carpeta.getAbsolutePath();
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
    
    //Recorro o directorio raíz para ler os directorios que contén e posteriormente pasalos á base de datos
    public void recorrerDirectorios(String path,Connection con) {

        File fileRaiz = new File(path);
        String[] directorios = fileRaiz.list();

        for (int i = 0; i < directorios.length; i++) {
            File archivo = new File(fileRaiz + File.separator + directorios[i]);
            if (archivo.isDirectory()) {
                insertarDirectorio(archivo,con);
                recorrerDirectorios(fileRaiz + File.separator + directorios[i],con);
            }
        }
    }
    
    //Método que coproba que o directorio lido non exista xa na base de datos
    public boolean comprobarDirectorio(String nome, Connection con) {
        boolean existe = false;
        try {
            String sql = "SELECT * FROM Directorio WHERE nomeDirectorio = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nome);
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
    
    //Lee o array de directorios e os que non existan, os crea
    public void restaurarDirectorios(Connection con) {

        Directorio[] array = selecionarDirectorios(con);
        
        for (int i = 0; i < array.length; i++) {
            String ruta = array[i].getNomeDirectorio();
            if (!new File(ruta).exists()) {
                File file = new File(ruta);
                file.mkdirs();
            }
        }
    } 
    
    //Rescata os directorios que hai na base de datos e os introduce nun array de tipo Directorio
    public Directorio[] selecionarDirectorios(Connection con) {
        int numDirectorios = operacion.numeroDirectorios(con);
        Directorio[] array = new Directorio[numDirectorios];

        try {
            String sql = "SELECT * FROM Directorio";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int cont = 0;
            while (rs.next()) {
                int id = rs.getInt("idDirectorio");
                String nome = rs.getString("nomeDirectorio");
                array[cont] = new Directorio(id, nome);
                cont += 1;
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return array;
    }
}

