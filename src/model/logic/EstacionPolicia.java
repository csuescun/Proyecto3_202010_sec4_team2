package model.logic;

public class EstacionPolicia implements Comparable<EstacionPolicia>
{

	/**
	 * Nombre de la estación de policía
	 */
	private String nombre; 

	/**
	 * ObjectID de la estación de policía
	 */
	private int objectID; 

	/**
	 * Descripcion de la estación de policía
	 */
	
	private String descripcion;
	
	/**
	 * Direccion de la estación de policía
	 */
	
	private String direccion;
	
	/**
	 * Servicio de la estación de policía
	 */
	
	private String servicio;
	
	/**
	 * Horario de la estación de policía
	 */
	
	private String horario;
	
	/**
	 * Telefono de la estación de policía
	 */
	
	private String telefono;
	
	/**
	 * IU Local de la estación de policía
	 */
	
	private String IUlocal;
	
	/**
	 * Latitud de la estación de policía
	 */
	private double latitud;

	/**
	 * Longitud de la estación de policía
	 */
	private double longitud;


	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Crea una estación de policía con toda la informacion respectiva.
	 * @param pNombre Nombre de la estación de policía
	 * @param pID Object ID de la estación de policía
	 * @param pLatitud Latitud de la estación de policía
	 * @param pLongitud Longitud de la estación de policía
	 */

	public EstacionPolicia(String pNombre, int pID, String pDes, String pDir, String pSer, String pHor, String pTel, String pIU, double pLatitud, double pLongitud)
	{
		nombre = pNombre;
		objectID = pID;
		descripcion = pDes;
		direccion = pDir;
		servicio = pSer;
		horario = pHor;
		telefono = pTel;
		IUlocal = pIU;
		latitud = pLatitud;
		longitud = pLongitud;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	public String darNombre()
	{
		return nombre;
	}
	
	public int darObjectID()
	{
		return objectID;
	}
	
	public String darDescripcion()
	{
		return descripcion;
	}
	
	public String darDireccion()
	{
		return direccion;
	}
	
	public String darServicio()
	{
		return servicio;
	}
	
	public String darHorario()
	{
		return horario;
	}
	
	public String darTelefono()
	{
		return telefono;
	}
	
	public String darIULocal()
	{
		return IUlocal;
	}
	
	public double darLatitud()
	{
		return latitud;
	}
	
	public double darLongitud()
	{
		return longitud;
	}

	@Override
	public int compareTo(EstacionPolicia o) 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString()
	{
		return "ObjectID; " + objectID + "; Nombre: " + nombre + "; Latitud: " + latitud + "; Longitud: " + longitud;
	}
	
	public String datosCluster()
	{
		return "ObjectID: " + objectID + "; EPODESCRIP: " + descripcion + "; EPODIR_SITIO: " + direccion + "; EPOLATITUD: " + latitud + "; EPOLONGITUD: " + longitud + "; EPOSERVICIO: "+ servicio + "; EPOHORARIO: " + horario + "; EPOTELEFONO: " + telefono + "EPOIULOCAL:" + IUlocal;
	}
	
	
}
