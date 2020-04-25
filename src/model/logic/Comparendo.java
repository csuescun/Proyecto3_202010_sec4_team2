package model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comparendo implements Comparable<Comparendo> {

	private int objectID;

	/**
	 * Fecha del comparendo
	 */

	private Date fecha;

	/**
	 * Medio dete del comparendo.
	 */

	private String medioDete;

	/**
	 * Clase de vehiculo del comparendo.
	 */

	private String claseVehiculo;

	/**
	 * Tipo de servicio del vehiculo del comparendo.
	 */

	private String tipoServicio;

	/**
	 * Infraccion del comparendo.
	 */

	private String infraccion;

	/**
	 * Descripcion de la infraccion del comparendo.
	 */

	private String descripInfraccion; 

	/**
	 * Localidad del comparendo.
	 */

	private String localidad;
	
	private String municipio;

	/**
	 * 
	 */
	private double latitud;

	/**
	 * 
	 */
	private double longitud;


	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Crea un comparendo con toda la informacion respectiva.
	 * @param pObjectID ObjectID del comparendo.
	 * @param pFecha Fecha del comparendo.
	 * @param pMedioDete Medio dete del comparendo.
	 * @param pClaseVehiculo Clase de vehiculo del comparendo.
	 * @param pTipoServicio Tipo de servicio del vehiculo del comparendo.
	 * @param pInfraccion Infraccion del comparendo.
	 * @param pDesInfraccion Descripcion de la infraccion del comparendo.
	 * @param pLocalidad Localidad del comparendo.
	 */

	public Comparendo(int pObjectID, String pFecha, String pMedioDete, String pClaseVehiculo, String pTipoServicio, String pInfraccion, String pDesInfraccion, String pLocalidad, double pLatitud, double pLongitud, String pMunicipio)
	{
		try{
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
		fecha = formatter.parse(pFecha);
		
		}
		catch(ParseException e)
		{
			e.getStackTrace();
		}
		
		objectID = pObjectID;
		 
		medioDete = pMedioDete;
		claseVehiculo = pClaseVehiculo; 
		tipoServicio = pTipoServicio; 
		infraccion = pInfraccion;
		descripInfraccion = pDesInfraccion;
		localidad = pLocalidad;
		
		latitud =pLatitud; 
		longitud = pLongitud;
		
		municipio = pMunicipio;
	}

	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------

	public int darObjectID()
	{
		return objectID;
	}

	public Date darFecha()
	{
		return fecha;
	}

	public String darMedioDete()
	{
		return medioDete;
	}

	public String darClaseVehiculo()
	{
		return claseVehiculo;
	}

	public String darTipoServicio()
	{
		return tipoServicio;
	}

	public String darInfraccion()
	{
		return infraccion;
	}

	public String darDesInfraccion()
	{
		return descripInfraccion;
	}

	public String darLocalidad()
	{
		return localidad;
	}
	
	public double darLatitud()
	{
		return latitud;
	}
	
	public double darLongitud()
	{
		return longitud;
	}
	
	public String darSimpleDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
	    String strDate= formatter.format(fecha);
	    return strDate;
	}

	@Override
	public String toString() {
		return "Comparendo [OBJECTID=" + objectID + ", FECHA_HORA=" + fecha + ", DES_INFRAC=" + descripInfraccion
				+ ", MEDIO_DETE=" + medioDete + ", CLASE_VEHI=" + claseVehiculo + ", TIPO_SERVI=" + tipoServicio
				+ ", INFRACCION=" + infraccion + ", LOCALIDAD=" + localidad + ", latitud=" + latitud + ", longitud="
				+ longitud + "]";
	}

	public String datosCluster()
	{
		return " OBJECTID: "+objectID+" FECHA_HORA: "+fecha+" LOCALIDAD: "+localidad +"INFRACCION: "+infraccion;	
	}
	
	
	public String datosCluster2()
	{
		return "OBJECTID:" + objectID + "CLASE VEHICULO:" + claseVehiculo + "Latitud:" + latitud + "Longitud:" + longitud ;
	}
	
	
	@Override
	public int compareTo(Comparendo comparendo) 
	{
		
		int l1 = objectID;
		int l2 = comparendo.darObjectID();
		
		if(l1 > l2)
		{
			return 1;
		}
		
		else if(l1 < l2)
		{
			return -1;
		}
		
		else
		{
			return 0;
		}
	}
	


}
