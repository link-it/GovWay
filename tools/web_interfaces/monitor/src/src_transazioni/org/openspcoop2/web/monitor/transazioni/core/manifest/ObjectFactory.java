//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.08 at 12:26:49 PM CEST 
//


package org.openspcoop2.web.monitor.transazioni.core.manifest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openspcoop2.web.monitor.transazioni.core.manifest package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Transazione_QNAME = new QName("http://www.openspcoop2.org/web/monitor/transazioni/core/manifest", "transazione");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openspcoop2.web.monitor.transazioni.core.manifest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProtocolloType }
     * 
     */
    public ProtocolloType createProtocolloType() {
        return new ProtocolloType();
    }

    /**
     * Create an instance of {@link ProtocolloType.ProfiloAsincrono }
     * 
     */
    public ProtocolloType.ProfiloAsincrono createProtocolloTypeProfiloAsincrono() {
        return new ProtocolloType.ProfiloAsincrono();
    }

    /**
     * Create an instance of {@link TransazioneType }
     * 
     */
    public TransazioneType createTransazioneType() {
        return new TransazioneType();
    }

    /**
     * Create an instance of {@link SoggettoType }
     * 
     */
    public SoggettoType createSoggettoType() {
        return new SoggettoType();
    }

    /**
     * Create an instance of {@link ProtocolloType.Servizio }
     * 
     */
    public ProtocolloType.Servizio createProtocolloTypeServizio() {
        return new ProtocolloType.Servizio();
    }

    /**
     * Create an instance of {@link ProtocolloType.Profilo }
     * 
     */
    public ProtocolloType.Profilo createProtocolloTypeProfilo() {
        return new ProtocolloType.Profilo();
    }

    /**
     * Create an instance of {@link ProtocolloType.Digest }
     * 
     */
    public ProtocolloType.Digest createProtocolloTypeDigest() {
        return new ProtocolloType.Digest();
    }

    /**
     * Create an instance of {@link ProtocolloType.Duplicati }
     * 
     */
    public ProtocolloType.Duplicati createProtocolloTypeDuplicati() {
        return new ProtocolloType.Duplicati();
    }

    /**
     * Create an instance of {@link ProtocolloType.ProfiloAsincrono.ServizioCorrelato }
     * 
     */
    public ProtocolloType.ProfiloAsincrono.ServizioCorrelato createProtocolloTypeProfiloAsincronoServizioCorrelato() {
        return new ProtocolloType.ProfiloAsincrono.ServizioCorrelato();
    }

    /**
     * Create an instance of {@link TransazioneType.Esito }
     * 
     */
    public TransazioneType.Esito createTransazioneTypeEsito() {
        return new TransazioneType.Esito();
    }

    /**
     * Create an instance of {@link TransazioneType.Contesto }
     * 
     */
    public TransazioneType.Contesto createTransazioneTypeContesto() {
        return new TransazioneType.Contesto();
    }

    /**
     * Create an instance of {@link TransazioneType.TempiAttraversamento }
     * 
     */
    public TransazioneType.TempiAttraversamento createTransazioneTypeTempiAttraversamento() {
        return new TransazioneType.TempiAttraversamento();
    }

    /**
     * Create an instance of {@link TransazioneType.DimensioneMessaggi }
     * 
     */
    public TransazioneType.DimensioneMessaggi createTransazioneTypeDimensioneMessaggi() {
        return new TransazioneType.DimensioneMessaggi();
    }

    /**
     * Create an instance of {@link TransazioneType.Dominio }
     * 
     */
    public TransazioneType.Dominio createTransazioneTypeDominio() {
        return new TransazioneType.Dominio();
    }

    /**
     * Create an instance of {@link TransazioneType.Diagnostica }
     * 
     */
    public TransazioneType.Diagnostica createTransazioneTypeDiagnostica() {
        return new TransazioneType.Diagnostica();
    }

    /**
     * Create an instance of {@link TransazioneType.CorrelazioneApplicativa }
     * 
     */
    public TransazioneType.CorrelazioneApplicativa createTransazioneTypeCorrelazioneApplicativa() {
        return new TransazioneType.CorrelazioneApplicativa();
    }

    /**
     * Create an instance of {@link TransazioneType.IntegrationManager }
     * 
     */
    public TransazioneType.IntegrationManager createTransazioneTypeIntegrationManager() {
        return new TransazioneType.IntegrationManager();
    }

    /**
     * Create an instance of {@link TransazioneType.DatiIntegrazione }
     * 
     */
    public TransazioneType.DatiIntegrazione createTransazioneTypeDatiIntegrazione() {
        return new TransazioneType.DatiIntegrazione();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransazioneType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest", name = "transazione")
    public JAXBElement<TransazioneType> createTransazione(TransazioneType value) {
        return new JAXBElement<TransazioneType>(ObjectFactory._Transazione_QNAME, TransazioneType.class, null, value);
    }

}
