
/**
 * Clase que describe las pruebas que se realizan en la clínica
 * 
 * @author Daniel Rodríguez González
 */
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
public abstract class prueba
{
    // almacena número de referencia de la prueba
    private int num;
    // almacena el tipo de prueba que se realiza
    private String tipo;
    // almacena la fecha de realización de la prueba
    protected LocalDate fecha;
    // almacena el resultado de la prueba 
    private String res;
    // almacena el paciente objeto de la prueba
    private paciente pac;
    // almacena al enfermero que realiza la prueba
    private enfermero enf;
    // almacena al técnico que obtendrá los resultados
    private tecnico tec;
    
    /**
     * Constructor de objetos de la clase prueba
     */
    public prueba()
    {
        num=0;
        fecha=LocalDate.now();
        //Sin resultado por defecto
        res = "***";
    }

    /**
     * Constructor parametrizado de objetos de la clase prueba
     * @param n - número de identificación de la prueba
     * @param t - tipo de prueba (Antígenos, PCR, Serológica)
     * @param f - fecha de realización de la prueba en formato String (AAAA-MM-DD)
     * @param r - resultado de la prueba
     * @param p - paciente objeto de la prueba
     * @param e - enfermero que realizará la prueba
     * @param te - técnico que obtendrá los resultados de la misma
     */
    public prueba(int n,String t,String f,String r,paciente p,enfermero e,tecnico te)
    {        
        num = n;
        tipo = t;
        fecha = LocalDate.parse(f);
        res = r;
        pac = p;
        enf = e;
        tec = te;
    }
    
    /**
     * Actualiza el resultado de la prueba
     * @param r - String que contiene el resultado de la prueba
     */
    public void actualizaRes(String r)
    {
        res = r;
    }
    
    /**
     * Asigna la fecha de la realización de la prueba
     * 
     */
    public void asignaFecha()
    {
        String aux;
        Scanner in = new Scanner(System.in);
        boolean respValid = false;
          while (respValid == false){
            System.out.println ("Introduzca fecha de realización de la prueba:");
            System.out.println ("(el formato es AAAA-MM-DD)");
            aux = in.nextLine();
            System.out.print("\u000C");
            if ((esFecha(aux)))
            {
                fecha = LocalDate.parse(aux);
                respValid = true;
                System.out.print("\u000C");
            }
            else{
                System.out.println ("¡Valores incorrectos!");
            }
        }
    }
    
    /**
     * Método abstracto que comprueba si dado un paciente pruede asignarse la prueba
     * @param pac - paciente al que queremos asignar la prueba
     * @return devuelve true si la prueba es asignable
     */
    public abstract boolean comprobarFechaPac(paciente pac);
    
    /**
     * Método abstracto que comprueba si un valor es un resultado de prueba válido
     */
    public abstract boolean comprobarRes(String t);
    
    /**
     * Devuelve la fecha de realización de la prueba
     * @return fecha en formato LocalDate
     */
    public LocalDate daFecha()
    {
        LocalDate date;
        date = fecha;
        return date;
    }
    
    /**
     * Método para comprobar si un valor introducido es una fecha
     * @param cadena - es el String a comprobar
     * @return devuelve true si cadena es una fecha
     */
    private boolean esFecha(String cadena)
    {
        try
        {
            LocalDate.parse(cadena);
            return true;
        } 
        catch (DateTimeParseException exception)
        {
            return false;
        }
    }
    
    /**
     * Método que extrae el número de identificación de la prueba
     * @return devuelve el número de identifiación de la prueba en formato int
     */
    public int extraeNum()
    {
        int n = num;
        return n;
    }
    
    /**
     * Método que identifica al paciente objeto de la prueba
     * @return Devuelve el paciente objeto de la prueba
     */
    public paciente extraePaciente()
    {
        paciente pc = pac;
        return pc;
    }
    
    /**
     * Método que da a la prueba un nuevo número de identificación
     * @param - número de identificación de la prueba en formato int
     */
    public void daNum(int numero)
    {
        num = numero;
    }
    
    /**
     * Método que da a la prueba un paciente objeto de la misma
     * @param - paciente objeto de la prueba
     */
    public void daPac(paciente paciente)
    {
        pac = paciente;
    }
    
    /**
     * Método que da a la prueba un enfermero que la realizará
     * @param - enfermero que realizará la prueba
     */
    public void daEnf(enfermero enfermero)
    {
        enf = enfermero;
    }
    
    /**
     * Método que da a la prueba un técnico de laboratorio encargado de analizar los resultados
     * @param - técnico de laboratorio
     */
    public void daTec(tecnico tecnico)
    {
        tec = tecnico;
    }
    
    /**
     * Método que da a la prueba un identificador de tipo de prueba
     * @param - identificador del tipo de prueba en formato String
     */
    public void daTipo(String tip)
    {
        tipo = tip;
    }
    
    /**
     * Método que devuelve el resultado de la prueba
     */
    public String extraeRes()
    {
        return res;
    }
    
    /**
     * Imprime por pantalla datos de la prueba para pacientes y enfermeros
     */
    public void imprimirDatos()
    {
        String formato = "%-25s%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,num,tipo,fecha,res,pac.daNombre());
    }
    
    /**
     * Imprime por pantalla datos de la prueba para el administrador
     */
    public void imprimirDatosComp()
    {
        String formato = "%-25s%-25s%-25s%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,num,tipo,fecha,res,pac.daDNI(),enf.daNum(),tec.daNum());
    }
    
    /**
     * Imprime por pantalla datos de la prueba referentes a un paciente en concreto
     */
    public void imprimirDatosPac()
    {
        String formato = "%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,num,tipo,fecha,res);
    }
}