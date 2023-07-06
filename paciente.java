/**
 * Clase específica para los pacientes de la clínica, extiende a la clase persona
 * 
 * @author Daniel Rodríguez González
 * @version (a version number or a date)
 */

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
public class paciente extends persona
{
    // ArrayList de pruebas realizadas
    private ArrayList<prueba> pruebas;
    // Vacuna asignada al paciente
    private vacuna vacuna;
    // Fecha de la primera dosis asignada al paciente
    protected LocalDate primera;
    // Fecha de la segunda dosis asignada al paciente
    protected LocalDate segunda;
    // edad del paciente
    private int edad;
    // indica si el paciente está vacunado
    private boolean vacunado;
    // indica si el paciente está inmunizado
    private boolean inmunizado;
    // indica si el paciente está confinado
    private boolean confinado;
    // indica si el paciente está contagiado
    private boolean contagiado;
    /**
     * Constructor de objetos de la clase paciente
     */
    public paciente()
    {
        primera = null;
        segunda = null;
        vacunado = false;
        inmunizado = false;
        confinado = false;
        contagiado = false;
        pruebas = new ArrayList<prueba>();
    }
    
    /**
     * Constructor de objetos parametrizado de la clase paciente
     * @param nom - nombre del paciente
     * @param id - DNI del paciente
     * @param phone - número de teléfono del paciente
     * @param age - edad del paciente
     * @param cont - indica si el paciente está o no contagiado
     * @param conf - indica si el paciente está o no confinado
     */
    public paciente(String nom,String id, String phone, int age,boolean vac,boolean inm, boolean cont, boolean conf)
    {
        super(nom,id,phone);
        edad = age;
        vacunado = vac;
        inmunizado = inm;
        contagiado = cont;
        confinado = conf;
        pruebas = new ArrayList<prueba>();
    }
    
    /**
     * Asigna una primera fecha de vacunación a un paciente
     * @param fecha - fecha de primera dosis de vacuna
     */
    protected void asignaFecha1(LocalDate fecha)
    {
        primera = fecha;
    }
    
    /**
     * Asigna una segunda fecha de vacunación a un paciente
     * @param fecha - fecha de primera dosis de vacuna
     */
    protected void asignaFecha2(LocalDate fecha)
    {
        segunda = fecha;
    }
    
    /**
     * Asigna una prueba al paciente
     * @param pru - prueba que se va a asignar al paciente
     */
    protected void asignaPrueba(prueba pru)
    {
        pruebas.add(pru);
    }
    
    /**
     * Asigna una vacuna a un paciente
     * @param vacuna - vacuna que será asignada al paciente
     */
    protected void asignaVacuna(vacuna vac)
    {
        vacuna = vac;
    }
    
    /**
     * Nos informa si un paciente tiene asignadas fechas de vacunación
     * @return devuelve true si el paciente ya ha sido citado para vacunación
     */
    public boolean asigVac()
    {
        boolean bool = false;
        if (!(primera==null))
        {
            bool = true;
        }
        return bool;
    }
    
    /**
     * Pone en confinamiento al paciente
     */
    protected void confinar()
    {
        confinado = true;
    }
    
    /**
     * Nos informa si un paciente está confinado
     * @return devuelve true si está confinado
     */
    public boolean conf()
    {
        boolean bool = false;
        if (confinado)
        {
            bool = true;
        }
        return bool;
    }
    
    /**
     * Nos informa si un paciente está contagiado
     * @return devuelve true si está contagiado
     */
    public boolean cont()
    {
        boolean bool = false;
    
        if (contagiado){
            bool = true;
        }
        return bool;
    }
    
    /**
     * Señala a un paciente como contagiado
     */
    protected void contagio()
    {
        contagiado = true;
    }
    
    /**
     * Devuelve la edad del paciente
     * @return edad del paciente en formato String
     */
    
    public int daEdad()
    {
        int age = edad;
        return age;
    }
    
