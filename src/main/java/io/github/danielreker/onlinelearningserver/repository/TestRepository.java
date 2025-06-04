package io.github.danielreker.onlinelearningserver.repository;

import io.github.danielreker.onlinelearningserver.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}