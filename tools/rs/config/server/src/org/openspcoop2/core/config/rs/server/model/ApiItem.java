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

import org.openspcoop2.core.config.rs.server.model.ApiBaseConSoggetto;
import org.openspcoop2.core.config.rs.server.model.StatoApiEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ApiItem extends ApiBaseConSoggetto {
  
  @Schema(required = true, description = "")
  private StatoApiEnum stato = null;
  
  @Schema(required = true, description = "")
  private String statoDescrizione = null;
 /**
   * Get stato
   * @return stato
  **/
  @JsonProperty("stato")
  @NotNull
  @Valid
  public StatoApiEnum getStato() {
    return this.stato;
  }

  public void setStato(StatoApiEnum stato) {
    this.stato = stato;
  }

  public ApiItem stato(StatoApiEnum stato) {
    this.stato = stato;
    return this;
  }

 /**
   * Get statoDescrizione
   * @return statoDescrizione
  **/
  @JsonProperty("stato_descrizione")
  @NotNull
  @Valid
  public String getStatoDescrizione() {
    return this.statoDescrizione;
  }

  public void setStatoDescrizione(String statoDescrizione) {
    this.statoDescrizione = statoDescrizione;
  }

  public ApiItem statoDescrizione(String statoDescrizione) {
    this.statoDescrizione = statoDescrizione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiItem {\n");
    sb.append("    ").append(ApiItem.toIndentedString(super.toString())).append("\n");
    sb.append("    stato: ").append(ApiItem.toIndentedString(this.stato)).append("\n");
    sb.append("    statoDescrizione: ").append(ApiItem.toIndentedString(this.statoDescrizione)).append("\n");
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
