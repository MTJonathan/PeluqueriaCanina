package Models;
    //Importar las clase JDBC
    import java.sql.DriverManager;
    import java.sql.Connection;
public class Conexion {
    //Metodo Estatico para establecer la conexiona a la BD
    public static Connection getConexion()
    {
        //Declarar una variable del tipo Connection
        Connection conexion;
        
        //Controlador de excepciones Try-Catch
        try
        {
            //Conexion JDBC
            //Cargar el controlador
            
            //Para MariaDB
            //Class.forName("org.mariadb.jdbc.Driver");
            //Para MySQL
            Class.forName("com.mysql.jdbc.Driver");
            
            //Declarar las variables: parametros de conexion
            //Para MariaDB
            //String cadena = "jdbc:mariadb://127.0.0.1:3306/bdpiad303";
            //Para MySQL
            String cadena = "jdbc:mysql://127.0.0.1:3306/peluqueria";
            String usuario = "root";
            String clave = "";
            
            //Instanciar y abrir la conexion
            conexion = DriverManager.getConnection(cadena, usuario, clave);
            
        }catch(Exception e)
        {
            //Asignar a la conexion el valor Null
            conexion = null;
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
        
        return conexion;
    }
}

