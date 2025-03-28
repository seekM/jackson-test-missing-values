package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class App {

  public static void main(String[] args) throws JsonProcessingException {

    var mapper = new XmlMapper().enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);

    // Desired output:
    //   Bar[str=s, elements=[e]]
    // -> works
    var bar = mapper.readValue("<Bar><str>s</str><element>e</element></Bar>", Bar.class);
    System.out.println(bar);

    // Desired:
    //   MismatchedInputException exception:
    //   "Missing creator property 'str' (index 0);
    //   `DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES` enabled"
    // -> works
    bar = mapper.readValue("<Bar><element>e</element></Bar>", Bar.class);
    System.out.println(bar);

    // Desired output:
    //   Bar[str=s, elements=[]]
    // Actual:
    //   MismatchedInputException exception:
    //   Missing creator property 'element' (index 1);
    //   `DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES` enabled
    bar = mapper.readValue("<Bar><str>s</str></Bar>", Bar.class);
    System.out.println(bar);
  }
}