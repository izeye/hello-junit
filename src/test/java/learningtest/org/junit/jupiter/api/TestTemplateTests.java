package learningtest.org.junit.jupiter.api;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TestTemplate}.
 *
 * @author Johnny Lim
 */
class TestTemplateTests {

    List<String> fruits = List.of("apple", "banana", "lemon");

    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider.class)
    void test(String fruit) {
        assertThat(fruits).contains(fruit);
    }

    static class MyTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
            return true;
        }

        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
            return Stream.of(invocationContext("apple"), invocationContext("banana"));
        }

        private TestTemplateInvocationContext invocationContext(String parameter) {
            return new TestTemplateInvocationContext() {
                @Override
                public String getDisplayName(int invocationIndex) {
                    return parameter;
                }

                @Override
                public List<Extension> getAdditionalExtensions() {
                    return List.of(new ParameterResolver() {
                        @Override
                        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                            return parameterContext.getParameter().getType() == String.class;
                        }

                        @Override
                        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                            return parameter;
                        }
                    });
                }
            };
        }

    }
}
