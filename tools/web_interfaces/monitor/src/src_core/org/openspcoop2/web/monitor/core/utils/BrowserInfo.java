package org.openspcoop2.web.monitor.core.utils;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class BrowserInfo implements Serializable{

	public static final String USER_AGENT_HEADER_NAME = "User-Agent";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	public enum BrowserFamily{ IE, CHROME, FIREFOX, SAFARI, OPERA}

	private Double version;
	private String browserName;
	private BrowserFamily browserFamily;
	private String userAgentString;

	public Double getVersion() {
		return this.version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public String getBrowserName() {
		return this.browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public BrowserFamily getBrowserFamily() {
		return this.browserFamily;
	}
	public void setBrowserFamily(BrowserFamily browserFamily) {
		this.browserFamily = browserFamily;
	}
	public String getUserAgentString() {
		return this.userAgentString;
	}
	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}

	/***
	 * 
	 * Funzione che calcola lo user agent
	 * 
	 * @param Information
	 * @return
	 */
	public static BrowserInfo getBrowserInfo(String Information) {
		BrowserInfo browserInfo = new BrowserInfo();


		browserInfo.setUserAgentString(Information);
		String browser = browserInfo.getUserAgentString();
		String info[] = null;
		BrowserFamily bf = null;

		if(browser.contains("MSIE")){
			String subsString = browser.substring( browser.indexOf("MSIE"));
			info = (subsString.split(";")[0]).split(" ");
			bf =BrowserFamily.IE;
		}
		else if(browser.contains("msie")){
			String subsString = browser.substring( browser.indexOf("msie"));
			info = (subsString.split(";")[0]).split(" ");
			bf =BrowserFamily.IE;
		}
		//		else if(browser.contains("rv:")){
		//			String subsString = browser.substring( browser.indexOf("rv"));
		//			int idx = subsString.indexOf(")");
		//			if(idx > -1)
		//				info = ((String)subsString.subSequence(0, idx)).split(":");
		//		}
		else if(browser.contains("Trident")){
			String subsString = browser.substring( browser.indexOf("Trident"));
			info = new String[2];

			info[0] = (subsString.split(";")[0]).split("/")[0];

			int idx = (subsString.split(";")[1]).indexOf(")");
			if(idx > -1)
				info[1] = ((String)(subsString.split(";")[1]).subSequence(0, idx)).split(":")[1];
			else
				info[1] = "";
			bf =BrowserFamily.IE;
		}
		else if(browser.contains("Firefox")){
			String subsString = browser.substring( browser.indexOf("Firefox"));
			info = (subsString.split(" ")[0]).split("/");
			bf =BrowserFamily.FIREFOX;
		}
		else if(browser.contains("Chrome")){
			String subsString = browser.substring( browser.indexOf("Chrome"));
			info = (subsString.split(" ")[0]).split("/");
			bf =BrowserFamily.CHROME;
		}
		else if(browser.contains("Opera")){
			String subsString = browser.substring( browser.indexOf("Opera"));
			info = (subsString.split(" ")[0]).split("/");
			bf =BrowserFamily.OPERA;
		}
		else if(browser.contains("Safari")){
			String subsString = browser.substring( browser.indexOf("Safari"));
			info = (subsString.split(" ")[0]).split("/");
			bf =BrowserFamily.SAFARI;
		}

		browserInfo.setBrowserName(info[0]);
		try{
			browserInfo.setVersion(StringUtils.isNotEmpty(info[1]) ? Double.parseDouble(info[1]) : null);
		}catch(NumberFormatException e){
			// versione non riconosciuta
			browserInfo.setVersion(null);
		}
		browserInfo.setBrowserFamily(bf); 

		return browserInfo;
	}


	public static BrowserInfo getBrowserInfo(FacesContext context) throws Exception{
		if(context != null){
			ExternalContext externalContext = context.getExternalContext();
			if(externalContext != null){
				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				String userAgent = request.getHeader(USER_AGENT_HEADER_NAME);
				return getBrowserInfo(userAgent);
			}
		}

		return null;
	}
	
	public static HttpServletResponse getResponse(FacesContext context) throws Exception{
		if(context != null){
			ExternalContext externalContext = context.getExternalContext();
			if(externalContext != null){
				HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
				return response;
			}
		}

		return null;
	}

}