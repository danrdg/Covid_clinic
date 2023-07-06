
/**
 * Clase específica del tipo de vacunas Johnson&Johnson
 * 
 * @author Daniel Rodríguez González
 */
import java.time.LocalDate;
public class johnson extends vacuna
{
    /**
     * Constructor for objects of class johnson
     */
    public johnson()
    {
        tipo = "Johnson&Josnson";
    }
    
    /**
     * Constructor de objetos parametrizado de la clase johnson
     */
    public johnson (int n)
    {
        num = n;
        tipo = "Johnson&Josnson";
    }
    
    /**
     * Método que comprueba si es posible vacunar dadas las fechas de la primera y segunda dosis.
     */
    public boolean checkAsignacion(LocalDate f1, LocalDate f2)
    {
        // Una vez vacunado de una primera dosis, el paciente no necesita más
        return false;
    }
    
    /**
     * comprueba si hay existencias de una vacuna
     * @return devuelve la primera vacuna Johnson&Johnson disponible en el stock
     */
    
    public vacuna checkInvent()
    {
        return lista.darVacunaJohnson();
    }

    /**
     * Comprueba si la vacunación está completada con una sola dosis
     */
    
    public boolean unadosis()
    {
        return true;
    }
}
