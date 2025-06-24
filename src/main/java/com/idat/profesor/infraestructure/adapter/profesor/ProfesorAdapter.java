package com.idat.profesor.infraestructure.adapter.profesor;

import com.idat.profesor.domain.model.Profesor;
import com.idat.profesor.domain.model.ProfesorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProfesorAdapter implements ProfesorRepository {

    private final ProfesorDataRepository profesorDataRepository;

    public ProfesorAdapter(ProfesorDataRepository profesorDataRepository) {
        this.profesorDataRepository = profesorDataRepository;
    }

    @Override
    public List<Profesor> getProfesores() {
        return profesorDataRepository.findAll()
                .stream()
                .filter(profesorData -> profesorData.getActive() == 1)
                .map(ProfesorMapper.MAPPER::toModel)
                .toList();
    }

    @Override
    public Profesor findById(String id) {
        Optional<ProfesorData> profesorOptional = profesorDataRepository.findById(id);
        if (profesorOptional.isPresent()) {
            if (profesorOptional.get().getActive() == 1) {
                return ProfesorMapper.MAPPER.toModel(profesorOptional.get());
            }
        }
        return null;
    }

    @Override
    public Profesor save(Profesor profesor) {

        ProfesorData data = ProfesorMapper.MAPPER.toData(profesor);

        if (data.getUid() == null || data.getUid().isEmpty()) {
            data.setUid(UUID.randomUUID().toString());
        }

        data.setActive(1);

        ProfesorData saved = profesorDataRepository.save(data);
        return ProfesorMapper.MAPPER.toModel(saved);
    }

    @Override
    public Profesor update(String uid, Profesor profesor) {
        Optional<ProfesorData> optional = profesorDataRepository.findById(uid);
        if (optional.isPresent()) {
            ProfesorData existing = optional.get();

            existing.setDocumentNumber(profesor.documentNumber());
            existing.setName(profesor.name());
            existing.setLastName(profesor.lastName());
            existing.setPhone(profesor.phone());
            existing.setEmail(profesor.email());
            existing.setPhoto(profesor.photo());

            ProfesorData updated = profesorDataRepository.save(existing);
            return ProfesorMapper.MAPPER.toModel(updated);
        } else {
            return null;
        }
    }

    @Override
    public void delete(String uid) {
        Optional<ProfesorData> optional = profesorDataRepository.findById(uid);
        if (optional.isPresent()) {
            ProfesorData existing = optional.get();
            existing.setActive(0);
            profesorDataRepository.save(existing);
        }
    }
}

