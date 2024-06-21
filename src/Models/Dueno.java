package Models;
    //Importar las clases JDBC
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Dueno {
    //Propiedades
    private int idDueno;
    private String nombre;
    private String cel;

    public Dueno() {
    }

    public Dueno(int idDueno, String nombre, String cel) {
        this.idDueno = idDueno;
        this.nombre = nombre;
        this.cel = cel;
    }
    
    public int getIdDueno() {
        return idDueno;
    }

    public void setIdDueno(int idDueno) {
        this.idDueno = idDueno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }
    
        public int setInsertar(Dueno dueno)
    {
        int nuevoId;
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //(1) INSERTAR
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("insert into dueno (nombre, telefono) values (?,?);");
            //Pasar los valores de los parametros SQL
            ps.setString(1, dueno.getNombre());
            ps.setString(2, dueno.getCel());
            //Ejecutar la instrcuccion SQL
            ps.executeUpdate(); //INSERT-UPDATE-DELETE
            
            //*********************************************
            //(2) LEER EL NUEVO ID GENERADO
            //Preparar la instruccion SQL
            ps = cnx.prepareStatement("select max(id) as nuevoId from dueno");
            //Ejecutar la instrcuccion SQL
            ResultSet rs = ps.executeQuery();
            
            //Desplazar el puntero de registros
            if(rs.next())
            {
                //Leer el nuevo id
                nuevoId =  rs.getInt("nuevoId");

            }else{
                //Establecer valor predeterminado para el nuevo id
                nuevoId = 0;
            }
        }
        catch(Exception e)
        {
            //Establecer valores predeterminados
            nuevoId = -1;            
        }
        //Retornar el nuevo ids
        return nuevoId;        
    }
        public Dueno getBuscarById(int idBuscar)
    {
        //Instanciar el modelo Producto
        Dueno dueno = new Dueno();
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("select * from dueno where id=?");
            //Pasar los valores de los parametros SQL
            ps.setInt(1, idBuscar);
            //Ejecutar la instrcuccion SQL
            ResultSet rs = ps.executeQuery(); //SELECT
            //Desplazar el puntero de registros
            if(rs.next())
            {
                //Leer los valores de la fila
                dueno.setIdDueno(rs.getInt("id"));
                dueno.setNombre(rs.getString("nombre"));
                dueno.setCel(rs.getString("telefono"));
            }else{
                //Establecer valores predeterminados
                dueno.setIdDueno(0);
                dueno.setNombre("");
                dueno.setCel("");
            }
        }
        catch(Exception e)
        {
            //Establecer valores predeterminados
            dueno.setIdDueno(-1);
            dueno.setNombre("-Error de conexion-");
            dueno.setCel("");        
        }
        //Retornar el objeto "producto"
        return dueno;
    }
    
        public void setEliminar(int idEliminar)
    {
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //(1) INSERTAR
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("delete from dueno where id=?;");
            //Pasar los valores de los parametros SQL
            ps.setInt(1, idEliminar);
            //Ejecutar la instrcuccion SQL
            ps.executeUpdate(); //INSERT-UPDATE-DELETE
            
        }
        catch(Exception e)
        {
            e.printStackTrace(); // Imprime la traza de la pila del error
            JOptionPane.showMessageDialog(null, "Error al eliminar mascota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setActualizar(Dueno dueno)
    {
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //(1) INSERTAR
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("update dueno set nombre=?, telefono=? where id=?;");
            //Pasar los valores de los parametros SQL
            ps.setString(1, dueno.getNombre());
            ps.setString(2, dueno.getCel());
            ps.setInt(3, dueno.getIdDueno());
            //Ejecutar la instrcuccion SQL
            ps.executeUpdate(); //INSERT-UPDATE-DELETE
            
        }
        catch(Exception e)
        {

        }
    }
}
