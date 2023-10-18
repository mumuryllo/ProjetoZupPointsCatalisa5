const token = localStorage.getItem('token');
const userId = localStorage.getItem('id');

fetch(`http://localhost:8080/certificados/${userId}`, {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  },
})
  .then(response => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Falha na requisição');
    }
  })
  .then(data => {
    // Verifique se a resposta contém dados de certificados
    if (data && Array.isArray(data)) {
      const certificadosAccordion = document.querySelector('#certificadosAccordion');

      data.forEach(certificado => {
        const newCertificadoItem = document.createElement('div');
        newCertificadoItem.className = 'accordion-item';

        newCertificadoItem.innerHTML = `
          <h2 class="accordion-header">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#certificado${certificado.id}Collapse">
              ${certificado.nome}
            </button>
          </h2>
          <div id="certificado${certificado.id}Collapse" class="accordion-collapse collapse" data-bs-parent="#certificadosAccordion">
            <div class="accordion-body">
              <p><strong>Nome:</strong> ${certificado.nome}</p>
              <p><strong>Link:</strong> <a href="${certificado.link}">${certificado.link}</a></p>
              <p><strong>Número de Credencial:</strong> ${certificado.numero_credencial}</p>
              <p><strong>Tipo de Certificado:</strong> ${certificado.tipo_certificado}</p>
              <p><strong>Validação Certificado:</strong> ${certificado.certificado_valido}</p>
            </div>
          </div>
        `;

        certificadosAccordion.appendChild(newCertificadoItem);
      });
    } else {
      console.error('A resposta da API não contém dados de certificados ou a estrutura não corresponde ao esperado.');
    }
  })
  .catch(error => {
    console.error('Ocorreu um erro:', error);
  });