package com.acme.edu;

import com.acme.edu.client.MessagePreprocessor;
import com.acme.edu.exception.InvalidMessageException;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageManagerTest implements SysoutCaptureAndAssertionAbility {
    @Before
    public void setUpSystemOut() {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }

    @Test(expected = InvalidMessageException.class)
    public void shouldThrowInvalidMessageExceptionWhenWrongCommand() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        manager.getFilteredMessage("/histhdbhdbh");

    }

    @Test()
    public void shouldDecorateSendMessageWhenSendCommandSND() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        Assertions.assertThat(manager.getFilteredMessage("/snd pussy")).contains("/snd", "2020", "pussy");
    }

    @Test()
    public void shouldSendMessageExitORHistWithoutChange() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        Assertions.assertThat(manager.getFilteredMessage("/exit")).isEqualTo("/exit");
        Assertions.assertThat(manager.getFilteredMessage("/hist")).isEqualTo("/hist");
    }
}