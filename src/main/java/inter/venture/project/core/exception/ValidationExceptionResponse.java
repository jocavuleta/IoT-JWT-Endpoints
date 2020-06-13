package inter.venture.project.core.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationExceptionResponse {

    private List<Violation> violations;

    public ValidationExceptionResponse() {
        this.violations = new ArrayList<>();
    }

    public ValidationExceptionResponse(List<Violation> violations) {
        this.violations = new ArrayList<>(violations);
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    public void addViolation(Violation violation) {
        this.violations.add(violation);
    }
}