    /**
     * Devuelve la primera fecha de vacunación del paciente
     * @return primera fecha de vacunación del paciente en formato LocalDate, null si no está asignada.
     */
    
    public LocalDate daFecha1()
    {
        return primera;
    }
    
    /**
     * Devuelve la segunda fecha de vacunación del paciente
     * @return segunda fecha de vacunación del paciente en formato LocalDate, null si no está asignada.
     */
    
    public LocalDate daFecha2()
    {
        return segunda;
    }
    
    /**
     * Devuelve la fecha de inicio del confinamiento
     * @Return fecha de inicio del confinamiento en formato LocalDate, corresponde a la última fecha de prueba PCR o de Antígenos si el paciente está confinado.
     */
    public LocalDate daFechaConf()
    {
        LocalDate fechaux = LocalDate.parse("1900-01-01");
        for (prueba p: pruebas)
        {
            // Solo confinamos al paciente tras un positivo en PCR o antígenos
            if ((p.daFecha().isAfter(fechaux)) && ((p instanceof pcr)||(p instanceof antigenos)))
            {
                fechaux = p.daFecha();
            }
        }
        return fechaux;
    }
    
    /**
     * Devuelve la lista de pruebas del paciente
     * @Return lista de pruebas del paciente en formato ArrayList
     */
    
    protected ArrayList<prueba> daPruebas()
    {
        return pruebas;
    }
    
    /**
     * Devuelve la vacuna asignada al paciente
     * @return vacuna asignada al paciente
     */
    
    protected vacuna daVacuna()
    {
        return vacuna;
    }
    
    /**
     * Saca del confinamiento a un paciente
     */
    protected void desconfinar()
    {
        confinado = false;
    }
    
    /**
     * Imprime solo datos personales del paciente por pantalla
     * 
     */
    public void imprimirDatos()
    {
        super.imprimirDatos();
        //edad en formato String
        String aux = String.valueOf(edad);
        String formato = "%-25s";
        System.out.printf(formato,aux);
    }
    
    /**
     * Nos informa si un paciente está inmunizado
     * @return devuelve true si está inmunizado
     */
    public boolean inm()
    {
        boolean bool = false;
        if (inmunizado){
            bool = true;
        }
        return bool;
    }
    
    /**
     * Inmuniza a un paciente
     */
    protected void inmunizar()
    {
        inmunizado = true;
    }
    
    /**
     * Método que recibe por teclado datos del paciente
     */
    protected void insertDatos ()
    {
        String aux;
        // el método invoca al de la superclase
        super.insertDatos();
        Scanner in = new Scanner(System.in);
        System.out.println ("Introduzca la edad:");
        aux = in.nextLine();
        while (!esNumero(aux))
        {
            System.out.print("\u000C");
            System.out.println ("Valores incorrectos. Debe introducir números.");
            System.out.println ("Introduzca la edad:");
            aux = in.nextLine();
        }
        System.out.print("\u000C");
        edad = Integer.parseInt(aux);
        System.out.println ("Paciente registrado correctamente.");
        System.out.println ("(Pulse una tecla para continuar)");
        in.nextLine();
        System.out.print("\u000C");
    }
    
    /**
     * Muestra por pantalla las pruebas del paciente
     */
    public void mostrarPruebas()
    {
        System.out.println ("Pruebas del paciente:");
        System.out.printf("%-25s%-25s%-25s%-25s%n","Referencia","Tipo","Fecha","Resultado");
        for (prueba p: pruebas)
        {
            p.imprimirDatosPac();
        }
    }
    
    /**
     * Señala a un paciente como no contagiado
     */
    protected void nocontagio()
    {
        contagiado = false;
    }
    
    /**
     * Nos informa si un paciente está vacunado
     * @return devuelve true si está vacunado
     */
    public boolean vac()
    {
        boolean bool = false;
        if (vacunado)
        {
            bool = true;
        }
        return bool;
    }
    
    /**
     * Vacuna a un paciente
     */
    protected void vacunar()
    {
        vacunado = true;
    }
    }

