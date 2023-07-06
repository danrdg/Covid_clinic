
/**
 * Implementa las funcionalidades y restricciones que definen los test de antígenos
 * 
 */
import java.time.LocalDate;
import java.util.ArrayList;
public class antigenos extends prueba
{
    /**
     * Constructor de objetos de la clase antigenos
     */
    public antigenos()
    {}

    /**
     * Constructor parametrizado de objetos de la clase antigenos
     */
    public antigenos(int n,String t,String f,String r,paciente p,enfermero e,tecnico te)
    {
        super(n,t,f,r,p,e,te);
    }
    
    /**
     * Método que comprueba si dada una fecha pruede asignarse una prueba a un paciente
     */
    public boolean comprobarFechaPac(paciente p)
    {
        // Los test de antígenos no tienen restricciones
        return true;
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
    
