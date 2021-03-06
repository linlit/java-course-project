package com.acme.edu.client;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.exception.InvalidMessageException;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessagePreprocessorTest implements SysoutCaptureAndAssertionAbility {
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

    @Test
    public void shouldDecorateSendMessageWhenSendCommandSND() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        Assertions.assertThat(manager.getFilteredMessage("/snd cat")).contains("/snd", "cat");
    }

    @Test
    public void shouldSendMessageExitORHistWithoutChange() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        Assertions.assertThat(manager.getFilteredMessage("/exit")).isEqualTo("/exit");
        Assertions.assertThat(manager.getFilteredMessage("/hist")).isEqualTo("/hist");
    }

    @Test
    public void shouldSendMessageExitORHistORChidWithoutChange() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        Assertions.assertThat(manager.getFilteredMessage("/exit")).isEqualTo("/exit");
        Assertions.assertThat(manager.getFilteredMessage("/hist")).isEqualTo("/hist");
        Assertions.assertThat(manager.getFilteredMessage("/chid name")).isEqualTo("/chid name");
    }

    @Test
    public void shouldNotifyWhenMessageLengthOverThan150() throws InvalidMessageException {
        MessagePreprocessor manager = new MessagePreprocessor();
        manager.getFilteredMessage("/snd qwertyuiopqwertyuiocfcgcvfgcvgjf" +
                "cgffgcjvggcghcghcgfhcfgcgpsdfghjklasdfghjkasdfghjksdfghjks" +
                "dfghjklsdfvgbhnsdfvbnxcvbndfghjsdfgbhncvbghnfcyscgcfgcvfgcf" +
                "gcfgcfgcfgcgcjcjchgjbbkkjkkjhkggvfcvghbjfvgbhjnkcdfgvhbjdcfg");
        assertSysoutEquals("Message length is over 150 characters" + System.lineSeparator());
    }

    @Test
    public void shouldDifferCommandFromOther() {
        MessagePreprocessor manager = new MessagePreprocessor();
        Assertions.assertThat(manager.isExitCommand("/snd g")).isFalse();
        Assertions.assertThat(manager.isExitCommand("/exit")).isTrue();
    }
}
