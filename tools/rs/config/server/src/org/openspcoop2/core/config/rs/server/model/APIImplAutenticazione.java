/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
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

import org.openspcoop2.core.config.rs.server.model.TipoAutenticazioneEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class APIImplAutenticazione  {
  
  @Schema(required = true, description = "")
  private TipoAutenticazioneEnum tipo = null;
  
  @Schema(description = "")
  private Boolean opzionale = false;
  
  @Schema(description = "nome autenticazione 'custom', set tipo='custom'")
 /**
   * nome autenticazione 'custom', set tipo='custom'  
  **/
  private String nome = null;
 /**
   * Get tipo
   * @return tipo
  **/
  @JsonProperty("tipo")
  @NotNull
  @Valid
  public TipoAutenticazioneEnum getTipo() {
    return this.tipo;
  }

  public void setTipo(TipoAutenticazioneEnum tipo) {
    this.tipo = tipo;
  }

  public APIImplAutenticazione tipo(TipoAutenticazioneEnum tipo) {
    this.tipo = tipo;
    return this;
  }

 /**
   * Get opzionale
   * @return opzionale
  **/
  @JsonProperty("opzionale")
  @Valid
  public Boolean isOpzionale() {
    return this.opzionale;
  }

  public void setOpzionale(Boolean opzionale) {
    this.opzionale = opzionale;
  }

  public APIImplAutenticazione opzionale(Boolean opzionale) {
    this.opzionale = opzionale;
    return this;
  }

 /**
   * nome autenticazione 'custom', set tipo&#x3D;'custom'
   * @return nome
  **/
  @JsonProperty("nome")
  @Valid
 @Size(max=255)  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public APIImplAutenticazione nome(String nome) {
    this.nome = nome;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class APIImplAutenticazione {\n");
    
    sb.append("    tipo: ").append(APIImplAutenticazione.toIndentedString(this.tipo)).append("\n");
    sb.append("    opzionale: ").append(APIImplAutenticazione.toIndentedString(this.opzionale)).append("\n");
    sb.append("    nome: ").append(APIImplAutenticazione.toIndentedString(this.nome)).append("\n");
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
