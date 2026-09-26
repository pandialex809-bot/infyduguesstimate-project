package com.infosys.controller;

import com.infosys.dto.LobRequest;
import com.infosys.entity.Lob;
import com.infosys.service.LobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/lobs")
@CrossOrigin(origins = "*")
public class LobController {
    private final LobService service;
    public LobController(LobService service) { this.service = service; }

    @GetMapping
    public List<Lob> all() { return service.all(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lob add(@Valid @RequestBody LobRequest request) { return service.add(request); }

    @PutMapping("/{lob}")
    public Lob update(@PathVariable String lob, @Valid @RequestBody LobRequest request) {
        return service.update(lob, request);
    }

    @DeleteMapping("/{lob}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String lob) { service.delete(lob); }
}
