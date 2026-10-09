package br.com.avaliacao.api_telemetria.controller;

//implementar teus Parameter diferente depois, tipo in, required


import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResolucaoDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.service.AlertaMotorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
 * manipulação de alertas
 *
 */

@Tag(name = "Controller de alertas motores", description = "Endpoints destinados aos alertas")
@RequestMapping("/v1/api/alertas-motores")
public class AlertaMotorController {

    AlertaMotorService service;

    public AlertaMotorController(AlertaMotorService service)
    {
        this.service = service;
    }

    /**
     * O método usa o JSON enviado pelo usuário para
     * registrar um novo alerta ao banco de dados.
     *
     * @param request, JSON enviado pelo usuário
     * @throws
     * @return DTO com os dados do alerta que acabou de ser registrado no banco
     */

    @Operation(summary = "Registra um alerta", description = "oi")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "O alerta foi criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O JSON enviado pelo usuário não é válido")
    })
    @PostMapping()
    // Criar um novo alerta para um motor.
    public ResponseEntity<AlertaResponseDTO> criar(@RequestBody (required = true) AlertaRequestDTO request)
    {
        AlertaResponseDTO novoAlerta = service.criar(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoAlerta.id())
                .toUri();

        return ResponseEntity.created(location).body(novoAlerta);
    }


    //Listar alertas com paginação.
    public void listarTodos(@Parameter @PageableDefault(page = 0, size = 10) Pageable pageable)
    {

    }

    //Buscar histórico de alertas de um motor específico.
    public void buscarPorMotor()
    {

    }

    /**
     * Busca o alerta no banco de dados pelo ID fornecido
     * e atualiza seu status de resolução
     *
     * @param id do alerta a ser atualizado
     * @throws
     * @return DTO com os dados do alerta que acabou de ser atualizado
     *
     */

    @Operation(summary = "Atualiza status de resolução", description = "Utiliza o ID fornecido e o JSON enviado pelo usuário para atualizar o status de resolução de um alerta")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "O alerta foi atualizado com sucesso"),
            @ApiResponse( responseCode = "400", description = "O JSON enviado pelo usuário não é válido", content = @Content),
            @ApiResponse( responseCode = "404", description = "O alerta com o ID fornecido não foi encontrado", content = @Content)
    })
    @PatchMapping("/{id}")
    //Atualizar o status de resolução de um alerta (ex: marcar como resolvido/não resolvido).
    public ResponseEntity<AlertaResponseDTO> atualizarStatusResolucao(
            @Parameter(
                name = "id",
                description = "ID do alerta a ser atualizado",
                in = ParameterIn.PATH)
            @PathVariable Integer id,
            @RequestBody (required = true) AlertaResolucaoDTO request)
    {
        AlertaResponseDTO alertaAtualizado = service.atualizarStatusResolucao(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(alertaAtualizado);
    }
}
