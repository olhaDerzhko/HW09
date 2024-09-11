package ui.runner;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        boolean testResult = context.getExecutionException().isPresent();
        System.out.println("\t\t\t\tException.isPresent() = " + testResult);
        System.out.println("\t\t\t\tTest context.getDisplayName(): "+ context.getDisplayName());

        TestRunner.isSuccess = !testResult;
    }
}
