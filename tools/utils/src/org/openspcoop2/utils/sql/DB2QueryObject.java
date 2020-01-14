/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
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

package org.openspcoop2.utils.sql;

import java.util.Iterator;
import java.util.Random;

import org.openspcoop2.utils.TipiDatabase;

/**
 * SQLServerQueryObject
 * 
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DB2QueryObject extends SQLQueryObjectCore {
	//private boolean sottoselect = false;


	public DB2QueryObject(TipiDatabase tipoDatabase) {
		super(tipoDatabase);
	}

	@Override
	public String getUnixTimestampConversion(String column){
		return "((((CAST (DAYS("+column+"-CURRENT_TIMEZONE) - DAYS('1970-01-01') AS BIGINT)*86400)+MIDNIGHT_SECONDS("+
				column+"-CURRENT_TIMEZONE))*1000)+(MICROSECOND("+
				column+")/1000))";
	}
	
	@Override
	public String getDiffUnixTimestamp(String columnMax,String columnMin){
		return "( "+getUnixTimestampConversion(columnMax)+" - "+getUnixTimestampConversion(columnMin)+" )";
	}
	
	
	


	@Override
	public ISQLQueryObject addSelectAvgTimestampField(String field,
			String alias) throws SQLQueryObjectException {
		if(field==null)
			throw new SQLQueryObjectException("field avg non puo' essere null");
		// Trasformo in UNIX_TIMESTAMP
		String fieldSQL = "avg("+this.getUnixTimestampConversion(field)+")";
		if(alias != null){
			//fieldSQL = fieldSQL + " as "+alias;
			fieldSQL = fieldSQL + this.getDefaultAliasFieldKeyword() + alias;
		}
		this._engine_addSelectField(null,fieldSQL,null,false,true);
		this.fieldNames.add(alias);
		return this;
	}


	@Override
	public ISQLQueryObject addSelectMaxTimestampField(String field, String alias)
			throws SQLQueryObjectException {
		if(field==null)
			throw new SQLQueryObjectException("field avg non puo' essere null");
		// Trasformo in UNIX_TIMESTAMP
		String fieldSQL = "max("+this.getUnixTimestampConversion(field)+")";
		if(alias != null){
			//fieldSQL = fieldSQL + " as "+alias;
			fieldSQL = fieldSQL + this.getDefaultAliasFieldKeyword() + alias;
		}
		this._engine_addSelectField(null,fieldSQL,null,false,true);
		this.fieldNames.add(alias);
		return this;
	}


	@Override
	public ISQLQueryObject addSelectMinTimestampField(String field, String alias)
			throws SQLQueryObjectException {
		if(field==null)
			throw new SQLQueryObjectException("field avg non puo' essere null");
		// Trasformo in UNIX_TIMESTAMP
		String fieldSQL = "min("+this.getUnixTimestampConversion(field)+")";
		if(alias != null){
			//fieldSQL = fieldSQL + " as "+alias;
			fieldSQL = fieldSQL + this.getDefaultAliasFieldKeyword() + alias;
		}
		this._engine_addSelectField(null,fieldSQL,null,false,true);
		this.fieldNames.add(alias);
		return this;
	}


	@Override
	public ISQLQueryObject addSelectSumTimestampField(String field, String alias)
			throws SQLQueryObjectException {
		if(field==null)
			throw new SQLQueryObjectException("field avg non puo' essere null");
		// Trasformo in UNIX_TIMESTAMP
		String fieldSQL = "sum("+this.getUnixTimestampConversion(field)+")";
		if(alias != null){
			//fieldSQL = fieldSQL + " as "+alias;
			fieldSQL = fieldSQL + this.getDefaultAliasFieldKeyword() + alias;
		}
		this._engine_addSelectField(null,fieldSQL,null,false,true);
		this.fieldNames.add(alias);
		return this;
	}





	@Override
	public ISQLQueryObject addFromTable(ISQLQueryObject subSelect)
			throws SQLQueryObjectException {
		StringBuffer bf = new StringBuffer();
		bf.append(" ( ");
		bf.append(subSelect.createSQLQuery());
		bf.append(" ) ");
		// Devo forzare l'utilizzo di un alias su una sottoselct dentro il FROM	
		// Genero Tre caratteri alafabetici casuali per dare un alias del tipo "tabellaXXX"
		StringBuffer subselectalias = new StringBuffer();
		subselectalias.append("tabella");
		Random rand = new Random();
		int rnd; 
		char base;
		for (int count=0 ; count < 3; count++ ){
			rnd = (rand.nextInt(52) );
			base = (rnd < 26) ? 'A' : 'a';
			subselectalias.append((char) (base + rnd % 26));			
		}
		this.addFromTable(bf.toString(),subselectalias.toString());
		return this;
	}



	@Override
	protected EscapeSQLConfiguration getEscapeSQLConfiguration(){
		
		EscapeSQLConfiguration config = new EscapeSQLConfiguration();
		config.addCharacter('_');
		config.addCharacter('%');
		config.addCharacter('\\');
		config.setUseEscapeClausole(true);
		config.setEscape('\\');
		
		// special
		config.addCharacterWithOtherEscapeChar('\'','\'');
		
		return config;
	}




	@Override
	public String createSQLQuery() throws SQLQueryObjectException{
		return this.createSQLQuery(false);
	}
	private String createSQLQuery(boolean union) throws SQLQueryObjectException{
		
		this.precheckBuildQuery();
		
		StringBuffer bf = new StringBuffer();

		bf.append("SELECT ");

		if(this.isSelectDistinct())
			bf.append(" DISTINCT ");

		// forzatura di indici
		if( (this.offset>=0 || this.limit>=0) == false ){
			Iterator<String> itForceIndex = this.forceIndexTableNames.iterator();
			while(itForceIndex.hasNext()){
				bf.append(" "+itForceIndex.next()+" ");
			}
		}

		// select field
		if(this.fields.size()==0){
			bf.append("*");
		}else{
			Iterator<String> it = this.fields.iterator();
			boolean first = true;
			while(it.hasNext()){
				if(!first)
					bf.append(",");
				else
					first = false;

				String field = it.next();
				if( this.offset>=0 ){

					field = this.normalizeField(field,false);

				} 
				bf.append(field);
			}
		}

		bf.append(getSQL(false,false,false,union));

		//if( this.offset>=0 || this.limit>=0)
		//	System.out.println("SQL ["+bf.toString()+"]");

		// Limit (senza offset)
		if(this.offset<0 && this.limit>0){
			bf.append(" FETCH FIRST ");
			bf.append(this.limit);
			bf.append(" ROWS ONLY ");
		}

		return bf.toString();
	}





	@Override
	public String _createSQLDelete() throws SQLQueryObjectException {

		StringBuffer bf = new StringBuffer();

		bf.append("DELETE ");

		bf.append(getSQL(true,false,false,false));
		return bf.toString();
	}





	private String getSQL(boolean delete,boolean update,boolean conditions,boolean union) throws SQLQueryObjectException {	
		StringBuffer bf = new StringBuffer();

		if(this.selectForUpdate){
			this.checkSelectForUpdate(update, delete, union);
		}
		
		if(update==false && conditions==false){
						
			// From
			bf.append(" FROM ");


			// Se e' presente Offset o Limit
			//if( (this.offset>=0 || this.limit>=0) && (delete==false)) {
			// Rilascio vincolo di order by in caso di limit impostato.
			// Il vincolo rimane per l'offset, per gestire le select annidate di qualche implementazioni come Oracle,SQLServer ...
			if( (this.offset>=0) && (delete==false)){

				//java.util.List<String> aliasOrderByDistinct = new java.util.ArrayList<String>();

				//if(this.isSelectDistinct()==false)			
				bf.append(" ( SELECT ");
				//else
				//	bf.append(" ( SELECT DISTINCT ");

				// Questa istruzione ci vuole altrimenti in presenza di order by, group by si ottiene il seguente errore:
				// The ORDER BY clause is invalid in views, inline functions, derived tables, subqueries, and common table expressions, unless TOP or FOR XML is also specified.
				// In questo segmento di select forse non server?
				// bf.append("TOP 100 PERCENT ");

				Iterator<String> itForceIndex = this.forceIndexTableNames.iterator();
				while(itForceIndex.hasNext()){
					bf.append(" "+itForceIndex.next()+" ");
				}

				if(this.isSelectDistinct()){
					if(this.fields.size()==0){
						Iterator<String> it = this.tableNames.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;
							String t = it.next();
							bf.append(t+".*");
						}
					}else{
						Iterator<String> it = this.fields.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;

							String field = it.next();
							if( this.offset>=0 ){

								field = this.normalizeField(field,false);

							} 
							bf.append(field);
						}
					}
				}
				else{
					if(this.fields.size()==0){
						Iterator<String> it = this.tableNames.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;
							String t = it.next();
							bf.append(t+".*");
						}
					}else{
						Iterator<String> it = this.fields.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;
							String f = it.next();
							bf.append(f);
						}
					}
				}


				bf.append(" , ROW_NUMBER() OVER ( ORDER BY ");

				// Condizione OrderBy
				if(this.orderBy.size()==0){
					throw new SQLQueryObjectException("Condizioni di OrderBy richieste");
				}
				if(this.orderBy.size()>0){
					Iterator<String> it = this.orderBy.iterator();
					boolean first = true;
					while(it.hasNext()){
						if(!first)
							bf.append(",");
						else
							first = false;
						String condizione = it.next();
						if(this.alias.containsKey(condizione)){
							if(this.isSelectDistinct()){
								bf.append(condizione);
							}
							else{
								bf.append(this.alias.get(condizione));
							}
						}else{
							bf.append(this.normalizeField(condizione));
						}
						boolean sortTypeAsc = this.sortTypeAsc;
						if(this.orderBySortType.containsKey(condizione)){
							sortTypeAsc = this.orderBySortType.get(condizione);
						}
						if(sortTypeAsc){
							bf.append(" ASC ");
						}else{
							bf.append(" DESC ");
						}
					}
				}

				bf.append(" ) AS rowNumber ");


				bf.append(" FROM ");

				if(this.isSelectDistinct()){

					bf.append(" ( SELECT DISTINCT ");

					if(this.fields.size()==0){
						Iterator<String> it = this.tableNames.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;
							String t = it.next();
							bf.append(t+".*");
						}
					}else{
						Iterator<String> it = this.fields.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;
							String f = it.next();
							bf.append(f);
						}
					}

					bf.append(" FROM ");
				}


				// Table dove effettuare la ricerca 'FromTable'
				if(this.tables.size()==0){
					throw new SQLQueryObjectException("Tabella di ricerca (... FROM Table ...) non definita");
				}else{
					Iterator<String> it = this.tables.iterator();
					boolean first = true;
					while(it.hasNext()){
						if(!first)
							bf.append(",");
						else
							first = false;
						bf.append(it.next());
					}
				}

				// Condizioni di Where
				if(this.conditions.size()>0){

					bf.append(" WHERE ");

					if(this.notBeforeConditions){
						bf.append(" NOT ( ");
					}

					for(int i=0; i<this.conditions.size(); i++){

						if(i>0){
							if(this.andLogicOperator){
								bf.append(" AND ");
							}else{
								bf.append(" OR ");
							}
						}
						String cond = this.conditions.get(i);				
						bf.append(cond);
					}

					if(this.notBeforeConditions){
						bf.append(" )");
					}
				}

				// Condizione GroupBy
				if((this.getGroupByConditions().size()>0) && (delete==false)){
					bf.append(" GROUP BY ");
					Iterator<String> it = this.getGroupByConditions().iterator();
					boolean first = true;
					while(it.hasNext()){
						if(!first)
							bf.append(",");
						else
							first = false;
						bf.append(it.next());
					}
				}


				if(this.isSelectDistinct()){

					// Order solo in presenza di select distinct
					if((this.orderBy.size()>0) && (delete==false)){
						bf.append(" ORDER BY ");
						Iterator<String> it = this.orderBy.iterator();
						boolean first = true;
						while(it.hasNext()){
							String column = it.next();
							if(!first)
								bf.append(",");
							else
								first = false;
							bf.append(column);
							boolean sortTypeAsc = this.sortTypeAsc;
							if(this.orderBySortType.containsKey(column)){
								sortTypeAsc = this.orderBySortType.get(column);
							}
							if(sortTypeAsc){
								bf.append(" ASC ");
							}else{
								bf.append(" DESC ");
							}
						}
					}

					// Devo forzare l'utilizzo di un alias su una sottoselct dentro il FROM			
					bf.append(" ) ");
					bf.append(this.getDefaultAliasFieldKeyword());
					bf.append("tableSelectRaw");
					// Genero Tre caratteri alfabetici casuali per dare un alias del tipo "tabellaXXX"
					Random rand = new Random();
					int rnd; 
					char base;
					for (int count=0 ; count < 3; count++ ){
						rnd = (rand.nextInt(52) );
						base = (rnd < 26) ? 'A' : 'a';
						bf.append((char) (base + rnd % 26));

					}
				}


				// Devo forzare l'utilizzo di un alias su una sottoselct dentro il FROM			
				bf.append(" ) ");
				bf.append(this.getDefaultAliasFieldKeyword());
				bf.append("tabella");
				// Genero Tre caratteri alfabetici casuali per dare un alias del tipo "tabellaXXX"
				Random rand = new Random();
				int rnd; 
				char base;
				for (int count=0 ; count < 3; count++ ){
					rnd = (rand.nextInt(52) );
					base = (rnd < 26) ? 'A' : 'a';
					bf.append((char) (base + rnd % 26));

				}
				bf.append(" WHERE ( ");

				if(this.offset>=0){
					bf.append(" rowNumber > ");
					bf.append(this.offset);
				}
				if(this.limit>=0){
					if(this.offset>=0)
						bf.append(" AND");
					bf.append(" rowNumber <=  ");
					if(this.offset>=0)
						bf.append(this.offset+this.limit);
					else
						bf.append(this.limit);
				}
				bf.append(" )");


				// ORDER BY FINALE
				if(union==false){
					if(this.orderBy.size()>0){
						bf.append(" ORDER BY ");
						Iterator<String> it = this.orderBy.iterator();
						boolean first = true;
						while(it.hasNext()){
							if(!first)
								bf.append(",");
							else
								first = false;
							String field = it.next();
							
							bf.append(this.normalizeField(field));	
						
							boolean sortTypeAsc = this.sortTypeAsc;
							if(this.orderBySortType.containsKey(field)){
								sortTypeAsc = this.orderBySortType.get(field);
							}
							if(sortTypeAsc){
								bf.append(" ASC ");
							}else{
								bf.append(" DESC ");
							}
						}
					}
				}

				/* 
				 * OLD ALIAS
			if(aliasOrderByDistinct.size()>0){
				bf.append(" ORDER BY ");
				Iterator<String> it = aliasOrderByDistinct.iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					bf.append(it.next());
				}
				if(this.sortTypeAsc){
					bf.append(" ASC ");
				}else{
					bf.append(" DESC ");
				}
			}
				 */
				
			}else{

				// Offset non presente

				// Table dove effettuare la ricerca 'FromTable'
				if(this.tables.size()==0){
					throw new SQLQueryObjectException("Tabella di ricerca (... FROM Table ...) non definita");
				}else{

					if(delete && this.tables.size()>2)
						throw new SQLQueryObjectException("Non e' possibile effettuare una delete con piu' di una tabella alla volta");

					Iterator<String> it = this.tables.iterator();
					boolean first = true;
					while(it.hasNext()){
						if(!first)
							bf.append(",");
						else
							first = false;
						bf.append(it.next());
					}
				}

				// Condizioni di Where
				if(this.conditions.size()>0){
					bf.append(" WHERE ");

					if(this.notBeforeConditions){
						bf.append("NOT (");
					}

					for(int i=0; i<this.conditions.size(); i++){
						if(i>0){
							if(this.andLogicOperator){
								bf.append(" AND ");
							}else{
								bf.append(" OR ");
							}
						}
						bf.append(this.conditions.get(i));
					}

					if(this.notBeforeConditions){
						bf.append(")");
					}

				}

				// Condizione GroupBy
				if((this.getGroupByConditions().size()>0) && (delete==false)){
					bf.append(" GROUP BY ");
					Iterator<String> it = this.getGroupByConditions().iterator();
					boolean first = true;
					while(it.hasNext()){
						if(!first)
							bf.append(",");
						else
							first = false;
						bf.append(it.next());
					}
				}

				// Condizione OrderBy
				if(union==false){
					if((this.orderBy.size()>0) && (delete==false)){
						bf.append(" ORDER BY ");
						Iterator<String> it = this.orderBy.iterator();
						boolean first = true;
						while(it.hasNext()){
							String column = it.next();
							if(!first)
								bf.append(",");
							else
								first = false;
							bf.append(column);
							boolean sortTypeAsc = this.sortTypeAsc;
							if(this.orderBySortType.containsKey(column)){
								sortTypeAsc = this.orderBySortType.get(column);
							}
							if(sortTypeAsc){
								bf.append(" ASC ");
							}else{
								bf.append(" DESC ");
							}
						}
					}
				}
				
				// ForUpdate
				if(this.selectForUpdate){
					bf.append(" FOR UPDATE ");
				}
			}
		}else{

			// UPDATE, conditions

			// Condizioni di Where
			if(this.conditions.size()>0){

				if(conditions==false)
					bf.append(" WHERE ");

				if(this.notBeforeConditions){
					bf.append("NOT (");
				}

				for(int i=0; i<this.conditions.size(); i++){
					if(i>0){
						if(this.andLogicOperator){
							bf.append(" AND ");
						}else{
							bf.append(" OR ");
						}
					}
					bf.append(this.conditions.get(i));
				}

				if(this.notBeforeConditions){
					bf.append(")");
				}

			}
			
			// Non genero per le condizioni, per update viene sollevata eccezione prima
			// ForUpdate
//			if(this.selectForUpdate){
//				bf.append(" FOR UPDATE ");
//			}
		}

		return bf.toString();
	}
	
	
	@Override
	public String createSQLUnion(boolean unionAll,
			ISQLQueryObject... sqlQueryObject) throws SQLQueryObjectException {

		// Controllo parametro su cui effettuare la UNION
		this.checkUnionField(false,sqlQueryObject);

		if(this.selectForUpdate){
			this.checkSelectForUpdate(false, false, true);
		}
		
		StringBuffer bf = new StringBuffer();

		// Non ha senso, la union fa gia la distinct, a meno di usare la unionAll ma in quel caso non si vuole la distinct
		// if(this.isSelectDistinct())
		//	bf.append(" DISTINCT ");

		// Se e' presente Offset o Limit
		// Rilascio vincolo di order by in caso di limit impostato.
		// Il vincolo rimane per l'offset, per gestire le select annidate di qualche implementazioni come Oracle,SQLServer ...
		//if( (this.offset>=0 || this.limit>=0) ){
		if( this.offset>=0 ){

			bf.append("SELECT * from ");

			bf.append(" ( SELECT ");

			if(this.fields.size()==0){
				Iterator<String> it = this.tableNames.iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					String t = it.next();
					bf.append(t+".*");
				}
			}else{
				Iterator<String> it = this.fields.iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					String f = it.next();
					bf.append(f);
				}
			}

			bf.append(" , ROW_NUMBER() OVER ( ORDER BY ");

			// Condizione OrderBy
			if(this.orderBy.size()==0){
				throw new SQLQueryObjectException("Condizioni di OrderBy richieste");
			}
			if(this.orderBy.size()>0){
				Iterator<String> it = this.orderBy.iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					String condizione = it.next();
					if(this.alias.containsKey(condizione)){
						bf.append(this.alias.get(condizione));
					}else{
						bf.append(condizione);
					}
					boolean sortTypeAsc = this.sortTypeAsc;
					if(this.orderBySortType.containsKey(condizione)){
						sortTypeAsc = this.orderBySortType.get(condizione);
					}
					if(sortTypeAsc){
						bf.append(" ASC ");
					}else{
						bf.append(" DESC ");
					}
				}
			}

			bf.append(" ) AS rowNumber ");

			// Table dove effettuare la ricerca 'FromTable'
			bf.append(" FROM ( ");

			for(int i=0; i<sqlQueryObject.length; i++){

				if(((DB2QueryObject)sqlQueryObject[i]).selectForUpdate){
					try{
						((DB2QueryObject)sqlQueryObject[i]).checkSelectForUpdate(false, false, true);
					}catch(Exception e){
						throw new SQLQueryObjectException("Parametro SqlQueryObject["+i+"] non valido: "+e.getMessage());
					}
				}
				
				if(i>0){
					bf.append(" UNION ");
					if(unionAll){
						bf.append(" ALL ");
					}
				}

				bf.append("( ");

				bf.append(((DB2QueryObject)sqlQueryObject[i]).createSQLQuery(true));

				bf.append(") ");
			}

			bf.append(" ) as subquery"+getSerial()+" ");

			// Condizioni di Where
			if(this.conditions.size()>0){

				bf.append(" WHERE ");

				if(this.notBeforeConditions){
					bf.append(" NOT ( ");
				}

				for(int i=0; i<this.conditions.size(); i++){

					if(i>0){
						if(this.andLogicOperator){
							bf.append(" AND ");
						}else{
							bf.append(" OR ");
						}
					}
					String cond = this.conditions.get(i);
					bf.append(cond);
				}

				if(this.notBeforeConditions){
					bf.append(" )");
				}
			}

			// Condizione GroupBy
			if((this.getGroupByConditions().size()>0)){
				bf.append(" GROUP BY ");
				Iterator<String> it = this.getGroupByConditions().iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					bf.append(it.next());
				}
			}

			bf.append(" ) as subquery"+getSerial()+" ");

			bf.append(" WHERE ( ");
			if(this.offset>=0){
				bf.append(" rowNumber > ");
				bf.append(this.offset);
			}
			if(this.limit>=0){
				if(this.offset>=0)
					bf.append(" AND");
				bf.append(" rowNumber <=  ");
				if(this.offset>=0)
					bf.append(this.offset+this.limit);
				else
					bf.append(this.limit);
			}
			bf.append(" )");


		}else{

			// no offset

			bf.append("SELECT ");


			Iterator<String> itForceIndex = this.forceIndexTableNames.iterator();
			while(itForceIndex.hasNext()){
				bf.append(" "+itForceIndex.next()+" ");
			}

			if(this.fields.size()==0){
				bf.append("*");
			}else{
				Iterator<String> it = this.fields.iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					String f = it.next();
					bf.append(f);
				}
			}

			bf.append(" FROM ( ");

			for(int i=0; i<sqlQueryObject.length; i++){

				if(((DB2QueryObject)sqlQueryObject[i]).selectForUpdate){
					try{
						((DB2QueryObject)sqlQueryObject[i]).checkSelectForUpdate(false, false, true);
					}catch(Exception e){
						throw new SQLQueryObjectException("Parametro SqlQueryObject["+i+"] non valido: "+e.getMessage());
					}
				}
				
				if(i>0){
					bf.append(" UNION ");
					if(unionAll){
						bf.append(" ALL ");
					}
				}

				bf.append("( ");

				bf.append(((DB2QueryObject)sqlQueryObject[i]).createSQLQuery(true));

				bf.append(") ");
			}

			bf.append(" ) as subquery"+getSerial()+" ");


			// Condizione GroupBy
			if((this.getGroupByConditions().size()>0)){
				bf.append(" GROUP BY ");
				Iterator<String> it = this.getGroupByConditions().iterator();
				boolean first = true;
				while(it.hasNext()){
					if(!first)
						bf.append(",");
					else
						first = false;
					bf.append(it.next());
				}
			}

			// Condizione OrderBy
			if(this.orderBy.size()>0){
				bf.append(" ORDER BY ");
				Iterator<String> it = this.orderBy.iterator();
				boolean first = true;
				while(it.hasNext()){
					String column = it.next();
					if(!first)
						bf.append(",");
					else
						first = false;
					bf.append(column);
					boolean sortTypeAsc = this.sortTypeAsc;
					if(this.orderBySortType.containsKey(column)){
						sortTypeAsc = this.orderBySortType.get(column);
					}
					if(sortTypeAsc){
						bf.append(" ASC ");
					}else{
						bf.append(" DESC ");
					}
				}
			}

			// Limit (senza offset)
			if(this.offset<0 && this.limit>0){
				bf.append(" FETCH FIRST ");
				bf.append(this.limit);
				bf.append(" ROWS ONLY ");
			}
		}

		return bf.toString();
	}

	@Override
	public String createSQLUnionCount(boolean unionAll, String aliasCount,
			ISQLQueryObject... sqlQueryObject) throws SQLQueryObjectException {

		// Controllo parametro su cui effettuare la UNION
		this.checkUnionField(true,sqlQueryObject);

		if(aliasCount==null){
			throw new SQLQueryObjectException("Alias per il count non definito");
		}

		StringBuffer bf = new StringBuffer();

		bf.append("SELECT count(*) "+this.getDefaultAliasFieldKeyword()+" ");
		bf.append(aliasCount);
		bf.append(" FROM ( ");

		bf.append( this.createSQLUnion(unionAll, sqlQueryObject) );

		bf.append(" ) as subquery"+getSerial()+" ");

		return bf.toString();
	}

	@Override
	public String _createSQLUpdate() throws SQLQueryObjectException {
		
		StringBuffer bf = new StringBuffer();
		bf.append("UPDATE ");
		bf.append(this.updateTable);
		bf.append(" SET ");
		for(int i=0; i<this.updateFieldsName.size(); i++){
			if(i>0)
				bf.append(" , ");
			bf.append(this.updateFieldsName.get(i));
			bf.append(" = ");
			bf.append(this.updateFieldsValue.get(i));
		}
		bf.append(getSQL(false,true,false,false));
		return bf.toString();
	}








	/* ---------------- WHERE CONDITIONS ------------------ */

	@Override
	public String _createSQLConditions() throws SQLQueryObjectException {
		
		StringBuffer bf = new StringBuffer();
		bf.append(getSQL(false,false,true,false));
		return bf.toString();
	}



}
