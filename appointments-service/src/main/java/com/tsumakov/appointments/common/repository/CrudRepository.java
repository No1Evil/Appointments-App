package dev.tsumakov.appointments.common.repository;

import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {

  Optional<E> findById(@Nonnull ID identifier) throws DataAccessException;

  Optional<E> findByIdLocking(@Nonnull ID identifier) throws DataAccessException;

  List<E> findAll() throws DataAccessException;

  ID create(@Nonnull E entity) throws DataAccessException;

  boolean update(@Nonnull E entity) throws DataAccessException;

  boolean delete(@Nonnull ID identifier) throws DataAccessException;
}
