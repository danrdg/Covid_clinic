
/**
 * Clase específica para los trabajadoresde la clínica, extiende a la clase persona
 * 
 * @author Daniel Rodríguez González
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.util.Locale;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
public abstract class trabajador extends persona 
{
    // el campo num contiene el número de empresa del trabajador
    private String numero;
    // ArrayList de pruebas asignadas al trabajador 
    protected ArrayList<prueba> pru;
    
    public trabajador()
    {}

    /**
     * Constructor de objetos parametrizado de la clase trabajador
     * @param nom - nombre del trabajador 
     * @param id - DNI del trabajador
     * @param phone - Número de teléfono del trabajador
     * @param num - número de empresa del trabajador
     */
    public trabajador(String nom, String id, String phone, String num)
    {
        //el constructor invoca al de la superclase
        super(nom,id,phone);
        numero = num;
        pru = new ArrayList<prueba>();
    }
    
    /**
     * Actualiza el resultado de una prueba seleccionada
     * @param n - número de identificación de la prueba
     * @param res - resultado de la prueba
     */
    protected void actPrueba(int n,String res)
    {
        for (prueba p:pru)
        {
            if (n==(p.extraeNum()))
            {
                p.actualizaRes(res);
                break;
            }
        }
    }
    
    /**
     * Asigna una prueba al trabajador
     */
    protected void addPrueba(prueba prueba)
    {
        pru.add(prueba);
    }
    
    /**
     * Asigna un nuevo número de empresa al trabajador en formato String
     * @param número entero a asignar al administrador
     */
    protected void asignaNum(int num)
    {
        numero =(String.valueOf(num));
    }
    
    /**
     * Buscamos un paciente asignado al trabajador analizando el ArrayList de pruebas asignadas
     * @return devuelve el paciente buscado, null si no lo encuentra
     * @param id - DNI del paciente buscado
     */
    protected paciente buscaPac(String id)
    {
        paciente aux = null;
        for (prueba p: pru)
        {
            if (p.extraePaciente().daDNI().equals(id))
            {
                aux = p.extraePaciente();
            }
        }
        return aux;
    }
    
    /**
     * Muestra por pantalla datos de un paciente asignado al trabajador tras pedir por teclado su DNI
     */
    
    public void buscarPac()
    {
        String texto = null;
        System.out.println ("Introduzca el DNI del paciente:");
        texto = recibe();
        // Asignamos el paciente cuyo DNI coincide con el introducido
        paciente pac = buscaPac(texto);
        // Si el valor introducido no se corresponde volvemos a pedirlo
        while ((pac == null)&&(!texto.equals("volver")))
        {
            System.out.println ("DNI desconocido, vuelva a intentarlo o escriba 'volver' para retroceder:");
            texto = recibe();
        }
        // Imprimimos datos del paciente buscado
        if (!texto.equals("volver"))
        {
            System.out.print("\u000C");
            System.out.println ("Datos del paciente:");
            imprimirCabPac();
            imprimeInfoPac(pac);
            pac.mostrarPruebas();
            continua();
                    }
        System.out.print("\u000C");
    }
    /**
     * Comprueba si una prueba es asignable al trabajador dada una fecha
     */
    
    protected abstract boolean checkPrueba(LocalDate fecha);
    
    /**
     * Comprueba si un número se corresponde con el de identificación de una prueba asignada y si la fecha de la misma es el día actual
     * @param n - número a comprobar
     * @return - devuelve la prueba asignada correspondiente o null si no existe
     */
    protected prueba comPrueba(int n)
    {
        for (prueba p:pru)
        {
            if ((n==(p.extraeNum()))&&(p.daFecha().isEqual(LocalDate.now())))
            {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Devuelve el número de empresa del trabajador en formato String
     */
    public String daNum()
    {
        return numero;
    }
    
    /**
     * Elimina la primera prueba asignada al trabajador
     */
    protected void delPrimPrueba()
    {
        pru.remove(0);
    }
    
    /**
     * Elimina una prueba del trabajador
     */
    protected void eliminaPrueba(prueba prueba)
    {
        pru.remove(prueba);
    }
    
    /**
     * Extrae la primera prueba asignada al trabajador
     * @return devuelve la primera prueba asignada al trabajador
     */
    protected prueba getPrimPrueba()
    {
        return pru.get(0);
    }
    
    /**
     * Método abstracto que gestiona las pruebas asignadas al trabajador
     */
    public abstract void gestionarPru();
    
    /**
     * Método abstracto destinado a imprimir por pantalla la cabecera del listado de pacientes de un trabajador, será diferente para cada tipo de sanitario
    */
    protected abstract void imprimirCabPac();

    /**
     * Imprime cabecera de listados de pruebas asignadas por pantalla
     */
    protected void imprimirCabPru()
    {
        String formato = "%-25s%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,"Número","Tipo","Fecha","Resultado","Paciente");
    }
    
    /**
     * Imprime datos del trabajador por pantalla
     * 
     */
    protected void imprimirDatos()
    {
        super.imprimirDatos();
        String formato = "%-25s%n";
        System.out.printf(formato,numero);
    }
    
    /**
     * Método abstracto destinado a imprimir por pantalla información relevante de un paciente asignado según el tipo de sanitario que acceda a la misma
     */
    
    protected abstract void imprimeInfoPac(paciente pac);
    
    /**
     * Imprimimos por pantalla un listado de pruebas asignadas al trabajador
     */
    protected void imprimeListadoPru()
    {
        for (prueba p: pru)
        {
            p.imprimirDatos();
        }
    }
    
    /**
     * Método abstracto destinado a imprimir por pantalla un listado de pacientes asignados a un trabajador, será diferente para cada tipo de sanitario
     */
    
    protected abstract void imprimeListadoPac();
    
    /**
     * Método abstracto destinado a imprimir por pantalla un menú de opciones para el trabajador, será diferente para cada tipo de sanitario
     */
    protected abstract void imprimeMenuTra();
    
    /**
     * Método abstracto destinado a mostrar listados propios de un trabajador
     */
    public abstract void mostrarList();
    
    /**
     * Comprueba el número de pruebas asignadas en una semana dada una fecha
     * @param - fecha en formato LocalDate sobre la que se hará el recuento de pruebas
     * @return un entero igual al número de pruebas asignadas en esa semana
     */
    protected int numPruebas(LocalDate fecha)
    {
        int cont = 0;
        int semana = fecha.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        // obtenemos la semana del año de la fecha dada
        for (prueba p:pru)
        {
            //si la prueba está programada para esa misma semana se cuenta
            if (p.daFecha().get(ChronoField.ALIGNED_WEEK_OF_YEAR)==semana)
            {
                cont++;
            }
        }
        return cont;
    }
    
    /**
     * Método abstracto destinado a recibir por teclado una opción del menú del trabajador y limpiar la consola
     */
    protected abstract String recibeOp();
    
}
