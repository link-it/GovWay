/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
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

package org.openspcoop2.utils.xacml;

import java.util.List;

import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.ResultType;


/**
 * ResultCombining
 *
 * @author Bussu Giovanni (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ResultCombining {

	public static DecisionType combinePermitOverrides(List<ResultType> resultType) {
		boolean isDeny = false;
		for(ResultType result : resultType) {
			if(result.getDecision().equals(DecisionType.PERMIT)) {
				return DecisionType.PERMIT;
			} else if(result.getDecision().equals(DecisionType.DENY)) {
				isDeny = true;
			} else if(result.getDecision().equals(DecisionType.INDETERMINATE)) {
				return DecisionType.INDETERMINATE;
			}
		}
		
		if(isDeny) {
			return DecisionType.DENY;
		}
		
		return DecisionType.NOT_APPLICABLE;
	}

	public static DecisionType combineDenyOverrides(List<ResultType> resultType) {
		boolean isPermit = false;
		for(ResultType result : resultType) {
			if(result.getDecision().equals(DecisionType.DENY)) {
				return DecisionType.DENY;
			} else if(result.getDecision().equals(DecisionType.INDETERMINATE)) {
				return DecisionType.INDETERMINATE;
			} else if(result.getDecision().equals(DecisionType.PERMIT)) {
				isPermit = true;
			}
		}
		
		if(isPermit) {
			return DecisionType.PERMIT;
		}
		
		return DecisionType.NOT_APPLICABLE;

	}
}
