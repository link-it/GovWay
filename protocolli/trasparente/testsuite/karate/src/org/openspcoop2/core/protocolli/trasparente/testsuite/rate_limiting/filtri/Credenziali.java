package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.filtri;

public class Credenziali {
	
	static final CredenzialiBasic nonFiltrateSoggetto = new CredenzialiBasic("SoggettoNonFiltrato", "SoggettoNonFiltrato");
	static final CredenzialiBasic filtrateSoggetto = new CredenzialiBasic("SoggettoFiltrato", "SoggettoFiltrato");
	
	static final CredenzialiBasic nonFiltrateApplicativo = new CredenzialiBasic("ApplicativoNonFiltrato", "ApplicativoNonFiltrato");
	static final CredenzialiBasic filtrateApplicativo = new CredenzialiBasic("ApplicativoFiltrato", "ApplicativoFiltrato");

}
