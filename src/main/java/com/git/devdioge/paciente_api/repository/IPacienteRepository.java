package com.git.devdioge.paciente_api.repository;

import com.git.devdioge.paciente_api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long>,
        IPacienteRepositoryCustom {

}
