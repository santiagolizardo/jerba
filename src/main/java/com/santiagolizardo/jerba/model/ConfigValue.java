package com.santiagolizardo.jerba.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ConfigValue {

	@PrimaryKey
	@Persistent
	private String name;

	@Persistent
	private String type;

	@Persistent
	private String value;

	public ConfigValue() {
	}

	public ConfigValue(String name, String value) {
		setName(name);
		setValue(value);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ConfigValue [name=" + name + ", type=" + type + ", value="
				+ value + "]";
	}
}
