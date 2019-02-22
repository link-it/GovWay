package org.openspcoop2.core.config.rs.server.model;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiImplInformazioniGenerali  {
  
  @Schema(description = "")
  private String tipo = null;
  
  @Schema(required = true, description = "")
  private String nome = null;
  
  @Schema(description = "")
  private String apiSoapServizio = null;
 /**
   * Get tipo
   * @return tipo
  **/
  @JsonProperty("tipo")
  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public ApiImplInformazioniGenerali tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

 /**
   * Get nome
   * @return nome
  **/
  @JsonProperty("nome")
  @NotNull
  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ApiImplInformazioniGenerali nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get apiSoapServizio
   * @return apiSoapServizio
  **/
  @JsonProperty("api_soap_servizio")
  public String getApiSoapServizio() {
    return this.apiSoapServizio;
  }

  public void setApiSoapServizio(String apiSoapServizio) {
    this.apiSoapServizio = apiSoapServizio;
  }

  public ApiImplInformazioniGenerali apiSoapServizio(String apiSoapServizio) {
    this.apiSoapServizio = apiSoapServizio;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiImplInformazioniGenerali {\n");
    
    sb.append("    tipo: ").append(ApiImplInformazioniGenerali.toIndentedString(this.tipo)).append("\n");
    sb.append("    nome: ").append(ApiImplInformazioniGenerali.toIndentedString(this.nome)).append("\n");
    sb.append("    apiSoapServizio: ").append(ApiImplInformazioniGenerali.toIndentedString(this.apiSoapServizio)).append("\n");
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