document.addEventListener('DOMContentLoaded', function () {
  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('id');
  const certificadosAccordion = document.querySelector('#certificadosAccordion');

  fetch(`http://localhost:8080/certificados`, {
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

          // Resto do seu código para criar os elementos do certificado

          certificadosAccordion.appendChild(certificadoItem);
        }
      });
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
});