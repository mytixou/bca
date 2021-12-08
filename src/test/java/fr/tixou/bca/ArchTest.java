package fr.tixou.bca;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fr.tixou.bca");

        noClasses()
            .that()
            .resideInAnyPackage("fr.tixou.bca.service..")
            .or()
            .resideInAnyPackage("fr.tixou.bca.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..fr.tixou.bca.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
