package dna;
import java.lang.String;
import java.util.HashSet;

// TODO: Implement the DNA datatype from scratch!
// Use the test cases to guide you.

public class DNA {
    private String seqOriginal;
    private String seqClean;
    private int seqCleanLength;
    final double MASS_A = 135.128;
    final double MASS_C = 111.103;
    final double MASS_G = 151.128;
    final double MASS_T = 125.107;
    final double MASS_JUNK = 100.000;

    public DNA(String seq) {
        seqOriginal = seq;
        seqClean = removeJunk(seqOriginal);
        seqCleanLength = seqClean.length();

        if(!validSeq()) {
            throw new IllegalArgumentException("Invalid DNA sequence");
        }

    }

    //@return true if DNA sequence is a protein, otherwise returns false
    public boolean isProtein() {

        boolean starts = seqClean.substring(0,2).equals("ATG");
        boolean ends = seqClean.substring(seqCleanLength-3, seqCleanLength-1).equals("TAA") || seqClean.substring(seqCleanLength-3, seqCleanLength-1).equals("TAG") || seqClean.substring(seqCleanLength-3, seqCleanLength-1).equals("TGA");
        boolean containsCodons = seqCleanLength >= 5;
        boolean containsCG = percentCG() >= 30.0;

        if (starts && ends && containsCodons && containsCG) {
            return true;
        }

        return false;
    }


    //@return double value of the mass of the DNA sequence, rounded to first decimal
    public double totalMass() {
        double junkMass = (seqOriginal.length() - seqCleanLength) * MASS_JUNK;
        double totalMass = junkMass;
        for (int i = 0; i < seqCleanLength; i++) {
            Character currentChar = seqClean.charAt(i);
            if (currentChar.equals('A')) {
                totalMass += MASS_A;
            }
            else if (currentChar.equals('T')) {
                totalMass += MASS_T;
            }
            else if (currentChar.equals('G')) {
                totalMass += MASS_G;
            }
            else {
                totalMass += MASS_C;
            }
        }

        totalMass = Math.round(totalMass * 10.0) /10.0;

        return totalMass;
    }

    //returns count of occurrence of nucleotide 'c' in DNA sequence
    public int nucleotideCount(char c) {
        return 0;
    }

    //returns a Set that contains all the distinct codons in DNA sequence;
    public HashSet codonSet() {
        return null;
    }

    public String mutateCodon(String originalCodon, String newCodon) {
        return seqClean;
    }

    //returns mass percentage of CG
    public double percentCG() {
        double percent;
        double massCG = 0;
        for (int i = 0; i < seqCleanLength; i++) {
            Character currentChar = seqClean.charAt(i);
            if (currentChar.equals('C')) {
                massCG += MASS_C;
            }
            else if (currentChar.equals('G')) {
                massCG += MASS_G;
            }
        }

        massCG = Math.round(massCG * 10.0) / 10.0;
        percent = massCG / this.totalMass() * 100.0;
        return percent;
    }

    // returns original sequence of DNA as String
    public String sequence() {
        return this.seqOriginal;
    }

    /*
     Removes junk characters in sequence
     @param seq sequence to clean
     @return result sequence with junk characters removed
    */
    private String removeJunk(String seq) {
        String result = "";
        for (int i = 0; i < seq.length(); i++) {
            Character currentChar = seq.charAt(i);
            if (currentChar.equals('A') || currentChar.equals('T') || currentChar.equals('C') || currentChar.equals('G')) {
                result = result + currentChar;
            }
        }
        return result;
    }

    //checks if sequence is valid
    private boolean validSeq() {
        if (seqCleanLength % 3 == 0) {
            return true;
        }
        else{
            return false;
        }
    }

}