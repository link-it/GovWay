package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

public class Credenziali {
	
	static public final CredenzialiBasic nonFiltrateSoggetto = new CredenzialiBasic("SoggettoNonFiltrato", "SoggettoNonFiltrato");
	static public final CredenzialiBasic filtrateSoggetto = new CredenzialiBasic("SoggettoFiltrato", "SoggettoFiltrato");
	
	static public final CredenzialiBasic nonFiltrateApplicativo = new CredenzialiBasic("ApplicativoNonFiltrato", "ApplicativoNonFiltrato");
	static public final CredenzialiBasic filtrateApplicativo = new CredenzialiBasic("ApplicativoFiltrato", "ApplicativoFiltrato");
	
	static public final CredenzialiBasic soggettoRuoloFiltrato = new CredenzialiBasic("SoggettoRuoloFiltrato", "SoggettoRuoloFiltrato");
	static public final CredenzialiBasic soggettoRuoloFiltrato2 = new CredenzialiBasic("SoggettoRuoloFiltrato2", "SoggettoRuoloFiltrato2");
	
	static public final CredenzialiBasic applicativoRuoloFiltrato = new CredenzialiBasic("ApplicativoRuoloFiltrato", "ApplicativoRuoloFiltrato");
	static public final CredenzialiBasic applicativoRuoloFiltrato2 = new CredenzialiBasic("ApplicativoRuoloFiltrato2", "ApplicativoRuoloFiltrato2");
	static public final CredenzialiBasic applicativoRuoloNonFiltrato = new CredenzialiBasic("ApplicativoRuoloNonFiltrato", "ApplicativoRuoloNonFiltrato");

	
	static public final CredenzialiBasic applicativoSITFFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreFiltrato", "ApplicativoSoggettoInternoTestFruitoreFiltrato");
	static public final CredenzialiBasic applicativoSITFNonFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreNonFiltrato", "ApplicativoSoggettoInternoTestFruitoreNonFiltrato");
	
	static public final CredenzialiBasic applicativoSITFRuoloFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreRuoloFiltrato", "ApplicativoSoggettoInternoTestFruitoreRuoloFiltrato");
	static public final CredenzialiBasic applicativoSITFRuoloNonFiltrato = new CredenzialiBasic("ApplicativoSoggettoInternoTestFruitoreRuoloNonFiltrato", "ApplicativoSoggettoInternoTestFruitoreRuoloNonFiltrato");

}
