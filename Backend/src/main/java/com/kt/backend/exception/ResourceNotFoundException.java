package com.kt.backend.exception;

//import lombok.Getter;
//import lombok.Setter;

//@Setter
//@Getter
public class ResourceNotFoundException extends RuntimeException {
	
	private Object fieldValue;
	
	private static final long serialVersionUID = 1L;
	String resourcename;
	String fieldname;
	long fieldvalue;
	
	public ResourceNotFoundException(String resourcename, String fieldname, long fieldvalue) {
		super(String.format("%s not found with %s: %s", resourcename, fieldname, fieldvalue));
		this.resourcename = resourcename;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourcename = resourceName;
        this.fieldname = fieldName;
        this.fieldValue = fieldValue;
    }
	//get set
	
	public String getResourcename() {
		return resourcename;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public long getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(long fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}
