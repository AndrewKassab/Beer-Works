package andrewkassab.spring.spring_6_rest_mvc.service;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
