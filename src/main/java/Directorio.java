


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

    public void setIdDirectorio(int idDirectorio) {
        this.idDirectorio = idDirectorio;
    }

    public String getNomeDirectorio() {
        return nomeDirectorio;
    }

    public void setNomeDirectorio(String nomeDirectorio) {
        this.nomeDirectorio = nomeDirectorio;
    }
    
    App app = new App();
        
    
    
}
