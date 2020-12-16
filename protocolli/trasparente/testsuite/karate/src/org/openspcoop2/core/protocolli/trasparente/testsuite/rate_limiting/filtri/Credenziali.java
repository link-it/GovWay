package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.filtri;

public class Credenziali {
	
	static final CredenzialiBasic nonFiltrateSoggetto = new CredenzialiBasic("SoggettoNonFiltrato", "SoggettoNonFiltrato");
	static final CredenzialiBasic filtrateSoggetto = new CredenzialiBasic("SoggettoFiltrato", "SoggettoFiltrato");
	
	static final CredenzialiBasic nonFiltrateApplicativo = new CredenzialiBasic("ApplicativoNonFiltrato", "ApplicativoNonFiltrato");
	static final CredenzialiBasic filtrateApplicativo = new CredenzialiBasic("ApplicativoFiltrato", "ApplicativoFiltrato");
	
	static final CredenzialiBasic soggettoRuoloFiltrato = new CredenzialiBasic("SoggettoRuoloFiltrato", "SoggettoRuoloFiltrato");
	static final CredenzialiBasic soggettoRuoloFiltrato2 = new CredenzialiBasic("SoggettoRuoloFiltrato2", "SoggettoRuoloFiltrato2");
	
	static final CredenzialiBasic applicativoRuoloFiltrato = new CredenzialiBasic("ApplicativoRuoloFiltrato", "ApplicativoRuoloFiltrato");
	static final CredenzialiBasic applicativoRuoloFiltrato2 = new CredenzialiBasic("ApplicativoRuoloFiltrato2", "ApplicativoRuoloFiltrato2");
	static final CredenzialiBasic applicativoRuoloNonFiltrato = new CredenzialiBasic("ApplicativoRuoloNonFiltrato", "ApplicativoRuoloNonFiltrato");

	
	static final CredenzialiBasic applicativoSITFFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreFiltrato", "ApplicativoSoggettoInternoTestFruitoreFiltrato");
	static final CredenzialiBasic applicativoSITFNonFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreNonFiltrato", "ApplicativoSoggettoInternoTestFruitoreNonFiltrato");
	
	static final CredenzialiBasic applicativoSITFRuoloFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreRuoloFiltrato", "ApplicativoSoggettoInternoTestFruitoreRuoloFiltrato");
	static final CredenzialiBasic applicativoSITFRuoloNonFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreRuoloNonFiltrato", "ApplicativoSoggettoInternoTestFruitoreRuoloNonFiltrato");

}
