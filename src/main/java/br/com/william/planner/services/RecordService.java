package br.com.william.planner.services;

import br.com.william.planner.exception.GenericException;
import br.com.william.planner.model.Record;
import br.com.william.planner.repositories.RecordCustomRepository;
import br.com.william.planner.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecordService {

    private final RecordRepository repository;
    private final RecordCustomRepository customRepository;

    private static final String SAVE_ERROR = "Error during save records";
    private static final String FIND_ERROR = "Error during find records";

    public Record save(Record record){
        log.info("Saving record. Service: {}, Customer: {}, DateTime: {}",
                record.getService(), record.getCustomer(), record.getDateTime());
        try {

            return repository.save(record);
        }
        catch (Exception e){
            log.error(SAVE_ERROR, e);
            throw new GenericException(SAVE_ERROR);
        }
    }

    public Page<Record> findPaginated(String service, String customer, String location, LocalDate initialDate,
                                      LocalDate finalDate, Boolean canceled, Boolean done, int page, int size){

        log.info("Finding record. Service: {}, Customer: {}, InitialDate: {}, FinalDate: {}",
                service, customer, initialDate, finalDate);
        try {

            return customRepository.findPaginated(service, customer, location, initialDate,
                    finalDate, canceled, done, page, size);
        } catch (Exception e) {
            log.error(FIND_ERROR, e);
            throw new GenericException(FIND_ERROR);
        }
    }
}
