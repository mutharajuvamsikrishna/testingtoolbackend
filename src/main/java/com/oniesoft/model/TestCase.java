package com.oniesoft.model;

import java.time.LocalDateTime;


import jakarta.persistence.*;


@Entity
public class TestCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String testCaseName;

	private String author;
	private String automationId;
	private String feature;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	public TestCase(){

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAutomationId() {
		return automationId;
	}

	public void setAutomationId(String automationId) {
		this.automationId = automationId;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "TestCase{" +
				"id=" + id +
				", testCaseName='" + testCaseName + '\'' +
				", author='" + author + '\'' +
				", automationId='" + automationId + '\'' +
				", feature='" + feature + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", project=" + project +
				'}';
	}
}
