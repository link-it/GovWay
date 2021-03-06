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
package org.openspcoop2.utils.openapi.validator;

import org.openspcoop2.utils.json.JsonValidatorAPI.ApiName;
import org.openspcoop2.utils.rest.ApiValidatorConfig;

/**
 * SwaggerApiValidatorConfig
 * 
 * @author Bussu Giovanni (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 *
 */
public class OpenapiApiValidatorConfig extends ApiValidatorConfig {

	private ApiName jsonValidatorAPI;
	private OpenapiApi4jValidatorConfig openApi4JConfig;

	public OpenapiApi4jValidatorConfig getOpenApi4JConfig() {
		return this.openApi4JConfig;
	}

	public void setOpenApi4JConfig(OpenapiApi4jValidatorConfig openApi4JConfig) {
		this.openApi4JConfig = openApi4JConfig;
	}

	public ApiName getJsonValidatorAPI() {
		return this.jsonValidatorAPI;
	}

	public void setJsonValidatorAPI(ApiName jsonValidatorAPI) {
		this.jsonValidatorAPI = jsonValidatorAPI;
	}
}
