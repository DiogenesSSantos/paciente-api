package com.git.devdioge.paciente_api.repository;

import com.git.devdioge.paciente_api.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IPacienteRepositoryImpl implements IPacienteRepositoryCustom {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Paciente> buscarTodos() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Paciente> query = builder.createQuery(Paciente.class);
        Root<Paciente> root = query.from(Paciente.class);

        query.select(root);

        TypedQuery<Paciente> typedQuery = entityManager.createQuery(query);
        List<Paciente> pacienteList = typedQuery.getResultList();

        return pacienteList;
    }

    @Override
    public List<Paciente> buscarPorNome(String nome) {
       CriteriaBuilder builder = entityManager.getCriteriaBuilder();
       CriteriaQuery<Paciente> query = builder.createQuery(Paciente.class);
       Root<Paciente> root = query.from(Paciente.class);
       query.select(root);

       if (nome == null || nome.isBlank()) {
           TypedQuery<Paciente> typedQuery = entityManager.createQuery(query);
           return typedQuery.getResultList();
       }

       Predicate predicate = builder.like(root.get("nome"),nome+"%");
       query.where(predicate);

       TypedQuery<Paciente> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Optional<Paciente> buscarPorCodigo(String codigo) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Paciente> query = builder.createQuery(Paciente.class);
        Root<Paciente> root = query.from(Paciente.class);

        query.select(root);
        Predicate validaCodigo = builder.equal(root.get("codigo"), codigo);
        query.where(validaCodigo);

        TypedQuery<Paciente> pacienteTypedQuery = entityManager.createQuery(query);
        Paciente paciente = pacienteTypedQuery.getSingleResultOrNull();

        return paciente == null ? Optional.empty() : Optional.of(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Paciente> query = builder.createQuery(Paciente.class);
        Root<Paciente> root = query.from(Paciente.class);
        query.where(builder.equal(root.get("id"), id));

        TypedQuery<Paciente> typedQuery = entityManager.createQuery(query);
        Paciente paciente = typedQuery.getSingleResultOrNull();

        return paciente == null ? Optional.empty() : Optional.of(paciente) ;
    }
}
