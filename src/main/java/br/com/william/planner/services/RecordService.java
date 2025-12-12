package br.com.william.planner.services;

import br.com.william.planner.model.Record;
import br.com.william.planner.repositories.RecordCustomRepository;
import br.com.william.planner.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository repository;
    private final RecordCustomRepository customRepository;

    public Record save(Record record){
        return repository.save(record);
    }

    public Page<Record> findPaginated(String service, String customer, String location, LocalDate initialDate,
                                      LocalDate finalDate, Boolean canceled, Boolean done, int page, int size){

        return customRepository.findPaginated(service, customer, location, initialDate,
                finalDate, canceled, done, page, size);
    }
}
