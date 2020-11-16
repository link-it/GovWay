/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
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

package org.openspcoop2.core.protocolli.trasparente.testsuite;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.openspcoop2.core.controllo_traffico.AttivazionePolicy;
import org.openspcoop2.core.controllo_traffico.beans.UniqueIdentifierUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
* DbUtils
*
* @author Francesco Scarlato (scarlato@link.it)
* @author $Author$
* @version $Rev$, $Date$
*/
public class DbUtils {

    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

    private final JdbcTemplate jdbc;

    public DbUtils(Map<String, String> config) {
        String url = (String) config.get("url");
        String username = (String) config.get("username");
        String password = (String) config.get("password");
        String driver = (String) config.get("driverClassName");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        this.jdbc = new JdbcTemplate(dataSource);
        logger.info("init jdbc template: {}", url);
    }

    public Object readValue(String query) {
        return this.jdbc.queryForObject(query, Object.class);
    }

    public Map<String, Object> readRow(String query) {
        return this.jdbc.queryForMap(query);
    }

    public List<Map<String, Object>> readRows(String query) {
        return this.jdbc.queryForList(query);
    }

    public int update(String query) {
        return this.jdbc.update(query);
    }
    

    public String getPolicyIdErogazione(String erogatore, String api) {
    	final String filtroPorta = "gw_" + erogatore + "/gw_" + api + "/v1";
    	String query = "select active_policy_id,POLICY_UPDATE_TIME from ct_active_policy WHERE POLICY_ALIAS='RichiestePerMinuto' AND FILTRO_PORTA='"+filtroPorta+"' AND FILTRO_RUOLO='applicativa' AND filtro_protocollo='trasparente'";
    	var result = readRow(query);
    	    	
    	String active_policy_id = (String) result.get("active_policy_id");
    	Timestamp policy_update_time = (Timestamp) result.get("policy_update_time");   	
    	
       	AttivazionePolicy policy = new AttivazionePolicy();
    	policy.setIdActivePolicy(active_policy_id);
    	policy.setUpdateTime(policy_update_time);
       	
    	// TODO: Il reset non mi funziona perchè per qualche motivo non mi appende il time alla url
       	return UniqueIdentifierUtilities.getUniqueId(policy);
    }
    
    public String getPolicyIdFruizione(String fruitore, String erogatore, String api) {
    	final String filtroPorta = "gw_" + fruitore + "/gw_" + erogatore + "/gw_" + api + "/v1";
    	
    	String query = "select active_policy_id,POLICY_UPDATE_TIME from ct_active_policy WHERE POLICY_ALIAS='RichiestePerMinuto' AND FILTRO_PORTA='"+filtroPorta+"' AND FILTRO_RUOLO='delegata' AND filtro_protocollo='trasparente'";
      	var result = readRow(query);
    	
    	String active_policy_id = (String) result.get("active_policy_id");
    	Timestamp policy_update_time = (Timestamp) result.get("policy_update_time");   	
    	
       	AttivazionePolicy policy = new AttivazionePolicy();
    	policy.setIdActivePolicy(active_policy_id);
    	policy.setUpdateTime(policy_update_time);
       	
       	return UniqueIdentifierUtilities.getUniqueId(policy);
    }

}
