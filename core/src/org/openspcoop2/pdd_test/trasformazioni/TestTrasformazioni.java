/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
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

package org.openspcoop2.pdd_test.trasformazioni;

import org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione;
import org.openspcoop2.pdd_test.Costanti;
import org.openspcoop2.utils.test.TestLogger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TestTrasformazioni
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TestTrasformazioni {

	private static final String ID_TEST = "Trasformazioni";
	
	@DataProvider(name="trasformazioniProvider")
	public Object[][] provider(){
		return new Object[][]{
				{TipoTrasformazione.TEMPLATE},
				{TipoTrasformazione.FREEMARKER_TEMPLATE},
				{TipoTrasformazione.FREEMARKER_TEMPLATE_ZIP},
				{TipoTrasformazione.VELOCITY_TEMPLATE},
				{TipoTrasformazione.VELOCITY_TEMPLATE_ZIP},
				{TipoTrasformazione.XSLT},
				{TipoTrasformazione.ZIP},
				{TipoTrasformazione.TGZ},
				{TipoTrasformazione.TAR}
		};
	}
	
	@Test(groups={Costanti.GRUPPO_PDD,Costanti.GRUPPO_PDD+"."+ID_TEST},dataProvider="trasformazioniProvider")
	public void testDynamicReplace(TipoTrasformazione tipo) throws Exception{
		
		TestLogger.info("Run test '"+ID_TEST+"' ...");
		org.openspcoop2.pdd.core.trasformazioni.Test.main(new String[] {tipo.name()});
		TestLogger.info("Run test '"+ID_TEST+"' ok");
		
	}
	
}
