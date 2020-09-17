package com.acme.edu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.System.lineSeparator;

public class BuildTest implements SysoutCaptureAndAssertionAbility {
    @Before
    public void setUpSystemOut() {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }

    @Test
    public void shouldWriteBuild() {
        Project.build();
        assertSysoutEquals("Build" + lineSeparator());
    }
}
