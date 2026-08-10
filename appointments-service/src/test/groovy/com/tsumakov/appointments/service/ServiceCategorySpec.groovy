package dev.tsumakov.appointments.service

import spock.lang.Specification

class ServiceCategorySpec extends Specification {

    static def createServiceCategory(String code = "code", String name = "code-name") {
        return ServiceCategory.builder()
                .code(code)
                .name(name)
                .build()
    }

    def "equals and hashCode should only compare the code field"() {
        given: "two categories with the same code but different names"
        def category1 = createServiceCategory("code1", "code-name-1")
        def category2 = createServiceCategory("code1", "code-name-2")

        and: "a category with a different code"
        def category3 = createServiceCategory("code2", "code-name-1")

        expect: "categories with the same code to be equal and have the same hashCode"
        category1 == category2
        category1.hashCode() == category2.hashCode()

        and: "categories with different codes to not be equal"
        category1 != category3
    }

}
