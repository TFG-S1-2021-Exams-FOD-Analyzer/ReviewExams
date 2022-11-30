package gastmappers;

import gastmappers.csharpmapper.CSharpMapper;
import gastmappers.exceptions.UnsupportedLanguageException;
import gastmappers.javamapper.JavaMapper;
import gastmappers.liemapper.LIEMapper;
import gastmappers.sb2mapper.SB2Mapper;

/**
 * This class models a Factory, to return the desired mapper based on the Language.
 */
public class MapperFactory {

    /**
     * This method returns an specific instance of a Mapper, based on a Language.
     *
     * @param language The Language to be mapped.
     * @return A new instance of a Mapper.
     * @throws UnsupportedLanguageException If the selected Language doesn't have a Mapper yet.
     * @see Mapper
     * @see Language
     */
    public Mapper createMapper(Language language) throws UnsupportedLanguageException {
        switch (language) {
            case JAVA:
                return new JavaMapper();
            case CSHARP:
                return new CSharpMapper();
            case LIE:
                return new LIEMapper();
            case SCRATCH:
                return new SB2Mapper();
            default:
                throw new UnsupportedLanguageException();
        }
    }
}
