package org.openspcoop2.monitor.sdk.condition;

import java.lang.reflect.Constructor;

import org.openspcoop2.utils.TipiDatabase;

import org.openspcoop2.monitor.sdk.constants.StatisticType;
import org.openspcoop2.monitor.sdk.exceptions.FilterFactoryException;
import org.openspcoop2.monitor.sdk.statistic.IStatistic;

/**
 * FilterFactory
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class FilterFactory {
	
	public static IStatisticFilter newFilterStatisticRepository(TipiDatabase tipiDatabase,StatisticType statisticType) throws FilterFactoryException {
		try {
			Class<?> c = Class.forName("org.openspcoop2.monitor.framework.condition.FilterStatisticRepositoryImpl");
			Constructor<?> constructor = c.getConstructor(TipiDatabase.class,StatisticType.class);
			return (IStatisticFilter) constructor.newInstance(tipiDatabase,statisticType);
		} catch (Exception e) {
			throw new FilterFactoryException (e);
		}
	}
	public static IStatisticFilter newFilterStatisticRepository(StatisticsContext searchContext) throws FilterFactoryException {
		try {
			TipiDatabase tipiDatabase = searchContext.getDatabaseType();
			if(tipiDatabase==null)
				throw new Exception("Field 'TipoDatabase' undefinded in SearchContext");
			StatisticType statisticType = searchContext.getStatisticType();
			if(statisticType==null)
				throw new Exception("Field 'StatisticType' undefinded in SearchContext");
			return newFilterStatisticRepository(tipiDatabase,statisticType);
		} catch (Exception e) {
			throw new FilterFactoryException (e);
		}
	}
	
	
	public static IFilter newFilterTransactionRepository(TipiDatabase tipiDatabase) throws FilterFactoryException {
		try {
			Class<?> c = Class.forName("org.openspcoop2.monitor.framework.condition.FilterTransactionRepositoryImpl");
			Constructor<?> constructor = c.getConstructor(TipiDatabase.class);
			return (IFilter) constructor.newInstance(tipiDatabase);
		} catch (Exception e) {
			throw new FilterFactoryException (e);
		}
	}
	public static IFilter newFilterTransactionRepository(Context searchContext) throws FilterFactoryException {
		try {
			TipiDatabase tipiDatabase = searchContext.getDatabaseType();
			if(tipiDatabase==null)
				throw new Exception("Field 'TipoDatabase' undefinded in SearchContext");
			return newFilterTransactionRepository(tipiDatabase);
		} catch (Exception e) {
			throw new FilterFactoryException (e);
		}
	}
	public static IFilter newFilterTransactionRepository(IStatistic context) throws FilterFactoryException {
		try {
			TipiDatabase tipiDatabase = context.getDatabaseType();
			if(tipiDatabase==null)
				throw new Exception("Field 'TipoDatabase' undefinded in IStatistic");
			return newFilterTransactionRepository(tipiDatabase);
		} catch (Exception e) {
			throw new FilterFactoryException (e);
		}
	}

}
