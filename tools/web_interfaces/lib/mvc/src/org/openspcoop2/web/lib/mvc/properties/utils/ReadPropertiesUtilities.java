/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it).
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
package org.openspcoop2.web.lib.mvc.properties.utils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.core.mvc.properties.Collection;
import org.openspcoop2.core.mvc.properties.Config;
import org.openspcoop2.core.mvc.properties.Item;
import org.openspcoop2.core.mvc.properties.Section;
import org.openspcoop2.core.mvc.properties.Subsection;
import org.openspcoop2.web.lib.mvc.properties.beans.ConfigBean;
import org.openspcoop2.web.lib.mvc.properties.beans.ItemBean;
import org.openspcoop2.web.lib.mvc.properties.beans.SectionBean;
import org.openspcoop2.web.lib.mvc.properties.beans.SubsectionBean;

/***
 * 
 * ReadPropertiesUtilities utilities per la lettura della configurazione.
 * 
 * @author pintori
 *
 */
public class ReadPropertiesUtilities {

	public static ConfigBean leggiConfigurazione(Config config, Map<String, Properties> propertiesMap) throws Exception{
		ConfigBean configurazione = new ConfigBean();
		
		ValidationEngine.validateConfig(config);
		
		org.openspcoop2.core.mvc.properties.Properties properties = config.getProperties();
		if(properties != null) {
			List<Collection> collectionList = properties.getCollectionList();
			for (Collection collection : collectionList) {
				configurazione.getListaNomiProperties().add(collection.getName());
			}
		}
		
		List<Section> sectionList = config.getSectionList();
		
		for (int i= 0; i < sectionList.size() ; i++) {
			Section section = sectionList.get(i);
			addSectionBean(configurazione, section,"s"+i,propertiesMap);
		}
		
		return configurazione;
	}

	public static void addSectionBean(ConfigBean configurazione, Section section, String sectionIdx, Map<String, Properties> propertiesMap) throws Exception {
		SectionBean sectionBean = new SectionBean(section,sectionIdx);
		configurazione.addItem(sectionBean);
		
		// aggiungo gli item
		if(section.getItemList() != null) {
			for (Item item : section.getItemList()) {
				addItemBean(configurazione,item,propertiesMap);
			}
		}
		
		// aggiungo le sotto sezioni
		if(section.getSubsectionList() != null) {
			for (int i= 0; i < section.getSubsectionList().size() ; i++) {
				Subsection subSection  = section.getSubsectionList().get(i);
				addSubsectionBean(configurazione, subSection, sectionIdx+ "_ss"+i ,  propertiesMap);
			}
		}
	}
	
	public static void addSubsectionBean(ConfigBean configurazione, Subsection subSection, String subsectionIdx, Map<String, Properties> propertiesMap) throws Exception {
		SubsectionBean subsectionBean = new SubsectionBean(subSection,subsectionIdx);
		configurazione.addItem(subsectionBean);
		
		// aggiungo gli item
		if(subSection.getItemList() != null) {
			for (Item item : subSection.getItemList()) {
				addItemBean(configurazione,item,propertiesMap);
			}
		}
	}
	
	
	public static void addItemBean(ConfigBean configurazione, Item item, Map<String, Properties> propertiesMap) throws Exception{
		ItemBean itemBean = new ItemBean(item, item.getName()); 
		String name = item.getProperty() != null ? item.getProperty().getName() : item.getName();
		
		String propertyValue = getPropertyValue(propertiesMap, name);
		System.out.println("READ -> Item: Name ["+item.getName()+"]-----------------");  
		
		// Lettura di un valore aggregato
		if(item.getProperty() != null && item.getProperty().isAppend()) {
			// estraggo il valore della property che contiene la sequenza delle chiavi salvate
			String propertiesKeysValues = getPropertyValue(propertiesMap, Costanti.PRE_KEY_PROPERTIES_DEFAULT + name);
			if(propertiesKeysValues != null) {
				String [] chiaviSalvate = propertiesKeysValues.split(Costanti.KEY_PROPERTIES_DEFAULT_SEPARATOR);
				String [] propertyValueSplit = propertyValue.split(item.getProperty().getAppendSeparator());
				
				System.out.println("READ -> SPLITPATTERN ["+item.getProperty().getAppendSeparator()+"]");
				System.out.println("READ -> #KEYS ["+chiaviSalvate.length+"] #VALUES ["+propertyValueSplit.length+"]");
				System.out.println("READ -> KEYS ["+propertiesKeysValues+"] VALUES ["+propertyValue+"]");
				
				String propertyValueTmp = null;
				for (int j = 0; j < chiaviSalvate.length; j++) {
					String key = chiaviSalvate[j];
					System.out.println("READ -> CHECK KEY ["+key+"] ITEM ["+item.getName()+"]");  
					if(key.equals(item.getName())) {
						propertyValueTmp = propertyValueSplit[j];
						System.out.println("READ -> KEY ["+key+"] Values ["+propertyValueTmp+"]");  
						break;
					}
				}
				
				propertyValue = propertyValueTmp;
			}
		} 
		
		System.out.println("READ -> Item: Name ["+item.getName()+"] Value ["+propertyValue+"]");  
		itemBean.init(propertyValue); 
		
		System.out.println("----------------------------------------\n");
		configurazione.addItem(itemBean);
	}




	public static String getPropertyValue(Map<String, Properties> propertiesMap, String key) {
		if(propertiesMap == null)
			return null;
		
		for (String mapKey : propertiesMap.keySet()) {
			if(propertiesMap.get(mapKey).containsKey(key))
				return propertiesMap.get(mapKey).getProperty(key);
			
		}
		
		return null;
	}
}