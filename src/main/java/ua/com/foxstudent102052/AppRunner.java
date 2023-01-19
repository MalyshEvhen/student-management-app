package ua.com.foxstudent102052;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.com.foxstudent102052.client.ConsoleUI;
import ua.com.foxstudent102052.service.TestDataInitializer;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppRunner implements ApplicationRunner {
    private final TestDataInitializer testDataInitializer;
    private final ConsoleUI consoleUI;

    @Override
    public void run(ApplicationArguments args) {
        testDataInitializer.initTestDada();
        consoleUI.callMainMenu();
    }
}

