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

import java.util.ArrayList;
import java.util.List;
import org.openspcoop2.core.config.rs.server.model.GruppoNome;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class GruppoBase extends GruppoNome {
  
  @Schema(example = "[\"az1\",\"az2\",\"az3\"]", required = true, description = "")
  private List<String> azioni = new ArrayList<String>();
 /**
   * Get azioni
   * @return azioni
  **/
  @JsonProperty("azioni")
  @NotNull
  @Valid
  public List<String> getAzioni() {
    return this.azioni;
  }

  public void setAzioni(List<String> azioni) {
    this.azioni = azioni;
  }

  public GruppoBase azioni(List<String> azioni) {
    this.azioni = azioni;
    return this;
  }

  public GruppoBase addAzioniItem(String azioniItem) {
    this.azioni.add(azioniItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GruppoBase {\n");
    sb.append("    ").append(GruppoBase.toIndentedString(super.toString())).append("\n");
    sb.append("    azioni: ").append(GruppoBase.toIndentedString(this.azioni)).append("\n");
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
