<%--
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
--%>



<%@ page session="true" import="java.util.*, org.openspcoop2.web.lib.mvc.*" %>

<%
String iddati = "";
String ct = request.getContentType();
if (ct != null && (ct.indexOf("multipart/form-data") != -1)) {
  iddati = (String) session.getAttribute("iddati");
} else {
  iddati = request.getParameter("iddati");
}
String gdString = "GeneralData";
String pdString = "PageData";
if (iddati != null && !iddati.equals("notdefined")) {
  gdString += iddati;
  pdString += iddati;
}
else
  iddati = "notdefined";
GeneralData gd = (GeneralData) session.getAttribute(gdString);
PageData pd = (PageData) session.getAttribute(pdString);


Vector<?> v = pd.getDati();

String numeroEntryS = request.getParameter("numeroEntry");
int numeroEntry = Integer.parseInt(numeroEntryS);

Vector<?> riga = (Vector<?>) v.elementAt(numeroEntry);

Vector<DataElement> vectorRiepilogo = new Vector<DataElement>();
Vector<DataElement> vectorImmagini = new Vector<DataElement>();
Vector<DataElement> vectorCheckBox = new Vector<DataElement>();
Vector<DataElement> vectorTags = new Vector<DataElement>();

for (int j = 0; j < riga.size(); j++) {
    DataElement de = (DataElement) riga.elementAt(j);
    
    if (de.getType().equals("image")) {
    	vectorImmagini.add(de);
   	} else  if (de.getType().equals("checkbox")) {
   		vectorCheckBox.add(de);
    } else if (de.getType().equals("button")) {
    	vectorTags.add(de);
    } else{
    	vectorRiepilogo.add(de);
    }
}
%>
<% if(vectorCheckBox.size() > 0){
		DataElement de = (DataElement) vectorCheckBox.elementAt(0);
		
		String statusType = de.getStatusTypes() != null && de.getStatusTypes().length>0 ? de.getStatusTypes()[0] : "";	
		String image = "status_red.png";
	 	if("yes".equals(statusType)){
	 		image = "status_green.png";
		}
		else if("warn".equals(statusType)){
			image = "status_yellow.png";
		}
		else if("off".equals(statusType)){
			image = "disconnected_grey.png";
		}
		else if("config_enable".equals(statusType)){
			image = "verified_green.png";
		}
		else if("config_warning".equals(statusType)){
			image = "verified_yellow.png";
		}
		else if("config_error".equals(statusType)){
			image = "verified_red.png";
		}
	 	else if("config_disable".equals(statusType)){
	 		image = "verified_grey.png";
		}

	 	String statusTooltip = de.getStatusToolTips() != null && de.getStatusToolTips().length>0 ? de.getStatusToolTips()[0] : "";		
		String statusTooltipTitleAttribute = statusTooltip != null && !statusTooltip.equals("") ? " title=\"" + statusTooltip + "\"" : "";
		 
	%>
	<td class="tdText" style="<%= de.getWidth() %>">
		<div id="stato_<%=numeroEntryS %>">
 			<span class="statoErogazioneIcon" id="iconConfigurazione_<%=numeroEntryS %>">
				<img src="images/tema_link/<%= image %>" <%= statusTooltipTitleAttribute %>/>
			</span>
		</div>
	</td>
<% } %>
<td>
	<div id="entry_<%=numeroEntryS %>" class="entryErogazione">
			<% 
				DataElement deTitolo = (DataElement) vectorRiepilogo.elementAt(0);
				String deTitoloName = "url_entry_"+numeroEntry;
				String deTitoloValue = !deTitolo.getValue().equals("") ? deTitolo.getValue() : "&nbsp;";
				String visualizzaAjaxStatus = deTitolo.isShowAjaxStatus() ? Costanti.JS_FUNCTION_VISUALIZZA_AJAX_STATUS : "";
				%><input id="<%= deTitoloName  %>" type="hidden" name="<%= deTitoloName  %>" value="<%= deTitolo.getUrl()  %>"/>
				
					<script type="text/javascript">
					   $('[id=entry_<%=numeroEntryS %>]')
					   .click(function() {
						   		<%= visualizzaAjaxStatus %>
								var val = $(this).children('input[id=url_entry_<%=numeroEntryS %>]').val();
								window.location = val;
					       });
				   </script>
				<%
			%>
		<div id="titolo_<%=numeroEntryS %>" class="titoloEntry">
			<span class="titoloEntry"><%=deTitoloValue %>&nbsp;&nbsp;&nbsp;&nbsp;</span>	
			
			<% if(vectorTags.size() > 0){ %>
				<div id="titolo_<%=numeroEntryS %>_tags" class="titoloTags">
					<% for(int z = 0; z < vectorTags.size(); z ++){ 
						DataElement tag = vectorTags.get(z);
					%>
						<span class="tag label label-info <%=tag.getStyleClass() %>"><%= tag.getLabel() %></span>
					<% } %>
				</div>
			<% } %>	
		</div>
		<% 
			DataElement deMetadati = (DataElement) vectorRiepilogo.elementAt(1);
			String deMetadatiValue = !deMetadati.getValue().equals("") ? deMetadati.getValue() : "&nbsp;";
			%>
		<div id="metadati_<%=numeroEntryS %>" class="metadatiEntry">
			<span class="metadatiEntry"><%=deMetadatiValue %>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
	</div>
</td>
<% if(vectorImmagini.size() > 0){ %>
	<td>
		<div id="funzionalita_<%=numeroEntryS %>" class="funzionalitaErogazione">
			<% 
			
			for (int j = 0; j < vectorImmagini.size(); j++) {
			    DataElement de = (DataElement) vectorImmagini.elementAt(j);
			 	String deValue = de.getValue();
			    String deName = !de.getName().equals("") ? de.getName() : "de_name_"+j;
				int wCount = deValue.split(" ") != null ? deValue.split(" ").length : 1;
				String spanClass= wCount == 1 ? "configurazioneErogazioneSpanOneLine" : "configurazioneErogazioneSpan";			
			  	%>
			  		<div id="configurazione_<%=j %>" class="configurazioneErogazione" title="<%=deValue %>">
			  			<div class="configurazioneErogazioneIcon">
				  			<span class="configurazioneErogazioneIcon" id="iconConfigurazione_<%=j %>">
								<i class="material-icons md-18 md-light">&#xE5CA;</i>
							</span>
						</div>
						<div class="<%=spanClass %>">
			  				<span class="<%=spanClass %>" ><%=deValue %></span>
		  				</div>
			  		</div>
		  		<%
			  	
			} // for
			%>
		</div>
	</td>
<% } %>

