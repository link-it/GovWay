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

public class ApiModISicurezzaMessaggio  {
  
  @Schema(required = true, description = "")
  private ModISicurezzaMessaggioEnum pattern = null;
  
  @Schema(description = "")
  private Boolean digestRichiesta = false;
  
  @Schema(description = "")
  private Boolean informazioniUtente = false;
  
  @Schema(description = "")
  private ModISicurezzaMessaggioRestHeaderEnum restHeader = null;
  
  @Schema(description = "")
  private Boolean soapFirmaAllegati = false;
  
  @Schema(description = "")
  private ModISicurezzaMessaggioApplicabilitaEnum applicabilita = null;
  
  @Schema(description = "")
  private ApiModISicurezzaMessaggioApplicabilitaCustom applicabilitaCustom = null;
 /**
   * Get pattern
   * @return pattern
  **/
  @JsonProperty("pattern")
  @NotNull
  @Valid
  public ModISicurezzaMessaggioEnum getPattern() {
    return this.pattern;
  }

  public void setPattern(ModISicurezzaMessaggioEnum pattern) {
    this.pattern = pattern;
  }

  public ApiModISicurezzaMessaggio pattern(ModISicurezzaMessaggioEnum pattern) {
    this.pattern = pattern;
    return this;
  }

 /**
   * Get digestRichiesta
   * @return digestRichiesta
  **/
  @JsonProperty("digest_richiesta")
  @Valid
  public Boolean isDigestRichiesta() {
    return this.digestRichiesta;
  }

  public void setDigestRichiesta(Boolean digestRichiesta) {
    this.digestRichiesta = digestRichiesta;
  }

  public ApiModISicurezzaMessaggio digestRichiesta(Boolean digestRichiesta) {
    this.digestRichiesta = digestRichiesta;
    return this;
  }

 /**
   * Get informazioniUtente
   * @return informazioniUtente
  **/
  @JsonProperty("informazioni_utente")
  @Valid
  public Boolean isInformazioniUtente() {
    return this.informazioniUtente;
  }

  public void setInformazioniUtente(Boolean informazioniUtente) {
    this.informazioniUtente = informazioniUtente;
  }

  public ApiModISicurezzaMessaggio informazioniUtente(Boolean informazioniUtente) {
    this.informazioniUtente = informazioniUtente;
    return this;
  }

 /**
   * Get restHeader
   * @return restHeader
  **/
  @JsonProperty("rest_header")
  @Valid
  public ModISicurezzaMessaggioRestHeaderEnum getRestHeader() {
    return this.restHeader;
  }

  public void setRestHeader(ModISicurezzaMessaggioRestHeaderEnum restHeader) {
    this.restHeader = restHeader;
  }

  public ApiModISicurezzaMessaggio restHeader(ModISicurezzaMessaggioRestHeaderEnum restHeader) {
    this.restHeader = restHeader;
    return this;
  }

 /**
   * Get soapFirmaAllegati
   * @return soapFirmaAllegati
  **/
  @JsonProperty("soap_firma_allegati")
  @Valid
  public Boolean isSoapFirmaAllegati() {
    return this.soapFirmaAllegati;
  }

  public void setSoapFirmaAllegati(Boolean soapFirmaAllegati) {
    this.soapFirmaAllegati = soapFirmaAllegati;
  }

  public ApiModISicurezzaMessaggio soapFirmaAllegati(Boolean soapFirmaAllegati) {
    this.soapFirmaAllegati = soapFirmaAllegati;
    return this;
  }

 /**
   * Get applicabilita
   * @return applicabilita
  **/
  @JsonProperty("applicabilita")
  @Valid
  public ModISicurezzaMessaggioApplicabilitaEnum getApplicabilita() {
    return this.applicabilita;
  }

  public void setApplicabilita(ModISicurezzaMessaggioApplicabilitaEnum applicabilita) {
    this.applicabilita = applicabilita;
  }

  public ApiModISicurezzaMessaggio applicabilita(ModISicurezzaMessaggioApplicabilitaEnum applicabilita) {
    this.applicabilita = applicabilita;
    return this;
  }

 /**
   * Get applicabilitaCustom
   * @return applicabilitaCustom
  **/
  @JsonProperty("applicabilita_custom")
  @Valid
  public ApiModISicurezzaMessaggioApplicabilitaCustom getApplicabilitaCustom() {
    return this.applicabilitaCustom;
  }

  public void setApplicabilitaCustom(ApiModISicurezzaMessaggioApplicabilitaCustom applicabilitaCustom) {
    this.applicabilitaCustom = applicabilitaCustom;
  }

  public ApiModISicurezzaMessaggio applicabilitaCustom(ApiModISicurezzaMessaggioApplicabilitaCustom applicabilitaCustom) {
    this.applicabilitaCustom = applicabilitaCustom;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiModISicurezzaMessaggio {\n");
    
    sb.append("    pattern: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.pattern)).append("\n");
    sb.append("    digestRichiesta: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.digestRichiesta)).append("\n");
    sb.append("    informazioniUtente: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.informazioniUtente)).append("\n");
    sb.append("    restHeader: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.restHeader)).append("\n");
    sb.append("    soapFirmaAllegati: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.soapFirmaAllegati)).append("\n");
    sb.append("    applicabilita: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.applicabilita)).append("\n");
    sb.append("    applicabilitaCustom: ").append(ApiModISicurezzaMessaggio.toIndentedString(this.applicabilitaCustom)).append("\n");
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
