
/**
 * Implementa las funcionalidades y restricciones que definen los test Serológicos
 * 
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
public class serologicos extends prueba
{
    /**
     * Constructor de objetos de la clase serologicos
     */
    public serologicos()
    {}

    /**
     * Constructor parametrizado de objetos de la clase antigenos
     */
    public serologicos(int n,String t,String f,String r,paciente p,enfermero e,tecnico te)
    {
        super(n,t,f,r,p,e,te);
    }   
    
    /**
     * Método que comprueba si dada una fecha pruede asignarse una test serológico a un paciente
     * El paciente deberá estar confinado un mínimo de 10 días antes del test
     * Deberán pasar mínimo 6 meses entre dos test serológicos
     * @param pac - paciente al que queremos asignar la prueba
     * @return devuelve true si la prueba es asignable
     */
    public boolean comprobarFechaPac(paciente pac)
    {
        ArrayList<prueba> pruebas = new ArrayList<prueba>();
        pruebas = pac.daPruebas();
        LocalDate fechaux = LocalDate.parse("1900-01-01");
        boolean aux = false;
        boolean condicion1 = false;
        boolean condicion2 = false;
        // Obtenemos la fecha del último test serológico del paciente
        for (prueba p:pruebas)
        {
            if ((p.daFecha().isAfter(fechaux)) && (p instanceof serologicos))
            {
                fechaux = p.daFecha();
            }
        }
        // Si el paciente no tiene test serológicos en su registro la prueba puede ser asignada
        if (fechaux.toString().equals("1900-01-01"))
        {
            condicion1 = true;
        }
        long meses = ChronoUnit.MONTHS.between(fechaux, fecha);
        if (meses>=6)
        {
            condicion1 = true;
        }
        else
        {
            condicion1 = false;
        }
        //Comprobamos si el paciente está o no confinado
        if (!pac.conf())
        {
            condicion2=false;
        }
        //Si está confinado deben pasar 10 días desde el confinamiento para asignar el test serológico
        else
        {
            //Extraemos fecha del último test de antígenos o pcr positivo
            fechaux = LocalDate.parse("1900-01-01");
            for (prueba p:pruebas)
            {
                if ((p.daFecha().isAfter(fechaux)) && (p.extraeRes().equals("positivo")))
                {
                    fechaux = p.daFecha();
                }
            }
            long dias = ChronoUnit.DAYS.between(fechaux, fecha);
            if (dias>=10)
            {
                condicion2 = true;
            }
            else
            {
                condicion2 = false;
            }
        }
        //Si se cumplen ambas condiciones la prueba se puede asignar
        aux = condicion1 && condicion2;
        if (!condicion1)
        {
            System.out.println ("Recuerde que el paciente debe esperar un periodo");
            System.out.println ("de 6 meses entre dos pruebas serológicas.");
        }
        if (!condicion2)
        {
            System.out.println ("Recuerde que el paciente debe esperar un periodo");
            System.out.println ("de 10 días desde el inicio del confinamiento.");
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
        if ((t.equals('0'))||(t.equals('1'))||(t.equals('2'))||(t.equals('3'))||(t.equals('4'))||(t.equals('5'))||(t.equals('6'))||(t.equals('7'))||(t.equals('8'))||(t.equals('9')))
        {
            return true;    
        }
        else
        {
            return false;
        }
    }
    
}