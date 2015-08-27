package com.github.habernal;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;


/**
 * @author Ivan Habernal
 */
public class NERTest
{
    public static void main(String[] args)
            throws Exception
    {
        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentLanguage("de");
        jCas.setDocumentText(
                "Die Fossillagerstätte Geiseltal befindet sich im ehemaligen Braunkohlerevier des Geiseltales südlich der Stadt Halle in Sachsen-Anhalt. Sie ist eine bedeutende Fundstelle heute ausgestorbener Pflanzen und Tiere aus der Zeit des Mittleren Eozäns vor 48 bis 41 Millionen Jahren. Im Geiseltal wurde nachweislich seit 1698 erstmals Kohle gefördert, die ersten Fossilien kamen aber erst Anfang des 20. Jahrhunderts eher zufällig zu Tage. Planmäßige wissenschaftliche Ausgrabungen begannen 1925 seitens der Martin-Luther-Universität Halle-Wittenberg. Unterbrochen durch den Zweiten Weltkrieg, können die Untersuchungen in zwei Forschungsphasen untergliedert werden. Aufgrund der zunehmenden Auskohlung der Rohstofflager kamen die Ausgrabungen Mitte der 1980er allmählich zum Erliegen und endeten endgültig zu Beginn des dritten Jahrtausends.");

        SimplePipeline.runPipeline(jCas,
                AnalysisEngineFactory.createEngineDescription(
                        BreakIteratorSegmenter.class
                ),
                AnalysisEngineFactory.createEngineDescription(
                        StanfordNamedEntityRecognizer.class
                ),
                AnalysisEngineFactory.createEngineDescription(
                        CasDumpWriter.class
                )
        );

        for (NamedEntity ne : JCasUtil.select(jCas, NamedEntity.class)) {
            System.out.println("Found NE: " + ne.getValue() + ", " + ne.getCoveredText());
        }
    }
}
