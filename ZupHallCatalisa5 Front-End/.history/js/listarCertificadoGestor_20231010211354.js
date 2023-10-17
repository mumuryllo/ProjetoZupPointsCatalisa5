function renderCertificados(certificados) {
    certificados.forEach(certificado => {
        const row = certificadosTable.insertRow();
        row.innerHTML = `
            <td>${certificado.nome}</td>
            <td>${certificado.link}</td>
            <td>${certificado.numero_credencial}</td>
            <td>${certificado.tipo_certificado}</td>
            <td>${certificado.colaborador.nome}</td>
            <td>${certificado.certificado_valido}</td>
            <td>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#validarModal${certificado.id}" data-atualizar-func="atualizarValidacao">
                    Validar
                </button>
            </td>
        `;

        // Adicionar o modal de validação
        const modal = document.createElement('div');
        modal.innerHTML = `
            <div class="modal fade" id="validarModal${certificado.id}" tabindex="-1" aria-labelledby="validarModalLabel${certificado.id}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="validarModalLabel${certificado.id}">Validar Certificado</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                        </div>
                        <div class="modal-body">
                            <label for="validacaoSelect">Selecione a Validação:</label>
                            <select class="form-select" id="validacaoSelect${certificado.id}">
                                <option value="VALIDO">VALIDO</option>
                                <option value="INVALIDO">INVALIDO</option>
                                <option value="PENDENTE">PENDENTE</option>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                            <button type="button" class="btn btn-primary" onclick="atualizarValidacao(${certificado.id}, this)">Salvar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.body.appendChild(modal);
    });
}

function atualizarValidacao(certificadoId, button) {
    const select = document.getElementById(`validacaoSelect${certificadoId}`);
    const novaValidacao = select.value;

    // Recuperar a função definida no atributo data
    const atualizarFunc = button.getAttribute('data-atualizar-func');
    // Executar a função
    window[`${atualizarFunc}`](certificadoId, novaValidacao);

    // Fechar o modal após a atualização
    const modal = new bootstrap.Modal(document.getElementById(`validarModal${certificadoId}`));
    modal.hide();
}