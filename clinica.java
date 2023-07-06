
/**
 * Clase principal del proyecto clinica.
 * Implementa una sistema integrado de gestión de una clínica de COVID-19
 * @author Daniel Rodríguez González
 * @version (a version number or a date)
 */
import java.util.Scanner;
public class clinica
{

/**
* Constructor de objetos de la clase clinica
*/

public clinica(){}

public static void main ()
{
    // Listado de empleados, pruebas y pacientes por defecto
    listado lista = new listado();
    //Opciones elegidas por teclado
    String opcion = "";
    String opcion2 = "";
    //Sanitarios y administradores que inician la aplicación
    administrador admin = null;
    enfermero enf = null;
    tecnico tec = null;
    //Variables auxiliares
    boolean aux; 
    String num;

    //Lanza pantalla de bienvenida
    System.out.print("\u000C");
    imprimeIntro();
    continua();
    while (!opcion.equals("4"))
    {
        aux = true;
        num = "";
        imprimeMenu();
        // Pide seleccionar una opción del menú principal
        opcion = recibeOp();
        //Entramos en menú de Administrador
        if (opcion.equals("1")) 
        {
            while (aux)
            {
                // pedimos número de empresa
                System.out.println ("Introduzca su número de empresa:");
                num = recibe();
                System.out.print("\u000C");
                if (num.equals("s"))
                {
                    break;
                }
                // Buscamos al administrador en la lista y le asignamos al campo admin
                admin = lista.buscaAdm(num);
                if (admin != null)
                {
                    aux = false;
                }
                else
                {
                    System.out.println ("Usuario no encontrado en el sistema.");
                    System.out.println ("Vuelva a intentarlo (o pulse s para salir).");
                }
            }
            
            // Menú específico del administrador
            opcion2 = "";
            while ((!aux) && (!opcion2.equals("9")))
            {
                admin.imprimeMenuAdmin();
                opcion2 = admin.recibeOp();
                if (opcion2.equals("1"))
                // Mostramos por pantalla pacientes , empleados y pruebas de la clínica
                {
                    admin.mostrarList();
                }
                
                if (opcion2.equals("2"))
                // Buscamos personas por DNI
                {
                    admin.buscarPer();
                }
                // Registrar un nuevo paciente o eliminar uno existente
                if (opcion2.equals("3"))
                {
                    admin.gestionaPac();
                }
                // Registrar un nuevo trabajador o eliminar uno existente
                if (opcion2.equals("4"))
                {
                    admin.gestionaTra();
                }
                // Asignar nueva prueba
                if (opcion2.equals("5"))
                {
                    admin.asignarPru();
                }
                // Asignar vacunación
                if (opcion2.equals("6"))
                {
                    admin.vacunar();
                }
                // Actualizar el stock de vacunas
                if (opcion2.equals("7"))
                {
                    admin.actualizarStock();
                }
                // Gestión de confinamientos
                if (opcion2.equals("8"))
                {
                    admin.gestionarConf();
                }
            }
        }
        //Entramos en menú de Enfermero
        if (opcion.equals("2")) 
        {
            while (aux)
            {
                // pedimos número de empresa
                System.out.println ("Introduzca su número de empresa:");
                num = recibe();
                System.out.print("\u000C");
                if (num.equals("s"))
                {
                    break;
                }
                // Buscamos al usuario en la lista de enfermeros y le asignamos al campo usuario
                enf = lista.buscaEnf(num);
                if (enf != null)
                {
                    aux = false;
                }
                else
                {
                    System.out.println ("Usuario no encontrado en el sistema.");
                    System.out.println ("Vuelva a intentarlo (o pulse s para salir).");
                }
            }
            
            // Menú específico del enfermero
            opcion2 = "";
            while ((!aux) && (!opcion2.equals("5")))
                {
                enf.imprimeMenuTra();
                opcion2 = enf.recibeOp();
                if (opcion2.equals("1"))
                // Mostramos por pantalla pacientes asignados
                {
                    enf.mostrarList();                
                }
            
                if (opcion2.equals("2"))
                // Búsqueda de pacientes por DNI
                {
                    enf.buscarPac();
                }
                
                if (opcion2.equals("3"))
                // Mostramos por pantalla pruebas asignadas
                {
                    enf.gestionarPru();
                }
                
                if (opcion2.equals("4"))
                {
                    enf.gestionarVac();
                }
            }
        }    
        //Entramos en menú de Técnico
        if (opcion.equals("3")) 
        {
            while (aux)
            {
                // pedimos número de empresa
                System.out.println ("Introduzca su número de empresa:");
                num = recibe();
                System.out.print("\u000C");
                if (num.equals("s"))
                {
                    break;
                }
                // Buscamos al usuario en la lista de técnicos y le asignamos al campo usuario
                tec = lista.buscaTec(num);
                if (tec != null)
                {
                    aux = false;
                }
                else
                {
                    System.out.println ("Usuario no encontrado en el sistema.");
                    System.out.println ("Vuelva a intentarlo (o pulse s para salir).");
                }
            }
            // Menú específico del técnico de laboratorio
            opcion2 = "";
            while ((!aux) && (!opcion2.equals("4")))
                {
                tec.imprimeMenuTra();
                opcion2 = tec.recibeOp();
                if (opcion2.equals("1"))
                // Mostramos por pantalla pacientes asignados
                {
                    tec.mostrarList();            
                }
            
                if (opcion2.equals("2"))
                // Búsqueda de pacientes por DNI
                {
                    tec.buscarPac();
                }   
                
                if (opcion2.equals("3"))
                {
                    tec.gestionarPru();
                }
            }
        }
    }
    //Salimos de la aplicación
        System.out.print("\u000C");
        imprimeSalida();
}

