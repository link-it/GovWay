package org.openspcoop2.web.monitor.core.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import org.openspcoop2.web.monitor.core.core.Utils;
import org.openspcoop2.web.monitor.core.listener.IEFilter;
import org.openspcoop2.web.monitor.core.utils.BrowserInfo;
import org.openspcoop2.web.monitor.core.utils.BrowserInfo.BrowserFamily;
import org.openspcoop2.web.monitor.core.utils.Costanti;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.utils.ContentAuthorizationCostanti;
import org.openspcoop2.web.monitor.core.utils.ContentAuthorizationManager;

public class BrowserFilter implements Filter {

	/** Logger utilizzato per debug. * */
	private static Logger log = LoggerManager.getPddMonitorCoreLogger();
	
	private static Map<BrowserFamily, Double> mappaAbilitazioneGraficiSVG;

	// configurazione filtro
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

//	private List<String> excludedPaths = null;
	
	private static final String parametroSVG = "usaSVG";
	private static final String parametroSVG_FORM_STATS = "sf_usaSVG";
	private static final String parametroSVG_FORM_MENU = "tf_usaSVG";
	private static final String parametroSVG_POLL_STATO = "ps_usaSVG";
	private static final String parametroGeneraReport = "generaReport";
	private static final String parametroEsitiLive = "esiti_live";
	private static final String parametroTipoReport = "tipoReportCombo";
	private static final String parametroTipoReportTabella = "Tabella";

	private static synchronized void loadMappaBrowser(){
		if(mappaAbilitazioneGraficiSVG == null)
			mappaAbilitazioneGraficiSVG = new HashMap<BrowserInfo.BrowserFamily, Double>();

		mappaAbilitazioneGraficiSVG.put(BrowserFamily.CHROME, 4D);
		mappaAbilitazioneGraficiSVG.put(BrowserFamily.FIREFOX, 3D);
		mappaAbilitazioneGraficiSVG.put(BrowserFamily.IE, 9D);
		mappaAbilitazioneGraficiSVG.put(BrowserFamily.OPERA, 10.1D);
		mappaAbilitazioneGraficiSVG.put(BrowserFamily.SAFARI, 3.2D);
	}

