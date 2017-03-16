package com.finder.algorithm;

/**
 * Created by Deep on 2017-03-16.
 * Gene is a gene for the DNA and it holds a character
 */
public class Gene
{
    // global fields
    private Random r;
    private char character;
    private final static char [] allChars = new char[getASCII().length];

    // constructor creates a Gene based on character provided
    public Gene(char character)
    {
        this.r = new Random();
        this.character = character;
    }

    // constructor creates a Gene based on a random character
    public Gene()
    {
        this.r = new Random();
        this.character = allChars[r.produceInt(0, (allChars.length -1))];
    }

    // initialize() initializes characters in genes
    public static void initialize()
    {
        getChars();
    }

    // getChars() uses getASCII to create an array of all the characters needed
    private static void getChars()
    {
        int [] ASCIINums = getASCII();

        for (int i = 0; i < allChars.length; i++)
            allChars[i] = (char) ASCIINums[i];
    }

    // getASCII() returns an array of all the ASCII values needed
    private static int[] getASCII()
    {
        // numbers for upper cases
        int startNumUpperCase = 65;
        int endNumUpperCase = 90;

        // numbers for lower cases
        int startNumLowerCase = 97;
        int endNumLowerCase = 122;

        // numbers for other characters used in english
        int [] otherNums = {32, 33, 34, 37, 38, 39, 40, 41, 44, 45, 46, 47, 58, 63, 64};

        int totalNums = (endNumUpperCase - startNumUpperCase + 1) +
                (endNumLowerCase - startNumLowerCase + 1) +
                otherNums.length;

        // array to store the numbers of all the used characters in english
        int [] characterNum = new int[totalNums];
        int index = 0;

        // store numbers for upper cases
        for (int i = startNumUpperCase; i <= endNumUpperCase; i++)
        {
            characterNum[index] = i;
            index++;
        }

        // store numbers for lower cases
        for (int i = startNumLowerCase; i <= endNumLowerCase; i++)
        {
            characterNum[index] = i;
            index++;
        }

        // store other characters
        for (int i = 0; i < otherNums.length; i++)
        {
            characterNum[index] = otherNums[i];
            index++;
        }

        return characterNum;
    }

    // getAllChars() returns all the characters that are being used
    public static char[] getAllChars()
    {
        return allChars;
    }

    // randomChar() returns a random valid character
    public static char randomChar()
    {
        Random r = new Random();

        return getAllChars()[r.produceInt(0, (getAllChars().length) - 1)];
    }

    // getChar() get the character in the gene
    public char getChar()
    {
        return character;
    }

    // setChar() set the character in the gene
    public void setChar(char character)
    {
        this.character = character;
    }
}