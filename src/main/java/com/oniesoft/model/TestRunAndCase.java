package com.oniesoft.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class TestRunAndCase {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String testCaseName;
        private String status;
        private String author;
        private String automationId;
        private String feature;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

public TestRunAndCase(){

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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


        @Override
        public String toString() {
            return "TestCase{" +
                    "id=" + id +
                    ", testCaseName='" + testCaseName + '\'' +
                    ", status='" + status + '\'' +
                    ", author='" + author + '\'' +
                    ", automationId='" + automationId + '\'' +
                    ", feature='" + feature + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    '}';
        }
    }
