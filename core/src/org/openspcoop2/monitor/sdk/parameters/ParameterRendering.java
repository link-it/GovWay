package org.openspcoop2.monitor.sdk.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * ParameterRendering
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ParameterRendering<T> {

	private String label;
	private String suggestion;
	private boolean required;
	private boolean hidden;	
	private T defaultValue;
	private Integer rows; // solo per text area
	private Integer columns; // solo per text area
	private List<String> values = new ArrayList<String>();
	
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSuggestion() {
		return this.suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public boolean isRequired() {
		return this.required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isHidden() {
		return this.hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public T getDefaultValue() {
		return this.defaultValue;
	}
	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<String> getValues() {
		return this.values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public Integer getRows() {
		return this.rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getColumns() {
		return this.columns;
	}
	public void setColumns(Integer columns) {
		this.columns = columns;
	}
	
}
