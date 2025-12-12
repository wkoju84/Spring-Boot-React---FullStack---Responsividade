package br.com.william.planner.controllers.converters;

import br.com.william.planner.controllers.dto.RecordDTO;
import br.com.william.planner.model.Record;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecordDTOConverter {

    public RecordDTO convert(Record record){

        return Optional.ofNullable(record)
                .map(source -> new RecordDTO(
                        source.getId(),
                        source.getService(),
                        source.getCustomer(),
                        source.getLocation(),
                        source.getDateTime(),
                        source.isDone(),
                        source.isCanceled()
                ))
                .orElse(null);
    }

    public Record convert(RecordDTO dto){

        return Optional.ofNullable(dto)
                .map(source -> Record.builder()
                        .id(source.id())
                        .service(source.service())
                        .customer(source.customer())
                        .location(source.location())
                        .dateTime(source.dateTime())
                        .done(source.done())
                        .canceled(source.canceled())
                        .build()
                )
                .orElse(null);
    }
}
