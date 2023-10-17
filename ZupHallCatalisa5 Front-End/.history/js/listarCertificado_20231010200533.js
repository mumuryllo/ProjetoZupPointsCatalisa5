const token = localStorage.getItem('token');
const userId = localStorage.getItem('id');

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
  .then(data => {
    const certificadosAccordion = document.querySelector('#certificadosAccordion');

    const certificadoItem = document.createElement('div');
    certificadoItem.className = 'accordion-item';

    certificadoItem.innerHTML = `
      <h2 class="accordion-header" id="certificadoHeading">
        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#certificadoCollapse" aria-expanded="true" aria-controls="certificadoCollapse">
          Certificado
        </button>
      </h2>
      <div id="certificadoCollapse" class="accordion-collapse collapse show" aria-labelledby="certificadoHeading" data-bs-parent="#certificadosAccordion">
        <div class="accordion-body">
          <p><strong>Nome:</strong> ${data.nome}</p>
          <p><strong>Link:</strong> <a href="${data.link}">${data.link}</a></p>
          <p><strong>Número de Credencial:</strong> ${data.numero_credencial}</p>
          <p><strong>Tipo de Certificado:</strong> ${data.tipo_certificado}</p>
        </div>
      </div>
    `;

    certificadosAccordion.appendChild(certificadoItem);
  })
  .catch(error => {
    console.error('Ocorreu um erro:', error);
  });