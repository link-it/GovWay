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

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ControlloAccessiAutenticazioneToken  {
  
  @Schema(description = "indica se nel token deve essere obbligatoriamente presente l'issuer")
 /**
   * indica se nel token deve essere obbligatoriamente presente l'issuer  
  **/
  private Boolean issuer = false;
  
  @Schema(description = "indica se nel token deve essere obbligatoriamente presente l'identificativo dell'applicazione")
 /**
   * indica se nel token deve essere obbligatoriamente presente l'identificativo dell'applicazione  
  **/
  private Boolean clientId = false;
  
  @Schema(description = "indica se nel token deve essere obbligatoriamente presente il subject (identificativo utente codificato)")
 /**
   * indica se nel token deve essere obbligatoriamente presente il subject (identificativo utente codificato)  
  **/
  private Boolean subject = false;
  
  @Schema(description = "indica se nel token deve essere obbligatoriamente presente l'utente (identificativo utente 'human-readable')")
 /**
   * indica se nel token deve essere obbligatoriamente presente l'utente (identificativo utente 'human-readable')  
  **/
  private Boolean username = false;
  
  @Schema(description = "indica se nel token deve essere obbligatoriamente presente l'email dell'utente")
 /**
   * indica se nel token deve essere obbligatoriamente presente l'email dell'utente  
  **/
  private Boolean email = false;
 /**
   * indica se nel token deve essere obbligatoriamente presente l&#x27;issuer
   * @return issuer
  **/
  @JsonProperty("issuer")
  @Valid
  public Boolean isIssuer() {
    return this.issuer;
  }

  public void setIssuer(Boolean issuer) {
    this.issuer = issuer;
  }

  public ControlloAccessiAutenticazioneToken issuer(Boolean issuer) {
    this.issuer = issuer;
    return this;
  }

 /**
   * indica se nel token deve essere obbligatoriamente presente l&#x27;identificativo dell&#x27;applicazione
   * @return clientId
  **/
  @JsonProperty("client_id")
  @Valid
  public Boolean isClientId() {
    return this.clientId;
  }

  public void setClientId(Boolean clientId) {
    this.clientId = clientId;
  }

  public ControlloAccessiAutenticazioneToken clientId(Boolean clientId) {
    this.clientId = clientId;
    return this;
  }

 /**
   * indica se nel token deve essere obbligatoriamente presente il subject (identificativo utente codificato)
   * @return subject
  **/
  @JsonProperty("subject")
  @Valid
  public Boolean isSubject() {
    return this.subject;
  }

  public void setSubject(Boolean subject) {
    this.subject = subject;
  }

  public ControlloAccessiAutenticazioneToken subject(Boolean subject) {
    this.subject = subject;
    return this;
  }

 /**
   * indica se nel token deve essere obbligatoriamente presente l&#x27;utente (identificativo utente &#x27;human-readable&#x27;)
   * @return username
  **/
  @JsonProperty("username")
  @Valid
  public Boolean isUsername() {
    return this.username;
  }

  public void setUsername(Boolean username) {
    this.username = username;
  }

  public ControlloAccessiAutenticazioneToken username(Boolean username) {
    this.username = username;
    return this;
  }

 /**
   * indica se nel token deve essere obbligatoriamente presente l&#x27;email dell&#x27;utente
   * @return email
  **/
  @JsonProperty("email")
  @Valid
  public Boolean isEmail() {
    return this.email;
  }

  public void setEmail(Boolean email) {
    this.email = email;
  }

  public ControlloAccessiAutenticazioneToken email(Boolean email) {
    this.email = email;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ControlloAccessiAutenticazioneToken {\n");
    
    sb.append("    issuer: ").append(ControlloAccessiAutenticazioneToken.toIndentedString(this.issuer)).append("\n");
    sb.append("    clientId: ").append(ControlloAccessiAutenticazioneToken.toIndentedString(this.clientId)).append("\n");
    sb.append("    subject: ").append(ControlloAccessiAutenticazioneToken.toIndentedString(this.subject)).append("\n");
    sb.append("    username: ").append(ControlloAccessiAutenticazioneToken.toIndentedString(this.username)).append("\n");
    sb.append("    email: ").append(ControlloAccessiAutenticazioneToken.toIndentedString(this.email)).append("\n");
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
