

public class dbConnection{
    String address, name, user, password;
    
    public dbConnection(){
    }

    public dbConnection(String address, String name, String user, String password) {
        this.address = address;
        this.name = name;
        this.user = user;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "dbConnection{" + "address=" + address + ", name=" + name + ", user=" + user + ", password=" + password + '}';
    }
    
    
}