package org.openspcoop2.core.transazioni.dao.jdbc;

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.slf4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.openspcoop2.core.transazioni.IdTransazioneApplicativoServer;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.beans.UpdateModel;

import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;

import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;

import org.openspcoop2.core.transazioni.TransazioneApplicativoServer;
import org.openspcoop2.core.transazioni.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCTransazioneApplicativoServerServiceImpl
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCTransazioneApplicativoServerServiceImpl extends JDBCTransazioneApplicativoServerServiceSearchImpl
	implements IJDBCServiceCRUDWithId<TransazioneApplicativoServer, IdTransazioneApplicativoServer, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TransazioneApplicativoServer transazioneApplicativoServer, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				


		// Object transazioneApplicativoServer
		sqlQueryObjectInsert.addInsertTable(this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()));
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().ID_TRANSAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().SERVIZIO_APPLICATIVO_EROGATORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_USCITA_RICHIESTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_ACCETTAZIONE_RISPOSTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_INGRESSO_RISPOSTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().RICHIESTA_USCITA_BYTES,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().RISPOSTA_INGRESSO_BYTES,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().CODICE_RISPOSTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_PRIMO_TENTATIVO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_ULTIMO_ERRORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().CODICE_RISPOSTA_ULTIMO_ERRORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().ULTIMO_ERRORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().NUMERO_TENTATIVI,false),"?");

		// Insert transazioneApplicativoServer
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getTransazioneApplicativoServerFetch().getKeyGeneratorObject(TransazioneApplicativoServer.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getIdTransazione(),TransazioneApplicativoServer.model().ID_TRANSAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getServizioApplicativoErogatore(),TransazioneApplicativoServer.model().SERVIZIO_APPLICATIVO_EROGATORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getDataUscitaRichiesta(),TransazioneApplicativoServer.model().DATA_USCITA_RICHIESTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getDataAccettazioneRisposta(),TransazioneApplicativoServer.model().DATA_ACCETTAZIONE_RISPOSTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getDataIngressoRisposta(),TransazioneApplicativoServer.model().DATA_INGRESSO_RISPOSTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getRichiestaUscitaBytes(),TransazioneApplicativoServer.model().RICHIESTA_USCITA_BYTES.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getRispostaIngressoBytes(),TransazioneApplicativoServer.model().RISPOSTA_INGRESSO_BYTES.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getCodiceRisposta(),TransazioneApplicativoServer.model().CODICE_RISPOSTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getDataPrimoTentativo(),TransazioneApplicativoServer.model().DATA_PRIMO_TENTATIVO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getDataUltimoErrore(),TransazioneApplicativoServer.model().DATA_ULTIMO_ERRORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getCodiceRispostaUltimoErrore(),TransazioneApplicativoServer.model().CODICE_RISPOSTA_ULTIMO_ERRORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getUltimoErrore(),TransazioneApplicativoServer.model().ULTIMO_ERRORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(transazioneApplicativoServer.getNumeroTentativi(),TransazioneApplicativoServer.model().NUMERO_TENTATIVI.getFieldType())
		);
		transazioneApplicativoServer.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTransazioneApplicativoServer oldId, TransazioneApplicativoServer transazioneApplicativoServer, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdTransazioneApplicativoServer(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = transazioneApplicativoServer.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: transazioneApplicativoServer.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			transazioneApplicativoServer.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, transazioneApplicativoServer, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, TransazioneApplicativoServer transazioneApplicativoServer, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		


		// Object transazioneApplicativoServer
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()));
		boolean isUpdate_transazioneApplicativoServer = true;
		java.util.List<JDBCObject> lstObjects_transazioneApplicativoServer = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().ID_TRANSAZIONE,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getIdTransazione(), TransazioneApplicativoServer.model().ID_TRANSAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().SERVIZIO_APPLICATIVO_EROGATORE,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getServizioApplicativoErogatore(), TransazioneApplicativoServer.model().SERVIZIO_APPLICATIVO_EROGATORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_USCITA_RICHIESTA,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getDataUscitaRichiesta(), TransazioneApplicativoServer.model().DATA_USCITA_RICHIESTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_ACCETTAZIONE_RISPOSTA,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getDataAccettazioneRisposta(), TransazioneApplicativoServer.model().DATA_ACCETTAZIONE_RISPOSTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_INGRESSO_RISPOSTA,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getDataIngressoRisposta(), TransazioneApplicativoServer.model().DATA_INGRESSO_RISPOSTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().RICHIESTA_USCITA_BYTES,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getRichiestaUscitaBytes(), TransazioneApplicativoServer.model().RICHIESTA_USCITA_BYTES.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().RISPOSTA_INGRESSO_BYTES,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getRispostaIngressoBytes(), TransazioneApplicativoServer.model().RISPOSTA_INGRESSO_BYTES.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().CODICE_RISPOSTA,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getCodiceRisposta(), TransazioneApplicativoServer.model().CODICE_RISPOSTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_PRIMO_TENTATIVO,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getDataPrimoTentativo(), TransazioneApplicativoServer.model().DATA_PRIMO_TENTATIVO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().DATA_ULTIMO_ERRORE,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getDataUltimoErrore(), TransazioneApplicativoServer.model().DATA_ULTIMO_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().CODICE_RISPOSTA_ULTIMO_ERRORE,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getCodiceRispostaUltimoErrore(), TransazioneApplicativoServer.model().CODICE_RISPOSTA_ULTIMO_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().ULTIMO_ERRORE,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getUltimoErrore(), TransazioneApplicativoServer.model().ULTIMO_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTransazioneApplicativoServerFieldConverter().toColumn(TransazioneApplicativoServer.model().NUMERO_TENTATIVI,false), "?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(transazioneApplicativoServer.getNumeroTentativi(), TransazioneApplicativoServer.model().NUMERO_TENTATIVI.getFieldType()));
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_transazioneApplicativoServer.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_transazioneApplicativoServer) {
			// Update transazioneApplicativoServer
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_transazioneApplicativoServer.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTransazioneApplicativoServer id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getTransazioneApplicativoServerFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTransazioneApplicativoServer id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getTransazioneApplicativoServerFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTransazioneApplicativoServer id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getTransazioneApplicativoServerFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getTransazioneApplicativoServerFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getTransazioneApplicativoServerFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getTransazioneApplicativoServerFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTransazioneApplicativoServer oldId, TransazioneApplicativoServer transazioneApplicativoServer, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, transazioneApplicativoServer,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, transazioneApplicativoServer,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, TransazioneApplicativoServer transazioneApplicativoServer, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, transazioneApplicativoServer,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, transazioneApplicativoServer,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TransazioneApplicativoServer transazioneApplicativoServer) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (transazioneApplicativoServer.getId()!=null) && (transazioneApplicativoServer.getId()>0) ){
			longId = transazioneApplicativoServer.getId();
		}
		else{
			IdTransazioneApplicativoServer idTransazioneApplicativoServer = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,transazioneApplicativoServer);
			longId = this.findIdTransazioneApplicativoServer(jdbcProperties,log,connection,sqlQueryObject,idTransazioneApplicativoServer,false);
			if(longId == null){
				return; // entry not exists
			}
		}		
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object transazioneApplicativoServer
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getTransazioneApplicativoServerFieldConverter().toTable(TransazioneApplicativoServer.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete transazioneApplicativoServer
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTransazioneApplicativoServer idTransazioneApplicativoServer) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdTransazioneApplicativoServer(jdbcProperties, log, connection, sqlQueryObject, idTransazioneApplicativoServer, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getTransazioneApplicativoServerFieldConverter()));

	}

	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {

		java.util.List<Long> lst = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, new JDBCPaginatedExpression(expression));
		
		for(Long id : lst) {
			this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		}
		
		return new NonNegativeNumber(lst.size());
	
	}



	// -- DB
	
	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotImplementedException, Exception {
		this._delete(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
}
