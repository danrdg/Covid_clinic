
/**
 * Clase específica del tipo de vacunas Moderna
 * 
 * @author Daniel Rodríguez González
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class moderna extends vacuna
{
    /**
     * Constructor de objetos de la clase moderna
     */
    public moderna()
    {
        tipo = "Moderna";
    }
    
    /**
     * Constructor de objetos parametrizado de la clase moderna
     */
    public moderna (int n)
    {
        num = n;
        tipo = "Moderna";
    }
    
    /**
     * Método que comprueba si es posible vacunar dadas las fechas de la primera y segunda dosis.
     */
    public boolean checkAsignacion(LocalDate f1, LocalDate f2)
    {
        long dias = ChronoUnit.DAYS.between(f1, f2);
        boolean resp;
        if (dias>=21)
        {
            resp = true;
        }
        else
        {
            resp = false;
        }
        return resp;
    }
    
    /**
     * comprueba si hay existencias de una vacuna
     * @return devuelve el número de vacunas Johnson&Johnson en formato int
     */
    
    public vacuna checkInvent()
    {
        return lista.darVacunaModerna();
    }
    
    /**
     * Comprueba si la vacunación está completada con una sola dosis
     */
    
    public boolean unadosis()
    {
        return false;
    }
}
