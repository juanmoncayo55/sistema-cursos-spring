package com.juan.curso.springboot.app.sistema.cursos.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageStudentResponse {
	private List<?> data;
	private int number;
	private int numberOfElements;
	private int size;
	private long totalElements;
	private int totalPages;
	
	public PageStudentResponse(Page<?> page) {
	    this.data = page.getContent();
	    this.number = page.getNumber();
	    this.numberOfElements = page.getNumberOfElements();
	    this.size = page.getSize();
	    this.totalElements = page.getTotalElements();
	    this.totalPages = page.getTotalPages();
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
