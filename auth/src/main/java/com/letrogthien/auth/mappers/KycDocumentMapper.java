package com.letrogthien.auth.mappers;

import com.letrogthien.auth.dtos.KycDocumentDto;
import com.letrogthien.auth.entities.KycDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface KycDocumentMapper {
    @Mappings({@Mapping(
            source = "user.id",
            target = "userId"
    ), @Mapping(
            source = "reviewer.id",
            target = "reviewerId"
    )})
    KycDocumentDto toDTO(KycDocument kycDocument);

    List<KycDocumentDto> toDTOs(List<KycDocument> kycDocuments);
}
