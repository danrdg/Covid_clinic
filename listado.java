
/**
 * Clase que almacena listados de los trabajadores, pacientes, vacunas presentes
 * en la clínica, así como métodos para visualizar o modificar dichos listados
 * @author Daniel Rodríguez González
 * 
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
public class listado
{
    // listado de pruebas efectuadas en la clínica
    private ArrayList<prueba> pruebas;
    // listado de administradores de la clínica
    private ArrayList<administrador> administradores;
    // listado de enfermeros de la clínica
    private ArrayList<enfermero> enfermeros;
    //listado de técnicos de la clínica
    private ArrayList<tecnico> tecnicos;
    // listado de pacientes de la clínica
    private ArrayList<paciente> pacientes;
    //listado de vacunas en la clínica
    private ArrayList<vacuna> vacunas;

    /**
     * Constructor de objetos de la clase listado
     */
    public listado()
    {
       int aux;
       pacientes = new ArrayList<paciente>();
       pruebas = new ArrayList<prueba>();
       tecnicos = new ArrayList<tecnico>();
       enfermeros = new ArrayList<enfermero>();
       administradores = new ArrayList<administrador>();
       vacunas = new ArrayList<vacuna>();
       // Stock de vacunas por defecto
       vacunas.add(new pfizer(1));
       vacunas.add(new moderna(2));
       vacunas.add(new johnson(3));
       // Listado de pacientes por defecto, creamos pacientes para el ArrayList
       pacientes.add(new paciente("Juan Pérez Simón","00000000a","623152555",35,false,false,true,true));
       pacientes.add(new paciente("María Díaz Rojo","11111111a","627151220",66,false,false,false,false));
       pacientes.add(new paciente("Benito Gil Pérez","22222222a","654966986",26,false,false,false,false));
       pacientes.add(new paciente("Juan McClein Tailor","33333333a","627151220",70,false,false,false,false));
       pacientes.add(new paciente("Boris Pavlov","44444444a","686935279",28,false,false,false,false));
       pacientes.add(new paciente("Helena Pérez Pérez","55555555a","665665556",34,false,false,false,false));
       pacientes.add(new paciente("Daniel Paz Díaz","66666666a","688756989",16,false,false,false,false));
       pacientes.add(new paciente("Felisa Pérez Gil","77777777a","666189512",98,false,false,false,false));
       pacientes.add(new paciente("Pilar Torres Herrero","88888888a","664189511",29,false,false,false,false));
       pacientes.add(new paciente("Hans Schwarz Berger","99999999a","686185512",68,false,false,false,false));
       // Listado de trabajadores por defecto, creamos trabajadores para los ArrayList
       
       tecnicos.add(new tecnico("Juan Martín Martín","71189865j","696858475","5"));
       tecnicos.add(new tecnico("Juana Martín Martín","71189864k","696858475","4"));
       enfermeros.add(new enfermero("Paz Díaz Gil","72659486m","696858475","3"));
       enfermeros.add(new enfermero("Luz Vega Martín","71189865j","696858475","2"));
       administradores.add(new administrador("Daniel Pérez Bravo","66589658b","696858475","1"));
       // Listado de pruebas diagnósticas por defecto, creamos pruebas diagnósticas
       pruebas.add(new pcr(1,"PCR","2021-05-07","positivo",pacientes.get(0),enfermeros.get(0),tecnicos.get(0)));
       pruebas.add(new antigenos(2,"Antígenos","2021-01-01","negativo",pacientes.get(1),enfermeros.get(0),tecnicos.get(0)));
       pruebas.add(new pcr(3,"PCR","2021-01-01","negativo",pacientes.get(2),enfermeros.get(0),tecnicos.get(0)));
       pruebas.add(new antigenos(4,"Antígenos","2021-05-17","***",pacientes.get(3),enfermeros.get(0),tecnicos.get(0)));
       pruebas.add(new antigenos(5,"Antígenos","2021-01-01","negativo",pacientes.get(4),enfermeros.get(1),tecnicos.get(1)));
       pruebas.add(new serologicos(6,"PCR","2021-01-01","negativo",pacientes.get(5),enfermeros.get(1),tecnicos.get(1)));
       pruebas.add(new antigenos(7,"Antígenos","2021-01-01","negativo",pacientes.get(6),enfermeros.get(1),tecnicos.get(1)));
       pruebas.add(new antigenos(8,"Antígenos","2021-05-18","***",pacientes.get(7),enfermeros.get(1),tecnicos.get(1)));
       // Asignamos las pruebas a pacientes
       for (aux = 0;aux < 8;aux++)
       {
           pacientes.get(aux).asignaPrueba(pruebas.get(aux));
       }
       // Asignamos pruebas a técnicos y enfermeros
       for (aux = 0; aux < 4;aux++)
       {
           tecnicos.get(0).addPrueba(pruebas.get(aux));
           enfermeros.get(0).addPrueba(pruebas.get(aux));
       }
       
       for (aux = 4; aux < 8;aux++)
       {
           tecnicos.get(1).addPrueba(pruebas.get(aux));
           enfermeros.get(1).addPrueba(pruebas.get(aux));
       }
       // El administrador creado almacena el listado por defecto
       actualizar();
    }
    
    /**
     * Actualiza el listado de la clínica a todos los objetos que hagan uso del mismo 
     * Se emplea cada vez que hay una modificación del listado general de la clínica
     */
    
    private void actualizar()
    {
        for (administrador a: administradores)
        {
            a.leerList(this);
        }
    }
    
    /**
     * Método que añade administradores al listado
     * @param recibe como parámetro el objeto de clase administrador a añadir
     */
    public void añadeAdministrador(administrador adm)
    {
        administradores.add(adm);
        actualizar();
    }
    
    /**
     * Método que añade enfermeros al listado
     * @param recibe como parámetro el objeto de clase enfermero a añadir
     */
    public void añadeEnfermero(enfermero enf)
    {
            enfermeros.add(enf);
    }
    
    /**
     * Método que añade pacientes al listado
     * @param recibe como parámetro el objeto de clase paciente a añadir
     */
    public void añadePaciente(paciente pac)
    {
        pacientes.add(pac);
    }
    
    /**
     * Método que añade pruebas al listado
     * @param recibe como parámetro el objeto de la clase prueba a añadir
     */
    public void añadePrueba(prueba pru)
    {
        prueba aux = pru;
        // Sumamos 1 número identificador de la última prueba
        aux.daNum(numPru()+1);
        pruebas.add(pru);
    }
    
    /**
     * Método que añade técnicos al listado
     * @param recibe como parámetro el objeto de clase técnico a añadir
     */
    public void añadeTecnico(tecnico tec)
    {
        tecnicos.add(tec);
    }
    
    /**
     * Método que asigna pruebas a enfermeros del listado
     * @param num - Número de empresa del enfermero en formato String
     * @param prueba - prueba a asignar al enfermero
     */
    public void asignaaEnf(String num,prueba prueba)
    {
        enfermero enf = null;
        for (enfermero e: enfermeros)
        {
            if (e.daNum().equals(num))
            {
                e.addPrueba(prueba);
                enf = e;
                break;
            }
        }
        for (prueba p: pruebas)
        {
            if ((p.extraeNum())==(prueba.extraeNum()))
            {
                p.daEnf(enf);
                break;
            }
        }
    }
    
    /**
     * Método que asigna pacientes a pruebas del listado
     * @param num - numero de identificación de la prueba en formato int;
     * @param paciente - paciente que será asignado a la prueba
     */
    public void asignaaPru(int num,paciente paciente)
    {
        prueba pru = null;
        for (prueba p: pruebas)
        {
            if (p.extraeNum()==num)
            {
                pru = p;
                p.daPac(paciente);
                break;
            }
        }
    }
    
    /**
     * Método que asigna pruebas a tecnicos del listado
     * @param num - Número de empresa del tecnico en formato String
     * @param prueba - prueba a asignar al tecnico
     */
    public void asignaaTec(String num,prueba prueba)
    {
        tecnico tec = null;
        for (tecnico t: tecnicos)
        {
            if (t.daNum().equals(num))
            {
                t.addPrueba(prueba);
                tec = t;
                break;
            }
        }
        for (prueba p: pruebas)
        {
            if ((p.extraeNum())==(prueba.extraeNum()))
            {
                p.daTec(tec);
                break;
            }
        }
    }
    
    /**
     * Dados un DNI de un paciente, una vacuna y dos fechas asigna una vacuna al paciente siendo las fechas las de la primera y segunda dosis.
     * @param pac - paciente al que se asignará la vacunación
     * @param vac - vacuna que se le asignará al paciente
     * @param f1 - fecha de la primera dosis en formato LocalDate que se le asignará al paciente
     * @param f2 - fecha de la segunda dosis en formato LocalDate que se le asignará al paciente
     */
    
    public void asignaVacunacion(paciente pac,vacuna vac,LocalDate f1,LocalDate f2)
    {
        for (paciente p: pacientes)
        {
            if (p.daDNI().equals(pac.daDNI()))
            {
                p.asignaFecha1(f1);
                p.asignaFecha2(f2);
                p.asignaVacuna(vac);
                break;
            }
        }
        for (vacuna v: vacunas)
        {
            if ((v.daNum())==(vac.daNum()))
            {
                vacunas.remove(v);
                break;
            }
        }
    }
    
    /**
     * Dado un número de empresa extrae un administrador de la clínica
     * @param num - número de empresa del administrador (formato String)
     * @return - devuelve el administrador con el número de empresa coincidente, null si no existe
     */
    
    public administrador buscaAdm(String num)
    {
        int aux = 0;
        administrador adm = null;
        for (administrador a:administradores)
        {
            if(a.daNum().equals(num))
            {
                adm = administradores.get(aux);
                break;
            }
            aux++;
        }
        return adm;
    }
    
    /**
     * Dado un número de empresa extrae un enfermero de la clínica
     * @param num - número de empresa del enfermero (formato String)
     * @return - devuelve el enfermero con el número de empresa coincidente, null si no existe
     */
    
    public enfermero buscaEnf(String num)
    {
        int aux = 0;
        enfermero enf = null;
        for (enfermero e:enfermeros)
        {
            if(e.daNum().equals(num))
            {
                enf = enfermeros.get(aux);
                break;
            }
            aux++;
        }
        return enf;
    }
    
    /**
     * Dado un número de empresa extrae un tecnico de la clínica
     * @param num - número de empresa del tecnico (formato String)
     * @return - devuelve el tecnico con el número de empresa coincidente, null si no existe
     */
    
    public tecnico buscaTec(String num)
    {
        int aux = 0;
        tecnico tec = null;
        for (tecnico t:tecnicos)
        {
            if(t.daNum().equals(num))
            {
                tec = tecnicos.get(aux);
                break;
            }
            aux++;
        }
        return tec;
    }
    
    /**
     * Método que comprueba si el stock de vacunas está vacío
     */
    public boolean compruebaStockVac()
    {
        boolean aux = false;
        if (vacunas.isEmpty())
        {
            aux = true;
        }
        return aux;
    }
    
    /**
     * Devuelve el listado de pacientes
     * @return listado de pacientes 
     */
    public ArrayList<paciente> daListPac()
    {
        return pacientes;
    }
    
    /**
     * Devuelve el listado de pacientes confinados
     * @return listado de pacientes confinados
     */
    public ArrayList<paciente> daListPacConf()
    {
        ArrayList paconf = new ArrayList<paciente>();
        for (paciente p: pacientes)
        {
            if (p.conf())
            {
                paconf.add(p);
            }
        }
        return paconf;
    }
    
    /**
     * Devuelve el listado de pruebas
     * @return listado de pruebas
     */
    public ArrayList<prueba> daListPru()
    {
        return pruebas;
    }
    
    /**
     * Extrae una vacuna Johnson&Johnson del stock
     * @return vacuna de tipo Johnson&Johnson, null si no hay en el stock
     */
    
    public vacuna darVacunaJohnson()
    {
        vacuna aux = null;
        for (vacuna v:vacunas)
        {
            if (v instanceof johnson)
            {
                aux = v;
            }
        }
        return aux;
    }
    
    /**
     * Extrae una vacuna Moderna del stock
     * @return vacuna de tipo Moderna, null si no hay en el stock
     */
    
    public vacuna darVacunaModerna()
    {
        vacuna aux = null;
        for (vacuna v:vacunas)
        {
            if (v instanceof moderna)
            {
                aux = v;
            }
        }
        return aux;
    }
    
    /**
     * Extrae una vacuna Pfizer del stock
     * @return vacuna de tipo Pfizer, null si no hay en el stock
     */
    
    public vacuna darVacunaPfizer()
    {
        vacuna aux = null;
        for (vacuna v:vacunas)
        {
            if (v instanceof pfizer)
            {
                aux = v;
            }
        }
        return aux;
    }
    
    /**
     * Método que elimina un paciente de las listas según su número de empresa
     * @param dni - número de empresa del paciente en formato String
     * @return devuelve true si ha encontrado y eliminado a un paciente
     */
    public boolean eliminarPac(String dni)
    {
        // Inicializamos variables auxiliares
        boolean aux = false;
        int num = 0;
        ArrayList pruebaux = new ArrayList<prueba>();
        prueba paux = null;
        for (prueba pr: pruebas)
        {
            pruebaux.add(pr);
        }
        // Buscamos paciente
        for (paciente p: pacientes)
        {
            if (p.daDNI().equals(dni))
            {
                // Elimina pruebas asociadas al paciente
                for (prueba pru: pruebas)
                {
                    if (pru.extraePaciente().daDNI().equals(dni))
                    {
                        num = pru.extraeNum();
                        pruebaux.remove(pru);
                        // Elimina también esas mismas pruebas asignadas a trabajadores y enfermeros
                        for (tecnico t:tecnicos)
                        {
                            paux = t.comPrueba(num);
                            if (!(paux==null))
                            {
                                t.eliminaPrueba(paux);
                            }
                        }
                        for (enfermero e:enfermeros)
                        {
                            paux = e.comPrueba(num);
                            if (!(paux==null))
                            {
                                e.eliminaPrueba(paux);
                            }
                        }
                    }
                }
                pruebas = pruebaux;
                // Por último elimina al paciente
                pacientes.remove(p);
                aux = true;
                break;
            }
        }
        return aux;
    }

    
    /**
     * Método que elimina un administrador, un técnico o un enfermero de las listas según su número de empresa
     * @param num - número de empresa del trabajador en formato String
     * @return devuelve true si ha encontrado y eliminado un administrador, un técnico o un enfermero
     */
    public boolean eliminarTra(String num)
    {
        boolean aux = false;
        for (administrador a: administradores)
        {
            if (a.daNum().equals(num))
            {
                administradores.remove(a);
                aux = true;
                break;
            }
        }
        for (enfermero e: enfermeros)
        {
            if (e.daNum().equals(num))
            {
                enfermeros.remove(e);
                aux = true;
                break;
            }
        }
        for (tecnico t: tecnicos)
        {
            if (t.daNum().equals(num))
            {
                tecnicos.remove(t);
                aux = true;
                break;
            }
        }
        return aux;
    }
    
    /**
     * Método que devuelve un administrador del listado de la clínica
     * @param n - posición del administrador en la lista
     * return devuelve el administrador de la posición indicada
     */
    
    public administrador extraerAdm(int n)
    {
        return administradores.get(n);
    }
    
    /**
     * Método que devuelve un enfermero del listado de la clínica
     * @param n - posición del enfermero en la lista
     * @return devuelve el enfermero de la posición indicada
     */
    
    public enfermero extraerEnf(int n)
    {
        return enfermeros.get(n);
    }
    
    /**
     * Método que devuelve un paciente del listado de la clínica
     * @param n - posición del paciente en la lista
     * return devuelve el paciente de la posición indicada
     */
    public paciente extraerPac(int n)
    {
        return pacientes.get(n);
    }
    
    /**
     * Método que devuelve una prueba del listado de la clínica
     * @param n - posición de la prueba en la lista
     * return devuelve la prueba de la posición indicada
     */
    public prueba extraerPru(int n)
    {
        return pruebas.get(n);
    }
    
    /**
     * Método que devuelve un técnico del listado de la clínica
     * @param n - posición del técnico en la lista
     * return devuelve el técnico de la posición indicada
     */
    
    public tecnico extraerTec(int n)
    {
        return tecnicos.get(n);
    }
    
    /**
     * Método que crea vacunas de un tipo seleccionado y las almacena en el ArrayList vacunas
     * @param op - String que identifica el tipo de vacuna (1 Johnson&Johnson,2 Moderna,3 Pfizer)
     * @param numero - entero que representa el número de vacunas a crear
     */
    
    public void incrementaStockVac(String op,int numero)
    {
        int cont = 0;
        //Calculamos número de identificación de las vacunas
        int num = numVac()+1;
        if (op.equals("1"))
        {
            while (cont<numero)
            {              
                vacunas.add(new johnson(num));
                cont++;
                num++;
            }
        }
        if (op.equals("2"))
        {
            while (cont<numero)
            {              
                vacunas.add(new moderna(num));
                cont++;
                num++;
            }
        }if (op.equals("1"))
        {
            while (cont<numero)
            {              
                vacunas.add(new pfizer(num));
                cont++;
                num++;
            }
        }
    }
    
    /**
     * Método que devuelve el número de administradores registrados en la clínica
     * return devuelve un entero, el número de administradores registrados
     */
    public int numAdm()
    {
        return administradores.size();
    }
    
    /**
     * Método que devuelve el número de enfermeros registrados en la clínica
     * @return devuelve un entero, el número de enfermeros registrados
     */
    public int numEnf()
    {
        return enfermeros.size();
    }
    
    /**
     * Método que devuelve el número de vacunas de tipo Johnson&Johnson
     * @return devuelve un entero, el número de vacunas de ese tipo
     */
    
    public int numJohnson()
    {
        int cont=0;
        for (vacuna v: vacunas)
        {
            if (v instanceof johnson)
            {
                cont++;    
            }
        }
        return cont;
    }
    
    /**
     * Método que devuelve el número de vacunas de tipo Moderna
     * @return devuelve un entero, el número de vacunas de ese tipo
     */
    
    public int numModerna()
    {
        int cont=0;
        for (vacuna v: vacunas)
        {
            if (v instanceof moderna)
            {
                cont++;    
            }
        }
        return cont;
    }
    
    /**
     * Método que devuelve el número de pacientes registrados en la clínica
     * return devuelve un entero, el número de pacientes registrados
     */
    public int numPac()
    {
        return pacientes.size();
    }
    
    /**
     * Método que devuelve el número de vacunas de tipo Pfizer
     * @return devuelve un entero, el número de vacunas de ese tipo
     */
    
    public int numPfizer()
    {
        int cont=0;
        for (vacuna v: vacunas)
        {
            if (v instanceof pfizer)
            {
                cont++;    
            }
        }
        return cont;
    }
    
    /**
     * Método que devuelve el número de pruebas realizadas en la clínica
     * return devuelve un entero, el número de pruebas hechas en la clínica
     */
    public int numPru()
    {
        return pruebas.size();
    }
    
    /**
     * Método que devuelve el número de técnicos registrados en la clínica
     * @return devuelve un entero, el número de técnicos registrados
     */
    public int numTec()
    {
        return tecnicos.size();
    }
    
    /**
     * Método que devuelve el número de vacunas disponibles en la clínica
     */
    public int numVac()
    {
        return vacunas.size();
    }
    
    /**
     * Devuelve el último número de empresa registrado en los listados
     * @return el último número de empresa registrado en los listados en formato int
     */
    public int ultimoNum()
    {
        int numtec=0;
        int numenf=0;
        int numadm=0;
        int mayor=0;
        String digitos;
        // Recorre el listado de administradores
        for (administrador a:administradores)
        {
            // Extrae el número de empresa en formato String, lo convierte en entero para comparar
            if (Integer.parseInt(a.daNum())>numadm)
            {
                numtec=Integer.parseInt(a.daNum());
            }
        }
        // Recorre el listado de enfermeros
        for (enfermero e:enfermeros)
        {
            if (Integer.parseInt(e.daNum())>numenf)
            {
                numenf=Integer.parseInt(e.daNum());
            }
        }
        // Recorre el listado de técnicos
        for (tecnico t:tecnicos)
        {
            if (Integer.parseInt(t.daNum())>numtec)
            {
                numtec=Integer.parseInt(t.daNum());
            }
        }
        // Devuelve el mayor de los tres números
        if ((numtec>=numenf)&&(numtec>=numadm))
        {
            mayor = numtec;
        }
        if ((numenf>=numtec)&&(numenf>=numadm))
        {
            mayor = numenf;
        }
        if ((numadm>=numtec)&&(numadm>=numenf))
        {
            mayor = numadm;
        }
        return mayor;
    }
}