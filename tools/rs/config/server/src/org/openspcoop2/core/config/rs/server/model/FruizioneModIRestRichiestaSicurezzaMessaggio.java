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

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class FruizioneModIRestRichiestaSicurezzaMessaggio  {
  
  @Schema(description = "")
  private ModISicurezzaMessaggioRestAlgoritmoFirma algoritmo = null;
  
  @Schema(description = "")
  private List<String> headerHttpFirmare = null;
  
  @Schema(required = true, description = "")
  private List<ModISicurezzaMessaggioRestRiferimentoX509> riferimentoX509 = new ArrayList<ModISicurezzaMessaggioRestRiferimentoX509>();
  
  @Schema(description = "")
  private Boolean certificateChain = null;
  
  @Schema(description = "")
  private Integer timeToLive = 300;
  
  @Schema(description = "")
  private String audience = null;
  
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
  public ModISicurezzaMessaggioRestAlgoritmoFirma getAlgoritmo() {
    return this.algoritmo;
  }

  public void setAlgoritmo(ModISicurezzaMessaggioRestAlgoritmoFirma algoritmo) {
    this.algoritmo = algoritmo;
  }

  public FruizioneModIRestRichiestaSicurezzaMessaggio algoritmo(ModISicurezzaMessaggioRestAlgoritmoFirma algoritmo) {
    this.algoritmo = algoritmo;
    return this;
  }

 /**
   * Get headerHttpFirmare
   * @return headerHttpFirmare
  **/
  @JsonProperty("header_http_firmare")
  @Valid
  public List<String> getHeaderHttpFirmare() {
    return this.headerHttpFirmare;
  }

  public void setHeaderHttpFirmare(List<String> headerHttpFirmare) {
    this.headerHttpFirmare = headerHttpFirmare;
  }

  public FruizioneModIRestRichiestaSicurezzaMessaggio headerHttpFirmare(List<String> headerHttpFirmare) {
    this.headerHttpFirmare = headerHttpFirmare;
    return this;
  }

  public FruizioneModIRestRichiestaSicurezzaMessaggio addHeaderHttpFirmareItem(String headerHttpFirmareItem) {
    this.headerHttpFirmare.add(headerHttpFirmareItem);
    return this;
  }

 /**
   * Get riferimentoX509
   * @return riferimentoX509
  **/
  @JsonProperty("riferimento_x509")
  @NotNull
  @Valid
 @Size(min=1,max=3)  public List<ModISicurezzaMessaggioRestRiferimentoX509> getRiferimentoX509() {
    return this.riferimentoX509;
  }

  public void setRiferimentoX509(List<ModISicurezzaMessaggioRestRiferimentoX509> riferimentoX509) {
    this.riferimentoX509 = riferimentoX509;
  }

  public FruizioneModIRestRichiestaSicurezzaMessaggio riferimentoX509(List<ModISicurezzaMessaggioRestRiferimentoX509> riferimentoX509) {
    this.riferimentoX509 = riferimentoX509;
    return this;
  }

  public FruizioneModIRestRichiestaSicurezzaMessaggio addRiferimentoX509Item(ModISicurezzaMessaggioRestRiferimentoX509 riferimentoX509Item) {
    this.riferimentoX509.add(riferimentoX509Item);
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

  public FruizioneModIRestRichiestaSicurezzaMessaggio certificateChain(Boolean certificateChain) {
    this.certificateChain = certificateChain;
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

  public FruizioneModIRestRichiestaSicurezzaMessaggio timeToLive(Integer timeToLive) {
    this.timeToLive = timeToLive;
    return this;
  }

 /**
   * Get audience
   * @return audience
  **/
  @JsonProperty("audience")
  @Valid
 @Size(max=4000)  public String getAudience() {
    return this.audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }

  public FruizioneModIRestRichiestaSicurezzaMessaggio audience(String audience) {
    this.audience = audience;
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

  public FruizioneModIRestRichiestaSicurezzaMessaggio informazioniUtenteCodiceEnte(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteCodiceEnte) {
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

  public FruizioneModIRestRichiestaSicurezzaMessaggio informazioniUtenteUserid(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteUserid) {
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

  public FruizioneModIRestRichiestaSicurezzaMessaggio informazioniUtenteIndirizzoIp(FruizioneModISoapRichiestaInformazioneUtente informazioniUtenteIndirizzoIp) {
    this.informazioniUtenteIndirizzoIp = informazioniUtenteIndirizzoIp;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FruizioneModIRestRichiestaSicurezzaMessaggio {\n");
    
    sb.append("    algoritmo: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.algoritmo)).append("\n");
    sb.append("    headerHttpFirmare: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.headerHttpFirmare)).append("\n");
    sb.append("    riferimentoX509: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.riferimentoX509)).append("\n");
    sb.append("    certificateChain: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.certificateChain)).append("\n");
    sb.append("    timeToLive: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.timeToLive)).append("\n");
    sb.append("    audience: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.audience)).append("\n");
    sb.append("    informazioniUtenteCodiceEnte: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.informazioniUtenteCodiceEnte)).append("\n");
    sb.append("    informazioniUtenteUserid: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.informazioniUtenteUserid)).append("\n");
    sb.append("    informazioniUtenteIndirizzoIp: ").append(FruizioneModIRestRichiestaSicurezzaMessaggio.toIndentedString(this.informazioniUtenteIndirizzoIp)).append("\n");
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
