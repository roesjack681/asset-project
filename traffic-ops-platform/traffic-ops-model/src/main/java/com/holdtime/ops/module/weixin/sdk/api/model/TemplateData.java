package com.holdtime.ops.module.weixin.sdk.api.model;

public class TemplateData {
	private String value;  
    private String color;
    
    public TemplateData() {
	}
    
    public TemplateData(String value, String color) {
    	this.value = value;
    	this.color = color;
	}
    
	public String getValue() {
		return value;
	}
	
	public TemplateData setValue(String value) {
		this.value = value;
		return this;
	}
	
	public String getColor() {
		return color;
	}
	
	public TemplateData setColor(String color) {
		this.color = color;
		return this;
	} 
}
