package com.codeartify.overengineered;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.overengineered"); // base package to scan

    @Test
    void only_allow_access_to_shared_via_contract_interfaces() {
        ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..module.shared..")
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAnyPackage("..contract.shared..", "..module.shared..");

        rule.check(importedClasses);
    }

    @Test
    void only_injected_classes_should_be_interfaces_from_contract() {
        ArchRule rule = fields()
                .that()
                .areAnnotatedWith("org.springframework.beans.factory.annotation.Autowired")
                .and()
                .areDeclaredInClassesThat()
                .resideInAPackage("..module..")
                .should(new ArchCondition<>("have raw type in ..contract..") {
                    @Override
                    public void check(JavaField field, ConditionEvents events) {
                        String fieldTypePackage = field.getRawType().getPackageName();

                        if (!fieldTypePackage.startsWith("com.overengineered.contract")) {
                            String message = String.format(
                                    "Field %s has type %s which is not from the ..contract.. package",
                                    field.getFullName(),
                                    field.getRawType().getFullName()
                            );
                            events.add(SimpleConditionEvent.violated(field, message));
                        }
                    }
                });

        rule.check(importedClasses);
    }

    @Test
    void no_cyclic_dependencies_between_packages() {
        ArchRule rule = SlicesRuleDefinition.slices()
                .matching("com.codeartify.overengineered.(*)..") // scan sub-packages
                .should().beFreeOfCycles();

        rule.check(importedClasses);
    }
}
