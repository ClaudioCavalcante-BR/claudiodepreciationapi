package br.edu.infnet.mono.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @NotBlank(message = "Rua é obrigatória.")
    @Size(min = 3, max = 120, message = "Rua deve ter entre 3 e 120 caracteres.")
	private String street;

    @NotBlank(message = "Número do endereço é obrigatório.")
    @Size(max = 10, message = "Número não pode exceder 10 caracteres.")
    private String number;

    @NotBlank(message = "CEP é obrigatório.")
    @Pattern(regexp = "^[0-9]{6}-[0-9]{3}$", message = "CEP inválido. Use o formato 000000-000.")   
	private String zipcode;	
    
    @NotBlank(message = "Cidade/Localização é obrigatória.")
    @Size(min = 2, max = 80, message = "Cidade deve ter entre 2 e 80 caracteres.")    
	private String location;
    
    @Size(max = 80, message = "Bairro não pode exceder 80 caracteres.")
	private String neighborhood;
    
    @NotBlank(message = "O Nome do Estado por extenso é obrigatório.")
    @Size(min = 3, max = 50, message = "Estado deve ter entre 3 e 50 caracteres.")
	private String state;
    
    @NotBlank(message = "A sigla da Unidade Federativa (UF) é obrigatória.")
    @Pattern(regexp = "^(AC|AL|AM|AP|BA|CE|DF|ES|GO|MA|MG|MS|MT|PA|PB|PE|PI|PR|RJ|RN|RO|RR|RS|SC|SE|SP|TO)$", 
             message = "UF inválida. Informe uma unidade federativa válida.")
	private String uf;
	
    
	private String complement;  
	
	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", street='" + street + '\'' +
				", number='" + number + '\'' +
				", zipcode='" + zipcode + '\'' +
				", location='" + location + '\'' +
				", neighborhood='" + neighborhood + '\'' +
				", state='" + state + '\'' +
				", uf='" + uf + '\'' +
				", complement='" + complement + '\'' +
				'}';
						
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}
}
