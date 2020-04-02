package pl.edu.agh.ki.covid19tablet.pdfgenetator.converters;

import java.util.ArrayList;
import java.util.List;

public class BooleanConverter {

    public List<ConvertedBoolean> convert(List<Boolean> booleanList) {
        ArrayList<ConvertedBoolean> convertedList = new ArrayList<>();

        for(Boolean bool : booleanList) {
            if (bool) convertedList.add(ConvertedBoolean.TAK);
            else convertedList.add(ConvertedBoolean.NIE);
        }

        return convertedList;
    }
}
