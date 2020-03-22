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
package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.AllegatoItem;
import org.openspcoop2.core.config.rs.server.model.RuoloAllegatoAPIImpl;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ApiImplAllegatoItem  {
  
  @Schema(required = true, description = "")
  private RuoloAllegatoAPIImpl ruolo = null;
  
  @Schema(required = true, description = "")
  private AllegatoItem allegato = null;
  
  @Schema(description = "")
  @com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "ruolo", visible = true )
  @com.fasterxml.jackson.annotation.JsonSubTypes({
    @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = TipoSpecificaSemiformaleEnum.class, name = "specificaSemiFormale"),
    @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = TipoSpecificaSicurezzaEnum.class, name = "specificaSicurezza"),
    @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = TipoSpecificaLivelloServizioEnum.class, name = "specificaLivelloServizio")  })
  private OneOfApiImplAllegatoItemTipoAllegato tipoAllegato = null;
 /**
   * Get ruolo
   * @return ruolo
  **/
  @JsonProperty("ruolo")
  @NotNull
  @Valid
  public RuoloAllegatoAPIImpl getRuolo() {
    return this.ruolo;
  }

  public void setRuolo(RuoloAllegatoAPIImpl ruolo) {
    this.ruolo = ruolo;
  }

  public ApiImplAllegatoItem ruolo(RuoloAllegatoAPIImpl ruolo) {
    this.ruolo = ruolo;
    return this;
  }

 /**
   * Get allegato
   * @return allegato
  **/
  @JsonProperty("allegato")
  @NotNull
  @Valid
  public AllegatoItem getAllegato() {
    return this.allegato;
  }

  public void setAllegato(AllegatoItem allegato) {
    this.allegato = allegato;
  }

  public ApiImplAllegatoItem allegato(AllegatoItem allegato) {
    this.allegato = allegato;
    return this;
  }

 /**
   * Get tipoAllegato
   * @return tipoAllegato
  **/
  @JsonProperty("tipo_allegato")
  @Valid
  public OneOfApiImplAllegatoItemTipoAllegato getTipoAllegato() {
    return this.tipoAllegato;
  }

  public void setTipoAllegato(OneOfApiImplAllegatoItemTipoAllegato tipoAllegato) {
    this.tipoAllegato = tipoAllegato;
  }

  public ApiImplAllegatoItem tipoAllegato(OneOfApiImplAllegatoItemTipoAllegato tipoAllegato) {
    this.tipoAllegato = tipoAllegato;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiImplAllegatoItem {\n");
    
    sb.append("    ruolo: ").append(ApiImplAllegatoItem.toIndentedString(this.ruolo)).append("\n");
    sb.append("    allegato: ").append(ApiImplAllegatoItem.toIndentedString(this.allegato)).append("\n");
    sb.append("    tipoAllegato: ").append(ApiImplAllegatoItem.toIndentedString(this.tipoAllegato)).append("\n");
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
