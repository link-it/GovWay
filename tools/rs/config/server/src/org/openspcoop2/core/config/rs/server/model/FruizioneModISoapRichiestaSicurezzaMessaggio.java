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
package org.openspcoop2.core.config.rs.server.model;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class FruizioneModISoapRichiestaSicurezzaMessaggio  {
  
  @Schema(description = "")
  private ModISicurezzaMessaggioSoapAlgoritmoFirma algoritmo = null;
  
  @Schema(description = "")
  private ModISicurezzaMessaggioSoapFormaCanonicaXml formaCanonicaXml = null;
  
  @Schema(description = "")
  private ModISicurezzaMessaggioSoapRiferimentoX509 riferimentoX509 = null;
  
  @Schema(description = "")
  private Boolean certificateChain = null;
  
  @Schema(description = "")
  private Boolean includiSignatureToken = null;
  
  @Schema(description = "")
  private Integer timeToLive = 300;
  
  @Schema(description = "")
  private String wsaTo = null;
  
  @Schema(description = "")
  private FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteCodiceEnte = null;
  
  @Schema(description = "")
  private FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteUserid = null;
  
  @Schema(description = "")
  private FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteIndirizzoIp = null;
 /**
   * Get algoritmo
   * @return algoritmo
  **/
  @JsonProperty("algoritmo")
  @Valid
  public ModISicurezzaMessaggioSoapAlgoritmoFirma getAlgoritmo() {
    return this.algoritmo;
  }

  public void setAlgoritmo(ModISicurezzaMessaggioSoapAlgoritmoFirma algoritmo) {
    this.algoritmo = algoritmo;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio algoritmo(ModISicurezzaMessaggioSoapAlgoritmoFirma algoritmo) {
    this.algoritmo = algoritmo;
    return this;
  }

 /**
   * Get formaCanonicaXml
   * @return formaCanonicaXml
  **/
  @JsonProperty("forma_canonica_xml")
  @Valid
  public ModISicurezzaMessaggioSoapFormaCanonicaXml getFormaCanonicaXml() {
    return this.formaCanonicaXml;
  }

  public void setFormaCanonicaXml(ModISicurezzaMessaggioSoapFormaCanonicaXml formaCanonicaXml) {
    this.formaCanonicaXml = formaCanonicaXml;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio formaCanonicaXml(ModISicurezzaMessaggioSoapFormaCanonicaXml formaCanonicaXml) {
    this.formaCanonicaXml = formaCanonicaXml;
    return this;
  }

 /**
   * Get riferimentoX509
   * @return riferimentoX509
  **/
  @JsonProperty("riferimento_x509")
  @Valid
  public ModISicurezzaMessaggioSoapRiferimentoX509 getRiferimentoX509() {
    return this.riferimentoX509;
  }

  public void setRiferimentoX509(ModISicurezzaMessaggioSoapRiferimentoX509 riferimentoX509) {
    this.riferimentoX509 = riferimentoX509;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio riferimentoX509(ModISicurezzaMessaggioSoapRiferimentoX509 riferimentoX509) {
    this.riferimentoX509 = riferimentoX509;
    return this;
  }

 /**
   * Get certificateChain
   * @return certificateChain
  **/
  @JsonProperty("certificate_chain")
  @Valid
  public Boolean isCertificateChain() {
    return this.certificateChain;
  }

  public void setCertificateChain(Boolean certificateChain) {
    this.certificateChain = certificateChain;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio certificateChain(Boolean certificateChain) {
    this.certificateChain = certificateChain;
    return this;
  }

 /**
   * Get includiSignatureToken
   * @return includiSignatureToken
  **/
  @JsonProperty("includi_signature_token")
  @Valid
  public Boolean isIncludiSignatureToken() {
    return this.includiSignatureToken;
  }

  public void setIncludiSignatureToken(Boolean includiSignatureToken) {
    this.includiSignatureToken = includiSignatureToken;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio includiSignatureToken(Boolean includiSignatureToken) {
    this.includiSignatureToken = includiSignatureToken;
    return this;
  }

 /**
   * Get timeToLive
   * @return timeToLive
  **/
  @JsonProperty("time_to_live")
  @Valid
  public Integer getTimeToLive() {
    return this.timeToLive;
  }

  public void setTimeToLive(Integer timeToLive) {
    this.timeToLive = timeToLive;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio timeToLive(Integer timeToLive) {
    this.timeToLive = timeToLive;
    return this;
  }

 /**
   * Get wsaTo
   * @return wsaTo
  **/
  @JsonProperty("wsa_to")
  @Valid
 @Size(max=4000)  public String getWsaTo() {
    return this.wsaTo;
  }

  public void setWsaTo(String wsaTo) {
    this.wsaTo = wsaTo;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio wsaTo(String wsaTo) {
    this.wsaTo = wsaTo;
    return this;
  }

 /**
   * Get informazioniUtenteCodiceEnte
   * @return informazioniUtenteCodiceEnte
  **/
  @JsonProperty("informazioni_utente_codice_ente")
  @Valid
  public FruizioneModISoapRichiestaInformazioneUtente getInformazioniUtenteCodiceEnte() {
    return this.informazioniUtenteCodiceEnte;
  }

  public void setInformazioniUtenteCodiceEnte(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteCodiceEnte) {
    this.informazioniUtenteCodiceEnte = informazioniUtenteCodiceEnte;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio informazioniUtenteCodiceEnte(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteCodiceEnte) {
    this.informazioniUtenteCodiceEnte = informazioniUtenteCodiceEnte;
    return this;
  }

 /**
   * Get informazioniUtenteUserid
   * @return informazioniUtenteUserid
  **/
  @JsonProperty("informazioni_utente_userid")
  @Valid
  public FruizioneModISoapRichiestaInformazioneUtente getInformazioniUtenteUserid() {
    return this.informazioniUtenteUserid;
  }

  public void setInformazioniUtenteUserid(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteUserid) {
    this.informazioniUtenteUserid = informazioniUtenteUserid;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio informazioniUtenteUserid(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteUserid) {
    this.informazioniUtenteUserid = informazioniUtenteUserid;
    return this;
  }

 /**
   * Get informazioniUtenteIndirizzoIp
   * @return informazioniUtenteIndirizzoIp
  **/
  @JsonProperty("informazioni_utente_indirizzo_ip")
  @Valid
  public FruizioneModISoapRichiestaInformazioneUtente getInformazioniUtenteIndirizzoIp() {
    return this.informazioniUtenteIndirizzoIp;
  }

  public void setInformazioniUtenteIndirizzoIp(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteIndirizzoIp) {
    this.informazioniUtenteIndirizzoIp = informazioniUtenteIndirizzoIp;
  }

  public FruizioneModISoapRichiestaSicurezzaMessaggio informazioniUtenteIndirizzoIp(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteIndirizzoIp) {
    this.informazioniUtenteIndirizzoIp = informazioniUtenteIndirizzoIp;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FruizioneModISoapRichiestaSicurezzaMessaggio {\n");
    
    sb.append("    algoritmo: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.algoritmo)).append("\n");
    sb.append("    formaCanonicaXml: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.formaCanonicaXml)).append("\n");
    sb.append("    riferimentoX509: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.riferimentoX509)).append("\n");
    sb.append("    certificateChain: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.certificateChain)).append("\n");
    sb.append("    includiSignatureToken: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.includiSignatureToken)).append("\n");
    sb.append("    timeToLive: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.timeToLive)).append("\n");
    sb.append("    wsaTo: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.wsaTo)).append("\n");
    sb.append("    informazioniUtenteCodiceEnte: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.informazioniUtenteCodiceEnte)).append("\n");
    sb.append("    informazioniUtenteUserid: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.informazioniUtenteUserid)).append("\n");
    sb.append("    informazioniUtenteIndirizzoIp: ").append(FruizioneModISoapRichiestaSicurezzaMessaggio.toIndentedString(this.informazioniUtenteIndirizzoIp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
