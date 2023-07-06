
/**
 * Write a description of class administrador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class administrador extends persona
{
    // el campo num contiene el número de empresa del administrador
    private String numero;
    // el campo contiene el listado de personal, pacientes e inventarios de la clínica
    private listado lista;
    
    /**
     * Constructor de la clase administrador
     */
    public administrador()
    {}
    
    /**
     * Constructor parametrizado de la clase administrador
     */
    public administrador(String nom, String id, String phone, String num)
    {
        //el constructor invoca al de la superclase
        super(nom,id,phone);
        numero = num;
    }
    
    /**
     * Método que permite actualizar el stock de vacunas introduciendo nuevas cantidades de vacunas por teclado
     */
    public void actualizarStock()
    {
        String op = "";
        String cantidad = "";
        int numero = 0;
        boolean aux = false;
        boolean aux2 = false;
        mostrarStock();
        continua();
        while (!(op.equals("1"))&&!(op.equals("2")))
        {
            System.out.println ("¿Quiere actualizar el stock de vacunas?:");
            System.out.println ("1. Sí");
            System.out.println ("2. No");
            op = recibe();
        }
        if (op.equals("2"))
        {
            aux=true;
        }
        while (!aux)
        {
            System.out.println ("Seleccione qué tipo de vacuna quiere actualizar:");
            System.out.println ("1. Johnson&Johnson");
            System.out.println ("2. Moderna");
            System.out.println ("3. Pfizer");
            op = recibe();
            if ((op.equals("1"))||(op.equals("2"))||(op.equals("3")))
            {
                while(!aux2)
                {
                    System.out.println ("Introduzca la cantidad de vacunas recibidas:");
                    cantidad = recibe();
                    if (esNumero(cantidad))
                    {
                        aux2=true;
                    }
                }
                numero = Integer.parseInt(cantidad);
                lista.incrementaStockVac(op,numero);
                System.out.println ("Stock actualizado correctamente.");
                continua();
                aux=true;
            }
        }
    }
    
    /**
     * Método que devuelve aleatoriamente un entero dado un rango de enteros
     * @return devuelve un entero elegido aleatoriamente entre el 0 y el valor anterior al parametrizado
     * @param num - límite superior (excluido) del rango de enteros elegidos aleatoriamente
     */
    private int aleatoriza(int num)
    {
        // así obtenemos uan semilla distinta en cada invocación al método
        Random r = new Random(System.currentTimeMillis());
        int n = r.nextInt(num);
        return n;
    }
    
    /**
     * Asigna un nuevo número de empresa al administrador en formato String
     * @param número entero a asignar al administrador
     */
    private void asignaNum(int num)
    {
        numero = (String.valueOf(num));
    }
    
    /**
     * Asigna una prueba a un enfermero y un técnico
     * @param prueba - prueba a asignar
     * @param enfermero - enfermero que realizará la prueba
     * @param tecnico - tecnico de laboratorio que obtendrá los resultados
     * 
     */
    private void asignaPrueba(prueba prueba,paciente paciente, enfermero enfermero, tecnico tecnico)
    {
        lista.asignaaPru(prueba.extraeNum(),paciente);
        lista.asignaaTec(tecnico.daNum(),prueba);
        lista.asignaaEnf(enfermero.daNum(),prueba);
        lista.añadePrueba(prueba);
    }
    
    /**
     * Pide datos por teclado para registrar una nueva prueba que será asignada a un paciente, un técnico y un enfermero
     */
    public void asignarPru()
    {
        Scanner in = new Scanner(System.in);
        enfermero auxenf = null;
        tecnico auxtec = null;
        boolean condicion1=false;
        boolean condicion2=false;
        boolean condicion3=false;
        paciente pac = null;
        String dni = "";
        //Pedimos paciente objeto de la prueba
        imprimeListadoPac();
        System.out.print("Introduzca el DNI del paciente:");
        dni = in.next();
        pac = buscaPac(dni);
        while ((buscaPac(dni)==null))
        {
            System.out.print("\u000C");
            System.out.println ("DNI desconocido, vuelva a intentarlo:");
            dni = in.next();
            pac = buscaPac(dni);
        }
        //Creamos nueva prueba tras elegir el tipo
        prueba pru = creaPrueba(recibeOpPrueba());
        //Comprobamos el tipo de prueba y el estado del paciente
        //Si el paciente está confinado solo se le pueden asignar test serológicos
        if (pac.conf())
        {
            while ((pru instanceof pcr)||(pru instanceof antigenos))
            {
                System.out.println ("El paciente está confinado, solo puede recibir test serológicos.");
                continua();
                pru = creaPrueba(recibeOpPrueba());
            }
        }
        //Si el paciente no está confinado solo se le pueden asignar test PCR o de Antígenos
        else
        {
            while (pru instanceof serologicos)
            {
                System.out.println ("El paciente no está confinado, solo puede recibir test PCR o de Antígenos.");
                continua();
                pru = creaPrueba(recibeOpPrueba());
            }
        }
        //Asignamos fecha de realización
        pru.asignaFecha();
        //Comprobamos si la fecha asignada es presente o futura
        while (!esFechaFutura(pru.daFecha()))
        {
            System.out.println ("Acción imposible. Introduzca una fecha presente o futura.");
            pru.asignaFecha();
        }
        //Asignamos paciente
        pru.daPac(pac);
        condicion1=false;
        condicion2=false;
        condicion3=false;
        while ((!condicion1)||(!condicion2)||(!condicion3))
        {
            //Comprobamos fecha de últimas pruebas del paciente (Primera Condición)
            condicion1 = pru.comprobarFechaPac(pac);
            if (!condicion1)
            {
                System.out.println ("¡Asignación imposible!");
                continua();
                pru.asignaFecha();
                while (!esFechaFutura(pru.daFecha()))
                {
                    System.out.println ("Acción imposible. Introduzca una fecha presente o futura.");
                    pru.asignaFecha();
                }
            }
            auxenf = eligeEnf(pru.daFecha());
            pru.daEnf(auxenf);
            //Comprobamos si hay enfermeros disponibles
            if (auxenf == null)
            {
                condicion2=false;
            }
            else
            {
                condicion2=true;
            }
            if (!condicion2)
            {
                System.out.println ("No hay enfermeros disponibles en esa fecha.");
                pru.asignaFecha();
            }
            auxtec = eligeTec(pru.daFecha());
            pru.daTec(auxtec);
            //Comprobamos si hay técnicos disponibles
            if (auxtec == null)
            {
                condicion3=false;
            }
            else
            {
                condicion3=true;
            }
            if (!condicion3)
            {
                System.out.println ("No hay tecnicos disponibles en esa fecha.");
                pru.asignaFecha();
            }      
        }
        // Una vez hechas las comprobaciones, asignamos la prueba.
        asignaPrueba(pru,pac,auxenf,auxtec);
        System.out.println ("Asignación completada con éxito.");
        continua();
    }
    
    /**
     * Buscamos un administrador de la clínica incluido en el listado por su número de empresa
     * @return devuelve el administrador buscado, null si no lo encuentra
     * @param id - Número de empresa del administrador buscado
     */
    private administrador buscaAdm(String id)
    {
        administrador adm = null;
        int aux=0;
        while (aux<lista.numAdm())
        {
            // Comprobamos uno a uno el número de empresa de los técnicos
            if (lista.extraerAdm(aux).daNum().equals(id))
            {
                adm = lista.extraerAdm(aux);
            }
            aux++;
        }
        return adm;
    }
    
    /**
     * Busca un enfermero de la clínica incluido en el listado por su número de empresa
     * @return devuelve el enfermero buscado, null si no lo encuentra
     * @param id - Número de empresa del empleado buscado
     */
    private enfermero buscaEnf(String id)
    {
        enfermero enf = null;
        int aux = 0;
        while (aux<lista.numEnf())
        {
            // Comprobamos uno a uno el número de empresa de los enfermeros
            if (lista.extraerEnf(aux).daNum().equals(id))
            {
                enf = lista.extraerEnf(aux);
            }
            aux++;
        }
        return enf;
    }
    
    /**
     * Buscamos un paciente incluido en el listado por su DNI
     * @return devuelve el paciente buscado, null si no lo encuentra
     * @param id - DNI del paciente buscado
     */
    private paciente buscaPac(String id)
    {
        paciente pac = null;
        int aux=0;
        while (aux<lista.numPac())
        {
            // Comprobamos uno a uno el DNI de los pacientes
            if (lista.extraerPac(aux).daDNI().equals(id))
            {
                pac = lista.extraerPac(aux);
            }
            aux++;
        }
        return pac;
    }

    
    /**
     * Despliega por pantalla un menú de opciones y permite elegir entre buscar a un paciente por su DNI y a un empleado por su número de empresa
     */
    public void buscarPer()
    {
        String texto = "";
        String texto2 = "";
        paciente pac = null;
        trabajador auxtrab = null;
        administrador auxadmin = null;
        while (!((texto.equals("1")) || (texto.equals("2")) || (texto.equals("3"))))
        {
            System.out.println ("Seleccione una opción:");
            System.out.println ("1. Buscar paciente");
            System.out.println ("2. Buscar empleado");
            System.out.println ("3. Volver");
            texto = recibe();
        }
        // Búsqueda de paciente
        if (texto.equals("1"))
        {
            // Asignamos el paciente cuyo DNI coincide con el introducido
            pac = eligePac();
            // Imprimimos datos del paciente buscado
            if (!(pac==null))
            {
                System.out.print("\u000C");
                System.out.println ("Datos del paciente:");
                imprimirCabPac();
                imprimirDatosPac(pac);
                System.out.println("");
                System.out.println("");
                pac.mostrarPruebas();
                continua();
            }
        }
        // Búsqueda de empleado
        if (texto.equals("2"))
        {
            System.out.println ("Introduzca el número de empresa del empleado:");
            texto2 = recibe();
            // Asignamos el trabajador (o administrador) cuyo número de empresa coincide con el introducido
            auxtrab = buscaTra(texto2);
            auxadmin = buscaAdm(texto2);
            while ((auxadmin == null)&&(!texto.equals("v"))&&(auxtrab == null))
            {
                System.out.println ("Empleado desconocido, vuelva a intentarlo o escriba 'v' para volver:");
                texto = recibe();
            }
            // Imprimimos datos del trabajador buscado
            if (!texto.equals("v"))
            {
                // Si el trabajador es administrador
                if (auxtrab == null)
                {
                    System.out.print("\u000C");
                    System.out.println ("Datos del administrador:");
                    auxadmin.imprimirCabTra();
                    auxadmin.imprimirDatos();
                    continua();
                }
                // Si el trabajador es enfermero o técnico
                if (auxadmin == null)
                {
                    if (auxtrab instanceof enfermero)
                    {
                        System.out.print("\u000C");
                        System.out.println ("Datos del enfermero:");
                    }
                    if (auxtrab instanceof tecnico)
                    {
                        System.out.print("\u000C");
                        System.out.println ("Datos del técnico:");
                    }
                    imprimirCabTra();
                    auxtrab.imprimirDatos();
                    continua();
                }
            }
        }
    }
    
    /**
     * Busca un trabajador de la clínica incluido en uno de los listados por su número de empresa
     * @return devuelve el empleado buscado, null si no lo encuentra
     * @param id - Número de empresa del empleado buscado
     */
    private trabajador buscaTra(String id)
    {
        trabajador tra = null;
        tra = buscaTec(id);
        tra = buscaEnf(id);
        return tra;
    }
    
    /**
     * Busca un técnico de la clínica incluido en el listado por su número de empresa
     * @return devuelve el técnico buscado, null si no lo encuentra
     * @param id - Número de empresa del técnico buscado
     */
    private tecnico buscaTec(String id)
    {
        tecnico tec = null;
        int aux = 0;
        while (aux<lista.numTec())
        {
            // Comprobamos uno a uno el número de empresa de los enfermeros
            if (lista.extraerTec(aux).daNum().equals(id))
            {
                tec = lista.extraerTec(aux);
            }
            aux++;
        }
        return tec;
    }
    
    /**
     * Comprueba si hay existencias de alguna vacuna
     * @return devuelve true si no hay existencias 
     */
    private boolean checkInventTotal()
    {
        return lista.compruebaStockVac();
    }
    
    /**
     * Crea una prueba del tipo seleccionado
     * @param - tipo indica el tipo de prueba a crear "1" Antígenos "2" PCR "3" Serológica
     * @return prueba del tipo indicado, null si no es del tipo correcto
     */
    
    private prueba creaPrueba(String tipo)
    {
        prueba pru = null;
        if (tipo.equals("1"))
        {
            pru = new antigenos();
            pru.daTipo("Antígenos");
        }
        if (tipo.equals("2"))
        {
            pru = new pcr();
            pru.daTipo("PCR");
        }
        if (tipo.equals("3"))
        {
            pru = new serologicos();
            pru.daTipo("Serológica");
        }
        return pru;
    }
    
    /**
     * Devuelve el número de empresa del administrador
     * @return el número de empresa del administrador en formato String
     */
    public String daNum()
    {
        return numero;
    }
    
    /**
     * Selecciona un enfermero para realizar una prueba que, dada una fecha, tenga hueco disponible para realizar la prueba
     * @return devuelve un enfermero que cumpla los requisitos mencionados, null si no hay enfermeros disponibles
     * @param fecha - fecha en formato LocalDate para realizar la comprobación
     */
    private enfermero eligeEnf(LocalDate fecha)
    {
        int cont = 0;
        enfermero enf = null;
        boolean aux = false;
        while (cont<lista.numEnf())
        {
            enf = lista.extraerEnf(cont);
            if (enf.checkPrueba(fecha))
            {
                aux = true;
                break;
            }
            cont++;
        }
        if (aux==false)
        {
            enf = null;
        }
        return enf;
    }
    
    /**
     * Pide elegir un paciente de la clínica introduciendo su DNI por teclado
     * @return devuelve el paciente elegido, null si elige cancelar la operación por teclado
     */
    private paciente eligePac()
    {
        Scanner in = new Scanner(System.in);
        paciente pac = null;
        String dni = "";
        System.out.print("Introduzca el DNI del paciente:");
        dni = in.next();
        pac = buscaPac(dni);
        while ((buscaPac(dni)==null)&&(!dni.equals("v")))
        {
            System.out.print("\u000C");
            System.out.println ("DNI desconocido, vuelva a intentarlo o escriba 'v' para volver:");
            dni = in.next();
            pac = buscaPac(dni);
        }
        System.out.print("\u000C");
        return pac;
    }
    
    /**
     * Selecciona un tecnico para realizar una prueba que, dada una fecha, tenga hueco disponible para realizar la prueba
     * @return devuelve un tecnico que cumpla los requisitos mencionados, null si no hay tecnicos disponibles
     * @param fecha - fecha en formato LocalDate para realizar la comprobación
     */
    private tecnico eligeTec(LocalDate fecha)
    {
        int cont = 0;
        tecnico tec = null;
        boolean aux = false;
        while (cont<lista.numTec())
        {
            tec = lista.extraerTec(cont);
            if (tec.checkPrueba(fecha))
            {
                aux = true;
                break;
            }
            cont++;
        }
        if (aux==false)
        {
            tec = null;
        }
        return tec;
    }
    
    /**
     * Pide elegir un trabajador de la clínica introduciendo su número de empresa por teclado
     * @return devuelve el trabajador elegido
     */
    private trabajador eligeTra()
    {
        Scanner in = new Scanner(System.in);
        String num = "";
        trabajador tra = null;
        System.out.print("Introduzca el número de empresa del trabajador:");
        num = in.next();
        tra = buscaTra(num);
        while ((buscaTra(num)==null))
        {
            System.out.print("\u000C");
            System.out.println ("Número de empresa desconocido, vuelva a intentarlo:");
            num = in.next();
            tra = buscaTra(num);
        }
        System.out.print("\u000C");
        return tra;
    }
    
    /**
     * Da al administrador la opción de eliminar o registrar a un paciente de la clínica
     */
    public void gestionaPac()
    {
        String texto = "";
        while (!((texto.equals("1"))||(texto.equals("2"))||(texto.equals("3"))))
        {
            System.out.println ("Seleccione una opción:");
            System.out.println ("1. Registrar nuevo paciente");
            System.out.println ("2. Eliminar paciente de la clínica");
            System.out.println ("3. Volver");
            texto = recibe();
        }
        // Registrar nuevo paciente
        if (texto.equals("1"))
        {
            paciente pac = new paciente();
            pac.insertDatos();
            lista.añadePaciente(pac);
        }
        // Eliminar paciente
        if (texto.equals("2"))
        {
            texto = "";
            imprimeListadoPac();
            System.out.println ("Introduzca el DNI del paciente:");
            texto = recibe();
            // Si hay un paciente coincidente se elimina
            if (lista.eliminarPac(texto))
            {
                System.out.println ("Paciente eliminado con éxito");
                continua();
            }
            else
            {
                System.out.println ("No existen pacientes con ese número de empresa.");
                continua();
            }
        }
    }
    
    /**
     * Da al administrador la opción de eliminar o registrar a otro trabajador de la clínica
     */
    public void gestionaTra()
    {
        String texto = "";
        String texto2 = "";
        administrador auxadmin;
        enfermero auxenf;
        tecnico auxtec;
        while (!((texto.equals("1"))||(texto.equals("2"))|| (texto.equals("3"))))
        {
            System.out.println ("Seleccione una opción:");
            System.out.println ("1. Registrar nuevo empleado");
            System.out.println ("2. Eliminar empleado de la clínica");
            System.out.println ("3. Volver");
            texto = recibe();
        }
        // Registrar nuevo empleado
        if (texto.equals("1"))
        {
            while (!((texto2.equals("1"))||(texto2.equals("2"))||(texto2.equals("3"))))
            {
                System.out.println ("¿Qué tipo de empleado va a registrar?:");
                System.out.println ("1. Administrador");
                System.out.println ("2. Enfermero");
                System.out.println ("3. Técnico");
                texto2 = recibe();
            }
            if (texto2.equals("1"))
            {
                auxadmin = new administrador();
                auxadmin.insertDatos();
                //Asigna nuevo número de empresa
                auxadmin.asignaNum(nuevoNum());
                registraAdm(auxadmin);
            }
            if (texto2.equals("2"))
            {
                auxenf = new enfermero();
                auxenf.insertDatos();
                //Asigna nuevo número de empresa
                auxenf.asignaNum(nuevoNum());
                registraEnf(auxenf);
            }
            if (texto2.equals("3"))
            {
                auxtec = new tecnico();
                auxtec.insertDatos();
                //Asigna nuevo número de empresa
                auxtec.asignaNum(nuevoNum());
                registraTec(auxtec);
            }
            System.out.println ("Trabajador registrado con éxito");
            continua();
        }
        // Eliminar empleado
        if (texto.equals("2"))
        {
            texto = "";
            imprimeListadoTra();
            System.out.println ("Introduzca el número de empresa del empleado:");
            texto = recibe();
            // Nos aseguramos de que el administrador no se elimine a sí mismo.
            if (texto.equals(this.daNum()))
            {
                System.out.println ("Operación cancelada. ¡Está introduciendo su propio número de empresa!");
                continua();
            }
            else
            {
                // Si hay un trabajador coincidente se elimina
                if (lista.eliminarTra(texto))
                {
                    System.out.println ("Trabajador eliminado con éxito");
                    continua();
                }
                else
                {
                    System.out.println ("No existen trabajadores con ese número de empresa.");
                    continua();
                }
            }
        }
    }
    
    /**
     * Gestiona el confinamiento de pacientes pidiendo por teclado la opción de mostrar los pacientes confinados o información relativa a un paciente en concreto
     */
    public void gestionarConf()
    {
        boolean aux = false;
        String op,dni;
        ArrayList<paciente> pac = lista.daListPacConf();
        while (!aux)
        {
            System.out.println ("Elija una opción:");
            System.out.println ("1. Mostrar pacientes confinados.");
            System.out.println ("2. Mostrar información de un paciente relativa al confinamiento.");
            System.out.println ("3. Volver.");
            op = recibe();
            int cont = 0;
            // Pacientes confinados registrados en la clínica
            if (op.equals("1"))
            {
                if (pac.isEmpty())
                {
                    System.out.println ("No hay pacientes confinados.");
                    continua();
                }
                else
                {
                    System.out.println ("Pacientes confinados:");
                    imprimirCabPacSimple();
                    for (paciente p: pac)
                    {
                        p.imprimirDatos();
                        System.out.println("");
                    }
                    continua();
                }
            }
            //Información relativa a un paciente identificado por su DNI
            if (op.equals("2"))
            {
                if (pac.isEmpty())
                {
                    System.out.println ("No hay pacientes confinados.");
                    continua();
                }
                else
                {
                    System.out.println ("Introduzca el DNI del paciente.");
                    dni = recibe();
                    
                    for (paciente p: pac)
                    {
                        
                        if (p.daDNI().equals(dni))
                        {
                            cont++;
                            imprimirCabPacSimple();
                            p.imprimirDatos();
                            System.out.println ("");
                            System.out.println ("");
                            System.out.println ("Fecha de inicio del confinamiento:");
                            // El paciente comienza el confinamiento tras ser positivo en una prueba
                            System.out.println (p.daFechaConf().toString());
                            continua();
                            break;
                        }
                        
                    }
                    if ((cont==0))
                    {
                        System.out.println ("No hay pacientes confinados con ese DNI.");
                        continua();
                    }
                }
            }          
            if (op.equals("3"))
            {
                aux = true;
            }
        }
    }
    
    /**
     * Imprime por pantalla un listado de pacientes de la clínica
     */
    private void imprimeListadoPac()
    {
        imprimirCabPac();
        int aux=0;
        while (aux<lista.numPac())
        {
            imprimirDatosPac(lista.extraerPac(aux));
            System.out.println("");
            aux++;        
        }
    }
    
    /**
     * Imprime por pantalla un listado de pruebas realizadas en la clínica
     */
    private void imprimeListadoPru()
    {
        imprimirCabPru();
        int aux=0;
        while (aux<lista.numPru())
        {
            lista.extraerPru(aux).imprimirDatosComp();
            aux++;
        }
    }
    
    /**
    * Imprime por pantalla un listado de trabajadores de la clínica
    */
    private void imprimeListadoTra()
    {
        int aux=0;
        System.out.println("");
        System.out.println ("Administradores:");
        System.out.println("");
        imprimirCabTra();
        while (aux<lista.numAdm())
        {
            lista.extraerAdm(aux).imprimirDatos();
            aux++;
        }
        System.out.println("");
        aux = 0;
        System.out.println ("Técnicos:");
        System.out.println("");
        imprimirCabTra();
        while (aux<lista.numTec())
        {
            lista.extraerTec(aux).imprimirDatos();
            aux++;
        }
        System.out.println("");
        aux = 0;
        System.out.println ("Enfermeros:");
        System.out.println("");
        imprimirCabTra();
        while (aux<lista.numEnf())
        {
            lista.extraerEnf(aux).imprimirDatos();
            aux++;
        }
    }
    
    /**
     * Imprime por pantalla un menú de opciones para el administrador
     */
    public void imprimeMenuAdmin()
    {
        {
        System.out.println ("Seleccione una opción:          ");
        System.out.println ("1 Mostrar listados              ");
        System.out.println ("2 Buscar paciente/empleado      ");
        System.out.println ("3 Administrar pacientes         ");
        System.out.println ("4 Administrar trabajadores      ");
        System.out.println ("5 Asignar prueba                ");
        System.out.println ("6 Asignar vacunación            ");
        System.out.println ("7 Actualizar stock de vacunas   ");
        System.out.println ("8 Gestión de confinamientos     ");
        System.out.println ("9 Salir                         ");
        System.out.println ("                                ");
        }
    }
    
    /**
     * Imprime por pantalla las diferentes pruebas disponibles
     */
    private void imprimeMenuPrueba()
    {
        System.out.println (" Seleccione el tipo de prueba:  ");
        System.out.println ("1 Test de antígenos             ");
        System.out.println ("2 PCR                           ");
        System.out.println ("3 Prueba serológica             ");
        System.out.println ("                                ");
    }
    
    /**
     * Método que imprime por pantalla la cabecera completa del listado de pacientes de la clínica
     */
    private void imprimirCabPac()
    {
        String formato = "%-25s%-25s%-25s%-25s%-15s%-15s%-15s%-15s%n";
        System.out.printf(formato,"Nombre","DNI","Teléfono", "Edad", "Vacunado","Inmunizado", "Contagiado", "Confinado");
    }
    
    /**
     * Método que imprime por pantalla la cabecera del listado de pacientes básico
     */
    private void imprimirCabPacSimple()
    {
        String formato = "%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,"Nombre","DNI","Teléfono", "Edad");
    }
    
    /**
     * Imprime cabecera del listado de pruebas por pantalla
     */
    private void imprimirCabPru()
    {
        String formato = "%-25s%-25s%-25s%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,"Número","Tipo","Fecha","Resultado","DNI del paciente","Número del Enfermero","Número del técnico");
    }
    
    /**
     * Imprime cabecera del listado de trabajadores por pantalla
     */
    private void imprimirCabTra()
    {
        String formato = "%-25s%-25s%-25s%-25s%n";
        System.out.printf(formato,"Nombre","DNI","Teléfono","Número de empresa");
    }
    
    /**
     * Imprime datos del administrador por pantalla
     * 
     */
    protected void imprimirDatos()
    {
        super.imprimirDatos();
        String formato = "%-25s%n";
        System.out.printf(formato,numero);
    }
    
    /**
     * Imprime por pantalla los datos completos de un paciente
     */
    
    private void imprimirDatosPac(paciente pac)
    {
        pac.imprimirDatos();
        // Indica si el paciente está o no vacunado
        if (pac.vac())
        {
            System.out.printf("%-15s","Sí");
        }
        else
        {
            System.out.printf("%-15s","No");
        }
        // Indica si el paciente está o no inmunizado
        if (pac.inm())
        {
            System.out.printf("%-15s","Sí");
        }
        else
        {
            System.out.printf("%-15s","No");
        }
        // Indica si el paciente está o no contagiado
        if (pac.cont())
        {
            System.out.printf("%-15s","Sí");
        }
        else
        {
            System.out.printf("%-15s","No");
        }
        // Indica si el paciente está o no confinado
        if (pac.conf())
        {
            System.out.printf("%-15s","Sí");
        }
        else
        {
            System.out.printf("%-15s","No");
        }

    }
    
    /**
     * Incluye una prueba en la lista de pruebas de la clínica
     * @param pru - prueba a incluir en la clínica
     */
    private void incluyePru(prueba pru)
    {
        lista.añadePrueba(pru);
    }
    
    /**
     * Método que lee un listado y lo almacena en el campo lista
     */
    public void leerList(listado list)
    {
        lista = list;
    }
    
    /**
     * Muestra por pantalla listados de pacientes, trabajadores y pruebas realizadas
     */
    public void mostrarList()
    {
        String texto = "";
        while (!((texto.equals("1")) || (texto.equals("2"))||(texto.equals("3"))||(texto.equals("4"))))
        {
            System.out.println ("Seleccione una opción:");
            System.out.println ("1. Listado de pacientes");
            System.out.println ("2. Listado de trabajadores");
            System.out.println ("3. Listado de pruebas");
            System.out.println ("4. Volver");
            texto = recibe();
        }
        if (texto.equals("1"))
        {
            System.out.println ("Pacientes registrados en la clínica:");
            imprimeListadoPac();
            continua();
        }
        if (texto.equals("2"))
        {
            System.out.println ("Trabajadores registrados en la clínica:");
            imprimeListadoTra();
            continua();
        }
        if (texto.equals("3"))
        {
            System.out.println ("Pruebas realizadas en la clínica:");
            imprimeListadoPru();
            continua();
        }
    }
    
    /**
     * Método que muestra por pantalla el stock de vacunas
     */
    
    private void mostrarStock()
    {
        System.out.println ("Stock de vacunas:");
        String formato = "%-25s%-25s%-25s%n";
        System.out.printf(formato,"Johnson&&Johnson","Moderna","Pfizer");
        System.out.printf(formato,lista.numJohnson(),lista.numModerna(),lista.numPfizer());
    }
    
    /**
     * Método que obtiene un nuevo número de empresa para asignar a un trabajador
     * Tiene en cuenta los números de empresa de la totalidad de trabajadores registrados y asigna el siguiente
     * @return número de empresa del nuevo trabajador en formato String
     */
    private int nuevoNum()
    {
        int num = lista.ultimoNum();
        num++;
        return num;
    }
    
    /**
     * Método que recibe por teclado una opción del menú del administrador y limpia la consola
     */
    public String recibeOp()
    {
        String op = "";
        boolean respValid = false;
        while (respValid == false){
            op = recibe();
            if ((op.equals("1"))||(op.equals("2"))||(op.equals("3"))||(op.equals("4"))||(op.equals("5"))||(op.equals("6"))||(op.equals("7"))||(op.equals("8"))||(op.equals("9"))){
                respValid = true;
            }
            else{
                System.out.print("\u000C");
                System.out.println ("Seleccione una entrada del menú:");
                imprimeMenuAdmin();
            }
        }
        System.out.print("\u000C");
        return op;
    }
    
    /**
      * Recibe por teclado una opción del menú de pruebas y limpia la consola
      * @return devuelve la respuesta correcta por teclado en formato String
      */
    private String recibeOpPrueba()
    {
        boolean respValid = false;
        Scanner in = new Scanner(System.in);
        String op = "";
        while (respValid == false){
            if ((op.equals("1")||(op.equals("2"))||(op.equals("3")))){
                respValid = true;
                System.out.print("\u000C");
            }
            else{
                System.out.print("\u000C");
                imprimeMenuPrueba();
                op = in.next();
            }
        }
        return op;
    }
    
    /**
     * Registra un administrador en la lista de administradores de la clínica
     * @param adm - administrador a registrar en la clínica
     */
    private void registraAdm(administrador adm)
    {
        lista.añadeAdministrador(adm);
    }
    
    /**
     * Registra un enfermero en la lista de enfermeros de la clínica
     * @param enf - enfermero a registrar en la clínica
     */
    private void registraEnf(enfermero enf)
    {
        lista.añadeEnfermero(enf);
    }
    
    /**
     * Registra un técnico en la lista de técnicos de la clínica
     * @param tec - técnico a registrar en la clínica
     */
    private void registraTec(tecnico tec)
    {
        lista.añadeTecnico(tec);
    }
    
    /**
     * Procede a pedir datos del paciente para asignar vacunación
     */
    public void vacunar()
    {
        if (checkInventTotal())
        {
            System.out.println ("¡El stock de vacunas está vacío!");
            continua();
        }
        else
        {
            // Inicializamos variables auxiliares
            boolean aux = false;
            boolean aux1 = false;
            vacuna vac=null;
            int tipo = 100;
            //Contiene pacientes mayores de 65
            ArrayList<paciente> pacmayores = new ArrayList();
            LocalDate fechadosis1 = null;
            LocalDate fechadosis2 = null;
            System.out.println ("Pacientes pendientes de vacunación:");
            paciente pac = null;
            for (paciente p: lista.daListPac())
            {
                if ((p.daEdad()>=65)&&!(p.asigVac()))
                {
                    pacmayores.add(p);
                }
            }
            if (!(pacmayores.isEmpty()))
            {
                System.out.println ("(Grupo prioritario de mayores de 65)");
                imprimirCabPacSimple();
                for (paciente p: pacmayores)
                {
                // Si no tiene asignadas fechas de vacunación ni está contagiado ni vacunado mostramos sus datos por pantalla
                if ((!(p.asigVac())&&!(p.cont()))&&!(p.vac())&&(p.daEdad()>=65))
                {
                    p.imprimirDatos();
                    System.out.println("");
                }
                }
                
            }
            else
            {
                System.out.println ("(Grupo secundario de menores de 65)");
                imprimirCabPacSimple();
                for (paciente p: lista.daListPac())
                {
                    // Si no tiene asignadas fechas de vacunación ni está contagiado ni vacunado mostramos sus datos por pantalla
                    if (!(p.asigVac())&&!(p.cont())&&!(p.vac()))
                    {
                        p.imprimirDatos();
                        System.out.println("");
                    }
                }
            }
            pac = eligePac();
            if (!(pac == null))
            {
                // Asignación de paciente
                while (!aux)
                {
                    // Elección aleatoria de vacuna
                    while(!aux1)
                    {
                        // Genera un número aleatorio de 0 a 2 para elegir el tipo de vacuna a suministrar
                        tipo = aleatoriza(3);
                        if (tipo==0)
                        {
                            vac = new johnson();
                            // Actualiza el listado para el objeto creado
                            vac.leerList(lista);
                            // Comprobamos si hay existencias
                            if (!(vac.checkInvent()==null))
                            {
                                aux1=true;
                            }
                        }
                        if (tipo==1)
                        {
                            vac = new moderna();
                            // Actualiza el listado para el objeto creado
                            vac.leerList(lista);
                            // Comprobamos si hay existencias
                            if (!(vac.checkInvent()==null))
                            {
                                aux1=true;
                            }
                        }
                        if (tipo==2)
                        {
                            vac = new pfizer();
                            // Actualiza el listado para el objeto creado
                            vac.leerList(lista);
                            // Comprobamos si hay existencias
                            if (!(vac.checkInvent()==null))
                            {
                                aux1=true;
                            }
                        }
                    }
                    vac = vac.checkInvent();
                    System.out.println ("A continuación elija la fecha de la primera dosis.");
                    fechadosis1 = vac.pedirFecha();
                    // Comprobamos si la fecha introducida es pasada
                    while (!esFechaFutura(fechadosis1))
                    {
                        System.out.println ("Acción imposible. Introduzca una fecha presente o futura.");
                        fechadosis1 = vac.pedirFecha();
                    }
                    if (vac.unadosis())
                    {
                        aux = true;
                        System.out.println ("Asignación correcta.");
                        continua();
                    }
                    else
                    {
                        System.out.println ("Elija la fecha de la segunda dosis.");
                        fechadosis2 = vac.pedirFecha();
                        while (!esFechaFutura(fechadosis2))
                        {
                            System.out.println ("Acción imposible. Introduzca una fecha presente o futura.");
                            fechadosis2 = vac.pedirFecha();
                        }
                        // Comprobamos fechas de vacunación entre las dos dosis
                        while (!(vac.checkAsignacion(fechadosis1,fechadosis2)))
                        {
                            System.out.println ("!Asignación imposible!");
                            System.out.println ("Recuerde que deben pasar 21 días entre la primera dosis y la segunda.");
                            System.out.println ("");
                            fechadosis2 = vac.pedirFecha();
                            while (!esFechaFutura(fechadosis2))
                            {
                                System.out.println ("Acción imposible. Introduzca una fecha presente o futura.");
                                fechadosis2 = vac.pedirFecha();
                            }
                        }
                        aux = true;
                        System.out.println ("Asignación correcta.");
                        continua();
                    }
                    // Actualizamos registro de vacunación
                    lista.asignaVacunacion(pac,vac,fechadosis1,fechadosis2);
                }
            }
        }
    }
           
}
