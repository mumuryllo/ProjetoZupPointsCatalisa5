const token = localStorage.getItem('token');
const userId = localStorage.getItem('id')

fetch(`http://localhost:8080/certificados/user/${userId}`, {
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
  .then(certificados => {
    // Certificados é uma lista de objetos, então você pode iterar sobre eles
    const certificadosAccordion = document.querySelector('#certificadosAccordion');
    
    certificados.forEach(certificado => {
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
          </div>
        </div>
      `;

      certificadosAccordion.appendChild(newCertificadoItem);
    });
  })
  .catch(error => {
    console.error('Ocorreu um erro:', error);
  });