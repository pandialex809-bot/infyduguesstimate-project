package com.infosys.service;

import com.infosys.dto.LobRequest;
import com.infosys.entity.Lob;
import com.infosys.repository.EstimateRepository;
import com.infosys.repository.LobRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LobService {
    private final LobRepository lobRepository;
    private final EstimateRepository estimateRepository;

    public LobService(LobRepository lobRepository, EstimateRepository estimateRepository) {
        this.lobRepository = lobRepository;
        this.estimateRepository = estimateRepository;
    }

    public List<Lob> all() {
        return lobRepository.findAll();
    }

    public Lob add(LobRequest request) {
        String name = request.getLob().trim().toUpperCase();
        if (lobRepository.existsById(name)) {
            throw new IllegalArgumentException("LOB already exists: " + name);
        }
        return lobRepository.save(new Lob(name, request.getDuAnchor().trim()));
    }

    public Lob update(String lobName, LobRequest request) {
        Lob lob = lobRepository.findById(lobName.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("LOB not found: " + lobName));
        lob.setDuAnchor(request.getDuAnchor().trim());
        return lobRepository.save(lob);
    }

    public void delete(String lobName) {
        if (!lobRepository.existsById(lobName.toUpperCase())) {
            throw new IllegalArgumentException("LOB not found: " + lobName);
        }
        estimateRepository.deleteByLobLobName(lobName.toUpperCase());
        lobRepository.deleteById(lobName.toUpperCase());
    }
}
