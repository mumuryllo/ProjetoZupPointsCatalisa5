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
                `;
            });
        }

        renderCertificados(certificados);
    })
    .catch(error => {
        console.error('Ocorreu um erro:', error);
    });
});