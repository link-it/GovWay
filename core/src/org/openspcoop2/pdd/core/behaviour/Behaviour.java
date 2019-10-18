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
package org.openspcoop2.pdd.core.behaviour;

import java.util.ArrayList;
import java.util.List;

import org.openspcoop2.core.id.IDServizioApplicativo;

/**
 * Behaviour
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class Behaviour {

	private BehaviourResponseTo responseTo = new BehaviourResponseTo();
	private IDServizioApplicativo applicativeSyncResponder;
	private List<BehaviourForwardTo> forwardTo = new ArrayList<BehaviourForwardTo>();
	
	public boolean isResponseTo() {
		return this.responseTo!=null && this.responseTo.isResponseTo();
	}
	public BehaviourResponseTo getResponseTo() {
		return this.responseTo;
	}
	public void setResponseTo(BehaviourResponseTo responseTo) {
		this.responseTo = responseTo;
	}
		
	public List<BehaviourForwardTo> getForwardTo() {
		return this.forwardTo;
	}
	public void setForwardTo(List<BehaviourForwardTo> forwardTo) {
		this.forwardTo = forwardTo;
	}
	
	public IDServizioApplicativo getApplicativeSyncResponder() {
		return this.applicativeSyncResponder;
	}
	public void setApplicativeSyncResponder(IDServizioApplicativo applicativeSyncResponder) {
		this.applicativeSyncResponder = applicativeSyncResponder;
	}
}
