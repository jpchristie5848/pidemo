package com.jipthechip.pidemo.repository;

import com.jipthechip.pidemo.model.LightLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightLevelRepository extends JpaRepository<LightLevel, Long> {
}
