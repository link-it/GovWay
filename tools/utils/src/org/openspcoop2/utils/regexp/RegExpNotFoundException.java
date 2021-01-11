/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2021 Link.it srl (https://link.it). 
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



package org.openspcoop2.utils.regexp;

/**	
 * Contiene la definizione di una eccezione lanciata dalle classi del package org.openspcoop2.utils.regexp
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */


public class RegExpNotFoundException extends Exception {

	 public RegExpNotFoundException(String message, Throwable cause)
		{
			super(message, cause);
			// TODO Auto-generated constructor stub
		}
		public RegExpNotFoundException(Throwable cause)
		{
			super(cause);
			// TODO Auto-generated constructor stub
		}
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		public RegExpNotFoundException() {
			super();
	    }
		public RegExpNotFoundException(String msg) {
	        super(msg);
	    }
}
