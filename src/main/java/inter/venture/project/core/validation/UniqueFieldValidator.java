package inter.venture.project.core.validation;

import inter.venture.project.core.validation.constraints.UniqueField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private Class entity;

    private String fieldName;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(this.entity);

        Root root = query.from(this.entity);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get(this.fieldName), value));

        query.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[]{}));

        return entityManager.createQuery(query).getSingleResult().equals(0L);
    }
}
