package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.ProfiloCollaborazioneEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiServizio  {
  
  @Schema(required = true, description = "")
  private String nome = null;
  
  @Schema(description = "")
  private String descrizione = null;
  
  @Schema(required = true, description = "")
  private ProfiloCollaborazioneEnum profiloCollaborazione = null;
  
  @Schema(description = "")
  private Boolean idCollaborazione = false;
  
  @Schema(description = "")
  private Boolean riferimentoIdRichiesta = null;
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

  public ApiServizio nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get descrizione
   * @return descrizione
  **/
  @JsonProperty("descrizione")
 @Size(max=255)  public String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public ApiServizio descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * Get profiloCollaborazione
   * @return profiloCollaborazione
  **/
  @JsonProperty("profilo_collaborazione")
  @NotNull
  public ProfiloCollaborazioneEnum getProfiloCollaborazione() {
    return this.profiloCollaborazione;
  }

  public void setProfiloCollaborazione(ProfiloCollaborazioneEnum profiloCollaborazione) {
    this.profiloCollaborazione = profiloCollaborazione;
  }

  public ApiServizio profiloCollaborazione(ProfiloCollaborazioneEnum profiloCollaborazione) {
    this.profiloCollaborazione = profiloCollaborazione;
    return this;
  }

 /**
   * Get idCollaborazione
   * @return idCollaborazione
  **/
  @JsonProperty("id_collaborazione")
  public Boolean isIdCollaborazione() {
    return this.idCollaborazione;
  }

  public void setIdCollaborazione(Boolean idCollaborazione) {
    this.idCollaborazione = idCollaborazione;
  }

  public ApiServizio idCollaborazione(Boolean idCollaborazione) {
    this.idCollaborazione = idCollaborazione;
    return this;
  }

 /**
   * Get riferimentoIdRichiesta
   * @return riferimentoIdRichiesta
  **/
  @JsonProperty("riferimento_id_richiesta")
  public Boolean isRiferimentoIdRichiesta() {
    return this.riferimentoIdRichiesta;
  }

  public void setRiferimentoIdRichiesta(Boolean riferimentoIdRichiesta) {
    this.riferimentoIdRichiesta = riferimentoIdRichiesta;
  }

  public ApiServizio riferimentoIdRichiesta(Boolean riferimentoIdRichiesta) {
    this.riferimentoIdRichiesta = riferimentoIdRichiesta;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiServizio {\n");
    
    sb.append("    nome: ").append(ApiServizio.toIndentedString(this.nome)).append("\n");
    sb.append("    descrizione: ").append(ApiServizio.toIndentedString(this.descrizione)).append("\n");
    sb.append("    profiloCollaborazione: ").append(ApiServizio.toIndentedString(this.profiloCollaborazione)).append("\n");
    sb.append("    idCollaborazione: ").append(ApiServizio.toIndentedString(this.idCollaborazione)).append("\n");
    sb.append("    riferimentoIdRichiesta: ").append(ApiServizio.toIndentedString(this.riferimentoIdRichiesta)).append("\n");
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