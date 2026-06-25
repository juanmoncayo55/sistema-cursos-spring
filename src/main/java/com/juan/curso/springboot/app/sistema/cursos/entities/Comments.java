package com.juan.curso.springboot.app.sistema.cursos.entities;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="{NotBlank.comments.comment}")
	private String comment;
	
	@NotNull(message="{NotNull.comments.date}")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="student_id", nullable = false)
	@JsonIgnore
	@NotNull(message="{NotNull.comments.student_id}")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="class_id", nullable = false)
	@JsonIgnore
	@NotNull(message="{NotNull.comments.class_id}")
	private Classes classe;
	
	public Comments() { }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Classes getClasse() {
		return classe;
	}

	public void setClasse(Classes classe) {
		this.classe = classe;
	}
	
	public Long getIdStudent() {
		return this.getStudent().getId();
	}
	
	public Long getIdClass() {
		return this.getClasse().getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comments other = (Comments) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Comments {id=" + id + ", comment=" + comment
				+ ", date=" + date + ", student=" + student + ", classe=" + classe + "}";
	}
	
	
}