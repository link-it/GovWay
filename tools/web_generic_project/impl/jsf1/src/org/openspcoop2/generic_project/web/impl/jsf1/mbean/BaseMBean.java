/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it).
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
package org.openspcoop2.generic_project.web.impl.jsf1.mbean;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactoryManager;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.CostantiJsf1Impl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DettaglioException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.FiltraException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.NuovoException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.ResetException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.RestoreSearchException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;


/**
 * BaseMBean classe generica che fornisce il supporto alle informazioni che vengono visualizzate in una pagina.
 * 
 * service: interfaccia con il livello service per l'accesso ai dati.
 * selectedElement: in caso di visualizzazione di tipo Elenco -> Dettaglio, 
 * fornisce il supporto per la gestione della selezione di un elemento da parte dell'utente.
 * selectedIds: se i dati sono presentati all'interno di un widget che fornisce il supporto alla selezione multipla,
 * tiene traccia della selezione effettuata.
 * toRemove: se i dati sono presentati all'interno di un widget che consente la cancellazione dei dati,
 * tiene traccia degli elementi da eliminare.
 * selectAll: flag che indica se sono stati selezionati tutti i dati.
 * search: Bean della ricerca relativa alle informazioni visualizzate.
 * 
 * @param <BeanType> tipo dell'oggetto
 * @param <KeyType> tipo della chiave dell'oggetto
 * @param <SearchFormType> form di ricerca.
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author: mergefairy $
 * @version $Rev: 10491 $, $Date: 2015-01-13 10:33:50 +0100 (Tue, 13 Jan 2015) $
 */
public class BaseMBean<BeanType,KeyType,SearchFormType extends SearchForm> { 

	protected IBaseService<BeanType,KeyType,SearchFormType> service;
	protected BeanType selectedElement;
	protected KeyType selectedId;
	protected Map<BeanType, Boolean> selectedIds = new HashMap<BeanType, Boolean>();
	protected ArrayList<BeanType> toRemove;
	private boolean selectedAll = false;
	protected SearchFormType search;
	protected BeanType metadata;
	protected transient Logger log= null;
	protected WebGenericProjectFactory factory;

	// outcome jsf per la navigazione, 
	protected String deleteOutcome = null;
	protected String inviaOutcome = null;
	protected String modificaOutcome = null;
	protected String dettaglioOutcome = null;
	protected String nuovoOutcome = null;
	protected String menuActionOutcome = null;
	protected String filtraOutcome = null;
	protected String restoreSearchOutcome = null;
	protected String annullaOutcome = null;
	protected String resetOutcome = null;

	public BaseMBean() {
		this(null);
	}
	
	public BaseMBean(Logger log) {
		try {
			this.log = log;
			setOutcomes();
			this.factory = WebGenericProjectFactoryManager.getInstance().getWebGenericProjectFactoryByName(CostantiJsf1Impl.FACTORY_NAME);
		} catch (FactoryException e) {
			this.getLog().error(e,e);
		}
	}
	
	public Logger getLog(){
		if(this.log == null)
			this.log = Logger.getLogger(BaseFormMBean.class);
		
		return this.log;
	}

	public WebGenericProjectFactory getFactory() {
		return this.factory;
	}

	public void setFactory(WebGenericProjectFactory factory) {
		this.factory = factory;
	}


