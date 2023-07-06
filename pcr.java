
/**
 * Implementa las funcionalidades y restricciones que definen los test PCR
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
public class pcr extends prueba
{ 
    /**
     * Constructor de objetos de la clase pcr
     */
    public pcr()
    {}

    /**
     * Constructor parametrizado de objetos de la clase antigenos
     */
    public pcr(int n,String t,String f,String r,paciente p,enfermero e,tecnico te)
    {
        super(n,t,f,r,p,e,te);
    }
    
    /**
     * Método que comprueba si dada una fecha pruede asignarse un test PCR a un paciente
     * Deben pasar un mínimo de 15 días entre dos test PCR
     * @param 
     * @return devuelve true si la prueba es asignable
     */
    public boolean comprobarFechaPac(paciente pac)
    {
        LocalDate fechaux = LocalDate.parse("1900-01-01");
        boolean aux = false;
        ArrayList<prueba> pruebas = pac.daPruebas();
        // Obtenemos la fecha de la última PCR del paciente
        for (prueba p:pruebas)
        {
            if ((p.daFecha().isAfter(fechaux)) && (p instanceof pcr))
            {
                fechaux = p.daFecha();
            }
        }
        // Si el paciente no tiene PCR en su registro la prueba puede ser asignada
        if (fechaux.toString().equals("1900-01-01"))
        {
            aux = true;
        }
        long dias = ChronoUnit.DAYS.between(fechaux, fecha);
        if (dias>=15)
        {
            aux = true;
        }
        else
        {
            aux = false;
        }
        if (!aux)
        {
            System.out.println ("Recuerde que el paciente debe esperar un periodo");
            System.out.println ("de 15 días entre dos pruebas PCR.");
        }
        return aux;
    }
    
    /**
     * Comprueba si un valor dado es un resultado válido de la prueba
     * @return devuelve true si la cadena es un resultado válido de la prueba
     * @param t - String que vamos a comprobar
     */
    public boolean comprobarRes(String t)
    {
        if ((t.equals("positivo"))||(t.equals("negativo")))
        {
            return true;    
        }
        else
        {
            return false;
        }
    }
}