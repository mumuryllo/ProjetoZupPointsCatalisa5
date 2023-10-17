
const token = localStorage.getItem('token');
const userId = localStorage.getItem('id')

fetch('http://localhost:8080/certificados', {
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
    const certificado1Collapse = document.querySelector('#certificado1Collapse .accordion-body');

    certificado1Collapse.innerHTML = `
      <p><strong>Nome:</strong> ${data.nome}</p>
      <p><strong>Link:</strong> <a href="${data.link}">${data.link}</a></p>
      <p><strong>Número de Credencial:</strong> ${data.numero_credencial}</p>
      <p><strong>Tipo de Certificado:</strong> ${data.tipo_certificado}</p>
    `;
  })
  .catch(error => {
    console.error('Ocorreu um erro:', error);
  });