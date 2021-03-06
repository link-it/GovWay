<?xml version="1.0" encoding="UTF-8"?>
<db:configuration xmlns:db="http://domibus.eu/configuration" party="${soggettoOperativo}">

	<mpcs>
		<mpc name="defaultMpc"
			 qualifiedName="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC"
			 enabled="true"
			 default="true"
			 retention_downloaded="0"
			 retention_undownloaded="14400"/>
	</mpcs>
	<businessProcesses>
		<roles>
			<role name="defaultInitiatorRole" 
					value="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/initiator"/>
			<role name="defaultResponderRole" 
					value="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/responder"/>
		</roles>
		<parties>
			<partyIdTypes>
				<#list partyIdTypes as partyIdType>
					<partyIdType name="${partyIdType.name}" value="${partyIdType.value}"/>
				</#list>
			</partyIdTypes>
			<#list soggetti as soggetto>
			<party name="${soggetto.ebmsUserMessagePartyCN}"
				    endpoint="${soggetto.ebmsUserMessagePartyEndpoint}"
					allowChunking="false">
				<identifier partyId="${soggetto.ebmsUserMessagePartyId}" partyIdType="${soggetto.ebmsUserMessagePartyIdTypeName}"/>
			</party>
			</#list>
		</parties>
		<meps>
			<mep name="oneway" value="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/oneWay"/>
			<mep name="twoway" value="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/twoWay"/>
			<binding name="push" value="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/push"/>
			<binding name="pushAndPush" value="http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/push-and-push"/>
		</meps>
		<properties>
${properties.propertyDefault}

			<#list properties.property as p>
			<property name="${p.name}" 
					key="${p.key}" 
					datatype="${p.datatype}" 
					required="${p.required?c}"/> 
			</#list>
${properties.propertySetDefault}
			
			<#list properties.propertySet as p>
			<propertySet name="${p.name}">
			<#list p.propertyRef as pName>
				<propertyRef property="${pName}"/>
			</#list>			
			</propertySet>
			</#list>
		</properties>				
		<payloadProfiles>
${payloadProfiles.payloadDefault}

			<#list payloadProfiles.payloads as payload>
			<payload name="${payload.name}"
					cid="${payload.cid}" 
					<#if payload.inBody?has_content>inBody="${payload.inBody?c}"</#if> 
					required="${payload.required?c}"
					<#if payload.schemaFile?has_content>schemaFile="${payload.schemaFile}"</#if> 
					<#if payload.maxSize?has_content>maxSize="${payload.maxSize}"</#if> 
					<#if payload.mimeType?has_content>mimeType="${payload.mimeType}"/></#if> 
			</#list>
${payloadProfiles.payloadProfileDefault}
			
			<#list payloadProfiles.payloadProfiles as payloadProfile>
			<payloadProfile name="${payloadProfile.name}" 
					maxSize="${payloadProfile.maxSizeAsString}">
			<#list payloadProfile.attachments as attachment>
				<attachment name="${attachment}"/>
			</#list>			
			</payloadProfile>
			</#list>			
		</payloadProfiles>
		<securities>
		<#list policies as policy>
			<security name="${policy.name}"
					policy="${policy.policy}"
					signatureMethod="RSA_SHA256" />
		</#list>
		</securities>
		<errorHandlings>
			<errorHandling name="demoErrorHandling" 
					errorAsResponse="true" 
					businessErrorNotifyProducer="false"
					businessErrorNotifyConsumer="false" 
					deliveryFailureNotifyProducer="false"/>
		</errorHandlings>
		<agreements>
            		<agreement name="agreement1" value="A1" type=""/>
		</agreements>
		<services>
		<#list apcList as apc>
			<service name="${apc.id}" value="${apc.ebmsUserMessageCollaborationInfoServiceName}" 
			<#if apc.ebmsUserMessageCollaborationInfoServiceType?has_content>type="${apc.ebmsUserMessageCollaborationInfoServiceType}"</#if> 
				/>
		</#list>
		</services>
		<actions>
		<#list apis?values as api>
			<#list api.actions?values as action>
				<action name="${action.id}" value="${action.ebmsUserMessageCollaborationInfoActionName}"/>
			</#list>
		</#list>
		</actions>
		<as4>
			<receptionAwareness name="receptionAwareness" retry="12;4;CONSTANT" duplicateDetection="true"/>
			<#list soggetti as soggetto>
			<#list soggetto.getAps(soggettoOperativo) as aps>
			<reliability name="Reliability_${aps.id}" nonRepudiation="${aps.ebmsReliabilityNonRepudiation?c}" replyPattern="${aps.ebmsReliabilityReplyPattern}"/>
			</#list>
		</#list>
		</as4>
		<legConfigurations>
		<#list soggetti as soggetto>
			<#list soggetto.getAps(soggettoOperativo) as aps>
					<#list aps.azioni as azioneK, azione>
			<legConfiguration name="${azioneK}" 
					service="${aps.api.id}" 
					action="${azione.id}" 
					defaultMpc="defaultMpc" 
					reliability="Reliability_${aps.id}" 
					security="${aps.ebmsSecurityProfile}"
					receptionAwareness="receptionAwareness" 
					propertySet="${azione.ebmsActionPropertySet}"
					payloadProfile="${azione.ebmsActionPayloadProfile}"
					errorHandling="demoErrorHandling"
					compressPayloads="${azione.ebmsActionCompressPayload?c}"/>
					</#list>
			</#list>
		</#list>
		</legConfigurations>
		<#list soggetti as soggetto>
			<#list soggetto.getAps(soggettoOperativo,true) as aps>
<process name="${aps.id}"
         agreement=""
         mep="${aps.ebmsMep}"
         binding="${aps.ebmsBinding}"
         initiatorRole="defaultInitiatorRole"
         responderRole="defaultResponderRole">
	<initiatorParties>
		<#list aps.cnFruitori as fruitore>
		<initiatorParty name="${fruitore}"/>
		</#list>
	</initiatorParties>
	<responderParties>
		<responderParty name="${soggetto.ebmsUserMessagePartyCN}"/>
	</responderParties>
	<legs>
		<#list aps.azioni?keys as azioneK>
		<leg name="${azioneK}"/>
		</#list>
	</legs>
</process>
			</#list>
		</#list>
	</businessProcesses>
</db:configuration>