	public static boolean disabilitaGraficiSVG(BrowserInfo browserInfo){
		boolean disabilita = false;

		if(mappaAbilitazioneGraficiSVG == null)
			loadMappaBrowser();

		if(browserInfo != null){
			Double versione = mappaAbilitazioneGraficiSVG.get(browserInfo.getBrowserFamily());

			if(versione != null && browserInfo.getVersion() != null && versione.doubleValue() >= browserInfo.getVersion().doubleValue())
				disabilita = true;
		}

		return disabilita;
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// Analisi dello user agent
		String userAgent = request.getHeader(Costanti.USER_AGENT_HEADER_NAME);

		if(userAgent != null) {

			try{

				log.debug("Decodifica Browser da Header UserAgent ["+userAgent+"]");
				String urlRichiesta = ((HttpServletRequest) request).getServletPath(); 
				log.debug("Richiesta Risorsa ["+urlRichiesta+"]");
				BrowserInfo browserInfo = BrowserInfo.getBrowserInfo(userAgent);

				String browsername = browserInfo.getBrowserName();
				Double browserversion = browserInfo.getVersion();

				log.debug("Browser Riconosciuto: Name ["+browsername+"] Version ["+browserversion+"].");

				//controllo se e' presente il parametro usaSVG
				boolean usaSVG = usaSVG(request);
				
				if(browserInfo.getBrowserFamily().equals(BrowserFamily.IE)){

					boolean abilitaModalitaIE8 = true;
					if(usaSVG){
						log.debug("Richiesto Accesso per La risorsa protetta.");
						abilitaModalitaIE8 = disabilitaGraficiSVG(browserInfo);
					}

					log.debug("La risorsa richiesta "+(abilitaModalitaIE8 ? "" : "non ")+"verra' visualizzata in modalita compatibilita IE8.");
					//Imposto l'header http necessario per forzare la visualizzazione.
//					if(abilitaModalitaIE8)
//						((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE8");

					// per tutte le versioni
					((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=edge");

					// Risolvo anche il problema di ie9 che non visualizza il contenuto dei file css della libreria Richfaces.
					// Esso invia solo "text/css" all'interno dell' Accept header.
					// la classe HtmlRenderUtils lancia un eccezione poiche' non gestisce questo tipo di Accept
					// allora la soluzione e' una patch del codice oppure aggiornare il valore dell'header Accept per far si che non
					// venga sollevata l'eccezione: applico questa soluzione impostando "text/css, */*" al posto di "text/css".

					String accept = request.getHeader("Accept");

					if ("text/css".equals(accept)) {
						chain.doFilter(new IEFilter().new IE9HttpServletRequestWrapper(request), response);

					}
					chain.doFilter(request, response);
				}
				else if(browserInfo.getBrowserFamily().equals(BrowserFamily.FIREFOX)){
					// solo sui path /a4j/*

					List<String> lst = new ArrayList<String>();
					lst.add("/a4j/");
					if(ContentAuthorizationManager.getInstance().contains(urlRichiesta, lst )){
						log.debug("Applico Fix per le risorse A4j in firefox.");
						// Dalla classe RichFacesFirefox11Filter Filtro per le risorse JSF in firefox

						chain.doFilter(new HttpServletRequestWrapper(request) {
							@Override
							public String getRequestURI() {
								try {
									return URLDecoder.decode(super.getRequestURI(), "UTF-8");
								} catch (UnsupportedEncodingException e) {

									throw new IllegalStateException("Cannot decode request URI.", e);
								}
							}
						}, response);
					}else 
						chain.doFilter(request, response);
				} else {
					chain.doFilter(request, response);
				}
			}catch(Exception e){
				log.debug("Browser non riconosciuto.");
				chain.doFilter(request, response);
			}
		}
	}

	/***************************************************************************
	 * Metodo destroy
	 */
	@Override
	public void destroy() {
		log.debug("DISTRUIZIONE FILTRO: BrowserFilter");
		this.filterConfig = null;
	}

	/***************************************************************************
	 * Init
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;

//		this.excludedPaths = new ArrayList<String>();
//		this.excludedPaths.addAll(Arrays.asList(ContentAuthorizationCostanti.listaPathConsentiti));
		// welcome contiene un grafico che puo' essere visualizzato in modalita' ie>8
//		this.excludedPaths.remove("/pages/welcome.jsf");
	}
	
	
	/**
	 * Controlla che la pagina richiesta sia tra quelle che non necessitano di filtro sui contenuti,
	 * sono "libere" le pagine di login e timeout, e i path delle risorse richiesta dinamicamente dal framework 
	 *	
	 */
	@SuppressWarnings("unchecked")
	public static boolean usaSVG(HttpServletRequest httpServletRequest) {
		int svgLength = 0;
		String svg = null;
		
		
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames(); 
		
		while (parameterNames.hasMoreElements()) {
			String parName = (String) parameterNames.nextElement();
			log.debug("Parametro ["+parName+"] con Valore ["+httpServletRequest.getParameter(parName)+"].");
			if(parName!= null && parName.endsWith(parametroSVG)){
				svg = httpServletRequest.getParameter(parName);
				log.debug("Parametro ["+parName+"] con Valore ["+httpServletRequest.getParameter(parName)+"] Utilizzato per pilotare il disegno dei grafici.");
				
				// controllo solo nei form delle statistiche se sto nella schermata form non devo fare cambio di modalita' se navigo si.
				if(parName.endsWith(parametroSVG_FORM_STATS)){
					 String paramGeneraReport = getParamValue(httpServletRequest, parametroGeneraReport);
					 String paramTipoReport = getParamValue(httpServletRequest, parametroTipoReport);
					 log.debug("Caso speciale Form Statistiche: Parametro ["+parametroGeneraReport+"] con Valore ["+paramGeneraReport+"] Utilizzato per pilotare il disegno dei grafici.");
					 if(StringUtils.isEmpty(paramGeneraReport))
						 svg = null;
					 else {
						 if(StringUtils.isNotEmpty(paramTipoReport) && paramTipoReport.equals(parametroTipoReportTabella)){
							 log.debug("Caso speciale Form Statistiche: Parametro ["+parametroTipoReport+"] con Valore ["+paramTipoReport+"] Visualizzazione del report in forma di tabella.");
							 svg = null;
						 }
					 }
				}
				
				// attivo il controllo SVG solo se ho cliccato nel menu' esitiLive
				if(parName.endsWith(parametroSVG_FORM_MENU)){
					 String paramEsitiLive =  getParamValue(httpServletRequest, parametroEsitiLive);
					 log.debug("Caso speciale Menu': Parametro ["+parametroEsitiLive+"] con Valore ["+paramEsitiLive+"] Utilizzato per pilotare il disegno dei grafici.");
					 if(StringUtils.isEmpty(paramEsitiLive))
						 svg = null;
				}
				
				// attivo il controllo SVG quando il polling dello stato si refresha 
				if(parName.endsWith(parametroSVG_POLL_STATO)){
					
					String[] listaPagineNoIE8 = ContentAuthorizationCostanti.listaPagineNoIE8;
					List<String> listaPagine = Arrays.asList(listaPagineNoIE8);
					boolean thisResource = !Utils.isContentAuthorizationRequiredForThisResource(httpServletRequest, listaPagine);
					
					log.debug("Caso speciale Menu': Parametro ["+parametroSVG_POLL_STATO+"] con Valore ["+thisResource+"] Utilizzato per pilotare il disegno dei grafici.");
					if(!thisResource)
						 svg = null;
				}
				
				break;
			}
		}

		if (svg != null) {
			svgLength = svg.length();
		} 
		
		log.debug("Attivo controllo SVG ["+(svgLength > 0 ? "SI" : "NO" )+"]");
		
		return svgLength > 0;
	}
	
	@SuppressWarnings("unchecked")
	public static String getParamValue(HttpServletRequest httpServletRequest, String paramName){
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames(); 
		
		while (parameterNames.hasMoreElements()) {
			String parName = (String) parameterNames.nextElement();
//			if(parName!= null && parName.endsWith(paramName)){
			if(parName!= null && parName.contains(paramName)){
				String parameterValue = httpServletRequest.getParameter(parName);
				log.debug("Trovato Parametro ["+parName+"] con Valore ["+parameterValue+"].");
				return parameterValue;
			}
		}
		return null;
			
	}

}