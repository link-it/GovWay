/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2021 Link.it srl (https://link.it).
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
package org.openspcoop2.generic_project.dao.xml;

import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.ExpressionImpl;
import org.openspcoop2.generic_project.expression.impl.formatter.IObjectFormatter;

/**
 * XMLExpression
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class XMLExpression extends ExpressionImpl {

	public XMLExpression() throws ExpressionException {
		super();
	}
	public XMLExpression(XMLPaginatedExpression expression) throws ExpressionException{
		super(expression);
	}
	
	public XMLExpression(IObjectFormatter objectFormatter) throws ExpressionException {
		super(objectFormatter);
	}

}
