package com.codeartify.overengineered;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class ArchitectureTest {

    private static final String BASE = "com.codeartify.overengineered";
    private static final String CONTRACT = BASE + ".contract..";
    private static final String MODULE = BASE + ".module..";

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE);

    // 1) No class defined in module can directly access another class in another feature package in module
    //    (e.g., module.person -> module.else is forbidden; within the same feature is allowed).
    @Test
    void module_classes_must_not_access_other_module_packages() {
        ArchCondition<JavaClass> notAccessOtherModuleFeatures = new ArchCondition<>("not access other module features") {
            @Override
            public void check(JavaClass origin, ConditionEvents events) {
                String originPkg = origin.getPackageName();
                if (!originPkg.startsWith(BASE + ".module.")) return;

                String originFeature = featureOf(originPkg);
                origin.getDirectDependenciesFromSelf().forEach(dep -> {
                    JavaClass target = dep.getTargetClass();
                    String targetPkg = target.getPackageName();
                    if (!targetPkg.startsWith(BASE + ".module.")) return;

                    String targetFeature = featureOf(targetPkg);
                    if (!originFeature.equals(targetFeature)) {
                        String message = String.format(
                                "Module class %s (feature %s) depends on %s (feature %s)",
                                origin.getName(), originFeature, target.getName(), targetFeature
                        );
                        events.add(SimpleConditionEvent.violated(origin, message));
                    }
                });
            }
        };

        ArchRule rule = classes().that().resideInAPackage(MODULE)
                .should(notAccessOtherModuleFeatures);

        rule.check(importedClasses);
    }

    // 2) Only classes defined in the same module package (or on a lower level) OR anywhere within the same feature
    //    can be accessed by a module class.
    //    Example: module.person.adapter.infrastructure can access its subpackage module.person.adapter.infrastructure.jpa
    //    and it can also access module.person.domain or module.person.app (same feature).
    @Test
    void module_classes_should_only_access_same_package_or_subpackages() {
        ArchCondition<JavaClass> onlyAccessSamePackageSubpackagesOrSameFeature =
                new ArchCondition<>("only access same package, its subpackages, or same feature") {
                    @Override
                    public void check(JavaClass origin, ConditionEvents events) {
                        String originPkg = origin.getPackageName();
                        if (!originPkg.startsWith(BASE + ".module.")) return;

                        String originFeature = featureOf(originPkg);

                        origin.getDirectDependenciesFromSelf().forEach(dep -> {
                            JavaClass target = dep.getTargetClass();
                            String targetPkg = target.getPackageName();

                            // Only constrain dependencies to other module classes
                            if (!targetPkg.startsWith(BASE + ".module.")) return;

                            boolean samePackage = targetPkg.equals(originPkg);
                            boolean inSubpackage = targetPkg.startsWith(originPkg + ".");
                            boolean sameFeature = originFeature.equals(featureOf(targetPkg));

                            if (!(samePackage || inSubpackage || sameFeature)) {
                                String message = String.format(
                                        "Module class %s (pkg %s, feature %s) depends on %s (pkg %s, feature %s), which is not same package, subpackage, or same feature",
                                        origin.getName(), originPkg, originFeature,
                                        target.getName(), targetPkg, featureOf(targetPkg)
                                );
                                events.add(SimpleConditionEvent.violated(origin, message));
                            }
                        });
                    }
                };

        ArchRule rule = classes()
                .that().resideInAPackage(MODULE)
                .should(onlyAccessSamePackageSubpackagesOrSameFeature);

        rule.check(importedClasses);
    }

    // helper to extract the feature name: com.x.module.<feature>.<rest>
    private static String featureOf(String pkg) {
        String marker = BASE + ".module.";
        int start = pkg.indexOf(marker);
        if (start < 0) return "";
        String after = pkg.substring(start + marker.length());
        int dot = after.indexOf('.');
        return dot < 0 ? after : after.substring(0, dot);
    }

    // 3) No class in contract can access anything in module.
    @Test
    void contract_must_not_depend_on_module() {
        ArchRule rule = noClasses().that().resideInAPackage(CONTRACT)
                .should().dependOnClassesThat().resideInAPackage(MODULE)
                .because("contract must be independent from module");
        rule.check(importedClasses);
    }


    // 4) Every package in contract needs a corresponding package in module with the same feature name.
    //    E.g., contract.person -> module.person
    @Test
    void every_contract_feature_has_matching_module_feature() {
        JavaPackage root = importedClasses.getPackage(BASE);
        Set<String> contractFeatures = root.getSubpackages().stream()
                .filter(p -> p.getName().startsWith(BASE + ".contract."))
                .map(p -> p.getName().substring((BASE + ".contract.").length()).split("\\.")[0])
                .collect(Collectors.toCollection(TreeSet::new));

        Set<String> moduleFeatures = root.getSubpackages().stream()
                .filter(p -> p.getName().startsWith(BASE + ".module."))
                .map(p -> p.getName().substring((BASE + ".module.").length()).split("\\.")[0])
                .collect(Collectors.toCollection(TreeSet::new));

        for (String feature : contractFeatures) {
            if (!moduleFeatures.contains(feature)) {
                throw new AssertionError("Missing corresponding module feature package for contract feature: " + feature +
                        " (expected package: " + BASE + ".module." + feature + ")");
            }
        }
    }

    // 5a) Presentation must not access contract outbound ports or contract exceptions
    @Test
    void presentation_must_not_use_contract_outbound_or_contract_exceptions() {
        ArchRule rule = noClasses().that()
                .resideInAPackage(BASE + ".module..adapter.presentation..")
                .should().accessClassesThat().resideInAnyPackage(
                        BASE + ".contract..port.outbound..",
                        BASE + ".contract..exception.."
                )
                .because("presentation must only use inbound ports and API DTOs, never outbound ports or contract exceptions");
        rule.check(importedClasses);
    }

    // 5b) Presentation must not access other module packages (but it may access its own presentation subtree)
    @Test
    void presentation_must_not_access_other_module_packages() {
        ArchCondition<JavaClass> notAccessOtherModulePackages = new ArchCondition<>("not access other module packages") {
            @Override
            public void check(JavaClass origin, ConditionEvents events) {
                String presentationRoot = BASE + ".module.";
                String originPkg = origin.getPackageName();
                if (!originPkg.contains(".adapter.presentation.")) return; // only presentation layer

                origin.getDirectDependenciesFromSelf().forEach(dep -> {
                    String targetPkg = dep.getTargetClass().getPackageName();
                    if (!targetPkg.startsWith(presentationRoot)) return; // allow any 3rd party/lib

                    boolean samePresentationTree = targetPkg.contains(".adapter.presentation.");
                    if (samePresentationTree) return; // allow self presentation subtree

                    String message = String.format(
                            "Presentation class %s depends on module class %s (%s)",
                            origin.getName(), dep.getTargetClass().getName(), targetPkg
                    );
                    events.add(SimpleConditionEvent.violated(origin, message));
                });
            }
        };

        ArchRule rule = classes()
                .that().resideInAPackage(BASE + ".module..adapter.presentation..")
                .should(notAccessOtherModulePackages);

        rule.check(importedClasses);
    }


    // 6) A class in module.app can only access interfaces defined in contract.port.outbound (regarding contract)
    //    Note: Access to its own module's domain is governed by rule #1; here we restrict contract usage.
    @Test
    void app_uses_only_contract_port_outbound_from_contract() {
        ArchRule rule = noClasses().that()
                .resideInAPackage(BASE + ".module..app..")
                .should().accessClassesThat().resideInAnyPackage(
                        BASE + ".contract..api..",
                        BASE + ".module..adapter.."
                )
                .because("application services may depend on outbound ports from contract and the domain, " +
                        "but must not use any adapter implementations");
        rule.check(importedClasses);
    }

    // 7) A class in module.adapter.infrastructure cannot be accessed directly from classes in module.app nor module.domain
    @Test
    void infrastructure_is_not_accessed_by_app_or_domain() {
        ArchRule rule = noClasses().that()
                .resideInAPackage(BASE + ".module..app..")
                .or().resideInAPackage(BASE + ".module..domain..")
                .should().accessClassesThat().resideInAPackage(BASE + ".module..adapter.infrastructure..")
                .because("infrastructure adapters must not be referenced directly by app or domain");
        rule.check(importedClasses);
    }

    // 8) Every module feature is an independent subdomain: no module package may access another module package.
    //    (e.g., module.person cannot access module.else).
    @Test
    void no_dependencies_between_module_features() {
        ArchRule rule = SlicesRuleDefinition.slices()
                .matching(BASE + ".module.(*)..")
                .should().notDependOnEachOther()
                .because("features/subdomains in module must be independent");
        rule.check(importedClasses);
    }

    @Test
    void app_services_must_implement_contract_inbound_port() {
        ArchCondition<JavaClass> implementContractInbound = new ArchCondition<>("implement an interface from contract.port.inbound") {
            @Override
            public void check(JavaClass clazz, ConditionEvents events) {
                boolean implementsInbound = clazz.getAllRawInterfaces().stream()
                        .anyMatch(i -> i.getPackageName().startsWith(BASE + ".contract.") &&
                                i.getPackageName().contains(".port.inbound"));
                if (!implementsInbound) {
                    String message = String.format(
                            "Class %s does not implement any contract inbound port interface",
                            clazz.getFullName()
                    );
                    events.add(SimpleConditionEvent.violated(clazz, message));
                }
            }
        };

        ArchRule rule = classes()
                .that().resideInAPackage(BASE + ".module..app..")
                .should(implementContractInbound)
                .because("application services must expose contract inbound ports");
        rule.check(importedClasses);
    }

    // ... existing code ...

    // 11) Every infrastructure adapter must implement a contract outbound port interface.
    @Test
    void infrastructure_adapters_must_implement_contract_outbound_port() {
        ArchCondition<JavaClass> implementContractOutbound = new ArchCondition<>("implement an interface from contract.port.outbound") {
            @Override
            public void check(JavaClass clazz, ConditionEvents events) {
                boolean implementsOutbound = clazz.getAllRawInterfaces().stream()
                        .anyMatch(i -> i.getPackageName().startsWith(BASE + ".contract.") &&
                                i.getPackageName().contains(".port.outbound"));
                if (!implementsOutbound) {
                    String message = String.format(
                            "Class %s does not implement any contract outbound port interface",
                            clazz.getFullName()
                    );
                    events.add(SimpleConditionEvent.violated(clazz, message));
                }
            }
        };

        ArchRule rule = classes()
                .that().resideInAPackage(BASE + ".module..adapter.infrastructure")
                .should(implementContractOutbound)
                .because("infrastructure adapters must implement contract outbound ports");
        rule.check(importedClasses);
    }

    @Test
    void presentation_references_only_contract_inbound_ports_and_not_app_services() {
        // 1) Forbid referencing module app services from presentation
        ArchRule presentationDoesNotReferenceApp =
                noClasses().that()
                        .resideInAPackage(BASE + ".module..adapter.presentation..")
                        .should().accessClassesThat().resideInAPackage(BASE + ".module..app..")
                        .because("presentation must not depend on app implementations; only on contract inbound ports");

        // 2) Any dependency from presentation to contract must be to inbound ports or API DTOs
        ArchCondition<JavaClass> onlyUsesInboundOrApiFromContract =
                new ArchCondition<>("depend on contract inbound ports or API DTOs only") {
                    @Override
                    public void check(JavaClass origin, ConditionEvents events) {
                        if (!origin.getPackageName().contains(".adapter.presentation.")) return;

                        origin.getDirectDependenciesFromSelf().forEach(dep -> {
                            String targetPkg = dep.getTargetClass().getPackageName();
                            if (!targetPkg.startsWith(BASE + ".contract.")) return; // ignore non-contract

                            boolean inbound = targetPkg.contains(".port.inbound");
                            boolean api = targetPkg.contains(".api");
                            if (!(inbound || api)) {
                                String message = String.format(
                                        "Presentation class %s depends on contract type %s not in port.inbound or api (%s)",
                                        origin.getName(), dep.getTargetClass().getName(), targetPkg
                                );
                                events.add(SimpleConditionEvent.violated(origin, message));
                            }
                        });
                    }
                };

        ArchRule presentationContractUsage =
                classes().that()
                        .resideInAPackage(BASE + ".module..adapter.presentation..")
                        .should(onlyUsesInboundOrApiFromContract)
                        .because("presentation should only use inbound ports (and API DTOs) from contract");

        presentationDoesNotReferenceApp.check(importedClasses);
        presentationContractUsage.check(importedClasses);
    }

    // ADDITIONAL RULE B: App services must ONLY reference contract outbound ports (when touching contract),
    // must NOT reference infrastructure adapters directly, and can freely use domain and libraries.
    @Test
    void app_references_only_contract_outbound_ports_and_not_infrastructure_adapters() {
        // 1) Forbid referencing infrastructure adapters from app
        ArchRule appDoesNotReferenceInfrastructure =
                noClasses().that()
                        .resideInAPackage(BASE + ".module..app..")
                        .should().accessClassesThat().resideInAPackage(BASE + ".module..adapter.infrastructure..")
                        .because("app must not depend on infrastructure implementations; only on contract outbound ports");

        // 2) Any dependency from app to contract must be to outbound ports only
        ArchCondition<JavaClass> onlyUsesOutboundFromContract =
                new ArchCondition<>("depend on contract outbound ports only") {
                    @Override
                    public void check(JavaClass origin, ConditionEvents events) {
                        if (!origin.getPackageName().contains(".module.") || !origin.getPackageName().contains(".app.")) return;

                        origin.getDirectDependenciesFromSelf().forEach(dep -> {
                            String targetPkg = dep.getTargetClass().getPackageName();
                            if (!targetPkg.startsWith(BASE + ".contract.")) return; // ignore non-contract

                            boolean outbound = targetPkg.contains(".port.outbound.");
                            if (!outbound) {
                                String message = String.format(
                                        "App class %s depends on contract type %s not in port.outbound (%s)",
                                        origin.getName(), dep.getTargetClass().getName(), targetPkg
                                );
                                events.add(SimpleConditionEvent.violated(origin, message));
                            }
                        });
                    }
                };

        ArchRule appContractUsage =
                classes().that()
                        .resideInAPackage(BASE + ".module..app..")
                        .should(onlyUsesOutboundFromContract)
                        .because("application services should only use outbound ports from contract");

        appDoesNotReferenceInfrastructure.check(importedClasses);
        appContractUsage.check(importedClasses);
    }

    @Test
    void domain_must_not_access_outside_domain() {
        ArchRule rule = classes().that()
                .resideInAPackage(BASE + ".module..domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        // allow only domain (any feature's domain is restricted elsewhere by feature isolation)
                        BASE + ".module..domain..",
                        BASE + ".contract..exception..",
                        // and JDK
                        "java..",
                        // And spring (additionally needed for using other libs in domain if needed)
                        "org.springframework.."
                )
                .because("domain should be independent and must not depend on app, adapters, or contract");
        rule.check(importedClasses);
    }

}
