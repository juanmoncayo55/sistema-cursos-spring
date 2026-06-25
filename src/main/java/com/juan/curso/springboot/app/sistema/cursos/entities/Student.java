package com.juan.curso.springboot.app.sistema.cursos.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "{NotBlank.student.name}")
	private String name;
	
	@NotBlank(message = "{NotBlank.student.lastname}")
	private String lastname;
	
	@Email(message = "{Email.student.email}")
	@NotBlank(message = "{NotBlank.student.email}")
	private String email;
	
	@NotBlank(message = "{NotBlank.student.dni}")
	@Size(message = "{Size.student.dni}", min = 5, max = 12)
	private String dni;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "estudiantes_cursos",
		joinColumns = @JoinColumn(name = "student_id"),
		inverseJoinColumns = @JoinColumn(name = "course_id"),
		uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
	)
	private Set<Courses> courses;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id", unique = true)
	private CardId cardId;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Comments> comments;
	
	public Student() {
		courses = new HashSet<>();
		comments = new HashSet<>();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}	
	
	public Set<Courses> getCourses() {
		return courses;
	}

	public void setCourses(Set<Courses> courses) {
		this.courses = courses;
	}

	public CardId getCardId() {
		return cardId;
	}

	public void setCardId(CardId cardId) {
		this.cardId = cardId;
	}

	public Set<Comments> getComments() {
		return comments;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}

	public void addCourse(Courses course) {
		this.courses.add(course);
		course.getStudents().add(this);
	}
	
	public void addComment(Comments comment) {
		this.comments.add(comment);
		comment.setStudent(this);
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
		Student other = (Student) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Student {id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", dni=" + dni + "}";
	}
	
	
}
