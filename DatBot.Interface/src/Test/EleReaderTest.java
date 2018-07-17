package Test;

import org.junit.Before;
import org.junit.Test;

import com.sun.media.sound.InvalidFormatException;

import main.Main;
import utils.d2p.EleReader;
import utils.d2p.elements.EleRegistry;
import utils.d2p.elements.NormalGraphicalElementData;

import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertNotNull;

public class EleReaderTest {

    private Path dofusPath;

    @Before
    public void setUp() throws Exception {
        this.dofusPath = Paths.get(Main.D2P_PATH);
    }

    @Test
    public void testLoadElements() throws InvalidFormatException {
        // given
        Path elementsPath = dofusPath.resolve("elements.ele");

        // when
        EleRegistry reg = EleReader.read(elementsPath);

        // then
        
        System.out.println(((NormalGraphicalElementData) reg.getElements().get(19086)).getGfxId());      
        
        assertNotNull("elements", reg.getElements());
        assertNotNull("jpg", reg.getJpg());
    }
}