package dna;
import java.util.HashSet;

public class DNA {
    private String seqOriginal;
    private String seqClean;
    private int seqCleanLength;
    private final double MASS_A = 135.128;
    private final double MASS_C = 111.103;
    private final double MASS_G = 151.128;
    private final double MASS_T = 125.107;
    private final double MASS_JUNK = 100.000;
    private final String[] validEnd = {"TAA", "TAG", "TGA"};

    public DNA(String seq) {
        seqOriginal = seq;
        seqClean = removeJunk(seqOriginal);
        seqCleanLength = seqClean.length();

        if(!validSeq(seqClean)) {
            throw new IllegalArgumentException("Invalid DNA sequence");
        }
    }

    //@return true if DNA sequence is a protein, otherwise returns false
    public boolean isProtein() {

        if (seqClean.startsWith("ATG")) {
            if (seqCleanLength >= 5) {
                if (percentCG() >= 30.0) {
                    for (String codon : validEnd) {
                        if (seqClean.endsWith(codon)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //@return value of the mass of the DNA sequence, rounded to first decimal
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

    /*
     @param c a valid nucleotide
     @return count of occurrence of a nucleotide in DNA sequence
    */
    public int nucleotideCount(char c) {
        int count = 0;

        if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
            return count;
        }

        for (int i = 0; i < seqCleanLength; i++) {
            char currentChar = seqClean.charAt(i);
            if (currentChar == c) {
                count++;
            }
        }

        return count;
    }

    //@return a Set that contains all the distinct codons in DNA sequence
    public HashSet codonSet() {

        HashSet<String> codons = new HashSet<String>();

        for (int i = 0; i < seqCleanLength; i += 3) {
            codons.add(seqClean.substring(i, i+3));
        }

        return codons;
    }

    /*
     Changes all instances of a codon in the sequence to another codon
     @param codonOrg the String of the codon you want to change
     @param codonNew the String to change the old codon into
           ^^ must be valid codons
     */
    public void mutateCodon(String codonOrg, String codonNew) {

        String seqNew = "";

        for (int i = 0; i < seqCleanLength; i += 3) {
            if (seqClean.substring(i, i+3).equals(codonOrg)) {
                seqNew += codonNew;
            }
            else {
                seqNew += seqClean.substring(i, i+3);
            }
        }

        String validCodon = removeJunk(codonNew);
        if (validSeq(validCodon)) {
            this.seqOriginal = seqNew;
        }
    }

    //@return mass percentage of CG
    public double percentCG() {
        double percent;
        double massCG = 0;

        massCG = nucleotideCount('C') * MASS_C + nucleotideCount('G') * MASS_G;

        massCG = Math.round(massCG * 10.0) / 10.0;
        percent = massCG / this.totalMass() * 100.0;

        return percent;
    }

    //@return original sequence of DNA
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
            char currentChar = seq.charAt(i);
            if (currentChar == 'A' || currentChar == 'T' || currentChar == 'C' || currentChar =='G') {
                result += currentChar;
            }
        }

        return result;
    }

    /*
     Checks to see if the DNA sequence is valid
     @param seq a sequence of DNA
     @return true if sequence is valid and false otherwise
    */
    private boolean validSeq(String seq) {

        if (seq.length() % 3 == 0) {
            return true;
        }
        else{
            return false;
        }

    }
}