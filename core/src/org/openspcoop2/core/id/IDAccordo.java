/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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



package org.openspcoop2.core.id;


/**
 * Classe utilizzata per rappresentare un identificatore di un Accordo di Servizio nel registro dei Servizi.
 * 
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author Nardi Lorenzo (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class IDAccordo implements java.io.Serializable {

	 /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/* ********  F I E L D S  P R I V A T I  ******** */

	/** Nome dell'accordo */
	protected String nome;
	/** Soggetto Referente (Puo' non essere definito). */
	protected IDSoggetto soggettoReferente;
	/** Versione. */
	protected String versione;


	/* ********  C O S T R U T T O R E  ******** */

		
	public String getNome() {
		return this.nome;
	}
	public IDSoggetto getSoggettoReferente() {
		return this.soggettoReferente;
	}
	public String getVersione() {
		return this.versione;
	}
	
	@Deprecated
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Deprecated
	public void setSoggettoReferente(IDSoggetto soggettoReferente) {
		this.soggettoReferente = soggettoReferente;
	}
	@Deprecated
	public void setVersione(String versione) {
		this.versione = versione;
	}
	
		
	@Override 
	public String toString(){
		StringBuffer bf = new StringBuffer();
		bf.append(this.nome);
		if(this.versione!=null)
			bf.append("["+this.versione+"]");
		if(this.soggettoReferente!=null)
			bf.append("["+this.soggettoReferente.toString()+"]");
		return bf.toString();
	}
		
	@Override 
	public boolean equals(Object object){
		if(object == null)
			return false;
		if(object.getClass().getName().equals(this.getClass().getName()) == false)
			return false;
		IDAccordo id = (IDAccordo) object;
		
		if(this.nome==null){
			if(id.nome!=null)
				return false;
		}else{
			if(this.nome.equals(id.nome)==false)
				return false;
		}

		if(this.versione==null){
			if(id.versione!=null)
				return false;
		}else{
			if(this.versione.equals(id.versione)==false)
				return false;
		}
		
		if(this.soggettoReferente==null){
			if(id.soggettoReferente!=null)
				return false;
		}else{
			if(this.soggettoReferente.equals(id.soggettoReferente)==false)
				return false;
		}
		
		return true;
	}

	// Utile per usare l'oggetto in hashtable come chiave
	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}

	@Override
	public IDAccordo clone(){
		IDAccordo idAccordo = new IDAccordo();
		if(this.nome!=null){
			idAccordo.nome = new String(this.nome);
		}
		if(this.versione!=null){
			idAccordo.versione = new String(this.versione);
		}
		if(this.soggettoReferente!=null){
			idAccordo.soggettoReferente = this.soggettoReferente.clone();
		}
		return idAccordo;
	}
}






