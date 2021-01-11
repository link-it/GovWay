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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.11 at 12:49:08 PM CEST 
//


package org.openspcoop2.web.monitor.transazioni.core.manifest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for protocollo_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocollo_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fruitore" type="{http://www.openspcoop2.org/web/monitor/transazioni/core/manifest}soggetto_type" minOccurs="0"/&gt;
 *         &lt;element name="erogatore" type="{http://www.openspcoop2.org/web/monitor/transazioni/core/manifest}soggetto_type" minOccurs="0"/&gt;
 *         &lt;element name="servizio" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="versione" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="azione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="api" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="tags" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="tag" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="nome" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="id-messaggio-richiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id-messaggio-risposta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data-id-msg-richiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="data-id-msg-risposta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="profilo" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="codice" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="id-collaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="uri-accordo-servizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="profilo-asincrono" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="servizio-correlato" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                           &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="id-correlazione" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="digest" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="richiesta" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="risposta" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="duplicati" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="richiesta" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *                 &lt;attribute name="risposta" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocollo_type", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest", propOrder = {
    "fruitore",
    "erogatore",
    "servizio",
    "azione",
    "api",
    "idMessaggioRichiesta",
    "idMessaggioRisposta",
    "dataIdMsgRichiesta",
    "dataIdMsgRisposta",
    "profilo",
    "idCollaborazione",
    "uriAccordoServizio",
    "profiloAsincrono",
    "digest",
    "duplicati"
})
public class ProtocolloType {

    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected SoggettoType fruitore;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected SoggettoType erogatore;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected ProtocolloType.Servizio servizio;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected String azione;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected ProtocolloType.Api api;
    @XmlElement(name = "id-messaggio-richiesta", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected String idMessaggioRichiesta;
    @XmlElement(name = "id-messaggio-risposta", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected String idMessaggioRisposta;
    @XmlElement(name = "data-id-msg-richiesta", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIdMsgRichiesta;
    @XmlElement(name = "data-id-msg-risposta", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIdMsgRisposta;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected ProtocolloType.Profilo profilo;
    @XmlElement(name = "id-collaborazione", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected String idCollaborazione;
    @XmlElement(name = "uri-accordo-servizio", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected String uriAccordoServizio;
    @XmlElement(name = "profilo-asincrono", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected ProtocolloType.ProfiloAsincrono profiloAsincrono;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected ProtocolloType.Digest digest;
    @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
    protected ProtocolloType.Duplicati duplicati;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the fruitore property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoType }
     *     
     */
    public SoggettoType getFruitore() {
        return this.fruitore;
    }

    /**
     * Sets the value of the fruitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoType }
     *     
     */
    public void setFruitore(SoggettoType value) {
        this.fruitore = value;
    }

    /**
     * Gets the value of the erogatore property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoType }
     *     
     */
    public SoggettoType getErogatore() {
        return this.erogatore;
    }

    /**
     * Sets the value of the erogatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoType }
     *     
     */
    public void setErogatore(SoggettoType value) {
        this.erogatore = value;
    }

    /**
     * Gets the value of the servizio property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloType.Servizio }
     *     
     */
    public ProtocolloType.Servizio getServizio() {
        return this.servizio;
    }

    /**
     * Sets the value of the servizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloType.Servizio }
     *     
     */
    public void setServizio(ProtocolloType.Servizio value) {
        this.servizio = value;
    }

    /**
     * Gets the value of the azione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAzione() {
        return this.azione;
    }

    /**
     * Sets the value of the azione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAzione(String value) {
        this.azione = value;
    }

    /**
     * Gets the value of the api property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloType.Api }
     *     
     */
    public ProtocolloType.Api getApi() {
        return this.api;
    }

    /**
     * Sets the value of the api property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloType.Api }
     *     
     */
    public void setApi(ProtocolloType.Api value) {
        this.api = value;
    }

    /**
     * Gets the value of the idMessaggioRichiesta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMessaggioRichiesta() {
        return this.idMessaggioRichiesta;
    }

    /**
     * Sets the value of the idMessaggioRichiesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMessaggioRichiesta(String value) {
        this.idMessaggioRichiesta = value;
    }

    /**
     * Gets the value of the idMessaggioRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMessaggioRisposta() {
        return this.idMessaggioRisposta;
    }

    /**
     * Sets the value of the idMessaggioRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMessaggioRisposta(String value) {
        this.idMessaggioRisposta = value;
    }

    /**
     * Gets the value of the dataIdMsgRichiesta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIdMsgRichiesta() {
        return this.dataIdMsgRichiesta;
    }

    /**
     * Sets the value of the dataIdMsgRichiesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIdMsgRichiesta(XMLGregorianCalendar value) {
        this.dataIdMsgRichiesta = value;
    }

    /**
     * Gets the value of the dataIdMsgRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIdMsgRisposta() {
        return this.dataIdMsgRisposta;
    }

    /**
     * Sets the value of the dataIdMsgRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIdMsgRisposta(XMLGregorianCalendar value) {
        this.dataIdMsgRisposta = value;
    }

    /**
     * Gets the value of the profilo property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloType.Profilo }
     *     
     */
    public ProtocolloType.Profilo getProfilo() {
        return this.profilo;
    }

    /**
     * Sets the value of the profilo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloType.Profilo }
     *     
     */
    public void setProfilo(ProtocolloType.Profilo value) {
        this.profilo = value;
    }

    /**
     * Gets the value of the idCollaborazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCollaborazione() {
        return this.idCollaborazione;
    }

    /**
     * Sets the value of the idCollaborazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCollaborazione(String value) {
        this.idCollaborazione = value;
    }

    /**
     * Gets the value of the uriAccordoServizio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUriAccordoServizio() {
        return this.uriAccordoServizio;
    }

    /**
     * Sets the value of the uriAccordoServizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUriAccordoServizio(String value) {
        this.uriAccordoServizio = value;
    }

    /**
     * Gets the value of the profiloAsincrono property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloType.ProfiloAsincrono }
     *     
     */
    public ProtocolloType.ProfiloAsincrono getProfiloAsincrono() {
        return this.profiloAsincrono;
    }

    /**
     * Sets the value of the profiloAsincrono property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloType.ProfiloAsincrono }
     *     
     */
    public void setProfiloAsincrono(ProtocolloType.ProfiloAsincrono value) {
        this.profiloAsincrono = value;
    }

    /**
     * Gets the value of the digest property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloType.Digest }
     *     
     */
    public ProtocolloType.Digest getDigest() {
        return this.digest;
    }

    /**
     * Sets the value of the digest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloType.Digest }
     *     
     */
    public void setDigest(ProtocolloType.Digest value) {
        this.digest = value;
    }

    /**
     * Gets the value of the duplicati property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloType.Duplicati }
     *     
     */
    public ProtocolloType.Duplicati getDuplicati() {
        return this.duplicati;
    }

    /**
     * Sets the value of the duplicati property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloType.Duplicati }
     *     
     */
    public void setDuplicati(ProtocolloType.Duplicati value) {
        this.duplicati = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="tags" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="tag" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="nome" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tags"
    })
    public static class Api {

        @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
        protected ProtocolloType.Api.Tags tags;
        @XmlAttribute(name = "nome")
        protected String nome;
        @XmlAttribute(name = "tipo")
        protected String tipo;

        /**
         * Gets the value of the tags property.
         * 
         * @return
         *     possible object is
         *     {@link ProtocolloType.Api.Tags }
         *     
         */
        public ProtocolloType.Api.Tags getTags() {
            return this.tags;
        }

        /**
         * Sets the value of the tags property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProtocolloType.Api.Tags }
         *     
         */
        public void setTags(ProtocolloType.Api.Tags value) {
            this.tags = value;
        }

        /**
         * Gets the value of the nome property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNome() {
            return this.nome;
        }

        /**
         * Sets the value of the nome property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNome(String value) {
            this.nome = value;
        }

        /**
         * Gets the value of the tipo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipo() {
            return this.tipo;
        }

        /**
         * Sets the value of the tipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipo(String value) {
            this.tipo = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="tag" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "tag"
        })
        public static class Tags {

            @XmlElement(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest", required = true)
            protected List<String> tag;

            /**
             * Gets the value of the tag property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the tag property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTag().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getTag() {
                if (this.tag == null) {
                    this.tag = new ArrayList<String>();
                }
                return this.tag;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="richiesta" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="risposta" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Digest {

        @XmlAttribute(name = "richiesta")
        protected String richiesta;
        @XmlAttribute(name = "risposta")
        protected String risposta;

        /**
         * Gets the value of the richiesta property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRichiesta() {
            return this.richiesta;
        }

        /**
         * Sets the value of the richiesta property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRichiesta(String value) {
            this.richiesta = value;
        }

        /**
         * Gets the value of the risposta property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRisposta() {
            return this.risposta;
        }

        /**
         * Sets the value of the risposta property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRisposta(String value) {
            this.risposta = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="richiesta" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *       &lt;attribute name="risposta" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Duplicati {

        @XmlAttribute(name = "richiesta")
        protected Integer richiesta;
        @XmlAttribute(name = "risposta")
        protected Integer risposta;

        /**
         * Gets the value of the richiesta property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getRichiesta() {
            return this.richiesta;
        }

        /**
         * Sets the value of the richiesta property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setRichiesta(Integer value) {
            this.richiesta = value;
        }

        /**
         * Gets the value of the risposta property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getRisposta() {
            return this.risposta;
        }

        /**
         * Sets the value of the risposta property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setRisposta(Integer value) {
            this.risposta = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="codice" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Profilo {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "codice")
        protected String codice;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return this.value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the codice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodice() {
            return this.codice;
        }

        /**
         * Sets the value of the codice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodice(String value) {
            this.codice = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="servizio-correlato" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="id-correlazione" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "servizioCorrelato"
    })
    public static class ProfiloAsincrono {

        @XmlElement(name = "servizio-correlato", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
        protected ProtocolloType.ProfiloAsincrono.ServizioCorrelato servizioCorrelato;
        @XmlAttribute(name = "id-correlazione")
        protected String idCorrelazione;

        /**
         * Gets the value of the servizioCorrelato property.
         * 
         * @return
         *     possible object is
         *     {@link ProtocolloType.ProfiloAsincrono.ServizioCorrelato }
         *     
         */
        public ProtocolloType.ProfiloAsincrono.ServizioCorrelato getServizioCorrelato() {
            return this.servizioCorrelato;
        }

        /**
         * Sets the value of the servizioCorrelato property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProtocolloType.ProfiloAsincrono.ServizioCorrelato }
         *     
         */
        public void setServizioCorrelato(ProtocolloType.ProfiloAsincrono.ServizioCorrelato value) {
            this.servizioCorrelato = value;
        }

        /**
         * Gets the value of the idCorrelazione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdCorrelazione() {
            return this.idCorrelazione;
        }

        /**
         * Sets the value of the idCorrelazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdCorrelazione(String value) {
            this.idCorrelazione = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *       &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ServizioCorrelato {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "tipo")
            protected String tipo;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return this.value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the tipo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipo() {
                return this.tipo;
            }

            /**
             * Sets the value of the tipo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipo(String value) {
                this.tipo = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="versione" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Servizio {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "tipo")
        protected String tipo;
        @XmlAttribute(name = "versione")
        protected String versione;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return this.value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the tipo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipo() {
            return this.tipo;
        }

        /**
         * Sets the value of the tipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipo(String value) {
            this.tipo = value;
        }

        /**
         * Gets the value of the versione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVersione() {
            return this.versione;
        }

        /**
         * Sets the value of the versione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVersione(String value) {
            this.versione = value;
        }

    }

}
