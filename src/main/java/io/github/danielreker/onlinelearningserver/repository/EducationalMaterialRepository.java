package io.github.danielreker.onlinelearningserver.repository;

import io.github.danielreker.onlinelearningserver.model.EducationalMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalMaterialRepository extends JpaRepository<EducationalMaterial, Long>, JpaSpecificationExecutor<EducationalMaterial> {

}