package games.strategy.engine;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import games.strategy.engine.config.GameEngineProperty;
import games.strategy.engine.config.GameEnginePropertyReader;
import games.strategy.util.Version;

@RunWith(MockitoJUnitRunner.class)
public class EngineVersionTest {

  @Test
  public void testVersionParsingStopsAtFourNumbers() {
    final String expectedOutput = "1.2.3.4";
    String input = expectedOutput + ".5";


    EngineVersion testObj = createTestObj(input);
    assertThat(
        "We wrote to property file version:'1.2.3.4.5', and expect Version class to chomp off the fifth number and "
            + "return '1.2.3.4'",
        testObj.getVersion(), is(new Version(1, 2, 3, 4)));

    input = expectedOutput;
    testObj = createTestObj(input);
    assertThat(
        "We wrote to property file version:'1.2.3.4', and expect to get the same value back when reading from property "
            + "file.",
        testObj.getVersion(), is(new Version(1, 2, 3, 4)));
  }

  @Mock
  private GameEnginePropertyReader mockReader;

  private EngineVersion createTestObj(final String inputVersion) {
    when(mockReader.readProperty(GameEngineProperty.ENGINE_VERSION)).thenReturn(inputVersion);
    return new EngineVersion(mockReader);
  }


  @Test
  public void testGetExactVersion() {
    final String input = "1.2.3.4.5";
    final EngineVersion testObj = createTestObj(input);
    assertThat(
        "getExactVersion should return exactly what was set in the property file, no chomping of the fifth number.",
        testObj.getFullVersion(), is(input));
  }
}
