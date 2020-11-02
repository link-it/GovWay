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





package org.openspcoop2.core.commons;

import org.openspcoop2.generic_project.expression.IExpression;

/**
 * ExpressionUtils
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ExpressionUtils
{
	public static void enable(IExpression expr, String p) {
		expr.addProperty(p, true);
	}
	public static boolean isEnabled(IExpression expr, String p) {
		Object o = expr.getProperty(p);
		if(o!=null && o instanceof Boolean) {
			return (boolean) o;
		}
		return false;
	}
}