package org.openspcoop2.web.monitor.statistiche.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.web.monitor.core.datamodel.BaseDataModel;
import org.openspcoop2.web.monitor.core.datamodel.ResDistribuzione;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.statistiche.dao.IStatisticheGiornaliere;
import org.openspcoop2.web.monitor.statistiche.dao.StatisticheGiornaliereService;
import org.openspcoop2.web.monitor.statistiche.mbean.DistribuzionePerSoggettoBean;
import org.slf4j.Logger;

public class DistribuzioneSoggettoDM extends BaseDataModel<String, ResDistribuzione, IStatisticheGiornaliere> {

	private static final long serialVersionUID = 500153520162806619L;
	private static Logger log =  LoggerManager.getPddMonitorCoreLogger();
	
	private boolean visualizzaComandiExport = false;
	
	@Override
	public int getRowCount() {
		try {
			this.visualizzaComandiExport = false;
			int count = this.getDataProvider().countAllDistribuzioneSoggetto();
			
			if(count > 0)
				this.visualizzaComandiExport = true;
			
			return count;
			
		} catch (ServiceException e) {
			DistribuzioneSoggettoDM.log.error(e.getMessage(), e);
			return 0;
		}
	}
	
	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		try{	
			if(this.detached){
				for (String key : this.wrappedKeys) {
					setRowKey(key);
					visitor.process(context, key, argument);
				}
			}else{
				int start = ((SequenceRange)range).getFirstRow();
				int limit = ((SequenceRange)range).getRows();

				this.wrappedKeys = new ArrayList<String>();
				List<ResDistribuzione> list =  new ArrayList<ResDistribuzione>();
				
				try {
					list =  this.getDataProvider().findAllDistribuzioneSoggetto(start, limit);
				} catch (ServiceException e) {
					DistribuzioneSoggettoDM.log.error(e.getMessage(), e);
				}
				
				list = DistribuzionePerSoggettoBean.calcolaLabels(list, ((StatisticheGiornaliereService) this.getDataProvider()).getDistribSoggettoSearch().getProtocollo());
				
				for (ResDistribuzione r : list) {
					this.wrappedData.put(r.getRisultato(), r);
					this.wrappedKeys.add(r.getRisultato());
					visitor.process(context, r.getRisultato(), argument);
				}
			}
		} catch (Exception e) {
			DistribuzioneSoggettoDM.log.error(e.getMessage(), e);
		}

	}
	
	public boolean isVisualizzaComandiExport() {
		return this.visualizzaComandiExport;
	}

	public void setVisualizzaComandiExport(boolean visualizzaComandiExport) {
		this.visualizzaComandiExport = visualizzaComandiExport;
	}
	
}
