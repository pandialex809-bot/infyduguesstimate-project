package com.infosys.repository;

import com.infosys.entity.Lob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobRepository extends JpaRepository<Lob, String> {
}
