
/**
 * Clase que va a contener información sobre todas las personas que visiten
 * la clínica
 * @author Daniel Rodríguez González 
 */
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public abstract class persona
{
    // Nombre de la persona
    protected String nombre;
    // DNI de la persona
    protected String dni;
    // Teléfono de la persona
    protected String tfno;
    /**
     * Constructor de objetos de la clase persona
     */
    public persona()
    {}
    
    /**
     * Constructor parametrizado para los objetos de la clase persona
     * @param nom - nombre de la persona 
     * @param id - DNI de la persona
     * @param phone - Número de teléfono de la persona
     */
    public persona(String nom,String id, String phone)
    {
        nombre = nom;
        dni = id;
        tfno = phone;
    }
    
    /**
     * Pide pulsar una tecla para continuar la ejecución
     */
    
    protected void continua()
    {
        System.out.println ("(Pulse una tecla para continuar)");
        Scanner in = new Scanner(System.in);
        in.nextLine();
        System.out.print("\u000C");
    }
    
    /**
     * @return Devuelve el DNI de la persona referida
     */
    protected String daDNI()
    {
        String id;
        id = dni;
        return id;
    }
    
    /**
     * @return Devuelve el nombre de la persona referida
     */
    protected String daNombre()
    {
        String nom;
        nom = nombre;
        return nom;
    }
    
    /**
     * Método para comprobar si un valor introducido es una fecha
     * @param cadena - es el String a comprobar
     * @return devuelve true si cadena es una fecha
     */
    protected boolean esFecha(String cadena)
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
     * Método que comprueba si una fecha es anterior a la actual
     * @return devuelve true si la fecha a comprobar es presente o futura
     * @param fecha - fecha a comprobar en formato LocalDate
     */
    protected boolean esFechaFutura(LocalDate fecha)
    {
        boolean aux = false;
        if (fecha.isEqual(LocalDate.now())||(fecha.isAfter(LocalDate.now())))
        {
            aux = true;
        }
        return aux;   
    }
    
    /**
     * Método para comprobar si un valor introducido es un Dni
     * @param cadena es el String a comprobar
     * @return devuelve true si cadena es un DNI (
     */
    private boolean esDni(String cadena)
    {
        boolean aux =  false;
        int cont = 0;
        int longitud = cadena.length();
        if (longitud == 9)
        {
            aux = true;
            for (char c : cadena.toCharArray()) 
            {
                cont++;
                if (cont<9)
                {
                    // Si alguno de los 8 primeros caracteres no es un número devuelve falso
                    if (!Character.isDigit(c)) 
                    {
                        aux = false;
                        break;
                    }
                }
                //Si el último caracter no es una letra devuelve falso
                if (cont==9)
                {
                    if (!Character.isLetter(c))
                    {
                        aux = false;
                        break;
                    }
                }
            }
        }
        return aux;
    }
    
    /**
     * Método para comprobar si un valor introducido es un nombre
     * @param cadena es el String a comprobar
     * @return devuelve true si cadena no contiene números ni caracteres especiales
     */
    private boolean esNombre(String cadena)
    {
        boolean aux =  true;
        for (char c : cadena.toCharArray()) 
        {
            // Si algún caracter no es una letra o un espacio devuelve falso
            if (!(Character.isLetter(c)||Character.isSpace(c))) 
            {
                aux = false;
                break;
            } 
        }
        return aux;
    }
    
    /**
     * Método para comprobar si un valor introducido es un número
     * @param cadena es el String a comprobar
     * @return devuelve true si cadena es un número
     */
    protected boolean esNumero(String cadena)
    {
        boolean aux =  true;
        for (char c : cadena.toCharArray()) 
        {
            // Si algún caracter no es un número devuelve falso
            if (!(Character.isDigit(c))) 
            {
                aux = false;
                break;
            } 
        }
        return aux;
    }
    
    /**
     * Método para comprobar si un valor introducido es un telefono
     * @param cadena es el String a comprobar
     * @return devuelve true si cadena es un número de teléfono (9 dígitos)
     */
    private boolean esTelefono(String cadena)
    {
        int longitud = cadena.length();
        boolean aux = false;
        if (longitud == 9)
        {
            aux = true;
            for (char c : cadena.toCharArray()) 
            {
                // Si algún caracter no es un número devuelve falso
                if (!(Character.isDigit(c))) 
                {
                    aux = false;
                    break;
                } 
            }
        }
        return aux;
    }

    /**
     * Imprime datos de la persona por pantalla
     */
    protected void imprimirDatos()
    {
        String formato = "%-25s%-25s%-25s";
        System.out.printf (formato,nombre,dni,tfno);
    }
    
    /**
     * Método que asigna datos personales a la persona
     * 
     * @param  recibe por teclado como cadenas el nombre, dni y número de teléfono
     */
    protected void insertDatos ()
    {
        String aux = "";
        Scanner in = new Scanner(System.in);
        // Nombre y apellidos y su comprobación
        System.out.println ("Introduzca nombre y apellidos:");
        aux = in.nextLine();
        while (!esNombre(aux))
        {
             System.out.print("\u000C");
            System.out.println ("Valores incorrectos. Debe introducir letras.");
            System.out.println ("Introduzca nombre y apellidos:");
            aux = in.nextLine();
        }
        nombre = aux;
        System.out.print("\u000C");
        // DNI y su comprobación
        System.out.println ("Introduzca DNI:");
        aux = in.nextLine();
        while (!esDni(aux))
        {
             System.out.print("\u000C");
            System.out.println ("Valores incorrectos. Debe introducir un DNI.");
            System.out.println ("Introduzca DNI:");
            aux = in.nextLine();
        }
        dni = aux;
        System.out.print("\u000C");
        // Número de teléfono y su comprobación
        System.out.println ("Introduzca teléfono:");
        aux = in.nextLine();
        while (!esTelefono(aux))
        {
            System.out.print("\u000C");
            System.out.println ("Valores incorrectos. Debe introducir un número de teléfono.");
            System.out.println ("Introduzca teléfono:");
            aux = in.nextLine();
        }
        tfno = aux;
        System.out.print("\u000C");
    }
    
    /**
     * recibe por teclado una cadena de caracteres
     * @return devuelve la cadena de caracteres introducida por teclado
     */
    protected String recibe()
    {
        Scanner in = new Scanner(System.in);
        String texto = in.next();
        System.out.print("\u000C");
        return texto;
    }
    
}
