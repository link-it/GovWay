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
import org.openspcoop2.utils.service.beans.Lista;
import org.openspcoop2.core.config.rs.server.model.ScopeItem;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ListaScope extends Lista {
  
  @Schema(required = true, description = "")
  private List<ScopeItem> items = new ArrayList<ScopeItem>();
 /**
   * Get items
   * @return items
  **/
  @JsonProperty("items")
  @NotNull
  @Valid
  public List<ScopeItem> getItems() {
    return this.items;
  }

  public void setItems(List<ScopeItem> items) {
    this.items = items;
  }

  public ListaScope items(List<ScopeItem> items) {
    this.items = items;
    return this;
  }

  public ListaScope addItemsItem(ScopeItem itemsItem) {
    this.items.add(itemsItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaScope {\n");
    sb.append("    ").append(ListaScope.toIndentedString(super.toString())).append("\n");
    sb.append("    items: ").append(ListaScope.toIndentedString(this.items)).append("\n");
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
