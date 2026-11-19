package com.setupforge.setupforge_backend.setup.repository;

import com.setupforge.setupforge_backend.setup.model.Setup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetupRepository extends JpaRepository<Setup, Long> {

}
