package org.openspcoop2.web.monitor.transazioni.mbean;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.transazioni.DumpAllegato;
import org.openspcoop2.core.transazioni.DumpContenuto;
import org.openspcoop2.core.transazioni.DumpHeaderTrasporto;
import org.openspcoop2.core.transazioni.DumpMessaggio;
import org.openspcoop2.core.transazioni.Transazione;
import org.openspcoop2.core.transazioni.constants.TipoMessaggio;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.JSONUtils;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.web.monitor.core.core.Utils;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.mbean.PdDBaseBean;
import org.openspcoop2.web.monitor.core.utils.MessageUtils;
import org.openspcoop2.web.monitor.core.utils.MimeTypeUtils;
import org.openspcoop2.web.monitor.transazioni.bean.DumpContenutoBean;
import org.openspcoop2.web.monitor.transazioni.dao.ITransazioniService;
import org.slf4j.Logger;

public class DettagliDump extends PdDBaseBean<Transazione, String, ITransazioniService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private transient Logger log =  LoggerManager.getPddMonitorCoreLogger();


	private String idTransazione;
	private TipoMessaggio tipoMessaggio;

	private DumpMessaggio dumpMessaggio;
	private DumpAllegato selectedAttachment;

	private boolean base64Decode;

	public void setBase64Decode(boolean base64Decode) {
		this.base64Decode = base64Decode;
	}

	public void setSelectedAttachment(DumpAllegato selectedAttachment) {
		this.selectedAttachment = selectedAttachment;
	}

	public String getIdTransazione() {
		return this.idTransazione;
	}

	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}

	public boolean isVisualizzaMessaggio(){
		boolean visualizzaMessaggio = true;
		if(this.dumpMessaggio!=null && this.dumpMessaggio.getBody()!=null) {
			if(this.dumpMessaggio.getBody() == null)
				return false;

			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(this.dumpMessaggio.getBody(),contenutoDocumentoStringBuffer);
			if(errore!= null)
				return false;

			//			MessageType messageType= MessageType.XML;
			//			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
			//				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
			//			}

			//			switch (messageType) {
			//			case BINARY:
			//			case MIME_MULTIPART:
			//				// questi due casi dovrebbero essere gestiti sopra 
			//				break;	
			//			case JSON:
			//				JSONUtils jsonUtils = JSONUtils.getInstance(true);
			//				try {
			//					toRet = jsonUtils.toString(jsonUtils.getAsNode(this.dumpMessaggio.getBody()));
			//				} catch (UtilsException e) {
			//				}
			//				break;
			//			case SOAP_11:
			//			case SOAP_12:
			//			case XML:
			//			default:
			//				toRet = Utils.prettifyXml(this.dumpMessaggio.getBody());
			//				break;
			//			}
		}


		return visualizzaMessaggio;
	}

	public String getPrettyEnvelop(){
		String toRet = null;
		if(this.dumpMessaggio!=null && this.dumpMessaggio.getBody()!=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(this.dumpMessaggio.getBody(),contenutoDocumentoStringBuffer);
			if(errore!= null)
				return "";

			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
			}

			switch (messageType) {
			case BINARY:
			case MIME_MULTIPART:
				// questi due casi dovrebbero essere gestiti sopra 
				break;	
			case JSON:
				JSONUtils jsonUtils = JSONUtils.getInstance(true);
				try {
					toRet = jsonUtils.toString(jsonUtils.getAsNode(this.dumpMessaggio.getBody()));
				} catch (UtilsException e) {
				}
				break;
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = Utils.prettifyXml(this.dumpMessaggio.getBody());
				break;
			}
		}

		if(toRet == null)
			toRet = this.dumpMessaggio.getBody() != null ? new String(this.dumpMessaggio.getBody()) : "";

			return toRet;
	}

	public String getBrush() {
		String toRet = null;
		if(this.dumpMessaggio!=null && this.dumpMessaggio.getBody()!=null) {
			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
			}

			switch (messageType) {
			case JSON:
				toRet = "json";
				break;
			case BINARY:
			case MIME_MULTIPART:
				// per ora restituisco il default
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = "xml";
				break;
			}
		}

		return toRet;
	}

	public String getErroreVisualizzaMessaggio(){
		if(this.dumpMessaggio!=null && this.dumpMessaggio.getBody()!=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(this.dumpMessaggio.getBody(),contenutoDocumentoStringBuffer);
			return errore;
		}

		return null;
	}

	public DumpMessaggio getDumpMessaggio(){
		if(this.dumpMessaggio!=null)
			return this.dumpMessaggio;

		try {
			this.dumpMessaggio = (((ITransazioniService)this.service)).getDumpMessaggio(this.idTransazione, this.tipoMessaggio);
		} catch (Exception e) {
			this.log.error(e.getMessage(), e);

		}

		return this.dumpMessaggio;
	}

	/***
	 * //  eliminato dalla tabella styleClass="#{allegato.mimeTypeImageClass}"
	 * 
	 * @return Lista degli Allegati
	 */
	public List<DumpAllegato> getAllegati(){

		if(this.getDumpMessaggio()==null)
			return null;

		List<DumpAllegato> list = (((ITransazioniService)this.service)).getAllegatiMessaggio(this.dumpMessaggio.getIdTransazione(), this.dumpMessaggio.getTipoMessaggio(), this.dumpMessaggio.getId());

		if(list.size()>0){
			List<DumpAllegato> newL = new ArrayList<DumpAllegato>();
			for (DumpAllegato dumpAllegato : list) {
				newL.add(new DumpAllegatoBean(dumpAllegato));
			}
			return newL;
		}
		else{
			return null;
		}

	}

	public List<DumpHeaderTrasporto> getHeadersTrasporto(){

		if(this.getDumpMessaggio()==null)
			return null;

		List<DumpHeaderTrasporto> list = (((ITransazioniService)this.service)).getHeaderTrasporto(this.dumpMessaggio.getIdTransazione(), this.dumpMessaggio.getTipoMessaggio(), this.dumpMessaggio.getId());

		return (list.size()>0) ? list : null;
	}

	public List<DumpContenuto> getContenuti(){

		if(this.getDumpMessaggio()==null)
			return null;

		List<DumpContenuto> list = (((ITransazioniService)this.service)).getContenutiSpecifici(this.dumpMessaggio.getIdTransazione(), this.dumpMessaggio.getTipoMessaggio(), this.dumpMessaggio.getId());

		if(list.size()>0){
			List<DumpContenuto> listNew = new ArrayList<DumpContenuto>();
			for (DumpContenuto dumpContenuto : list) {
				listNew.add(new DumpContenutoBean(dumpContenuto));
			}
			return listNew;
		}
		else{
			return null;
		}

		//return (list.size()>0) ? list : null;
	}

	public String downloadMessaggio(){
		this.log.debug("downloading messaggio: "+this.dumpMessaggio.getId());
		try{
			//recupero informazioni sul file


			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

			// Now we create some variables we will use for writting the file to the
			// response
			// String filePath = null;
			int read = 0;
			byte[] bytes = new byte[1024];

			// Be sure to retrieve the absolute path to the file with the required
			// method
			// filePath = pathToTheFile;

			// Now set the content type for our response, be sure to use the best
			// suitable content type depending on your file
			// the content type presented here is ok for, lets say, text files and
			// others (like CSVs, PDFs)
			response.setContentType(this.dumpMessaggio.getContentType());

			// This is another important attribute for the header of the response
			// Here fileName, is a String with the name that you will suggest as a
			// name to save as
			// I use the same name as it is stored in the file system of the server.
			//String fileName = "allegato_"+this.selectedAttachment.getId();
			// NOTA: L'id potrebbe essere -1 nel caso di mascheramento logico.
			String fileName = "messaggio";

			String ext = MimeTypeUtils.fileExtensionForMIMEType(this.dumpMessaggio.getContentType());

			fileName+="."+ext;

			// Setto Proprietà Export File
			HttpUtilities.setOutputFile(response, true, fileName, this.dumpMessaggio.getContentType());

			// Streams we will use to read, write the file bytes to our response
			ByteArrayInputStream bis = null;
			OutputStream os = null;

			// First we load the file in our InputStream
			byte[] contenutoBody = this.dumpMessaggio.getBody();
			//			if(this.base64Decode){
			//				contenutoBody = ((DumpAllegatoBean)this.dumpMessaggio).decodeBase64();
			//			}
			bis = new ByteArrayInputStream(contenutoBody);
			os = response.getOutputStream();

			// While there are still bytes in the file, read them and write them to
			// our OutputStream
			while ((read = bis.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			// Clean resources
			os.flush();
			os.close();

			FacesContext.getCurrentInstance().responseComplete();

			// End of the method
		}catch (Exception e) {
			this.log.error(e.getMessage(), e);
			MessageUtils.addErrorMsg("Si e' verificato un errore durante il download del messaggio.");
		}
		return null;
	}

	public String download(){
		this.log.debug("downloading allegato: "+this.selectedAttachment.getId());
		try{
			//recupero informazioni sul file


			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

			// Now we create some variables we will use for writting the file to the
			// response
			// String filePath = null;
			int read = 0;
			byte[] bytes = new byte[1024];

			// Be sure to retrieve the absolute path to the file with the required
			// method
			// filePath = pathToTheFile;

			// Now set the content type for our response, be sure to use the best
			// suitable content type depending on your file
			// the content type presented here is ok for, lets say, text files and
			// others (like CSVs, PDFs)
			response.setContentType(this.selectedAttachment.getContentType());

			// This is another important attribute for the header of the response
			// Here fileName, is a String with the name that you will suggest as a
			// name to save as
			// I use the same name as it is stored in the file system of the server.
			//String fileName = "allegato_"+this.selectedAttachment.getId();
			// NOTA: L'id potrebbe essere -1 nel caso di mascheramento logico.
			String fileName = "allegato";

			String ext = MimeTypeUtils.fileExtensionForMIMEType(this.selectedAttachment.getContentType());

			fileName+="."+ext;

			// Setto Proprietà Export File
			HttpUtilities.setOutputFile(response, true, fileName, this.selectedAttachment.getContentType());

			// Streams we will use to read, write the file bytes to our response
			ByteArrayInputStream bis = null;
			OutputStream os = null;

			// First we load the file in our InputStream
			byte[] contenutoAllegato = this.selectedAttachment.getAllegato();
			if(this.base64Decode){
				contenutoAllegato = ((DumpAllegatoBean)this.selectedAttachment).decodeBase64();
			}
			bis = new ByteArrayInputStream(contenutoAllegato);
			os = response.getOutputStream();

			// While there are still bytes in the file, read them and write them to
			// our OutputStream
			while ((read = bis.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			// Clean resources
			os.flush();
			os.close();

			FacesContext.getCurrentInstance().responseComplete();

			// End of the method
		}catch (Exception e) {
			this.log.error(e.getMessage(), e);
			MessageUtils.addErrorMsg("Si e' verificato un errore durante il download dell'allegato.");
		}
		return null;
	}

	public String downloadAll(){

		this.log.debug("downloading all attachments");
		try{
			//recupero informazioni sul file


			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

			// Now we create some variables we will use for writting the file to the
			// response
			// String filePath = null;
			byte[] bytes = new byte[1024];

			// Be sure to retrieve the absolute path to the file with the required
			// method
			// filePath = pathToTheFile;

			// This is another important attribute for the header of the response
			// Here fileName, is a String with the name that you will suggest as a
			// name to save as
			// I use the same name as it is stored in the file system of the server.

			String fileName = this.dumpMessaggio.getIdTransazione()+"-Attachments.zip";

			// Setto Proprietà Export File
			HttpUtilities.setOutputFile(response, true, fileName);


			// Streams we will use to read, write the file bytes to our response
			// First we load the file in our InputStream
			List<DumpAllegato> allegatiCore = (((ITransazioniService)this.service)).getAllegatiMessaggio(this.dumpMessaggio.getIdTransazione(), this.dumpMessaggio.getTipoMessaggio(), this.dumpMessaggio.getId());

			List<DumpAllegato> allegati = new ArrayList<DumpAllegato>();
			for (DumpAllegato dumpAllegato : allegatiCore) {
				allegati.add(new DumpAllegatoBean(dumpAllegato));
			}

			ZipOutputStream zip = new ZipOutputStream(response.getOutputStream());
			InputStream in = null;

			int index = 1;
			for (DumpAllegato allegato : allegati) {

				String allegatofileName = "allegato_"+index;

				String allegatoExt = MimeTypeUtils.fileExtensionForMIMEType(allegato.getContentType());

				allegatofileName+="."+allegatoExt;
				zip.putNextEntry(new ZipEntry(allegatofileName));

				byte[] contenutoAllegato = allegato.getAllegato();
				in = new ByteArrayInputStream(contenutoAllegato);
				int len;
				while ((len = in.read(bytes)) > 0) {
					zip.write(bytes, 0, len);
				}
				zip.closeEntry();
				in.close();

				try{
					DumpAllegatoBean da = (DumpAllegatoBean)allegato;
					if(da.isBase64()){
						contenutoAllegato = da.decodeBase64();
						allegatofileName = "allegato_"+index+".decodeBase64";
						allegatofileName+="."+allegatoExt;
						zip.putNextEntry(new ZipEntry(allegatofileName));

						in = new ByteArrayInputStream(contenutoAllegato);
						while ((len = in.read(bytes)) > 0) {
							zip.write(bytes, 0, len);
						}
						zip.closeEntry();
						in.close();
					}
				}catch(Throwable e){
					this.log.error(e.getMessage(), e);
				}

				index++;
			}
			zip.flush();
			zip.close();

			FacesContext.getCurrentInstance().responseComplete();

			// End of the method
		}catch (Exception e) {
			this.log.error(e.getMessage(), e);
			MessageUtils.addErrorMsg("Si e' verificato un errore durante il download dell'allegato.");
		}
		return null;
	}

	public void setTipoMessaggio(String value) {
		if(value != null )
			this.tipoMessaggio = (TipoMessaggio) TipoMessaggio.toEnumConstantFromString(value);
	}

	public String getTipoMessaggio() {
		if(this.tipoMessaggio == null){
			return null;
		}else{
			return this.tipoMessaggio.toString();
		}
	}

	public org.openspcoop2.core.transazioni.constants.TipoMessaggio getTipoMessaggioEnum() {
		return this.tipoMessaggio;
	}

	public void setTipoMessaggioEnum(org.openspcoop2.core.transazioni.constants.TipoMessaggio tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public String getTitoloPagina() {
		if(this.tipoMessaggio != null) {
			switch (this.tipoMessaggio) {
			case RICHIESTA_INGRESSO:
				return "Messaggio di Richiesta Contenuti Ingresso";
			case RICHIESTA_USCITA:
				return "Messaggio di Richiesta Contenuti Uscita";
			case RISPOSTA_INGRESSO:
				return "Messaggio di Risposta Contenuti Ingresso";
			case RISPOSTA_USCITA:	
				return "Messaggio di Risposta Contenuti Uscita";
			case INTEGRATION_MANAGER:
			case RICHIESTA_INGRESSO_DUMP_BINARIO:
			case RICHIESTA_USCITA_DUMP_BINARIO:
			case RISPOSTA_INGRESSO_DUMP_BINARIO:
			case RISPOSTA_USCITA_DUMP_BINARIO:
			default:
				return "Contenuti Messaggio";
			}
		}
		return "Contenuti Messaggio";
	}
}
