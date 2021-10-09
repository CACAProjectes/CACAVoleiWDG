/**
 * 
 */
package es.xuan.cacavoleiwdg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author jcamposp			
 *
 */
public class Prova implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private final static String CTE_URL_FCVB_COMPETICIONS = "https://fcvolei.cat/apicompeticiones.php?slug=2a-divisio-infantil-femenina-2122";
	private final static String CTE_PROXY_IP = "10.126.132.10";
	private final static String CTE_PROXY_PORT = "8080";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = getContingutURL(CTE_URL_FCVB_COMPETICIONS);
		System.out.println(str.length());
		parserJson(str);
	}


	private static void parserJson(String pText) {
		JSONObject obj = new JSONObject(pText);
		//String pageName = obj.getJSONObject("pageInfo").getString("pageName");

		JSONArray arr = obj.getJSONArray("grupos"); // 
		for (int i = 0; i < arr.length(); i++)
		{
		    String nomGrup = arr.getJSONObject(i).getString("nombreGrupo");
		    if (nomGrup.equalsIgnoreCase("GRUP E")) {
			    JSONArray arrEq = arr.getJSONObject(i).getJSONArray("equipos"); // 
			    System.out.println("");
		    }
		}		
	}


	//@SuppressWarnings("null")
	private static String getContingutURL(String pStrUrl) {
		URLConnection yc = null;
	    InputStreamReader isr = null;
		StringBuilder lineStr = new StringBuilder();
		String strAux = "";
		try {
			yc = new URL(pStrUrl).openConnection();
			//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			//StrictMode.setThreadPolicy(policy);
			isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
		} catch (Exception ex) {	
			// Hi ha PROXY
		}	
		if (isr == null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(CTE_PROXY_IP, Integer.parseInt(CTE_PROXY_PORT)));
		    try {
				yc = new URL(pStrUrl).openConnection(proxy);
				isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
		try {
			//
			BufferedReader in = new BufferedReader(isr);
		    while((strAux = in.readLine()) != null) {
		    	lineStr.append(strAux);
		    }
		    in.close();
		    isr.close();
		}
		catch (Exception ex) {
			System.err.println("Error -getContingutURL.1-: " + ex);
		}
		return lineStr.toString();
	}
}
