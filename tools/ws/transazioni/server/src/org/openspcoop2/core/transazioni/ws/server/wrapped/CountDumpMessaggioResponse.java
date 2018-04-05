package org.openspcoop2.core.transazioni.ws.server.wrapped;

/**
 * <p>Java class for CountDumpMessaggioResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="countResponse">
 *     &lt;sequence>
 *         &lt;element name="countItems" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="1" />
 *     &lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
 
import java.io.Serializable;
 
import javax.xml.bind.annotation.XmlElement;

/**     
 * CountDumpMessaggioResponse
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(name = "countResponse", namespace="http://www.openspcoop2.org/core/transazioni/management", propOrder = {
    "countItems"
})
@javax.xml.bind.annotation.XmlRootElement(name = "countResponse")
public class CountDumpMessaggioResponse extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
	
	private static final long serialVersionUID = -1L;
	
	@javax.xml.bind.annotation.XmlSchemaType(name="long")
  @XmlElement(name="countItems",required=true,nillable=false)
	private Long countItems;
	
	public void setCountItems(Long countItems){
		this.countItems = countItems;
	}
	
	public Long getCountItems(){
		return this.countItems;
	}
	
	
	
	
}