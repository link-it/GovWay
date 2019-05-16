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

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class AuthenticationHttpsConfigurazioneManuale  {
  
  @Schema(example = "cn=esterno", required = true, description = "")
  private String subject = null;
  
  @Schema(example = "cn=esterno", description = "")
  private String issuer = null;
 /**
   * Get subject
   * @return subject
  **/
  @JsonProperty("subject")
  @NotNull
  @Valid
 @Size(max=2800)  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public AuthenticationHttpsConfigurazioneManuale subject(String subject) {
    this.subject = subject;
    return this;
  }

 /**
   * Get issuer
   * @return issuer
  **/
  @JsonProperty("issuer")
  @Valid
 @Size(max=2800)  public String getIssuer() {
    return this.issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public AuthenticationHttpsConfigurazioneManuale issuer(String issuer) {
    this.issuer = issuer;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationHttpsConfigurazioneManuale {\n");
    
    sb.append("    subject: ").append(AuthenticationHttpsConfigurazioneManuale.toIndentedString(this.subject)).append("\n");
    sb.append("    issuer: ").append(AuthenticationHttpsConfigurazioneManuale.toIndentedString(this.issuer)).append("\n");
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