    /**
     * Imprime en la consola la pantalla de inicio
     */
    private static void imprimeIntro()
    {
        System.out.println ("********************************");
        System.out.println ("*                              *");
        System.out.println ("*   Bienvenido a la clínica    *");
        System.out.println ("*           Covid              *");
        System.out.println ("*                              *");
        System.out.println ("********************************");

    }
      
    /**
     * Imprime en la consola la pantalla del menú inicial
     */
    private static void imprimeMenu()
    {
        System.out.println ("Por favor, elija una opción de acceso:    ");
        System.out.println ("                                ");
        System.out.println ("1 Administrador                 ");
        System.out.println ("2 Enfermero                     ");
        System.out.println ("3 Técnico de laboratorio        ");
        System.out.println ("4 Salir                         ");
        System.out.println ("                                ");
    }
    
    /**
     * Imprime en la consola la pantalla la despedida
     */
    private static void imprimeSalida()
    {
        System.out.println ("********************************");
        System.out.println ("*                              *");
        System.out.println ("*                              *");
        System.out.println ("*        ¡Hasta pronto!        *");
        System.out.println ("*                              *");
        System.out.println ("*                              *");
        System.out.println ("********************************");  
    }
    
    /**
     * Recibe por teclado una opción del menú y limpia la consola
     * @return devuelve la opción del menú introducida por teclado en formato String
     */
    private static String recibeOp()
    {
        String op = "";
        boolean respValid = false;
        while (respValid == false){
            op = recibe();
            if ((op.equals("1")||(op.equals("2"))||(op.equals("3"))||(op.equals("4")))){
                respValid = true;
            }
            else{
                System.out.print("\u000C");
                imprimeMenu();
            }
        }
        System.out.print("\u000C");
        return op;
    }
    

    /**
     * recibe por teclado una cadena de caracteres
     * @return devuelve la cadena de caracteres introducida por teclado en formato String
     */
    private static String recibe()
    {
        Scanner in = new Scanner(System.in);
        String texto = in.next();
        System.out.print("\u000C");
        return texto;
    }
    
    /**
     * Pide pulsar una tecla para continuar la ejecución
     */
    
    private static void continua()
    {
        System.out.println ("(Pulse una tecla para continuar)");
        Scanner in = new Scanner(System.in);
        in.nextLine();
        System.out.print("\u000C");
    }
    
    /**
     * Método para comprobar si un valor introducido es un número
     * @param cadena - es el String a comprobar
     * @return devuelve true si cadena es un número
     */
    private static boolean esNumero(String cadena)
    {
        try 
        {
        Integer.parseInt(cadena);
        return true;
        } 
        catch (NumberFormatException nfe)
        {
        return false;
        }
    }
    } 
    

