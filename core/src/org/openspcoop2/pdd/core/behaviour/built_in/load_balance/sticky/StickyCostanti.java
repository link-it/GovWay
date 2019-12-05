/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it). 
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
package org.openspcoop2.pdd.core.behaviour.built_in.load_balance.sticky;

/**
 * Costanti
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class StickyCostanti  {

	public static final String ROUND_ROBIN = "roundRobin";
	public static final String RANDOM = "random";
	public static final String WEIGHT_RANDOM = "weightRandom";
	public static final String WEIGHT_ROUND_ROBIN = "weightRoundRobin";
	public static final String IP_HASH = "ipHash";
	public static final String LEAST_CONNECTIONS = "leastConnections";
	
	public static final String LOAD_BALANCER_TYPE = "type";
	public static final String LOAD_BALANCER_WEIGHT = "weight";
	
	public static final String STICKY = "sticky";
	public static final String STICKY_TIPO_SELETTORE = "sticky_selettore";
	public static final String STICKY_PATTERN = "sticky_pattern";
	public static final String STICKY_EXPIRE = "sticky_expire";
}
