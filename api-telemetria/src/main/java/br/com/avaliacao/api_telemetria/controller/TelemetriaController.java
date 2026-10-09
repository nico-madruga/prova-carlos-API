package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.TelemetriaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaResponseDTO;
import br.com.avaliacao.api_telemetria.service.TelemetriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Classe que faz o controle de endpoints direcionados a
 * manipulação de telemetria
 *
 */

@Tag(name = "Controller de telemetria", description = "Endpoints destinados a telemetria")
@RequestMapping("/v1/api/telemetrias")
public class TelemetriaController {

    TelemetriaService service;

    public TelemetriaController(TelemetriaService service)
    {
        this.service = service;
    }


    public void listarTodos(@Parameter @PageableDefault(page = 0, size = 10) Pageable pageable){

    }

    public void buscarPorId(){
    }


    public void listarPorMotor(){

    }

    /**
     * O método usa o JSON enviado pelo usuário para
     * registrar uma nova telemetria ao banco de dados.
     *
     * @param request, JSON enviado pelo usuário
     * @throws
     * @return DTO com os dados da telemetria que acabou de ser registrado no banco
     */

    @Operation(summary = "Registra uma telemetria", description = "oi")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "A telemetria foi criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "O JSON enviado pelo usuário não é válido")
    })
    @PostMapping()
    public ResponseEntity<TelemetriaResponseDTO> registrar(@RequestBody(required = true) TelemetriaRequestDTO request)
    {
        TelemetriaResponseDTO novaTelemetria = service.registrar(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaTelemetria.id())
                .toUri();

        return ResponseEntity.created(location).body(novaTelemetria);
    }

}
