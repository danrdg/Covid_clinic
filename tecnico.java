/**
 * Clase que describe a los técnicos de laboratorio que trabajan en la clínica
 * Hereda de la clase trabajador
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.time.LocalDate;
public class tecnico extends trabajador
{
    // Constante que indica el máximo de pruebas asignables por semana a técnicos
    private final int NUM_PRUEBAS_TEC = 4;

    public tecnico()
    {}

    /**
     * Constructor de objetos parametrizado de la clase tecnico
     * @param nom - nombre del técnico 
     * @param id - DNI del técnico
     * @param phone - Número de teléfono del técnico
     * @param num - número de empresa del técnico
     * @tip - tipo de trabajador
     */
    public tecnico(String nom, String id, String phone, String num)
    {
        super(nom,id,phone,num);
    }
    
    /**
     * Comprueba si una prueba es asignable al técnico dada una fecha
     * @return devuelve true si es asignable la prueba correctamente, false si no ha sido posible
     */
    protected boolean checkPrueba(LocalDate fecha)
    {
        boolean aux = false;
        //Si dada una fecha en esa semana hay menos de 4 pruebas
        if ((numPruebas(fecha))<NUM_PRUEBAS_TEC)
        {
            aux = true;
        }
        return aux;
    }
    
    /**
     * Método que muestra por pantalla las pruebas asignadas al técnico y permite actualizar los resultados de las mismas
     */
    public void gestionarPru()
    {
        String texto = "";
        String texto2 = "";
        prueba prueb = null;
        String op="";
        ArrayList pruebashoy=new ArrayList<prueba>();
        while (!(op.equals("1"))&&!(op.equals("2"))&&!(op.equals("3")))
        {
            System.out.println ("Elija una opción:");
            System.out.println ("1. Mostrar histórico de pruebas asignadas");
            System.out.println ("2. Actualizar resultados de pruebas pendientes");
            System.out.println ("3. Volver");
            op = recibe();
        }
        if (op.equals("1"))
        {
            // Mostramos por pantalla pruebas asignadas
            System.out.println ("Pruebas asignadas:");
            imprimirCabPru();
            imprimeListadoPru();
            continua();
        }
        if (op.equals("2"))
        {
            // Almacenamos las pruebas pendientes para actualizar hoy
            for (prueba p: pru)
            {
                if ((p.extraeRes().equals("***"))&&(p.daFecha().isEqual(LocalDate.now())))
                {
                    pruebashoy.add(p);
                }
            }
            if (pruebashoy.isEmpty())
            {
                System.out.println ("No tiene pruebas que analizar hoy.");
                continua();
            }
            else
            {
                // Mostramos por pantalla pruebas pendientes para actualizar hoy
                System.out.println ("Pruebas asignadas hoy:");
                imprimirCabPru();
                for (prueba p: pru)
                {
                    if ((p.extraeRes().equals("***"))&&(p.daFecha().isEqual(LocalDate.now())))
                    {
                        p.imprimirDatos();
                    }
                }
        
                // Pedimos al usuario identificar la prueba
                System.out.println ("Introduzca el número de identificación de la prueba a actualizar:");
                texto = recibe();
                // Comprueba si el dato introducido por teclado corresponde con un código de prueba asignado al usuario
                if (esNumero(texto))
                {
                    prueb = comPrueba(Integer.valueOf(texto));
                }
                while ((prueb==null)&&(!texto.equals("v")))
                {
                    System.out.print("\u000C");
                    System.out.println ("¡No existe ninguna prueba asignada con ese código!");
                    System.out.println ("Introduzca el número de identificación de la prueba a actualizar o escriba 'v' para volver:");
                    texto = recibe();
                    if (esNumero(texto))
                    {
                        prueb = comPrueba(Integer.valueOf(texto));
                    }
                }
                if (!texto.equals("v"))
                {
                    System.out.print("\u000C");
                    // Una vez identificada la prueba actualiza el resultado de la misma
                    System.out.println ("Por favor, indique el resultado de la prueba:");
                    System.out.println ("Recuerde: 'positivo' o 'negativo' para PCR y test de antígenos, un valor de 0 a 9 para prueba serológica");
                    while (!prueb.comprobarRes(texto2))
                    {
                        texto2 = recibe();
                        if (prueb.comprobarRes(texto2))
                        {        
                            //texto contiene el nº de prueba, texto 2 el resultado
                            actPrueba(Integer.valueOf(texto),texto2);
                            if (esNumero(texto2))
                            {
                                //si el resultado de un test serológico es favorable sacamos al paciente de la cuarentena
                                if (Integer.valueOf(texto2)>2)
                                {
                                    prueb.extraePaciente().desconfinar();
                                    prueb.extraePaciente().nocontagio();
                                }
                                //los positivos en PCR y antígenos implican el inicio del confinamiento.
                                if (texto2.equals("positivo"))
                                {
                                    prueb.extraePaciente().contagio();
                                    prueb.extraePaciente().confinar();
                                }
                        
                            }
                            System.out.println ("Resultado actualizado correctamente");
                            continua();
                            break;
                        }
                        else
                        {
                            System.out.println ("Valor no válido, vuelva a intentarlo.");
                            System.out.println ("Recuerde: 'positivo' o 'negativo' para PCR y test serológicos, un valor de 0 a 9 para test de antígenos");
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Imprime por pantalla información de un paciente asignado
     */
    protected void imprimeInfoPac(paciente pac)
    {
        // Datos personales
        pac.imprimirDatos();
        System.out.printf("%n","");
    }
    
    /**
     * Imprime por pantalla un listado de pacientes (información reducida) asignados al técnico, extraidos de las pruebas asignadas.
     */
    protected void imprimeListadoPac()
    {
        ArrayList<paciente> aux = new ArrayList<paciente>();
        imprimirCabPac();
        for (prueba p: pru)
        {
            if (!aux.contains(p.extraePaciente()))
            {
                p.extraePaciente().imprimirDatos();
                System.out.printf("%n","");
            }
            // Nos aseguramos de que cada paciente se imprima una sola vez
            aux.add(p.extraePaciente());
        }
    }
    
    /**
     * Imprime por pantalla un menú de opciones para el técnico
     */
    public void imprimeMenuTra()
    {
        {
        System.out.println ("Seleccione una opción:          ");
        System.out.println ("1 Listado de pacientes asignados");
        System.out.println ("2 Buscar Paciente               ");
        System.out.println ("3 Actualizar resultados         ");
        System.out.println ("4 Volver                        ");
        System.out.println ("                                ");
    }
    }
    
    /**
     * Método que imprime por pantalla la cabecera del listado de pacientes del técnico
     */
    protected void imprimirCabPac()
    {
        String formato = "%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,"Nombre","DNI","Teléfono", "Edad");
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
     * Método que recibe por teclado una opción del menú del técnico y limpia la consola
     */
    public String recibeOp()
    {
        String op = "";
        boolean respValid = false;
        while (respValid == false){
            op = recibe();
            if ((op.equals("1"))||(op.equals("2"))||(op.equals("3"))||(op.equals("4")))
            {
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
    

