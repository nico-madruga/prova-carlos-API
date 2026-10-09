package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.MotorStatusUpdateDTO;
import br.com.avaliacao.api_telemetria.service.MotorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
 * manipulação de motores
 *
 */


@Tag(name = "Controller de motores", description = "Endpoints destinados aos motores")
@RequestMapping("/v1/api/motor")
public class MotorController {

    MotorService service;

    public MotorController(MotorService service)
    {
        this.service = service;
    }

    /**
     * O método usa o JSON enviado pelo usuário para
     * registrar um novo motor ao banco de dados.
     *
     * @param request, JSON enviado pelo usuário
     * @throws
     * @return DTO com os dados do motor que acabou de ser registrado no banco
     */

    @Operation(summary = "Registra um motor", description = "oi")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "O motor foi criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O JSON enviado pelo usuário não é válido")
    })
    @PostMapping
    public ResponseEntity< MotorResponseDTO> criar(@RequestBody (required = true) MotorRequestDTO request)
    {
        MotorResponseDTO novoMotor = service.criar(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoMotor.id())
                .toUri();

        return ResponseEntity.created(location).body(novoMotor);
    }


    public void listarTodos(@Parameter @PageableDefault(page = 0, size = 10) Pageable pageable){

    }

    /**
     * Busca o motor no banco de dados pelo ID fornecido
     *
     * @param id do motor a ser buscado
     * @throws
     * @return DTO com os dados do motor que foi solicitado
     */

    @Operation(summary = "Busca motor por ID", description = "Utiliza o ID fornecido para buscar um motor")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "O motor foi encontrado"),
            @ApiResponse( responseCode = "404", description = "O motor com o ID fornecido não foi encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MotorResponseDTO> buscarPorId(
            @Parameter(
                    name = "id",
                    description = "ID do alerta a ser atualizado",
                    in = ParameterIn.PATH)
            @PathVariable Integer id)
    {
        MotorResponseDTO motorBuscado = service.buscarPorId(id);

        return ResponseEntity.ok().body(motorBuscado);
    }


    public void buscarPorCodigoAtivo() {
    }


    public  void atualizar() {

    }


    public void atualizarStatus(){

    }
}
