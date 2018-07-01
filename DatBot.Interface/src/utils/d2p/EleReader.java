package utils.d2p;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sun.media.sound.InvalidFormatException;

import utils.d2p.elements.AnimatedGraphicalElementData;
import utils.d2p.elements.BlendedGraphicalElementData;
import utils.d2p.elements.BoundingBoxGraphicalElementData;
import utils.d2p.elements.EleRegistry;
import utils.d2p.elements.EntityGraphicalElementData;
import utils.d2p.elements.GraphicalElementData;
import utils.d2p.elements.NormalGraphicalElementData;
import utils.d2p.elements.ParticlesGraphicalElementData;
import utils.d2p.elements.reader.Bytes;
import utils.d2p.elements.reader.DataReader;
import utils.d2p.elements.reader.HeapDataReader;
import utils.d2p.elements.reader.Zlib;

public final class EleReader {
    private EleReader() {}

    /**
     * Read all elements from an ELE file
     * @param path where to find the file
     * @return a non-null registry
     * @throws InvalidFormatException 
     */
    public static EleRegistry read(Path path) throws InvalidFormatException {
        byte[] bytes = Bytes.from(path);
        DataReader reader = new HeapDataReader(bytes, 0, bytes.length);

        byte header = reader.read_i8();
        if (header != 69) {
            byte[] uncompressed = Zlib.uncompress(bytes);
            DataReader tmp_reader = new HeapDataReader(uncompressed, 0, uncompressed.length);
            header = tmp_reader.read_i8();

            if (header != 69) {
                throw new InvalidFormatException();
            }

            reader = tmp_reader;
        }

        byte fileVersion = reader.read_i8();

        Map<Integer, GraphicalElementData> elements = new HashMap<>();
        long elementsCount = reader.read_ui32();
        for (long i = 0; i < elementsCount; i++) {
            // version>=9 supports lazy-loading
            // the file in that case write the number of bytes to skip
            int skipLen = 0;
            if (fileVersion >= 9) {
                skipLen = reader.read_ui16();
            }

            int elementId = reader.read_i32();
            long elementPosition = reader.getPosition();
            byte elementType = reader.read_i8();

            GraphicalElementData element = readGraphicalElement(reader, fileVersion, elementId, elementType);
            elements.put(elementId, element);

            if (fileVersion > 8) {
                reader.setPosition(elementPosition + skipLen - 4);
            }
        }

        Set<Integer> jpg = new HashSet<>();
        if (fileVersion >= 8) {
            int gfxCount = reader.read_i32();
            for (int i = 0; i < gfxCount; i++) {
                int gfxId = reader.read_i32();
                jpg.add(gfxId);
            }
        }

        return new EleRegistry(fileVersion, elements, jpg);
    }

    private static GraphicalElementData readGraphicalElement(DataReader reader, int version, int id, int type) {
        GraphicalElementData result;
        switch (type) {
            case 0:
                result = new NormalGraphicalElementData(id, type);
                break;
            case 1:
                result = new BoundingBoxGraphicalElementData(id, type);
                break;
            case 2:
                result = new AnimatedGraphicalElementData(id, type);
                break;
            case 3:
                result = new EntityGraphicalElementData(id, type);
                break;
            case 4:
                result = new ParticlesGraphicalElementData(id, type);
                break;
            case 5:
                result = new BlendedGraphicalElementData(id, type);
                break;
            default:
                throw new IllegalArgumentException();
        }
        readGraphicalElement0(reader, version, type, result);
        return result;
    }

    private static void readGraphicalElement0(DataReader reader, int version, int edType, GraphicalElementData element) {
        switch (edType) {
            case 0:
                NormalGraphicalElementData nged = (NormalGraphicalElementData) element;
                nged.setGfxId(reader.read_i32());
                nged.setHeight(reader.read_i8());
                nged.setHorizontalSymmetry(reader.read_bool());
                nged.setOriginX(reader.read_i16());
                nged.setOriginY(reader.read_i16());
                nged.setSizeX(reader.read_i16());
                nged.setSizeY(reader.read_i16());
                break;
            case 1: // BOUNDING_BOX
                BoundingBoxGraphicalElementData bbged = (BoundingBoxGraphicalElementData) element;
                readGraphicalElement0(reader, version, 0, bbged);
                break;
            case 2: // ANIMATED
                AnimatedGraphicalElementData aged = (AnimatedGraphicalElementData) element;
                readGraphicalElement0(reader, version, 0, aged);
                if (version == 4) {
                    aged.setMinDelay(reader.read_i32());
                    aged.setMaxDelay(reader.read_i32());
                }
                break;
            case 3: // ENTITY
                EntityGraphicalElementData eged = (EntityGraphicalElementData) element;
                eged.setEntityLook(reader.read_str());
                eged.setHorizontalSymmetry(reader.read_bool());
                if (version >= 7) {
                    eged.setPlayAnimation(reader.read_bool());
                }
                if (version >= 6) {
                    eged.setPlayAnimStatic(reader.read_bool());
                }
                if (version >= 5) {
                    eged.setMinDelay(reader.read_i32());
                    eged.setMaxDelay(reader.read_i32());
                }
                break;
            case 4: // PARTICLES
                ParticlesGraphicalElementData pged = (ParticlesGraphicalElementData) element;
                pged.setScriptId(reader.read_i16());
                break;
            case 5: // BLENDED
                BlendedGraphicalElementData bged = (BlendedGraphicalElementData) element;
                readGraphicalElement0(reader, version, 0, bged);
                bged.setBlendMode(reader.read_str());
                break;
        }
    }
}