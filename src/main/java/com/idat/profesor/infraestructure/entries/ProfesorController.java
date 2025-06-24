package com.idat.profesor.infraestructure.entries;

import com.idat.profesor.domain.model.Profesor;
import com.idat.profesor.domain.service.ProfesorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instituto/profesor")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> findAll() {
        List<Profesor> profesores = profesorService.findAll();
        return ResponseEntity.ok(profesores);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Profesor> findByUid(@PathVariable String uid) {
        Profesor profesor = profesorService.findById(uid);
        if (profesor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profesor);
    }

    @PostMapping
    public ResponseEntity<Profesor> save(@RequestBody Profesor profesor) {
        Profesor saved = profesorService.save(profesor);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Profesor> update(@PathVariable String uid, @RequestBody Profesor profesor) {
        Profesor updated = profesorService.update(uid, profesor);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> delete(@PathVariable String uid) {
        profesorService.delete(uid);
        return ResponseEntity.noContent().build();
    }
}

