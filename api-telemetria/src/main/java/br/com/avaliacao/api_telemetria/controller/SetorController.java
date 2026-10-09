package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
 * manipulação de setores
 *
 */

@Tag(name = "Controller de setores", description = "Endpoints destinados aos setores")
@RequestMapping("/v1/api/setores")
public class SetorController {

    SetorService service;

    public SetorController(SetorService service)
    {
        this.service = service;
    }

    /**
     * O método usa o JSON enviado pelo usuário para
     * registrar um novo setor ao banco de dados.
     *
     * @param request, JSON enviado pelo usuário
     * @throws
     * @return DTO com os dados do setor que acabou de ser registrado no banco
     */

    @Operation(summary = "Registra um setor", description = "oi")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "O setor foi criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O JSON enviado pelo usuário não é válido")
    })
    @PostMapping()
    public ResponseEntity<SetorResponseDTO> criar(@RequestBody (required = true) SetorRequestDTO request)
    {
        SetorResponseDTO novoSetor = service.criar(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoSetor.id())
                .toUri();

        return ResponseEntity.created(location).body(novoSetor);
    }

    /**
     * O método lista todos os setores registrados no sistema
     *
     * @param pageable
     * @throws
     * @return
     */
    public  void listarTodos(@Parameter @PageableDefault(page = 0, size = 10) Pageable pageable){

    }

    public void buscarPorId(){
    }


    public void atualizar(){

    }
}
