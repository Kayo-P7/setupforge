package com.setupforge.setupforge_backend.setup.mapper;

import com.setupforge.setupforge_backend.setup.dto.setupDTO.request.SetupRequest;
import com.setupforge.setupforge_backend.setup.dto.setupDTO.response.SetupResponse;
import com.setupforge.setupforge_backend.setup.model.Setup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class SetupMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    public abstract Setup toSetup(SetupRequest setupRequest);

    public abstract SetupResponse toSetupResponse(Setup setup);
}

