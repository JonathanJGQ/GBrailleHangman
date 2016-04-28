package com.gbraille.forca;

import java.util.ArrayList;

import com.gbraille.forca.DifficultyClass.DifficultyLevel;
import com.gbraille.forca.LanguageClass.TipoLanguage;
import com.gbraille.forca.TipoClass.TipoLevel;

public class MainFunctions {
    public static int selectedOption = 2; // 1 = SIM, 2 = NAO
    
    /* get the answers from database */
    public static int questionId;
    public static String question;
    public static String answer;
    public static String missingChar;    
    public static int missingCharPos;
    public static int selectedAnswerLength;
    public static ArrayList<String> answerCharList = new ArrayList<String>();
    
    public static int score;
    
    public static int dificultyLevel = DifficultyLevel.FACIL.getValue();
    
    public static int tipoLevel = TipoLevel.ORTOGRAFICO.getValue();
    
    public static int tipolingua = TipoLanguage.PT.getValue();
    
}