package com.kpmac.kata;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class BasicTest {

    @Test
    public void gradleIsImportingJunitAndWhatnotCorrectly() {
        assertThat("everything works!").isEqualTo("everything works!");
    }

    @Test
    public void gradleIsRunningTests() {
        fail("tests are actually running!");
    }
}
