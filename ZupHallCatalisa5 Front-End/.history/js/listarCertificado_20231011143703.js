document.addEventListener('DOMContentLoaded', function () {
  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('id');
  const certificadosAccordion = document.querySelector('#certificadosAccordion');

  fetch(`http://localhost:8080/certificados/${userId}`, {
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
      certificadosAccordion.innerHTML = ''; // Limpa o conteúdo existente

      certificados.forEach(certificado => {
        if (certificado.colaborador_id === userId) {
          const certificadoItem = document.createElement('div');
          certificadoItem.className = 'accordion-item';

          certificadoItem.innerHTML = `
            <h2 class="accordion-header" id="certificado${certificado.id}Heading">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#certificado${certificado.id}Collapse" aria-expanded="true" aria-controls="certificado${certificado.id}Collapse">
                Certificado ${certificado.id}
              </button>
            </h2>
            <div id="certificado${certificado.id}Collapse" class="accordion-collapse collapse" aria-labelledby="certificado${certificado.id}Heading" data-bs-parent="#certificadosAccordion">
              <div class="accordion-body">
                <p><strong>Nome:</strong> ${certificado.nome}</p>
                <p><strong>Link:</strong> <a href="${certificado.link}">${certificado.link}</a></p>
                <p><strong>Número de Credencial:</strong> ${certificado.numero_credencial}</p>
                <p><strong>Tipo de Certificado:</strong> ${certificado.tipo_certificado}</p>
                <p><strong>Validação Certificado:</strong> ${certificado.certificado_valido}</p>
              </div>
            </div>
          `;

          certificadosAccordion.appendChild(certificadoItem);
        }
      });
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
});