	public void setService(IBaseService<BeanType, KeyType,SearchFormType> service) {
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	public BeanType getSelectedElement() throws Exception{
		if(this.selectedElement==null){
			try{
				ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
				this.selectedElement = ((Class<BeanType>)parameterizedType.getActualTypeArguments()[0]).newInstance();
			}catch (Exception e) {
				this.getLog().error(e,e);
				throw e;
			}
		}
		return this.selectedElement;
	}

	public void setSelectedElement(BeanType selectedElement) {
		this.selectedElement = selectedElement;
	}

	public Map<BeanType, Boolean> getSelectedIds() {
		return this.selectedIds;
	}

	public void setSelectedIds(Map<BeanType, Boolean> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public List<FacesMessage> getMessages(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> ite = ctx.getMessages();
		ArrayList<FacesMessage> list = new ArrayList<FacesMessage>();
		while (ite.hasNext()) {
			list.add(ite.next());
		}
		return list;
	}

	public boolean isSelectedAll() {
		return this.selectedAll;
	}

	public void setSelectedAll(boolean selectedAll) {
		this.selectedAll = selectedAll;
	}

	public SearchFormType getSearch() {
		return this.search;
	}

	public void setSearch(SearchFormType search) {
		this.search = search;
	}

	@SuppressWarnings("unchecked")
	public BeanType getMetadata() throws Exception{
		if(this.metadata==null){
			try{
				ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
				this.metadata = ((Class<BeanType>)parameterizedType.getActualTypeArguments()[0]).newInstance();
			}catch (Exception e) {
				this.getLog().error(e,e);
				throw e;
			}
		}
		return this.metadata;
	}

	public void setMetadata(BeanType metadata) {
		this.metadata = metadata;
	}

	/**
	 * Listener eseguito prima di aggiungere un nuovo ricerca, setta a null il selectedElement
	 * in modo da "scordarsi" i valori gia' impostati.
	 * @param ae
	 */
	public void addNewListener(ActionEvent ae){
		this.selectedElement = null;
		this.selectedId = null;
	}

	public KeyType getSelectedId() {
		return this.selectedId;
	}

	public void setSelectedId(KeyType selectedId) {
		this.selectedId = selectedId;
	}


	/** Metodi che vengono utilizzati all'interno della pagina*/

	public String invia(){
		try{
			return _invia();
		}catch (InviaException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo invia: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _invia() throws InviaException{
		try{
			this.service.store(this.selectedElement);
		}catch(Exception e){
			throw new InviaException(e);
		}
		return this.getInviaOutcome();
	}


	public String modifica(){
		try{
			return _modifica();
		}catch (ModificaException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo modifica: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _modifica() throws ModificaException{
		return this.getModificaOutcome();
	}
	public String delete(){
		try{
			return _delete();
		}catch (DeleteException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo delete: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}

		//		this.toRemove = new ArrayList<BeanType>();
		//		Iterator<BeanType> it = this.selectedIds.keySet().iterator();
		//		while (it.hasNext()) {
		//			BeanType elem = it.next();
		//			if(this.selectedIds.get(elem).booleanValue()){
		//				this.toRemove.add(elem);
		//				it.remove();
		//			}
		//		}
		//
		//		String m = null;
		//		for (BeanType elem : this.toRemove) {
		//			try{
		//				this.service.delete(elem);
		//			}catch (Exception e) {
		//				//				FacesContext ctx = FacesContext.getCurrentInstance();
		//				m = Utils.getInstance().getMessageFromCommonsResourceBundle("DELETE_ERROR") + ":" + e.getMessage();
		//				//				MessageUtils.addErrorMsg(m + ": " + e.getMessage());
		//				//				String m = Utils.getMessageFromResourceBundle(ctx.getApplication().getMessageBundle(), "DELETE_ERROR", new String[]{elem.toString()}, ctx.getViewRoot().getLocale());
		//				//				ctx.addMessage(null, new FacesMessage(m,e.getLocalizedMessage()));
		//			}
		//
		//		}


		//		return m;
	}

	protected String _delete() throws DeleteException{
		try{
			this.toRemove = new ArrayList<BeanType>();
			Iterator<BeanType> it = this.selectedIds.keySet().iterator();
			while (it.hasNext()) {
				BeanType elem = it.next();
				if(this.selectedIds.get(elem).booleanValue()){
					this.toRemove.add(elem);
					it.remove();
				}
			}
			for (BeanType elem : this.toRemove) {
				this.service.delete(elem);
			}

			return getDeleteOutcome();
		}catch (Exception e) {
			throw new DeleteException(e);
		}
	}

	public String dettaglio(){
		try{
			return _dettaglio();
		}catch (DettaglioException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo dettaglio: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _dettaglio() throws DettaglioException{
		try{
			// do nothing
		}catch(Exception e){
			throw new DettaglioException(e);
		}
		return this.getDettaglioOutcome();
	}


	public String nuovo(){
		try{
			return _nuovo();
		}catch (NuovoException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo nuovo: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _nuovo() throws NuovoException{
		try{
			// do nothing
		}catch(Exception e){
			throw new NuovoException(e);
		}
		return this.getNuovoOutcome();
	}


	public String annulla(){
		try{
			return _annulla();
		}catch (AnnullaException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo annulla: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _annulla() throws AnnullaException{
		try{
			// do nothing
		}catch(Exception e){
			throw new AnnullaException(e);
		}
		return this.getAnnullaOutcome();
	}


	public String filtra(){
		try{
			return _filtra();
		}catch (FiltraException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo filtra: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _filtra() throws FiltraException{
		try{
			// do nothing
		}catch(Exception e){
			throw new FiltraException(e);
		}
		return this.getFiltraOutcome();
	}


	public String menuAction(){
		try{
			return _menuAction();
		}catch (MenuActionException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo menu' action: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _menuAction() throws MenuActionException{
		try{
			// do nothing
		}catch(Exception e){
			throw new MenuActionException(e);
		}
		return this.getMenuActionOutcome();
	}


	public String reset(){
		try{
			return _reset();
		}catch (ResetException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo reset: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _reset() throws ResetException{
		try{
			this.search.reset();
		}catch(Exception e){
			throw new ResetException(e);
		}
		return this.getResetOutcome();
	}


	public String restoreSearch(){
		try{
			return _restoreSearch();
		}catch (RestoreSearchException e){
			this.getLog().error("Si e' verificato un errore durante l'esecuzione del metodo restore search: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(e.getMessage());
			return null;
		}
	}

	protected String _restoreSearch() throws RestoreSearchException{
		try{
			this.search.setRestoreSearch(true); 
		}catch(Exception e){
			throw new RestoreSearchException(e);
		}
		return this.getRestoreSearchOutcome();

	}

	// Getter degli outcome JSF

	protected void setOutcomes(){
		this.annullaOutcome = null;
		this.deleteOutcome = null;
		this.dettaglioOutcome = null;
		this.filtraOutcome = null;
		this.inviaOutcome = null;
		this.menuActionOutcome= null;
		this.modificaOutcome = null;
		this.nuovoOutcome = null;
		this.resetOutcome = null;
		this.restoreSearchOutcome = null;
	}

	public String getDeleteOutcome() {
		return this.deleteOutcome;
	}

	public String getInviaOutcome() {
		return this.inviaOutcome;
	}

	public String getModificaOutcome() {
		return this.modificaOutcome;
	}

	public String getDettaglioOutcome() {
		return this.dettaglioOutcome;
	}

	public String getNuovoOutcome() {
		return this.nuovoOutcome;
	}

	public String getMenuActionOutcome() {
		return this.menuActionOutcome;
	}

	public String getFiltraOutcome() {
		return this.filtraOutcome;
	}

	public String getRestoreSearchOutcome() {
		return this.restoreSearchOutcome;
	}

	public String getAnnullaOutcome() {
		return this.annullaOutcome;
	}
	public String getResetOutcome() {
		return this.resetOutcome;
	}
}
