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
package org.openspcoop2.generic_project.web.impl.jsf2.input.impl;

import org.openspcoop2.generic_project.web.impl.jsf2.input.SelectItem;
import org.openspcoop2.generic_project.web.input.FieldType;
import org.openspcoop2.generic_project.web.input.MultipleCheckBox;

/**
 * Implementazione di un elemento di input di tipo Multiple Checkbox.
 * 
 * @author Pintori Giuliano (pintori@link.it)
 *
 */
public class MultipleCheckBoxImpl extends MultipleChoiceImpl implements MultipleCheckBox<SelectItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MultipleCheckBoxImpl(){
		super();
		
		setType(FieldType.MULTIPLE_CHECKBOX); 
	}

	
}
