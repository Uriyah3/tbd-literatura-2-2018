package com.tbd.elasticsearch.moduloSentimentAnalysis;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentimentAnalysisController {
	@Autowired
	private Classifier classifier;

	@RequestMapping("/classify")
    public HashMap<String,Double> classify(@RequestParam(value="text") String text) {

		HashMap<String,Double> datos=this.classifier.classify(text);
		Iterator<Double> lista=datos.values().iterator();

		Double negative=lista.next();
		Double positive=lista.next();
		//System.out.println(negative);
		//System.out.println(positive);

        return this.classifier.classify(text);
    }


	public Classifier getClassifier() {
		return classifier;
	}
	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}
	
}
