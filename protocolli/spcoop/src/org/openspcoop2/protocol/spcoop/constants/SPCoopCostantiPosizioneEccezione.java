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
package org.openspcoop2.protocol.spcoop.constants;

/**
*
* @author Poli Andrea (apoli@link.it)
* @author $Author$
* @version $Rev$, $Date$
*/
public enum SPCoopCostantiPosizioneEccezione {

	ECCEZIONE_FORMATO_NON_CORRETTO_POSIZIONE("Intestazione"),
	ECCEZIONE_FORMATO_INTESTAZIONE_NON_CORRETTO_POSIZIONE_ACTOR("Intestazione/actor"),
	ECCEZIONE_FORMATO_INTESTAZIONE_NON_CORRETTO_POSIZIONE_MUST_UNDERSTAND("Intestazione/mustUnderstand"),
	ECCEZIONE_FORMATO_INTESTAZIONE_NON_CORRETTO_POSIZIONE_NAMESPACE("Intestazione/namespace"),
	ECCEZIONE_FORMATO_INTESTAZIONE_NON_CORRETTO_POSIZIONE_INTESTAZIONE_MESSAGGIO("IntestazioneMessaggio"),
	ECCEZIONE_FORMATO_INTESTAZIONE_NON_CORRETTO_POSIZIONE_MESSAGGIO("Messaggio"),
	ECCEZIONE_FORMATO_CORPO_NON_CORRETTO_POSIZIONE("Body"),
	ECCEZIONE_MITTENTE_SCONOSCIUTO_POSIZIONE("Mittente"),
	ECCEZIONE_MITTENTE_SCONOSCIUTO_POSIZIONE_IDENTIFICATIVO_PARTE("Mittente/IdentificativoParte"),
	ECCEZIONE_MITTENTE_SCONOSCIUTO_POSIZIONE_IDENTIFICATIVO_PARTE_TIPO("Mittente/IdentificativoParte/tipo"),
	ECCEZIONE_MITTENTE_SCONOSCIUTO_POSIZIONE_IDENTIFICATIVO_PARTE_IND_TELEMATICO("Mittente/IdentificativoParte/indirizzoTelematico"),
	ECCEZIONE_DESTINATARIO_SCONOSCIUTO_POSIZIONE("Destinatario"),
	ECCEZIONE_DESTINATARIO_SCONOSCIUTO_POSIZIONE_IDENTIFICATIVO_PARTE("Destinatario/IdentificativoParte"),
	ECCEZIONE_DESTINATARIO_SCONOSCIUTO_POSIZIONE_IDENTIFICATIVO_PARTE_TIPO("Destinatario/IdentificativoParte/tipo"),
	ECCEZIONE_DESTINATARIO_SCONOSCIUTO_POSIZIONE_IDENTIFICATIVO_PARTE_IND_TELEMATICO("Destinatario/IdentificativoParte/indirizzoTelematico"),
	ECCEZIONE_PROFILO_COLLABORAZIONE_SCONOSCIUTO_POSIZIONE("ProfiloCollaborazione"),
	ECCEZIONE_PROFILO_COLLABORAZIONE_SCONOSCIUTO_POSIZIONE_SERVIZIO_CORRELATO("ProfiloCollaborazione/servizioCorrelato"),
	ECCEZIONE_PROFILO_COLLABORAZIONE_SCONOSCIUTO_POSIZIONE_TIPO_SERVIZIO_CORRELATO("ProfiloCollaborazione/tipo"),
	ECCEZIONE_COLLABORAZIONE_SCONOSCIUTA_POSIZIONE("Collaborazione"),
	ECCEZIONE_SERVIZIO_SCONOSCIUTO_POSIZIONE("Servizio"),
	ECCEZIONE_SERVIZIO_SCONOSCIUTO_POSIZIONE_TIPO("Servizio/tipo"),
	ECCEZIONE_AZIONE_SCONOSCIUTA_POSIZIONE("Azione"),
	ECCEZIONE_ID_MESSAGGIO_NON_DEFINITO_POSIZIONE("Messaggio/Identificatore"),
	ECCEZIONE_RIFERIMENTO_MESSAGGIO_NON_DEFINITO_POSIZIONE("Messaggio/RiferimentoMessaggio"),
	ECCEZIONE_ID_MESSAGGIO_NON_VALIDO_POSIZIONE("Messaggio/Identificatore"),
	ECCEZIONE_RIFERIMENTO_MESSAGGIO_NON_VALIDO_POSIZIONE("Messaggio/RiferimentoMessaggio"),
	ECCEZIONE_SCADENZA_NON_VALIDA_POSIZIONE("Messaggio/Scadenza"),
	ECCEZIONE_PROFILO_TRASMISSIONE_NON_VALIDO_POSIZIONE("ProfiloTrasmissione"),
	ECCEZIONE_PROFILO_TRASMISSIONE_NON_VALIDO_POSIZIONE_INOLTRO("ProfiloTrasmissione/inoltro"),
	ECCEZIONE_PROFILO_TRASMISSIONE_NON_VALIDO_POSIZIONE_CONFERMA_RICEZIONE("ProfiloTrasmissione/confermaRicezione"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE("Sequenza"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_NUMERO_PROGRESSIVO("Sequenza/numeroProgressivo"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_NUMERO_PROGRESSIVO_BUSTA_CAPOSTIPITE("Sequenza/numeroProgressivo/!1bustaCapostipite"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_TIPO_MITTENTE("Sequenza/Mittente/tipo"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_MITTENTE("Sequenza/Mittente"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_TIPO_DESTINATARIO("Sequenza/Destinatario/tipo"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_DESTINATARIO("Sequenza/Destinatario"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_TIPO_SERVIZIO("Sequenza/Servizio/tipo"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_SERVIZIO("Sequenza/Servizio"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_AZIONE("Sequenza/Azione"),
	ECCEZIONE_SEQUENZA_NON_VALIDA_POSIZIONE_COLLABORAZIONE("Sequenza/Collaborazione"),
	ECCEZIONE_MANIFEST_POSIZIONE("SOAPBody"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE("SOAPBody/Descrizione"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_NAMESPACE("SOAPBody/Descrizione/namespace"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO("SOAPBody/Descrizione/DescrizioneMessaggio"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_HREF("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/href"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_ROLE("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/role"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_ROLE_PRINCIPALE_DUPLICATO("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/role ("+
			SPCoopCostanti.MANIFEST_KEY_ROLE_RICHIESTA+"/"+SPCoopCostanti.MANIFEST_KEY_ROLE_RISPOSTA+") non univoco"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_ROLE_PRINCIPALE_ASSENTE("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/role ("+
			SPCoopCostanti.MANIFEST_KEY_ROLE_RICHIESTA+"/"+SPCoopCostanti.MANIFEST_KEY_ROLE_RISPOSTA+") non esistente"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_ID("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/id"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_SCHEMA("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/Schema"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_SCHEMA_POSIZIONE("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/Schema/posizione"),
	ECCEZIONE_MANIFEST_POSIZIONE_DESCRIZIONE_DESCRIZIONE_MESSAGGIO_RIFERIMENTO_TITOLO("SOAPBody/Descrizione/DescrizioneMessaggio/Riferimento/Titolo"),
	ECCEZIONE_LISTA_RISCONTRI_NON_VALIDA_POSIZIONE("ListaRiscontri"),
	ECCEZIONE_LISTA_RISCONTRI_NON_VALIDA_POSIZIONE_RISCONTRO("ListaRiscontri/Riscontro"),
	ECCEZIONE_LISTA_RISCONTRI_NON_VALIDA_POSIZIONE_RISCONTRO_IDENTIFICATORE("ListaRiscontri/Riscontro/Identificatore"),
	ECCEZIONE_LISTA_RISCONTRI_NON_VALIDA_POSIZIONE_RISCONTRO_ORA_REGISTRAZIONE("ListaRiscontri/Riscontro/OraRegistrazione"),
	ECCEZIONE_LISTA_RISCONTRI_NON_VALIDA_POSIZIONE_RISCONTRO_ORA_REGISTRAZIONE_TEMPO("ListaRiscontri/Riscontro/OraRegistrazione/tempo"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE("ListaTrasmissioni"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE("ListaTrasmissioni/Trasmissione"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_ORIGINE("ListaTrasmissioni/Trasmissione/Origine"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_ORIGINE_IDENTIFICATIVO_PARTE("ListaTrasmissioni/Trasmissione/Origine/IdentificativoParte"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_ORIGINE_IDENTIFICATIVO_PARTE_TIPO("ListaTrasmissioni/Trasmissione/Origine/IdentificativoParte/tipo"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_ORIGINE_IDENTIFICATIVO_PARTE_IND_TELEMATICO("ListaTrasmissioni/Trasmissione/Origine/IdentificativoParte/indirizzoTelematico"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_DESTINAZIONE("ListaTrasmissioni/Trasmissione/Destinazione"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_DESTINAZIONE_IDENTIFICATIVO_PARTE("ListaTrasmissioni/Trasmissione/Destinazione/IdentificativoParte"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_DESTINAZIONE_IDENTIFICATIVO_PARTE_TIPO("ListaTrasmissioni/Trasmissione/Destinazione/IdentificativoParte/tipo"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_DESTINAZIONE_IDENTIFICATIVO_PARTE_IND_TELEMATICO("ListaTrasmissioni/Trasmissione/Destinazione/IdentificativoParte/indirizzoTelematico"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_ORA_REGISTRAZIONE("ListaTrasmissioni/Trasmissione/OraRegistrazione"),
	ECCEZIONE_LISTA_TRASMISSIONI_NON_VALIDA_POSIZIONE_TRASMISSIONE_ORA_REGISTRAZIONE_TEMPO("ListaTrasmissioni/Trasmissione/OraRegistrazione/tempo"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE("ListaEccezioni"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_CONTESTO_CODIFICA("ListaEccezioni/contestoCodifica"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_CODICE_ECCEZIONE("ListaEccezioni/codiceEccezione"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_RILEVANZA("ListaEccezioni/rilevanza"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_POSIZIONE("ListaEccezioni/posizione"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_SOAP_FAULT("ListaEccezioni/SOAPFault"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_SOAP_FAULT_STRING("ListaEccezioni/SOAPFault/FaultString"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_SOAP_FAULT_CODE("ListaEccezioni/SOAPFault/FaultCode"),
	ECCEZIONE_LISTA_ECCEZIONI_NON_VALIDA_POSIZIONE_SOAP_FAULT_DETAILS_PRESENTI("ListaEccezioni/SOAPFault/Details"),
	ECCEZIONE_MESSAGGIO_SCADUTO_POSIZIONE("Messaggio/Scadenza"),
	ECCEZIONE_TRASPARENZA_TEMPORALE_NON_SUPPORTATA_POSIZIONE("Sequenza"),
	ECCEZIONE_ORA_REGISTRAZIONE_NON_VALIDA_POSIZIONE("Messaggio/OraRegistrazione"),
	ECCEZIONE_ORA_REGISTRAZIONE_NON_VALIDA_POSIZIONE_TEMPO("Messaggio/OraRegistrazione/tempo");

	private final String posizione;

	SPCoopCostantiPosizioneEccezione(String posizione){
		this.posizione = posizione;
	}

	@Override
	public String toString(){
		return this.posizione;
	}

}
