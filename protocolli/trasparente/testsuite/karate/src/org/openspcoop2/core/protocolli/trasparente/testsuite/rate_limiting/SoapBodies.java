package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;

public class SoapBodies {
	
	private final static String minuto = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
			"    <soap:Body>\n" + 
			"        <ns2:Minuto xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
			"        </ns2:Minuto>\n" + 
			"    </soap:Body>\n" + 
			"</soap:Envelope>";
	
	
	private final static String giornaliero = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
			"    <soap:Body>\n" + 
			"        <ns2:Giornaliero xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
			"        </ns2:Giornaliero>\n" + 
			"    </soap:Body>\n" + 
			"</soap:Envelope>";
	
	
	private final static String orario = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
			"    <soap:Body>\n" + 
			"        <ns2:Orario xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
			"        </ns2:Orario>\n" + 
			"    </soap:Body>\n" + 
			"</soap:Envelope>";
	
	private final static String richiesteSimultanee =  "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
			"    <soap:Body>\n" + 
			"        <ns2:RichiesteSimultanee xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
			"        </ns2:RichiesteSimultanee>\n" + 
			"    </soap:Body>\n" + 
			"</soap:Envelope>";
	
	
	public final static String get(PolicyAlias alias) {
		switch(alias) {
		case GIORNALIERO:
			return giornaliero;
		case MINUTO:
			return minuto;
		case ORARIO:
			return orario;
		case RICHIESTE_SIMULTANEE:
			return richiesteSimultanee;
		default:
			return null;
		}
	}

}
