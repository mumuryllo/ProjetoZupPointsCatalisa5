document.addEventListener('DOMContentLoaded', function () {
    const certificadosTable = document.getElementById('certificadosTable');
    const token = localStorage.getItem('token')
    fetch('http://localhost:8080/certificados', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Falha na requisição');
        }
    })
    .then(certificados => {
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
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#validarModal${certificado.id}">
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
                            <button type="button" class="btn btn-primary" onclick="atualizarValidacao(${certificado.id})">Salvar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.body.appendChild(modal);
    });
}

// ... seu código anterior ...

function atualizarValidacao(certificadoId) {
    const select = document.getElementById(`validacaoSelect${certificadoId}`);
    const novaValidacao = select.value;
    
    const token = localStorage.getItem('token');

    fetch(`http://localhost:8080/certificados/${certificadoId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ certificado_valido: novaValidacao })
    })
    .then(response => {
        if (response.ok) {
            // Certificado validado com sucesso
            // Você pode adicionar uma mensagem de sucesso ou atualizar a lista de certificados, se necessário
            console.log('Certificado validado com sucesso.');
        } else {
            throw new Error('Falha na atualização da validação do certificado');
        }
    })
    .catch(error => {
        console.error('Ocorreu um erro:', error);
    });

    // Fechar o modal após a atualização, se desejar
    const modal = new bootstrap.Modal(document.getElementById(`validarModal${certificadoId}`));
    modal.hide();
}
            });
        }

        renderCertificados(certificados);
    })
    .catch(error => {
        console.error('Ocorreu um erro:', error);
    });
});