package org.openspcoop2.web.monitor.core.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.Expression;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.openspcoop2.generic_project.dao.IServiceSearchWithId;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.richfaces.model.FilterField;
import org.richfaces.model.Modifiable;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;
import org.slf4j.Logger;

import org.openspcoop2.web.monitor.core.bean.AbstractCoreSearchForm;
import org.openspcoop2.web.monitor.core.dao.ISearchFormService;

/***
 * SortableBaseDataModel Estende la classe {@link BaseDataModel} aggiungendo la funzionalita' di ordinamento delle colonne della tabella.
 *  
 * @author pintori
 *
 * @param <K> Tipo della chiave del bean da visualizzare
 * @param <T> Tipo del bean da visualizzare
 * @param <D> Tipo del DataProvider
 * @param <S> Tipo del SearchForm
 */
public abstract class SortableBaseDataModel<K, T, D, S extends AbstractCoreSearchForm> extends BaseDataModelWithSearchForm<K, T, D, S> implements Modifiable{
	/** */
	private static final long serialVersionUID = 2954923950179861809L;
	/** */
//	protected SortOrder sortOrder = SortOrder.DESC;
	/** */
//	protected String sortField = getDefaultSortField();
	
	private static Logger log = LoggerWrapperFactory.getLogger(SortableBaseDataModel.class);
	
//	private Map<String, Ordering> sortOrders = new HashMap<String, Ordering>();
	
	@Override
	public void modify(List<FilterField> filterFields,
			List<SortField2> sortFields) {
		log.debug("L'utente ha modificato l'ordinamento dei risultati.");
		if (sortFields != null && !sortFields.isEmpty())
	    {
	        SortField2 sortField2 = sortFields.get(0);
	        Expression expression = sortField2.getExpression();
	        String expressionStr = expression.getExpressionString();
	        
	        if (!expression.isLiteralText())
	        {
	            expressionStr = expressionStr.replaceAll("[#|$]{1}\\{.*?\\.", "").replaceAll("\\}", "");
	        }
	        
	        this.setSortField(expressionStr);
	        log.debug("Nuovo Sort Field ["+this.getSortField()+"]");
	 	        
	        Ordering ordering = sortField2.getOrdering();
	 
	        this.aggiornaSortOrder(expressionStr, ordering);
	      
	        if (ordering == Ordering.DESCENDING)
	        {
	            this.setSortOrder(SortOrder.DESC);
	        }
	        else
	        {
	            this.setSortOrder(SortOrder.ASC);
	        }
	    }
	}
	
	private void aggiornaSortOrder(String field, Ordering order) {
		Map<String, Ordering> tmpMap = new HashMap<String, Ordering>();
		for (String f : this.getSortOrders().keySet()) {
			if(f.equals(field)){
				tmpMap.put(f, order);
			} else {
				tmpMap.put(f, Ordering.UNSORTED);
			}
		}
		
		this.getSortOrders().clear();
		this.getSortOrders().putAll(tmpMap); 
	}

	/**
	 * @see org.ajax4jsf.model.ExtendedDataModel#walk(javax.faces.context.FacesContext,
	 *      org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument)	throws IOException	{
		try{
			boolean usaBuffer = (this.detached || this.wrappedKeys != null); // && isSortObjectNull;
//			log.debug("Condizione walk ["+usaBuffer+"]"); 
			
			if (usaBuffer){
				for (final K key : this.wrappedKeys) {
					setRowKey(key);
					visitor.process(context, key, argument);
				}
			} else {
				this.checkDataProvider();
				int start = 0; int limit = 0;
				AbstractCoreSearchForm searchForm =  null;
				if(this.dataProvider instanceof ISearchFormService) {
					searchForm = ((ISearchFormService<T, K, AbstractCoreSearchForm>)this.dataProvider).getSearch();

					if(searchForm.isUseCount()) {
						// ripristino la ricerca.
						if(searchForm.isRestoreSearch()){
							start = searchForm.getStart();
							limit = searchForm.getLimit();
							searchForm.setRestoreSearch(false);

							int pageIndex = (start / limit) + 1;
							//					searchForm.setPageIndex(pageIndex);
							searchForm.setCurrentPage(pageIndex);
							// Aggiorno valori paginazione
							range = new SequenceRange(start,limit);
						}
						else{
							start = ((SequenceRange)range).getFirstRow();
							limit = ((SequenceRange)range).getRows();
						}

						//				log.debug("Richiesti Record S["+start+"] L["+limit+"], FiltroPagina ["+searchForm.getCurrentPage()+"]"); 

						searchForm.setStart(start);
						searchForm.setLimit(limit); 
					}else {
						// se non uso la count allora start e limit sono gestiti dai tasti nella pagina
						start = searchForm.getStart();
						limit = searchForm.getLimit();
					}
				}
				if(this.dataProvider instanceof IServiceSearchWithId){
					start = ((SequenceRange)range).getFirstRow();
					limit = ((SequenceRange)range).getRows();
				}
//				final int start = ((SequenceRange) range).getFirstRow();
//				final int limit = ((SequenceRange) range).getRows();

				this.wrappedKeys = new ArrayList<K>();

				List<T> bufferList = findObjects(start, limit, this.getSortField(), this.getSortOrder());
				if(searchForm != null) {
					searchForm.setCurrentSearchSize(bufferList != null ? bufferList.size() : 0);
				}
				for (final T obj : bufferList) {
					this.wrappedData.put(getId(obj), obj);
					this.wrappedKeys.add(getId(obj));
					visitor.process(context,getId(obj) , argument);
				}
			}
		}catch(Exception e){
			log.error("Errore durante la walk: "+e.getMessage(),e); 
		}
	}
	
	/**
	 * @return String
	 */
	public abstract String getDefaultSortField();

	public abstract SortOrder getSortOrder();

	public abstract void setSortOrder(SortOrder sortOrder);

	public abstract String getSortField(); 

	public abstract void setSortField(String sortField);

	public abstract Map<String, Ordering> getSortOrders();

	public abstract void setSortOrders(Map<String, Ordering> sortOrders) ;
}