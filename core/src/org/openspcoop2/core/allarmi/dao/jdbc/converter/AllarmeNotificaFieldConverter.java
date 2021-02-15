package org.openspcoop2.core.allarmi.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import org.openspcoop2.core.allarmi.AllarmeNotifica;
import org.openspcoop2.core.constants.CostantiDB;


/**     
 * AllarmeNotificaFieldConverter
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AllarmeNotificaFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public AllarmeNotificaFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public AllarmeNotificaFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return AllarmeNotifica.model();
	}
	
	@Override
	public TipiDatabase getDatabaseType() throws ExpressionException {
		return this.databaseType;
	}
	


	@Override
	public String toColumn(IField field,boolean returnAlias,boolean appendTablePrefix) throws ExpressionException {
		
		// In the case of columns with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the column containing the alias
		
		if(field.equals(AllarmeNotifica.model().DATA_NOTIFICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_notifica";
			}else{
				return "data_notifica";
			}
		}
		if(field.equals(AllarmeNotifica.model().ID_ALLARME.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(AllarmeNotifica.model().OLD_STATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".old_stato";
			}else{
				return "old_stato";
			}
		}
		if(field.equals(AllarmeNotifica.model().OLD_DETTAGLIO_STATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".old_stato_dettaglio";
			}else{
				return "old_stato_dettaglio";
			}
		}
		if(field.equals(AllarmeNotifica.model().NUOVO_STATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nuovo_stato";
			}else{
				return "nuovo_stato";
			}
		}
		if(field.equals(AllarmeNotifica.model().NUOVO_DETTAGLIO_STATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nuovo_stato_dettaglio";
			}else{
				return "nuovo_stato_dettaglio";
			}
		}
		if(field.equals(AllarmeNotifica.model().HISTORY_ENTRY)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".history_entry";
			}else{
				return "history_entry";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(AllarmeNotifica.model().DATA_NOTIFICA)){
			return this.toTable(AllarmeNotifica.model(), returnAlias);
		}
		if(field.equals(AllarmeNotifica.model().ID_ALLARME.NOME)){
			return this.toTable(AllarmeNotifica.model().ID_ALLARME, returnAlias);
		}
		if(field.equals(AllarmeNotifica.model().OLD_STATO)){
			return this.toTable(AllarmeNotifica.model(), returnAlias);
		}
		if(field.equals(AllarmeNotifica.model().OLD_DETTAGLIO_STATO)){
			return this.toTable(AllarmeNotifica.model(), returnAlias);
		}
		if(field.equals(AllarmeNotifica.model().NUOVO_STATO)){
			return this.toTable(AllarmeNotifica.model(), returnAlias);
		}
		if(field.equals(AllarmeNotifica.model().NUOVO_DETTAGLIO_STATO)){
			return this.toTable(AllarmeNotifica.model(), returnAlias);
		}
		if(field.equals(AllarmeNotifica.model().HISTORY_ENTRY)){
			return this.toTable(AllarmeNotifica.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(AllarmeNotifica.model())){
			return CostantiDB.ALLARMI_NOTIFICHE;
		}
		if(model.equals(AllarmeNotifica.model().ID_ALLARME)){
			return CostantiDB.ALLARMI;
		}


		return super.toTable(model,returnAlias);
		
	}

}
