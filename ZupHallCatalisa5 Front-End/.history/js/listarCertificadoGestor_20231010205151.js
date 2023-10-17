document.addEventListener('DOMContentLoaded', function () {
    const certificadosTable = document.getElementById('certificadosTable');

    fetch('http://seuservidor/certificados', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer seuToken' // Lembre-se de substituir com seu token real
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
        // Função para criar linhas na tabela com os certificados
        function renderCertificados(certificados) {
            certificados.forEach(certificado => {
                const row = certificadosTable.insertRow();
                row.innerHTML = `
                    <td>${certificado.nome}</td>
                    <td>${certificado.link}</td>
                    <td>${certificado.numero_credencial}</td>
                    <td>${certificado.tipoCertificado}</td>
                `;
            });
        }

        // Renderiza os certificados na tabela
        renderCertificados(certificados);
    })
    .catch(error => {
        console.error('Ocorreu um erro:', error);
    });
});