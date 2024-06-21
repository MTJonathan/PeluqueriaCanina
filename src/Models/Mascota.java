package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Mascota{
    private String nombre;
    private String raza;
    private String color;
    private String alergico;
    private String atencion;
    private String observaciones;
    private Dueno Undueno;
    

    public Mascota() {
    }

    public Mascota(String nombre, String raza, String color, String alergico, String atencion, String observaciones, Dueno Undueno) {
        this.nombre = nombre;
        this.raza = raza;
        this.color = color;
        this.alergico = alergico;
        this.atencion = atencion;
        this.observaciones = observaciones;
        this.Undueno = Undueno;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlergico() {
        return alergico;
    }

    public void setAlergico(String alergico) {
        this.alergico = alergico;
    }

    public String getAtencion() {
        return atencion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Dueno getUndueno() {
        return Undueno;
    }

    public void setUndueno(Dueno Undueno) {
        this.Undueno = Undueno;
    }


    public int setInsertar(Mascota mascota)
    {
        int nuevoId;
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //(1) INSERTAR
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("insert into mascota (nombre, color, raza, alergico, atencion, observaciones, dueno_id) values (?,?,?,?,?,?,?);");
            //Pasar los valores de los parametros SQL
            ps.setString(1, mascota.getNombre());
            ps.setString(2, mascota.getColor());
            ps.setString(3, mascota.getRaza());
            ps.setString(4, mascota.getAlergico());
            ps.setString(5, mascota.getAtencion());
            ps.setString(6, mascota.getObservaciones());
            ps.setInt(7, mascota.getUndueno().getIdDueno());
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
        //Retornar el nuevo id
        return nuevoId;        
    }
    
    public List<Mascota> getBuscar()
    {   
        List<Mascota> mascotas = new ArrayList<>();
        
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("SELECT dueno.id, mascota.nombre, mascota.raza, mascota.color, mascota.alergico, mascota.atencion, dueno.nombre, dueno.telefono, mascota.observaciones FROM mascota JOIN dueno ON mascota.dueno_id = dueno.id;");
            //Pasar los valores de los parametros SQL
            
            //Ejecutar la instrcuccion SQL
            ResultSet rs = ps.executeQuery(); //SELECT
            //Desplazar el puntero de registros
            while(rs.next())
            {
                //Instanciar el modelo Producto
                Mascota mascota = new Mascota();
                Dueno dueno = new Dueno();
                //Leer los valores de la fila
                mascota.setNombre(rs.getString("mascota.nombre"));
                mascota.setRaza(rs.getString("mascota.raza"));
                mascota.setColor(rs.getString("mascota.color"));
                mascota.setAlergico(rs.getString("mascota.alergico"));
                mascota.setAtencion(rs.getString("mascota.atencion"));
                mascota.setObservaciones(rs.getString("mascota.observaciones"));
                
                dueno.setIdDueno(rs.getInt("dueno.id"));
                dueno.setNombre(rs.getString("dueno.nombre"));
                dueno.setCel(rs.getString("dueno.telefono"));
                mascota.setUndueno(dueno);
                mascotas.add(mascota);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();      
        }
        //Retornar el objeto "mascota"
        return mascotas;
    }
    public void setEliminar(int idEliminar)
    {
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //(1) INSERTAR
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("delete from mascota where dueno_id=?;");
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
    public void setActualizar(Mascota mascota)
    {
        try
        {
            //Establecer la conexion
            Connection cnx = Conexion.getConexion();
            //(1) INSERTAR
            //Preparar la instruccion SQL
            PreparedStatement ps = cnx.prepareStatement("update mascota set nombre=?, raza=?, color=?, alergico=?, atencion=?, observaciones=? where dueno_id=?;");
            //Pasar los valores de los parametros SQL
            ps.setString(1, mascota.getNombre());
            ps.setString(2, mascota.getRaza());
            ps.setString(3, mascota.getColor());
            ps.setString(4, mascota.getAlergico());
            ps.setString(5, mascota.getAtencion());
            ps.setString(6, mascota.getObservaciones());
            ps.setInt(7, mascota.getUndueno().getIdDueno());
            //Ejecutar la instrcuccion SQL
            ps.executeUpdate(); //INSERT-UPDATE-DELETE
            
        }
        catch(Exception e)
        {

        }
    }
    
}
