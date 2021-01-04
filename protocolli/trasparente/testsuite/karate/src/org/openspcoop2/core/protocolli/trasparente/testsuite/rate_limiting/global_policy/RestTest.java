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


package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.global_policy;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class RestTest extends ConfigLoader {
	
	@Test
	public void erogazione() {
		testGlobalPolicy(TipoServizio.EROGAZIONE);
	}
	
	
	static void testGlobalPolicy(TipoServizio tipoServizio) {
		// Usiamo l'erogazione già presente per il test numero_richieste
		// visto che li ci sono policy sul numero di richieste parallele passando lo header
		// GovWay-TestSuite-RL-GlobalPolicy=Orario per attivare la policy globale
		// vedo anche che succede agli headers quando c'è una policy globale+policy locale


		final int maxRequests = 5;
		final int windowSize = Utils.getPolicyWindowSize(PolicyAlias.ORARIO);
		Utils.waitForNewHour();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteRest/v1/richieste-simultanee");
		request.addHeader("GovWay-TestSuite-RL-GlobalPolicy", "Orario");
		
		Vector<HttpResponse> responseOk = Utils.makeSequentialRequests(request, maxRequests);
		Vector<HttpResponse> responseBlocked = Utils.makeSequentialRequests(request, maxRequests);
		
		// TODO: Gli header da controllare sono diversi.
		// X-RateLimit-Limit=10, 10;w=3600, X-RateLimit-Remaining=9
		// Sono solo questi due. Siamo sicuri? Chiedi ad andrea.
		
		//org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.checkOkRequests(responseOk, windowSize, maxRequests);
		//org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.checkFailedRequests(responseBlocked, windowSize, maxRequests);
		

	}

}
