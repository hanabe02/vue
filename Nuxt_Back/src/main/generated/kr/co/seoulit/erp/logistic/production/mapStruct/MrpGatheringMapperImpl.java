package kr.co.seoulit.erp.logistic.production.mapStruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import kr.co.seoulit.erp.logistic.production.domain.MrpGathering;
import kr.co.seoulit.erp.logistic.production.domain.MrpGatheringDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-05T20:59:55+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.20.1 (Amazon.com Inc.)"
)
@Component
public class MrpGatheringMapperImpl implements MrpGatheringMapper {

    @Override
    public MrpGatheringDTO toDto(MrpGathering mrpGathering) {
        if ( mrpGathering == null ) {
            return null;
        }

        MrpGatheringDTO mrpGatheringDTO = new MrpGatheringDTO();

        mrpGatheringDTO.setMrpGatheringSeq( mrpGathering.getMrpGatheringSeq() );
        mrpGatheringDTO.setMrpGatheringNo( mrpGathering.getMrpGatheringNo() );
        mrpGatheringDTO.setItemCode( mrpGathering.getItemCode() );
        mrpGatheringDTO.setItemName( mrpGathering.getItemName() );
        mrpGatheringDTO.setClaimDate( mrpGathering.getClaimDate() );
        mrpGatheringDTO.setDueDate( mrpGathering.getDueDate() );
        mrpGatheringDTO.setOrderOrProductionStatus( mrpGathering.getOrderOrProductionStatus() );
        mrpGatheringDTO.setNecessaryAmount( mrpGathering.getNecessaryAmount() );

        return mrpGatheringDTO;
    }

    @Override
    public MrpGathering toEntity(MrpGatheringDTO mrpGatheringDTO) {
        if ( mrpGatheringDTO == null ) {
            return null;
        }

        MrpGathering mrpGathering = new MrpGathering();

        mrpGathering.setMrpGatheringNo( mrpGatheringDTO.getMrpGatheringNo() );
        mrpGathering.setOrderOrProductionStatus( mrpGatheringDTO.getOrderOrProductionStatus() );
        mrpGathering.setItemCode( mrpGatheringDTO.getItemCode() );
        mrpGathering.setItemName( mrpGatheringDTO.getItemName() );
        mrpGathering.setClaimDate( mrpGatheringDTO.getClaimDate() );
        mrpGathering.setDueDate( mrpGatheringDTO.getDueDate() );
        mrpGathering.setNecessaryAmount( mrpGatheringDTO.getNecessaryAmount() );
        mrpGathering.setMrpGatheringSeq( mrpGatheringDTO.getMrpGatheringSeq() );

        return mrpGathering;
    }

    @Override
    public List<MrpGatheringDTO> toDtoList(List<MrpGathering> mrpGatheringList) {
        if ( mrpGatheringList == null ) {
            return null;
        }

        List<MrpGatheringDTO> list = new ArrayList<MrpGatheringDTO>( mrpGatheringList.size() );
        for ( MrpGathering mrpGathering : mrpGatheringList ) {
            list.add( toDto( mrpGathering ) );
        }

        return list;
    }
}
