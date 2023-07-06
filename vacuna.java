/**
 * Clase abstracta que engloba las vacunas de la clínica
 */
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
public abstract class vacuna
{
    // campo que contiene el número de identificación de la vacuna
    protected int num;
    // campo que contiene los listados de la clínica
    protected listado lista;
    // Nombre del tipo de vacuna
    protected String tipo;
    /**
     * Constructor de objetos de la clase vacuna
     */
    public vacuna()
    {  
    }
    
    /**
     * Método que asigna un número de identificación de la vacuna en base al resto de vacunas existentes en el stock
     */
    public void asignarNum()
    {
        num = lista.numVac()+1;
    }
    
    /**
     * Método abstracto que comprueba si es posible vacunar dadas las fechas de la primera y segunda dosis.
     */
    public abstract boolean checkAsignacion(LocalDate f1, LocalDate f2);
    
    /**
     * Método abstracto que devuelve la primera vacuna encontrada en el inventario si hay existencias
     */
    
    public abstract vacuna checkInvent(); 
    
    /**
     * Método que devuelve el número de identificación de la vacuna
     * @return devuelve el número de identificación en formato Int
     */
    public int daNum()
    {
        return num;
    }
    
    /**
     * Método abstracto que devuelve el nombre del tipo de vacuna a administrar
     * @return tipo de vacuna en formato String
     */
    public String daTipo()
    {
        return tipo;
    }
    
    /**
     * Método para comprobar si un valor introducido es una fecha
     * @param cadena es el String a comprobar
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
     * Método que lee un listado y lo almacena en el campo lista
     */
    public void leerList(listado list)
    {
        lista = list;
    }
    
    /**
     * Método que pide por pantalla la fecha de vacunación comprobando el formato hasta que introduzcamos un valor correcto
     * El formato es AAAA-MM-DD
     * @return devuelve la fecha introducida en formato LocalDate
     */
    public LocalDate pedirFecha()
    {
        LocalDate fecha = null;
        String aux, bucle;
        bucle = "";
        Scanner in = new Scanner(System.in);
        while (bucle.equals("")) {
            System.out.println ("Introduzca la fecha de vacunación:");
            System.out.println ("(el formato es AAAA-MM-DD)");
            aux = in.nextLine();
            if (esFecha(aux)){
                fecha = LocalDate.parse(aux);
                bucle = " ";
                System.out.print("\u000C");
            }
            else{
                System.out.println ("¡Valores incorrectos!");
                System.out.println ("(Pulse una tecla para continuar)");
                in.nextLine();
                System.out.print("\u000C");
            }
        }
        return fecha;
    }
    
    /**
     * Método abstracto para comprobar si la vacunación está completada con una sola dosis
     */
    public abstract boolean unadosis();
}
