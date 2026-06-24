package com.juan.curso.springboot.app.sistema.cursos.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "classes")
public class Classes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String mode;
	
	@Column(name = "max_capacity")
	private Integer maxCapacity;
	
	private String status;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "course_id")
	private Courses course;
	
	@OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Comments> comments;
	
	public Classes() {
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Set<Comments> getComments() {
		return comments;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comments comment) {
		this.comments.add(comment);
		comment.setClasse(this);
	}
	
	public Long getIdCourse() {
		return this.course.getId();
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
		Classes other = (Classes) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Classes {id=" + id + ", name=" + name + ", mode=" + mode + ", maxCapacity=" + maxCapacity + ", status="
				+ status + "}";
	}
}
