package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Ad;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseAdService<T extends Ad> {

    protected final JpaRepository<T, Long> repository;

    protected BaseAdService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T createAd(T ad) {
        return repository.save(ad);
    }

    public Optional<T> getAd(Long id) {
        return repository.findById(id);
    }

    public List<T> getAllAds() {
        return repository.findAll();
    }

    public void deleteAd(Long id) {
        repository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(Specification<T> specification) {
        if (repository instanceof JpaSpecificationExecutor<?> specRepo) {
            return ((JpaSpecificationExecutor<T>) specRepo).findAll(specification);
        }
        throw new UnsupportedOperationException("Repository does not support Specifications");
    }
}
