package com.ngdb.persistence;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS)
public abstract class AbstractRepository<T> {

	private Class<T> clazz;

	@Inject
	@Autowired
	@PersistenceContext(unitName = "MyPersistenceUnit")
	protected EntityManager entityManager;

	protected AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T trouverParId(Long id) {
		return entityManager.find(clazz, id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void enregistrer(T t) {
		entityManager.persist(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> trouverTout() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cquery = cb.createQuery(clazz);
		Root<T> root = cquery.from(clazz);
		cquery.select(root);

		Query query = entityManager.createQuery(cquery);
		query.setMaxResults(50);
		return query.getResultList();
	}

	protected T getSingleResult(TypedQuery<T> query) throws ElementNonTrouveException {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new ElementNonTrouveException(e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void supprimer(T t) {
		entityManager.remove(t);
	}

    @Transactional(propagation = Propagation.REQUIRED)
    public T mettreAJour(T t) {
        return entityManager.merge(t);
    }

}
