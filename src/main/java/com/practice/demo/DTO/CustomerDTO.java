package com.practice.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String firstname;
    private String lastname;
    
	public void setFirstname(String string) {
		this.firstname=string;
		
	}
	public void setLastname(String string) {
		this.lastname=string;		
	}
	public String getFirstname() {
		// TODO Auto-generated method stub
		return this.firstname;
	}
	public String getLastname() {
		// TODO Auto-generated method stub
		return this.lastname;
	}
}
