package gastmappers;

import gastmappers.exceptions.UnsupportedLanguageException;

/**
 * This enum modelates the languages that can be mapped in the project.
 */
public enum Language {
    JAVA,
    RPG,
    CSHARP,
    PYTHON,
    SCRATCH,
    LIE;

    /**
     * This method returns a Language based on a String code.
     *
     * @param language The string with the desired Language.
     * @return The required Language.
     * @throws UnsupportedLanguageException When the provided Language is not supported in the project.
     */
    public static Language getLanguageFromString(String language) throws UnsupportedLanguageException {
        switch (language) {
            case "JAVA":
                return JAVA;
            case "RPG":
                return RPG;
            case "CSHARP":
                return CSHARP;
            case "PYTHON":
                return PYTHON;
            case "SCRATCH":
                return SCRATCH;
            case "LIE":
                return LIE;
            default:
                throw new UnsupportedLanguageException();
        }
    }

    /**
     * This method returns the file extension associated to a particular Language.
     *
     * @param language The enum representation of the Language.
     * @return A String with the extension.
     * @throws UnsupportedLanguageException When the provided Language is not supported in the project.
     */
    public static String getFileExtension(Language language) throws UnsupportedLanguageException {
        switch (language) {
            case JAVA:
                return "java";
            case RPG:
                return "rpg";
            case CSHARP:
                return "cs";
            case PYTHON:
                return "py";
            case SCRATCH:
                return "json";
            case LIE:
                return "lie";
            default:
                throw new UnsupportedLanguageException();
        }
    }
}

