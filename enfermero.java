
/**
 * Clase que describe a los enfermeros que trabajan en la clínica
 * Hereda de la clase técnico todas sus funcionalidades, las amplia gestionando la vacunación de pacientes asignados
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;
public class enfermero extends trabajador implements vacunador
{
    // ArrayList de pacientes asignados al enfermero para vacunar
    private ArrayList<paciente> pac;
    // Constante que indica el máximo de pruebas asignables por semana
    private final int NUM_PRUEBAS_ENF = 5;
    
    public enfermero() 
    {
        pac = new ArrayList<paciente>();
    }

    /**
     * Constructor de objetos parametrizado de la clase enfermero
     * @param nom - nombre del técnico 
     * @param id - DNI del técnico
     * @param phone - Número de teléfono del técnico
     * @param num - número de empresa del técnico
     * @tip - tipo de trabajador
     */
    public enfermero(String nom, String id, String phone, String num)
    {
        super(nom,id,phone,num);
        pac = new ArrayList<paciente>();
        pru = new ArrayList<prueba>();
        actualizaPac();
    }
    
    /**
     * Extrae de las pruebas asignadas los pacientes pendientes de vacunación y los almacena
     */
    private void actualizaPac()
    {
        // borra el ArrayList de pacientes pendientes de vacunación
        pac.clear();
        //recorre listado de pruebas asignadas al trabajador
        for (prueba pr : pru)
        {
            //paciente objeto de la prueba
            paciente pc = pr.extraePaciente();
            // si no está vacunado o no está inmunizado
            if (!pc.vac()||!pc.inm())
            {
                //almacenamos cada paciente una sola vez
                if (!pac.contains(pc))
                {
                    pac.add(pc);
                }         
            }
        }
    } 

    /**
     * Comprueba si una prueba es asignable al enfermero dada una fecha
     * @return devuelve true si es asignable la prueba correctamente, false si no ha sido posible
     */
    protected boolean checkPrueba(LocalDate fecha)
    {
        boolean aux = false;
        //Si dada una fecha en esa semana hay menos de 5 pruebas
        if ((numPruebas(fecha))<NUM_PRUEBAS_ENF)
        {
            aux = true;
        }
        return aux;
    }
    
    /**
     * Método que muestra por pantalla las pruebas asignadas al enfermero
     */
    public void gestionarPru()
    {
        boolean aux = true;
        String op = "";
        String texto = "";
        LocalDate fecha = null;
        Scanner in = new Scanner(System.in);
        while (!(op.equals("1"))&&!(op.equals("2")))
        {
            System.out.println ("Elija una opción:");
            System.out.println ("1. Mostrar histórico de pruebas asignadas.");
            System.out.println ("2. Mostrar pruebas asignadas por fecha.");
            op = recibe();
        }
        if (op.equals("1"))
        {
            // Mostramos por pantalla todas las pruebas
            System.out.println ("Histórico de pruebas:");
            imprimirCabPru();
            imprimeListadoPru();
            continua();
        }
        if (op.equals("2"))
        {
            while (!esFecha(texto))
            {
            System.out.println ("Introduzca fecha de realización de la prueba:");
            System.out.println ("(el formato es AAAA-MM-DD)");
            texto = in.nextLine();
            System.out.print("\u000C");
            }
            fecha = LocalDate.parse(texto);
            // Mostramos solo pruebas asignadas en la fecha elegida
            System.out.println("Pruebas asignadas en "+texto);
            imprimirCabPru();
            for (prueba p:pru)
            {
                if (p.daFecha().toString().equals(texto))
                {
                    p.imprimirDatos();
                }
            }
            continua();
        }
    }
    
    public void gestionarVac()
    {
        paciente pac = null;
        String texto = "";
        String op = "";
        while (!(op.equals("1"))&&!(op.equals("2"))&&!(op.equals("3")))
        {
            System.out.println ("Elija una opción:");
            System.out.println ("1. Mostrar pacientes pendientes de vacunación");
            System.out.println ("2. Registrar vacunación");
            System.out.println ("3. Volver");
            op = recibe();
        }
        if (op.equals("1"))
        {
            // Mostramos pacientes pendientes de vacunación
            if (!hayVacunaciones())
            {
                  System.out.println ("Ninguno de sus pacientes tiene asignadas fechas de vacunación");
                  continua();
            }
            else
            {
                // Mostramos lista de pacientes asignados para vacunación
                System.out.println ("Pacientes pendientes de vacunar:");
                imprimirCabPac();
                imprimeListadoVac();
                continua();
            }
        }
        
        if (op.equals("2"))
        {
            //Mostramos pacientes pendientes de vacunación hoy
            if (!hayVacunacionesHoy())
            {
                  System.out.println ("Ninguno de sus pacientes tiene asignadas fechas de vacunación hoy");
                  continua();
            }
            else
            {
                // Mostramos lista de pacientes asignados para vacunación hoy
                System.out.println ("Pacientes asignados para vacunación a fecha de " + LocalDate.now().toString());
                imprimirCabPac();
                imprimeListadoVacHoy();
                System.out.println ("Introduzca el DNI del paciente a vacunar:");
                texto = recibe();
                // Asignamos el paciente cuyo DNI coincide con el introducido
                pac = buscaPac(texto);
                // Si el valor introducido no se corresponde volvemos a pedirlo
                while ((pac == null)&&(!texto.equals("v")))
                {
                    System.out.println ("DNI desconocido, vuelva a intentarlo o escriba 'v' para volver:");
                    imprimirCabPac();
                    imprimeListadoVacHoy();
                    texto = recibe();
                    pac = buscaPac(texto);
                }
                if (!texto.equals("volver"))
                {
                    if (pac.daFecha1()==null)
                    {
                        System.out.println("Acción imposible. Ese paciente no tiene vacunación asignada.");
                        continua();
                    }
                    else
                    {
                        // Si se trata de la segunda dosis
                        if ((pac.daFecha1()==null)&&(!(pac.daFecha2()==null)))
                        {
                            //El paciente queda inmunizado
                            if (pac.daFecha2().isEqual(LocalDate.now()))
                            {
                                System.out.println ("La dosis a administrar es de " + pac.daVacuna().daTipo());
                                continua();
                                pac.vacunar();
                                pac.inmunizar();
                                pac.asignaFecha2(null);
                                System.out.println ("Vacunación registrada con éxito.");
                                System.out.println ("El paciente está ahora inmunizado.");
                                continua();
                            }
                            else
                            {
                                System.out.println ("El paciente seleccionado no tiene cita para hoy.");
                            }
                        }
                        // Si se trata de la primera dosis
                        if (!(pac.daFecha1()==null))
                        {
                            // Si solo es una dosis
                            if (pac.daFecha2()==null)
                            {
                                if (pac.daFecha1().isEqual(LocalDate.now()))
                                {
                                    System.out.println ("La dosis a administrar es de " + pac.daVacuna().daTipo());
                                    continua();
                                    pac.vacunar();
                                    pac.inmunizar();
                                    pac.asignaFecha1(null);
                                    System.out.println ("Vacunación registrada con éxito.");
                                    System.out.println ("El paciente está ahora inmunizado.");
                                    continua();
                                }
                                else
                                {
                                    System.out.println ("El paciente seleccionado no tiene cita para hoy.");
                                    continua();
                                }
                            }
                            // Si son dos dosis
                            else
                            {
                                if (pac.daFecha1().isEqual(LocalDate.now()))
                                {
                                    System.out.println ("La dosis a administrar es de " + pac.daVacuna().daTipo());
                                    continua();
                                    pac.vacunar();
                                    pac.asignaFecha1(null);
                                    System.out.println ("Vacunación registrada con éxito.");
                                    System.out.println ("Se le administrará la próxima dosis en " + pac.daFecha2().toString());
                                    continua();
                                }
                                else
                                {
                                    System.out.println ("El paciente seleccionado no tiene cita para hoy.");
                                    continua();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Imprime por pantalla un menú de opciones para el enfermero
     */
    public void imprimeMenuTra()
    {
        {
        System.out.println ("Seleccione una opción:          ");
        System.out.println ("1 Listado de pacientes asignados");
        System.out.println ("2 Buscar Paciente               ");
        System.out.println ("3 Pruebas asignadas             ");
        System.out.println ("4 Vacunar pacientes             ");
        System.out.println ("5 Volver                        ");
    }
    }
    
    /**
     * Método que responde si el enfermero tiene o no vacunaciones asignadas
     * @return devuelve true si hay vacunaciones asignadas
     */
    
    protected boolean hayVacunaciones()
    {
        boolean aux = false;
        for (prueba p: pru)
        {
            if (p.extraePaciente().asigVac())
            {
                aux = true;
            }
        }
        return aux;
    }
    
    /**
     * Método que responde si el enfermero tiene o no vacunaciones asignadas hoy
     * @return devuelve true si hay vacunaciones asignadas
     */
    
    private boolean hayVacunacionesHoy()
    {
        boolean aux = false;
        for (prueba p: pru)
        {
            if (p.extraePaciente().asigVac())
            {
                // Si cualquiera de las dos fechas de vacunación concide con la fecha de hoy
                if (!(p.extraePaciente().daFecha1().isEqual(LocalDate.now())))
                {
                    if (!(p.extraePaciente().daFecha2()==null))
                    {
                        if (p.extraePaciente().daFecha2().isEqual(LocalDate.now()))
                        {
                            aux=true;
                        }
                    }
                }
                else
                {
                    aux=true;
                }
            }
        }
        return aux;
    }
    
    /**
     * Imprime por pantalla información de un paciente asignado
     */
    protected void imprimeInfoPac(paciente pac)
    {
        // Datos personales
        pac.imprimirDatos();
        if (pac.inm())
        {
            System.out.printf("%-25s","Sí");
        }
        else
        {
            System.out.printf("%-25s","No");
        }
        System.out.printf("%n","");
    }
    
    /**
     * Imprime por pantalla un listado de pacientes (información ampliada) asignados al enfermero extraidos de las pruebas asignadas
     */
    protected void imprimeListadoPac()
    {
        imprimirCabPac();
        actualizaPac();
        ArrayList<paciente> aux = new ArrayList<paciente>();
        for (prueba p: pru)
        {
            if (!aux.contains(p.extraePaciente()))
            {
                imprimeInfoPac(p.extraePaciente());
            }
            // Nos aseguramos de que cada paciente se imprima una sola vez
            aux.add(p.extraePaciente());
        }
    }
    
    /**
     * Imprime por pantalla un listado de los pacientes asignados al enfermero pendientes de vacunación
     */
    private void imprimeListadoVac()
    {
        actualizaPac();
        for (paciente p: pac)
        {
            if (p.asigVac())
            {
                imprimeInfoPac(p);
            }
        }
    }
    
    /**
     * Imprime por pantalla un listado de los pacientes asignados al enfermero pendientes de vacunación hoy
     */
    private void imprimeListadoVacHoy()
    {
        actualizaPac();
        for (paciente p: pac)
        {
            if (p.asigVac())
            {
                if ((p.daFecha1().isEqual(LocalDate.now())) || (p.daFecha2().isEqual(LocalDate.now())))
                {
                    imprimeInfoPac(p);
                }
            }
        }
        
    }
    
    /**
     * Método que imprime por pantalla la cabecera del listado de pacientes del enfermero
     */
    protected void imprimirCabPac()
    {
        String formato = "%-25s%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,"Nombre","DNI","Teléfono", "Edad", "Inmunizado");
    }
    
    /**
     * Muestra lista de pacientes asignados
     */
    public void mostrarList()
    {
        System.out.println ("Pacientes asignados:");
        imprimeListadoPac();
        continua();   
    }
    
    /**
     * Método que recibe por teclado una opción del menú del enfermero y limpia la consola
     */
    public String recibeOp()
    {
        String op = "";
        boolean respValid = false;
        while (respValid == false){
            op = recibe();
            if ((op.equals("1")||(op.equals("2"))||(op.equals("3"))||(op.equals("4"))||(op.equals("5")))){
                respValid = true;
            }
            else{
                System.out.print("\u000C");
                System.out.println ("Seleccione una entrada del menú:");
                imprimeMenuTra();
            }
        }
        System.out.print("\u000C");
        return op;
    }
}
