/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package org.openspcoop2.message.utils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Messaggio
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 13574 $, $Date: 2018-01-26 12:24:34 +0100 (Fri, 26 Jan 2018) $
 */
public class DumpMessaggioMultipartInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718160136521047108L;
		
	private String contentId;
	private String contentLocation;
	private String contentType;
	private HashMap<String, String> headers = new HashMap<>();
	
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentLocation() {
		return this.contentLocation;
	}

	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	public HashMap<String, String> getHeaders() {
		return this.headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}
	
}
